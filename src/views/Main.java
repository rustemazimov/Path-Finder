package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Program begins from this class's start(*) method
 * @author Rustem Azimov
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
         * Convert MainView.fxml 's data into AnchorPane
         * in order to use it in a window
         */
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("../resources/fxml_files/MainView.fxml"));

        //Define the window's title as "Path Finder"
        primaryStage.setTitle("Path Finder");

        /*
         * Add to ^primaryStage^ a scene
         * which contains ^root^
         * (loaded AnchorPane from MainView.fxml file
         */
        primaryStage.setScene(new Scene(root));

        //Show the window
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
