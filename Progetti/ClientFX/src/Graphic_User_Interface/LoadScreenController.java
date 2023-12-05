package Graphic_User_Interface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Classe che gestisce la schermata di login del programma
 */
public class LoadScreenController {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    private String ip;
    private int port=0;
    @FXML
    private AnchorPane pane; 

    /**
     * Imposta le informazioni relative alla connessione col server
     * @param s : stringa contenente l'indirizzo ip
     * @param p : stringa contenente il numero di port
     */
    public void setNetInfo(String s, String p){
        this.port = Integer.parseInt(p);
        ip = s;
    }

    /**
     * Imposta le informazioni relative alla port su cui è situato il server
     * @param p : stringa contenente il numero di port
     */
    public void setPort(int p){

        this.port = p;        

    }

    /**
     * Tenta la connessione col server e successivamente porta alla schermata di scelta della tabella
     * @param event : evento necessario per il cambio di scena
     */
     @FXML
    private void connection(ActionEvent event){
        try {
            InetAddress addr = InetAddress.getByName(ip);

            socket = new Socket(addr,port);
            System.out.println(socket);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
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
            controller.setConn(out, in,socket,port);
            controller.setButton();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Connection with localhost");
            window.setScene(tableViewScene);
            window.show();
        }  catch (IOException e) {
           Disconnect(event);
        }
    }

    /**
     * Richiama la scena di errore in caso di connessione non riuscita
     * @param event : evento necessario per il cambio di scena
     */
    @FXML
    private void Disconnect(ActionEvent event) {
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
        controller.setlabel("errore 0: Connessione non riuscita");
        controller.setPreWindows((Stage)((Node)event.getSource()).getScene().getWindow());
        controller.setPort(port);
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");
        errorStage.setScene(tableViewScene);
        errorStage.show();
    }
    
}


