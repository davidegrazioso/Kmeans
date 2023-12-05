package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

import utility.Keyboard;

/**
 * Classe main del client.
*/ 
public class MainTest {
	private ObjectOutputStream out; // stream con la risposta del server
	private ObjectInputStream in ; // stream con richieste del client
	
	
	
	/**
	* Costruttore della classe che inizializza il client
	* @param ip : string indirizzo ip del server
	* @param port : int porta alla quale il client si deve connettere
	* @throws IOException considera un errore nell'interruzione in operazioni di i/o
	*/
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	 // stream con richieste del client
	}
	
	/**
	 * Metodo che prevede la stampa a schermo sotto forma di menú e si chiede all'utente 
	 * di inserire la propria scelta
	 * @return intero che indica quale azione far compire al server
	 */
	private int menu(){
		int answer;
		System.out.println("Scegli una opzione");
		do{
			System.out.println("(1) Load Cluster");
			System.out.println("(2) Learn Data");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
		
	}
	
	/**
	 * Effettua delle chiamate al server passando alcuni elementi come il nome della tabella
	 * e il numero di iterata
	 * @return stringa contenente il kmeans tramite il server
	 * @throws SocketException considera se si ha un errore nell'accesso a un socket
	 * @throws ServerException considera se fallisce la connessione al server.
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o 
	 * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
	 */
	private String learningFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(3);
		//Nome della tabella + il numero di volta che é stato eseguito il file
		//ad esempio playtennis1  poi dalla seconda volta ci sará playtennis2 a disposizione e cosí via
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);

		System.out.print("Numero iterate:"); 
		int k=Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return (String)in.readObject();
		else {
			System.out.println(result);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new ServerException(result);

		}
	}

	/**
	 * Inizializza la tabella tramite il server.
	 * @throws SocketException considera se si ha un errore nell'accesso a un socket
	 * @throws ServerException considera se fallisce la connessione al server.
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o 
	 * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
	 */
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		
		out.writeObject(0);
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		String result = (String)in.readObject();
		if(!result.equals("OK")){
		
			System.out.println(result);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new ServerException(result);
		
		}
	}

	/**
	 * Effettua delle chiamate al server passando il numero di cluster da creare
	 * @return Ritorna una stringa contenente il cluster set tramite il server
	 * @throws SocketException considera se si ha un errore nell'accesso a un socket
	 * @throws ServerException considera se fallisce la connessione al server.
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o 
	 * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
	 */
	private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
		
		out.writeObject(1);
		System.out.print("Numero di cluster:");
		int k=Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			return (String)in.readObject();
		} else {
					
			System.out.println(result);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new ServerException(result);
		
		
		} 
	}
	
	/**
	 * Viene salvato tramite il server il risultato del k-means
	 * @throws SocketException considera se si ha un errore nell'accesso a un socket
	 * @throws ServerException considera se fallisce la connessione al server.
	 * @throws IOException considera un errore nell'interruzione in operazioni di i/o 
	 * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
	 */
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(2);
		String result = (String)in.readObject();
		if(!result.equals("OK")){
					
			System.out.println(result);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new ServerException(result);
		
		
		}
			 
	}
	/**
	 * Metodo main
	 * @param args : argomenti esterni che vengono passati dalla command line
	 */
	public static void main(String[] args) {


		String ip=args[0];
		int port=new Integer(args[1]).intValue();

		
		
		MainTest main=null;

		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.out.println(e);
			return;
		}
		
		
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer)
			{
				case 1:
					try {
						String kmeans = main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.out.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.out.println(e);
						return ;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					}

					break;

				case 2: // learning from db
				
					while(true){
						try{
							main.storeTableFromDb();
							break; //esce fuori dal while
						}
						
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
							
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}
					

					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					char answer='y';//itera per learning al variare di k
					do{
						try
						{
							String clusterSet = main.learningFromDbTable();
							System.out.println(clusterSet);

							main.storeClusterInFile();
							
						}
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} 
						catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}catch (IOException e) {
							System.out.println(e);
							return;
						}
					
						System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
						answer=Keyboard.readChar();
					}
					while(answer=='y');
					break; //fine case 2
					default:
					System.out.println("Opzione non valida!");
			}

			System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
			}
		while(true);
		}
	}
