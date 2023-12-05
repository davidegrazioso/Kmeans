package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Example
 * Classe che modella una singola transazione letta dal database
 * Implementa l'interfaccia Comparable
 */

public class Example implements Comparable<Example>{
	
	private List<Object> example=new ArrayList<Object>(); // lista contenente gli elementi di una singola transazione

	/**
	 * Permette di aggiungere un elemento alla transazione
	 * @param o : elemento da aggiungere alla transazione
	 */
	public void add(Object o){
		example.add(o);
	}
	
	/**
	 * Restituisce l'elemento di example in i-esima posizione
	 * @param i : indice di example dal quale si vuole ottenere un elemento
	 * @return elemento di example in i-esima posizone
	 */
	public Object get(int i){
		return example.get(i);
	}
	/**
	 * compara due occorrenze di example. Se le due occorrenze sono uguali restituisce 0, altrimenti restituisce il compareTo 
	 * dei primi due elementi diversi in posizioni corrispondenti dei due Example
	 * 
	 * @param ex : occorrenza di example da comparare con l'oggetto corrente
	 * @return 0 se i due Example sono uguali, altrimenti la comparazione tra i primi due elementi diversi dei due example
	 */
	public int compareTo(Example ex) {	
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	/**
	 * Restituisce il contenuto della lista example sotto forma di stringa
	 * @return stringa contenente gli elementi dell'example
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}
