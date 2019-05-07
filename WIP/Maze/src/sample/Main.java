package sample;

import GraphicElements.Bouncer;
import GraphicElements.Maze;
import GraphicElements.Obstacle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application implements Initializable {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
    
        Model model = new Model (controller);
        
        model.createGeneticAlgorithm(3, 100, 5, 0.0, false, 1);
        
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        
    }
}
