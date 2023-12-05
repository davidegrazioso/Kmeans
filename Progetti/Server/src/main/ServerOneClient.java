package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;


import data.Data;
import exception.OutOfRangeSampleSize;
import mining.KMeansMiner;
/**
 * Classe che gestisce una singola richiesta da client.
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in; 
    private ObjectOutputStream out;
    private KMeansMiner kmeans;
    private Data data;
    private String trainingTableName = null;
 
    /**
     * Costruttore di classe. Inizializza gli attributi socket, in e out. Avvia il thread.
     * @param s : socket necessaria per avviare il singolo thread
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o
     */

    public ServerOneClient(Socket s) throws IOException{
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();    
    }

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client
     */
    public void run(){
        int decision = 0;
        String fileNameInput = "output/";
        String fileNameOutput = "output/";
        int k = 0;

        try {
            do {
                decision = (int) in.readObject();
               

                    if(decision == 0){
                        try {
                            trainingTableName = in.readObject().toString();
                            data = new Data(trainingTableName);
                            out.writeObject("OK");
                        } catch (ClassNotFoundException | IOException e) {
                            
                           out.writeObject("Errore 1: la tabella non e' valida o e' inaccessibile");
                        } catch (SQLException e) {
                           out.writeObject("Errore 1: la tabella non e' valida o e' inaccessibile");
                        }
                        
                    }
                        
                    if(decision == 1){
                        try {

                            k =(int) in.readObject();
                            kmeans = new KMeansMiner(k);
                            kmeans.kmeans(data);
                            out.writeObject("OK");
                            out.writeObject(kmeans.getC().toString(data));
                        } catch (ClassNotFoundException | IOException e) {
                        
                            out.writeObject("Errore 1: la tabella non e' valida o e' inaccessibile"); 
                        } catch (OutOfRangeSampleSize e) {
                            out.writeObject("Errore 2: il numero di cluster richiesto non e' valido");

                        }
                    }
                    
                    if (decision == 2) {
                        
                        try {
                                              
                            int iterazione = 1;
                            boolean fileTrovato = true;
                            String estensioneFile = ".dat";


                            while (fileTrovato) {
                                String nomeCompleto = fileNameOutput + trainingTableName + iterazione + estensioneFile;
                                File file = new File(nomeCompleto);
                                if (file.exists()) {
                                    iterazione++;
                                } else {
                                    kmeans.Save(nomeCompleto);
                                    System.out.println("File salvato con successo: " + nomeCompleto);
                                    fileTrovato = false;
                                }
                            }
                            out.writeObject("OK");
        
                        } catch (IOException e) {
                           out.writeObject("Errore 3: fallimento nel salvataggio dei cluster su file");
                        }
                    }
        
                    if (decision == 3) {
                        
                        try {
                            trainingTableName = in.readObject().toString();
                            k =(int) in.readObject();

                             String nomeCompleto = fileNameInput + trainingTableName + k + ".dat";

                            data = new Data(trainingTableName);

                            kmeans = new KMeansMiner(nomeCompleto);

                            out.writeObject("OK");

                            
                            out.writeObject(kmeans.getC().toString(data));
                            
                            k = 0;
                           
                        } catch (ClassNotFoundException | IOException e) {
                            
                            out.writeObject("Errore 1: la tabella non e' valida o e' inaccessibile");

                        } catch (SQLException e) {
                            out.writeObject("Errore 1: la tabella non e' valida o e' inaccessibile");
                        } 
                    }
        
                
                }while (true);   
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Un client é stato chiuso");
        }
       
    }
}

