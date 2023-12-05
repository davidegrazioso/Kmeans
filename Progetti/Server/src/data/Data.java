package data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import database.DbAccess;
import database.Example;
import database.TableData;
import database.TableSchema;
import database.TableSchema.Column;
import database.TableData.QUERY_TYPE;
import exception.EmptySetException;
import exception.NoValueException;
import exception.OutOfRangeSampleSize;


/**
 * Data
 * classe che serve per modellare un insieme di transazioni
 */

public class Data {
// Le visibilità di classi , attributi e metodi devono essere decise dagli studenti	
	private List<Example> data =new ArrayList<Example>(); // una matrice nXm di tipo Object dove ogni riga modella una transazione
	private int numberOfExamples; // numero di transazioni nella tabella (numero di righe in data)
	private List<Attribute> attributeSet;// lista degli attributi in ciascuna tupla (schema della tabella di dati)
	private String table;


   /**
	* costruttore della classe
    * @throws SQLException un eccezione che restituisce errori da sql
	 */
	public Data(String table) throws SQLException{
		DbAccess db = new DbAccess();
		TableData tableData = new TableData(db);
		this.table = table; 
		//data

		
			try {
				data = tableData.getDistinctTransazioni(table);
			} catch (EmptySetException e) {
				
				e.printStackTrace();
			}
		
		
		// numberOfExamples
		
		numberOfExamples=data.size();	
		 
		
		//explanatory Set
		
		attributeSet = new LinkedList<Attribute>();

		//  avvalorare ciascune elemento di attributeSet con un oggetto della classe DiscreteAttribute 
		//che modella il corrispondente attributo (e.g. outlook, temperature,etc)
		

		
		TableSchema tableSchema = new TableSchema(db, table);
		
		for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
			Column c = tableSchema.getColumn(i);
			if (c.isNumber()){
				try {
					double minValue = ((Number) tableData.getAggregateColumnValue(table, c, QUERY_TYPE.MIN)).doubleValue();
					double maxValue = ((Number) tableData.getAggregateColumnValue(table, c, QUERY_TYPE.MAX)).doubleValue();

					attributeSet.add(new ContinuousAttribute(c.getColumnName(), i,minValue, maxValue));
					
				} catch (NoValueException e) {
					//Gestire gli errori
					e.printStackTrace();
				}
			}else{
				try {
					
					attributeSet.add(new DiscreteAttribute(c.getColumnName(), i,
					(TreeSet) tableData.getDistinctColumnValues(table, c)));
				} catch (NoValueException e) {
					//Gestire gli errori
					e.printStackTrace();
				}
			}
			
		}
	}
	
		
	/**
	 * restituisce il valore dell'attributo numberOfExamples
	 * @return numero di righe in data
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}

	/**
	 * restituisce il numero di valori presenti nell'array attributeSet
	 * @return  lunghezza dell'array attributeSet
	 */
	public int getNumberOfExplanatoryAttributes(){
		return attributeSet.size();
	}

	/**
	 * restituisce l'array attributeSet rappresentante l'elenco degli attributi nella tabella
	 * @return insieme degli attributi in data
	 */
	public List<Attribute> getAttributeSchema(){
		return attributeSet;	
	}

	/**
	 * restituisce il valore presente nella matrice Data nella posizione data dai parametri presi in input
	 * @param exampleIndex : indice di riga da consultare
	 * @param attributeIndex : indice di colonna da consultare
	 * @return elemento di data in nella riga indicizzata da exampleIndex e nella colonna indicizzata da attributeIndex
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		
		return data.get(exampleIndex).get(attributeIndex);

	}

	/**
	 *  crea una stringa in cui memorizza lo schema della tabella 
	 *  e le transazioni memorizzate in data, opportunamente enumerate.
	 *	Restituisce tale stringa
	 * @return stringa contenente l'intero stato dell'oggetto data
	 */
	public String toString(){
		
		String out ="";

		List<Attribute> schema = getAttributeSchema();

		for(int i = 0; i < getNumberOfExplanatoryAttributes(); i++){

			out += schema.get(i) +",";
			

		}

		out += "\n";

		for(int i = 0; i < getNumberOfExamples(); i++){

			out += (i+1) + ":";
			
			for(int j = 0; j < getNumberOfExplanatoryAttributes(); j++ ){
				
					out +=getAttributeValue(i,j) + ",";

			}

			out += "\n";

		}
	
		return out;
	}

	/**
	 * Crea e restituisce un oggetto di Tuple che modella 
     * come sequenza di coppie Attributo-valore la i-esima riga in data
	 * @param index : indice di riga della tabella della quale si vuole creare una tupla
	 * @return tupla creata partendo dalla riga di indice "index" di data
	 */
	public Tuple getItemSet(int index){ 

		Tuple tuple=new Tuple(attributeSet.size());

		for(int i=0;i<attributeSet.size();i++){
			//modificato, precedentemente explanatorySet.length
			if(attributeSet.get(i) instanceof DiscreteAttribute){
				tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet.get(i),(String)getAttributeValue(index, i)),i);
			}
			else if(attributeSet.get(i) instanceof ContinuousAttribute){
				Double value =((Number) getAttributeValue(index, i)).doubleValue();
				tuple.add(new ContinuousItem((ContinuousAttribute) attributeSet.get(i),value),i);
			}
			
		} 
			
		return tuple;

	}
	

	
	/**
	 * genera k centroidi casuali e distinti indicandone anche l'indice nella tabella
	 * 
	 * @param k : numero di centroidi che si desidera generare
	 * @return array contenente gli indici di riga dei centroidi scelti
	 * @throws OutofRangeSampleSize eccezione controllata da considerare qualora il numero k di cluster inserito 
 	 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili  dall'insieme di transazioni.
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize{
		if (k <= 0 || k >getNumberOfExamples() ) throw new OutOfRangeSampleSize("Numero di cluster non valido");
		int centroidIndexes[]=new int[k];
		//choose k random different centroids in data.
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i=0;i<k;i++){
			boolean found=false;
			int c;
			do{
				found=false;
				c=rand.nextInt(getNumberOfExamples()); //PASSO PRIMO, genero un centroide casuale decidendo un indice random tra 0 e 15
				
				// verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
				for(int j=0;j<i;j++){
					if(Compare(centroidIndexes[j],c)){
						found=true;
						break;
					}
				}			
			}while(found);
			centroidIndexes[i]=c; 
		}
		return centroidIndexes;
	}


	/**
	 * restituisce vero se le due righe di data contengono gli 
     * stessi valori, falso altrimenti
	 * @param i : indice della prima riga da confrontare di data
	 * @param j : indice della seconda riga da confrontare di data
	 * @return booleano che corrisponde a true se le due righe risultano uguali
	 */

	private boolean Compare(int i,int j){
		for(int a = 0; a < getNumberOfExplanatoryAttributes();a++){
		
			
			if (!(getAttributeValue(i,a).equals(getAttributeValue(j,a)))){
			 return false;
			}
		}
		
		return true;
	}

	/**
	 * Restituisce il risultato del ComputePrototype specifico per il tipo di attributo
	 * @param idList : insieme degli indici di riga
	 * @param attribute : attributo rispetto al quale calcolare il centroide
	 * @return risultato della funzione ComputePrototype chiamata su idList e l'attributo 
	 */
	public Object ComputePrototype(HashSet<Integer> idList, Attribute attribute){
		if (attribute instanceof DiscreteAttribute)
		{
			return ComputePrototype(idList,(DiscreteAttribute)attribute);  
		}
		else if (attribute instanceof ContinuousAttribute)
		{
			return ComputePrototype(idList,(ContinuousAttribute)attribute);  
		}
		return null;

	} 

	
	/**
	 * dato un attributo, trovare la media dei suoi valori nel arraylist data 
	 * @param idList
	 * @param attribute
	 * @return media dei valori dell'attributo nella tabella
	 */
	

	 private Double ComputePrototype(HashSet<Integer> idList, ContinuousAttribute attribute){
		double prototipo = 0.0;
		double value = 0.0;
		for (int i = 0; i < getNumberOfExamples();i++) {
			
			if(idList.contains(i)) {
				value = (((Number) getAttributeValue(i, attribute.getIndex())).doubleValue());
				prototipo += value;
			}
			
		}
		return prototipo/idList.size();
		
	 }


	/**
	 *  Determina il valore che occorre più frequentemente 
	 * per attribute nel sottoinsieme di dati individuato da idList.
	 * @param idList : insieme degli indici di riga di data appartenenti ad un cluster
	 * @param attribute : attributo discreto rispetto al quale calcolare il centroide
	 * @return centroide rispetto ad attribute
	 */
	private String  ComputePrototype(HashSet<Integer> idList, DiscreteAttribute attribute){
		String str = "" ;
		
		int max = 0;
		
		Iterator<String> iterator = attribute.iterator();
		

		while(iterator.hasNext()){
            String val = iterator.next();
            if(max < attribute.Frequency(this, idList, val)){
                max = attribute.Frequency(this, idList, val);
                str = val;
            }
        }
	
		
		return str;

	}

	
	
}
