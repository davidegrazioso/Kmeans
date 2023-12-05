package mining;
import java.io.Serializable;

import data.Data;
import data.Tuple;
import exception.OutOfRangeSampleSize;

/**
 * ClusterSet: rappresenta un insieme di cluster (determinati da k-means)
 */
public class ClusterSet implements Serializable {
    
    private Cluster C[ ];  
    private int i = 0;
    
    /**
     * Creo l'elenco di cluster che può contenere al massimo k elementi
     * @param k : numero di cluster da generare
     */
    ClusterSet(int k){

        C = new Cluster[k]; 

    }
    
    /**
     * Aggiunge un cluser all'insieme dei cluster
     * @param c : cluster da aggiungere all'insieme
     */
    public void add(Cluster c){

        C[i] = c;

        i++;
        
    }

    /**
     * Restituisce un cluster dato i
     * @param i : numero di indice di cluster
     * @return  cluster che viene restituito
     */
    public Cluster get(int i){
        return C[i];
    }

    /**
     * Vengono inizializzati i centroidi
     * @param data : insieme di transazioni
	 * @throws OutOfRangeSampleSize eccezione controllata da considerare qualora il numero k di cluster inserito 
 	 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili  dall'insieme di transazioni.
     */
    public void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        int centroidIndexes[];
        
       
            centroidIndexes = data.sampling(C.length);
       
            

            while(i<centroidIndexes.length) {

                Tuple centroidI=data.getItemSet(centroidIndexes[i]);
                add(new Cluster(centroidI));

            }
       
        
    }
    
    /**
     * Metodo che permette data una tupla di vedere a quale cluster é piú vicino
     * @param tuple : una tupla contenente la coppia nome attributo - valore
     * @return cluster di minor distanza
     */
    Cluster nearestCluster(Tuple tuple){
        double min = 100;
        Cluster cMinDis = new Cluster(tuple);
        for(int i=0; i< C.length;i++){
            if(min > tuple.getDistance(C[i].getCentroid())){
                cMinDis = C[i];
                min = tuple.getDistance(C[i].getCentroid());
            }
        }
        
        return cMinDis;
    }

    /**
     * Funzione che permette dato un id di ritornare 
     * il cluster corrente dato l'id se esiste quella transizione 
     * @param id :int che indica l'indice di una tupla per vedere 
     * se é presente in un cluster
     * @return  cluster corrente dato l'id se esiste 
     * quella transizione 
     */
    public Cluster currentCluster(int id){
            
        for(int i=0; i < C.length;i++){
                
            if(C[i].contain(id)){
                
            return C[i];
            }
        }

        return null;
    }

   
    /**
     * Aggiorna i centroidi tramite il metodo computeCentroid
     * @param data :  insieme di transazioni
     */
    public void updateCentroids(Data data){
        
        for(int i = 0; i < C.length ; i++){
            if(C[i].contain(i)){

                C[i].computeCentroid(data);
            }
        }
    }

    /**
     * Restituisce una string con tutti i clusterset
     * @return rappresentazione del clusterset su data in una stringa
     */
    public String toString(){
        String str="";
        for(int i=0;i<C.length;i++){
            System.out.println("i"+i);
            str += C[i].getCentroid();
        }
        return str;
    }
    
    /**
     * Restitituisce una stringa con cluster set e tutti i valori datti da data
     * @param data : insieme di transazioni
     * @return rappresentazione del clusterset su data in una stringa
     */
    public String toString(Data data){
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+=i+":"+C[i].toString(data)+"\n";
            }
        }
        return str;
    }
            
        

}   

