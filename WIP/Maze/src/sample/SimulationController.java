package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class SimulationController implements Initializable {
	
	private Model model;
	private long simulationTime;
	
	@FXML
	private AnchorPane pane;
	@FXML
	private Label labelCurrentGeneration, labelCurrentStatus, labelCurrentFittest;
	@FXML
	private Button buttonStartSimulation;
	@FXML
	private ProgressBar progressBar;
	
	public void addNode (Node node) {
		pane.getChildren().add(node);
	}
	
	public void addNodes (Collection <? extends Node> nodes) {
		pane.getChildren().addAll(nodes);
	}
	
	public void removeNode (Node node) {
		pane.getChildren().remove(node);
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setSimulationTime(long simulationTime) {
		this.simulationTime = simulationTime;
	}
	
	/*
		EVENT HANDLERS
		 */
	@FXML
	public void startSimulation() {
		Timer timer = new Timer();
		buttonStartSimulation.setDisable(true);
		
		model.nextGeneration();
		
		AnimationTimer progress = new AnimationTimer() {
			private long lastUpdate = 0;
			private double progress = 0;
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= simulationTime * 10000) {
					progress += 0.01;
					progressBar.setProgress(progress);
					lastUpdate = now;
				}
				
			}
		};
		progress.start();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				progress.stop();
				buttonStartSimulation.setDisable(false);
			}
		}, simulationTime + 100);
		
	}
	
	
	public void updateCurrentGeneration(String currentGeneration) {
		labelCurrentGeneration.setText(currentGeneration);
	}
	
	public void updateStatus(String status) {
		labelCurrentStatus.setText(status);
	}
	
	public void updateFittest(double fittestValue) {
		labelCurrentFittest.setText(String.format("%.2f", fittestValue));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
