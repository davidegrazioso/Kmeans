package mining;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Tuple;

/**
 * Cluster :
 * Classe che fa in modo di poter modellare un cluster
 */
public class Cluster implements Serializable {
	private Tuple centroid;

	private Set<Integer> clusteredData;

	/**
	* costruttore della classe
 	* @param centroid : oggetto di tipo Tupla che serve a creare il cluster
 	*/
	Cluster(Tuple centroid){
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
		
	}

	/** 
	 * Restituisce il centroide del cluster corrente
	 * @return centroide del cluster corrente
	 */	
	public Tuple getCentroid(){
		return centroid;
	}
	
	/**
	 * Assegna ad ogni occorrenza di item del centroide il suo valore più frequente all'interno del cluster
	 * @param data : insieme di transazioni su cui effettuare l'operazione
	 */
	public void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			
			centroid.get(i).update(data,(HashSet<Integer>) clusteredData);	 // MODIFICARE UPDATE
			
		}
		
	}
	
	
	/**
	 * restituisce true se la tupla sta cambiando il cluster
	 * @param id : indice di una riga nella matrice data
	 * @return clusteredData.add(id)
	 */
	public boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	/**
	 * verifica se una transazione è clusterizzata nell'array corrente
	 * @param id : indice di un cluster
	 * @return true se la transazzione è clusterizzata nell'array corrente, false altrimenti
	 */

	public boolean contain(int id){
		boolean result = false;

		for(Integer i : clusteredData) {
		  if( i == id) {
			result = true;
			break;
		  }
		}
	 
		return result;
	 
	}
	
	/**
	 * rimuove la tupla che ha cambiato cluster
	 * @param id : indice di una riga nella matrice data
	 */
	 public void removeTuple(int id){
		 clusteredData.remove(id);
		}
		
		/**
	 *  comprime il centroide del cluster in una stringa
	 * @return rappresentazione di stringa del centroide
	 */
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}
	

	/**
	 * comprime il cluster (compreso il suo centroide e le transazioni di Data facenti parti di esso) in una stringa
	 * @param data : insieme di transazioni
	 * @return rappresentazione del cluster su data in una stringa
	 * 
	 */
	public String toString(Data data){
		String str="Centroide=(";
		for(int i=0;i<centroid.getLength();i++){
			
			str+=centroid.get(i)+ " ";
			
		}
		str+=")\nExamples:\n";
		Object[] array=clusteredData.toArray();
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getAttributeValue((int) array[i], j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet((int)array[i]))+"\n";
			
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data, array);
		return str;
		
	}

}
