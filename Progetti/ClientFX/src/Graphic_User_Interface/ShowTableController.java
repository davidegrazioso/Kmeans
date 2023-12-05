package Graphic_User_Interface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Node;

/** 
 * Mostra il risultato del calcolo dei cluster
 */
public class ShowTableController {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int p;


    @FXML
    private ScrollPane scrollPane;

    
    @FXML
    private TextArea resultText;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button backToSelectClusterButton;

    private int choice;

    private String tableName;

    /**
     * Assegna la stringa presa in input a tablename
     * @param s : stringa che contiene il nome della tabella 
     */
    public void setTableName(String s){
        tableName = s;
    }



    /**
     * Imposta le informazioni relative alla connessione col server
     * @param out : stream per l'invio di oggetti al server
     * @param in : stream per la ricezione di oggetti col server
     * @param socket : oggetto necessario per la comunicazione col server
     * @param port : numero di port su cui è situato il server
     */
    public void setConn(ObjectOutputStream out, ObjectInputStream in, Socket socket, int port) {
        this.out = out;
        this.in = in;
        this.s = socket;
        this.p = port;
    }


    /**
     * Imposta l'area di testo a sola lettura e imposta le dimensioni del contenitore
-     */
    void inizialize(){

        resultText.setEditable(false);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }


    /**
     * Imposta la stringa del risultato della computazione nella label della scena
     * @param result : stringa contenente il risultato della computazione
     * @param n : intero che identifica la provenienza dei risultati (0 se da file, 1 se da database)
     */
    public void setResult(String result, int n){
        resultText.setText(result);
        choice = n;
        backToSelectClusterButton.setDisable( choice == 0);         
    }


    /**
     * Porta alla scena del menù di scelta principale
     * @param event : evento necessario al cambio di scena
     */
    public void backToMenu(ActionEvent event){
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/Graphic_User_Interface/SelectTable.fxml"));
           Parent tableViewParent = null;
            try {
                tableViewParent = loader.load();
            } catch (IOException exception) {
                System.err.println("There was an error loading the new screen");
            }
            Scene tableViewScene = new Scene(tableViewParent);
            
            SelectTableController controller = loader.getController();
            controller.setConn(out, in, s, p);
            controller.setButton();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Connection with localhost");
            window.setScene(tableViewScene);
            window.show();



    }

    /**
     * Porta alla scena dell'impostazione del numero di cluster
     * @param event : evento necessario al cambio di scena
     */
    public void backToSelectCluster(ActionEvent event){
          FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/Graphic_User_Interface/InsertNumberCluster.fxml"));
           Parent tableViewParent = null;
            try {
                tableViewParent = loader.load();
            } catch (IOException exception) {
                System.err.println("There was an error loading the new screen");
            }
            Scene tableViewScene = new Scene(tableViewParent);
            
            InsertNumberClusterController controller = loader.getController();
            controller.setConn(out, in, s, p);
            controller.setTableName(tableName);
            controller.setButton();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Cluster Selection");
            window.setScene(tableViewScene);
            window.show();


        
    }

    
    /**
     * Quando un tasto viene premuto, esce dal programma
     * @param e : evento necessario alla chiusura del programma
     */
   
    @FXML
    public void exit(ActionEvent e){
        System.exit(0);

    }


}