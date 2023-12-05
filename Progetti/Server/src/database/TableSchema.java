package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TableSchema
 * modella lo schema di una tabella di un database relazionale
 */

public class TableSchema {

	private DbAccess db; // oggetto utile per gestire la connessione con un database

	/**
	 * Column
	 * inner class che definisce una specifica colonna dello schema di una tabella
	 */
	public class Column{
		private String name; // nome dell'attributo della colonna
		private String type; // dominio associato all'attributo

		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * Restituisce il valore dell'attributo name della colonna
		 * @return nome della colonna
		 */
		public String getColumnName(){
			return name;
		}
		/**
		 * Stabilisce se una data colonna rappresenta un attributo numerico 
		 * @return 0 se la colonna rappresenta un attributo numerico, 1 altrimenti
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		/**
		 * Restituisce il valore degli attributi della colonna sotto forma di stringa
		 * @return stringa contenente lo stato dell'oggetto
		 */
		public String toString(){
			return name+":"+type;
		}
	}

	List<Column> tableSchema = new ArrayList<Column>(); // contiene tutte le colonne dello schema della tabella
/**
 * costruttore della classe. crea uno schema della tabella esaminata estraendo le informazioni dal database
 * @param db : oggetto utile per stabilire la connessione col database
 * @param tableName : nome della tabella di cui ricavare lo schema
 * @throws SQLException un eccezione che restituisce errori da sql
 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		Connection con=db.getConnection();
		
		DatabaseMetaData meta = con.getMetaData();
	    ResultSet res = meta.getColumns(null, null, tableName, null);

	    while (res.next()) {
	        if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		tableSchema.add(new Column(
	        			res.getString("COLUMN_NAME"),
	        			mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        			);
	      }
	      res.close();
	
	
	    
	    }
	  
		/**
		 * Restituisce il numero di elementi dello schema della tabella
		 * @return numero di elementi dello schema della tabella
		 */
	
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
		
		/**
		 * Restituisce l'elemento dell'insieme di colonne alla posizione identificata da index
		 * @param index : indice di tableSchema del quale si vuole ottenere un elemento
		 * @return elemento dell'insieme di colonne in posizione identificata da index
		 */
		public Column getColumn(int index){
			return tableSchema.get(index);
		}

		
	}

		     


