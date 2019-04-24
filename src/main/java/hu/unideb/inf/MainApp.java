package hu.unideb.inf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for starting application.
 */
public class MainApp extends Application {

    /** Logger. */
    private static Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Starting application...");

        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Telefonkönyv");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
