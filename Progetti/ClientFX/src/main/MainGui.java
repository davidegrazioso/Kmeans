package main;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Graphic_User_Interface.LoadScreenController;


/**
 * Si occupa dell'avvio dell'interfaccia grafica
 */
public class MainGui extends Application {

    

    /**
     * Avvia l'interfaccia grafica
     * @param primaryStage Finestra di avvio
     */
    @Override
  public void start(Stage primaryStage) {
    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphic_User_Interface/LoadScreen.fxml"));
        Parent root = loader.load();

        LoadScreenController controller = loader.getController();

        String[] args = getParameters().getRaw().toArray(new String[0]);
        if (args.length >= 2) {
            controller.setNetInfo(args[0], args[1]);
        }

        primaryStage.setTitle("K-means Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    } catch (IOException exception) {
        exception.printStackTrace();
    }
}

    /**
     * Avvia il metodo che avvia interfacca grafica
     * @param args : array dal quale si ricavano le informazioni sulla port e sull'indirizzo del server
     */
    public static void main(String[] args) {
       
        launch(args);
    }

}
