package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer{
    private static int  PORT = 8080;
    /**
     * Costruttore di classe. Inizializza la porta ed invoca run()
     * @param port : numero di port su cui ospitare il server
	* @throws IOException considera un errore nell'interruzione in operazioni di i/o
    */

    public MultiServer(int port) throws IOException{
        PORT = port;
        run();
    }


    /**
     * istanzia un oggetto di tipo MultiServer.
     * @param args : argomenti passati da command line
     */
    public static void main(String[] args) {
        try {
            new MultiServer(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di richiesta di connessioni 
     * da parte del client. Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o
     */
    public void run() throws IOException{
        
        ServerSocket s = new ServerSocket(PORT);
        try {
            
            while(true){
                Socket socket = s.accept();;
                try {
                    new ServerOneClient(socket);
                } catch (Exception e) {
                    socket.close();
                }
            }
        }finally {
            s.close();
        }

    }
}
