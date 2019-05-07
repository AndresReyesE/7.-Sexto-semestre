package sample;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Collection;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
//	@FXML
//	private Circle circle;
//
//	@FXML
//	private Rectangle collider;
	
	@FXML
	public AnchorPane pane;
	
	@FXML
	public Label sphereLayoutX, sphereLayoutY, sphereTranslateX, sphereTranslateY, sphereDistance, collisionDetected;

	public void addNode (Node node) {
		pane.getChildren().add(node);
	}
	
	public void addNodes (Collection <? extends Node> nodes) {
		pane.getChildren().addAll(nodes);
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
}
