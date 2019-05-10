package Project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * Carlos Andrés Reyes Evangelista
 * Universidad de las Américas Puebla
 * Ingeniería en Sistemas Computacionales
 *
 * May 9, 2019
 */

/**
 * Application to be invoked by the client. This program uses two views:
 * one lets the user choose their parameters for the execution and another one to let the user see the simulation
 * and progress of the evolution in a visual way.
 * This class creates an instance of each view, gets the controller for each, defines a model and interconnect them
 * as needed.
 * It also creates an instance of a mediator that'll let the controllers to switch to another scene
 * Finally, defines some basic event handlers to allow the user to drag the window by hold left click and drag
 */
public class Main extends Application {
    
    /**
     * Variables used for the drag and drop of the window
     */
    private double xOffset, yOffset;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creates the view used for choose the genetic algorithm parameters
        FXMLLoader parameterSelectionLoader = new FXMLLoader(getClass().getResource("ParameterSelectionView.fxml"));
        Parent parameterSelectionView = parameterSelectionLoader.load();
        //Gets the controller for this view
        ParameterSelectionController parameterSelectionController = parameterSelectionLoader.getController();
    
        //Creates the view used for run the simulations
        FXMLLoader simulationLoader = new FXMLLoader(getClass().getResource("SimulationView.fxml"));
        Parent simulationView = simulationLoader.load();
        //Gets the controller for this view
        SimulationController simulationController = simulationLoader.getController();
        
        //Defines the scene, i.e., the object that can be placed in a window and that'll hold the views
        Scene scene = new Scene(parameterSelectionView, 500, 650);
        
        //Creates a mediator that'll help to switch among views, its colleagues will be the available views
        SceneMediator sceneMediator = new SceneMediator(scene);
        sceneMediator.addColleague("Simulation view", simulationView);
        sceneMediator.addColleague("Parameter selection view", parameterSelectionView);
        
        parameterSelectionController.setMediator(sceneMediator);
        
        //Creates the model instance to be used for this application
        Model model = new Model();
        
        parameterSelectionController.setModel(model);
        simulationController.setModel(model);

        model.setSimulationController(simulationController);
        /*
        Methods to modify the stage: the window that'll hold the scene
        This window won't be susceptible to be resized
         */
        primaryStage.setTitle("Bouncing for life");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
    
        /*
        EventHandler initialized with a Lambda method invoked every time a click is detected in the window,
        it will update the variables used by the next function
         */
        EventHandler<MouseEvent> mousePressed = event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        };
    
        /*
        EventHandler initialized with a lambda function that will be invoked every time a drag is detected over the window,
        it will move the X and Y properties of the stage, this way allowing the user to move the window around the screen
         */
        EventHandler<MouseEvent> mouseDragged = mouseEvent ->  {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        };
        
        /*
        Both previous handlers will be used for both views
         */
        parameterSelectionView.setOnMousePressed(mousePressed);
        parameterSelectionView.setOnMouseDragged(mouseDragged);
        
        simulationView.setOnMousePressed(mousePressed);
        simulationView.setOnMouseDragged(mouseDragged);
        
        //Show the window to the user
        primaryStage.show();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}
