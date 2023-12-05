package Graphic_User_Interface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * permette la selezione della tabella da esaminare e la scelta sulla fonte di informazioni per i risultati
 */

public class SelectTableController {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int p;

    @FXML
    private Button buttonLearn;
    @FXML
    private Button buttonCalculate;
    @FXML
    private TextField tableName; 
    
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
        this.p=port;
    }



    /**
     * Disabilità il bottone in assenza di testo scritto
     */
    public void setButton(){
        BooleanBinding booleanBind = tableName.textProperty().isEmpty();
        buttonLearn.disableProperty().bind(booleanBind); 
        buttonCalculate.disableProperty().bind(booleanBind); 
    }

    /**
     * Viene chiamato in caso di premuta del primo bottone. Porta alla di schermata selezione del numero di iterate
     * @param event : evento necessario per il cambio di scena
     */
    public void goToSelectIterate(ActionEvent event){
        String table = tableName.getText();
          
       
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Graphic_User_Interface/InsertNumberIteration.fxml"));
            Parent tableViewParent = null;
            try {
                tableViewParent = loader.load();
            } catch (IOException exception) {
                System.err.println("There was an error loading the new screen");
            }
            Scene tableViewScene = new Scene(tableViewParent);

            InsertNumberIterationController controller = loader.getController();
            controller.setConn(out, in, s, p);
            controller.setTableName(table); //passo nome tabella
            controller.setButton();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Selection Iterate");
            window.setScene(tableViewScene);
            window.show();
    }


    /**
     * Viene chiamato in caso di premuta del secondo bottone. Porta alla schermata di selezione del numero di cluster
     * @param event : evento necessario per il cambio di scena
     */
    public void goToSelectClusters(ActionEvent event){
        String table = tableName.getText();
          
       
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
            controller.setTableName(table); //passo nome tabella
            controller.setButton();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Selection Cluster");
            window.setScene(tableViewScene);
            window.show();




    }



}
