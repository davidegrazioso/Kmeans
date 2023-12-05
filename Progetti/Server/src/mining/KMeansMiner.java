package mining;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.Data;
import exception.OutOfRangeSampleSize;

/**
 * KMeansMiner:
 * Classe che permette l'uso dell'algoritmo kmeans
 */
public class KMeansMiner {
    
    private ClusterSet C;

    /**
     * Construttore di kmeansminer  
     * @param k : intero che indica il numero di cluster da creare
     */
    public KMeansMiner(int k){
    
        C = new ClusterSet(k);

    }
    /**
     * Construttore di kmeansminer se si effettua il caricamento da file
     * @param fileName : Stringa che indica il nome del file da cui si caricano i dati
     * @throws FileNotFoundException eccezzione quando dato un determinato path non si trova il file
     * @throws IOException considera un errore nell'interruzione in operazioni di i/o
     * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
     */
    public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        C=(ClusterSet)in.readObject();
        in.close();
       

    }

    /**
     * Metodo che permette di salvare i dati in un file
     * @param fileName : Stringa che indica il nome del file su cui si salvano i dati
     * @throws FileNotFoundException eccezzione quando dato un determinato path non si trova il file
     * @throws IOException considera un errore nell'interruzione in operazioni di i/o
     */
    public void Save(String fileName) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(C);
        out.close();
      

    }

    /**
     * Ritorna un clusterSet
     * @return un'istanza di clusterSet 
     */
    public ClusterSet getC(){
        return C;
    }

    /**
     * segue l’algoritmo k-means eseguendo i passi
     * @param data : insieme di transazioni
     * @return  numero di iterazioni eseguite
	 * @throws OutOfRangeSampleSize eccezione controllata da considerare qualora il numero k di cluster inserito 
 	 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili  dall'insieme di transazioni.
     */
    public int kmeans(Data data) throws OutOfRangeSampleSize{
        int numberOfIterations=0;
        //STEP 1

        C.initializeCentroids(data);
        boolean changedCluster=false;
        do{
            numberOfIterations++;
            //STEP 2
            changedCluster=false;
            for(int i=0;i<data.getNumberOfExamples();i++){

                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));

                Cluster oldCluster=C.currentCluster(i);

                boolean currentChange=nearestCluster.addData(i);

                if(currentChange){
                    changedCluster=true;
                }    
                //rimuovo la tupla dal vecchio cluster
                if(currentChange && oldCluster!=null){
                //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
                }    
            }
            //STEP 3
            C.updateCentroids(data);
        }while(changedCluster);
        return numberOfIterations;
    }
}
