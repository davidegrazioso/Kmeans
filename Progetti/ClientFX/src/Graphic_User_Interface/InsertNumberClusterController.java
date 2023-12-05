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
 * Permette di inserire il numero di cluster desiderati ed effettuare il calcolo dei cluster da database
 */
public class InsertNumberClusterController {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int p;


    private String tableName;
    
    @FXML
    private Button buttonCalculateCluster;
    
    @FXML
    private TextField numberOfCluster; //da trasformare in intero con Integer.parseInt

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
     * Imposta il nome della tabella dalla quale prendere le informazioni
     * @param s : nome dela tabella su cui lavorare
     */
    public void setTableName(String s){
        tableName = s;
    }


    /**
     * Disabilità il bottone in assenza di testo scritto
     */
    public void setButton(){
        BooleanBinding booleanBind = numberOfCluster.textProperty().isEmpty();
        buttonCalculateCluster.disableProperty().bind(booleanBind);  
    }
    
    /**
     * Permette di scrivere nella Text Field solo numeri e un massimo di 5 cifre
     */
    @FXML
    private void formatTextFieldToNumbersOnly() {
        numberOfCluster.textProperty().addListener((observableValue, oldValue, newValue) -> {
        if (!newValue.matches("\\d*")) {
            numberOfCluster.setText(newValue.replaceAll("\\D", ""));
        }
        if (newValue.length() > 5){
            String copy = newValue.substring(0, 5);
			numberOfCluster.setText(copy);
        }
        });
    }

    /**
     * Ordina al server di calcolare i cluster e di salvarli nell'archivio. Porta alla scena di visione dei risultati
     * @param event : evento necessario per il cambio di scena
     */
    public void learnFromTable(ActionEvent event){
        
        
        try {
            out.writeObject(0);
            out.writeObject(tableName);
            String result = (String)in.readObject();

            if (!result.equals("OK")) {
                Disconnect(result, event);

                return; 
            } 

            out.writeObject(1); 
            String nClusterString = numberOfCluster.getText();
            int number = Integer.parseInt(nClusterString);

		    out.writeObject(number);
            result = (String)in.readObject();
            
		    if (result.equals("OK")) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Graphic_User_Interface/ShowTable.fxml"));
                Parent tableViewParent = null;
                try {
                    tableViewParent = loader.load();
                } catch (IOException exception) {
                    System.err.println("There was an error loading the new screen");
                }
                Scene tableViewScene = new Scene(tableViewParent);

                ShowTableController controllerShowTable = loader.getController();
                controllerShowTable.inizialize();
                controllerShowTable.setResult((String)in.readObject(),1);
                controllerShowTable.setConn(out, in, s, p);
                controllerShowTable.setTableName(tableName);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Show Table");
                window.setScene(tableViewScene);
                window.show();

		    } else {	
			    Disconnect(result, event); 
                return;
		    } 

            out.writeObject(2);
            result = (String)in.readObject();
            if (!result.equals("OK")) {
                Disconnect(result, event); 
                return;
            }
                         
        } catch (IOException | ClassNotFoundException e) {

            Disconnect("errore 0: Connessione non riuscita", event);
        
        }


    } 



    /**
     * Chiama la schermata di errore passandole un messaggio specifico 
     * @param error : stringa contenente le informazioni dell'errore
     * @param event : evento necessario per il cambio di scena
     */


    private void Disconnect(String error, ActionEvent event){
        try {
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Graphic_User_Interface/Error.fxml"));
        Parent tableViewParent = null;
        try {
            tableViewParent = loader.load();
        } catch (IOException exception) {
            System.err.println("There was an error loading the new screen");
        }
        Scene tableViewScene = new Scene(tableViewParent);
        ErrorController controller = loader.getController();
        controller.setlabel(error);
        controller.setPreWindows((Stage)((Node)event.getSource()).getScene().getWindow());
        controller.setPort(p);
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");
        errorStage.setScene(tableViewScene);
        errorStage.show();
    }
}