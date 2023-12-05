package Graphic_User_Interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Classe Controller per avvisare della mancata connesione con il server
 */
public class ErrorController {
    @FXML
    private Button OKbutton;
    @FXML
    private Label textlabel;
    private Stage prewindows;
    private int p=0;


    /**
     * Permette di passare la finestra questo Controller
     * @param prewindows finestra preccedente
     */
    void setPreWindows(Stage prewindows){
        this.prewindows = prewindows;
    }

    /**
     * Assegna port a p
     * @param port
     */
    public void setPort(int port){

        this.p = port;
    }

    /**
     * Chiude la finestra
     * @param event evento per gestire la chiusura della finestra e riavvio del programma
     */
    @FXML
    private void reconnect(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphic_User_Interface/LoadScreen.fxml"));
            Parent root = loader.load();
            // Ottieni il controller dal loader
            LoadScreenController controller = loader.getController();
    
            
            loader.setLocation(getClass().getResource("/Graphic_User_Interface/LoadScreen.fxml"));
            controller.setPort(p);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            prewindows.close();
            window.setTitle("K-means Client");
            window.setScene(new Scene(root));
            window.show();
        } catch (IOException e) {
            System.err.println("There was an error loading the new screen");
        }

    }

    /** 
     * Funzione per uscire dal programma
    */
    @FXML
    public void exit(ActionEvent e){
        System.exit(0);

    }

    /**
     * Funzione per impostare il testo del messaggio
     * @param text testo da impostare nella finestra
     */
    void setlabel(String text){
        textlabel.setText(text);
    }

}
