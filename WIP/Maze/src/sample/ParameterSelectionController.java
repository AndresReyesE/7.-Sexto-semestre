package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ParameterSelectionController implements Initializable {
	
	private int currentNumberOfGenerations, currentPopulationSize, currentTournamentSize, currentLevel, currentRadius, simulationTime;
	private double currentMutationRate;
	private boolean elitism;
	private Color bouncersColor;
	
	private SceneMediator mediator;
	private Model model;
	
	@FXML
	Spinner spinnerNumberOfGenerations, spinnerPopulationSize, spinnerTournamentSize, spinnerMutationRate, spinnerMazeLevel, spinnerRadius, spinnerSimulationTime;
	@FXML
	ImageView imageClose;
	@FXML
	Button buttonAccept;
	@FXML
	ColorPicker colorPicker;
	@FXML
	CheckBox checkboxElitism;
	
	
	void setMediator(SceneMediator mediator) {
		this.mediator = mediator;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	/*
	EVENT HANDLERS
	 */
	
	@FXML
	public void updatePopulationSize() {
		currentPopulationSize = (int) spinnerPopulationSize.getValue();
		spinnerTournamentSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, currentPopulationSize, currentTournamentSize, 5));
	}
	
	@FXML
	public void updateTournamentSize() {
		currentTournamentSize = (int) spinnerTournamentSize.getValue();
	}
	
	@FXML
	public void acceptClicked() {
		currentNumberOfGenerations = (int) spinnerNumberOfGenerations.getValue();
		currentPopulationSize = (int) spinnerPopulationSize.getValue();
		currentTournamentSize = (int) spinnerTournamentSize.getValue();
		currentMutationRate = (double) spinnerMutationRate.getValue();
		elitism = checkboxElitism.isSelected();
		
		currentLevel = (int) spinnerMazeLevel.getValue();
		currentRadius = (int) spinnerRadius.getValue();
		simulationTime = (int) spinnerSimulationTime.getValue();
		bouncersColor = colorPicker.getValue();
		
		model.createGeneticAlgorithm(currentNumberOfGenerations, currentPopulationSize, currentTournamentSize, currentMutationRate, elitism);
		model.setUserParameters(currentLevel, currentRadius, bouncersColor, simulationTime);
		model.createMaze();
		
		mediator.activate("Simulation view");
	}
	
	@FXML
	public void closeClicked() {
		System.out.println("Closing program...");
		Platform.exit();
		System.exit(0);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		currentTournamentSize = 10;
		spinnerNumberOfGenerations.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, 7));
		spinnerPopulationSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 10000, 50, 50));
		spinnerTournamentSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 40, 10, 5));
		spinnerMutationRate.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0.15, 0.05));
		
		spinnerMazeLevel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 3));
		spinnerRadius.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 32, 10));
		spinnerSimulationTime.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 30, 5));
		
		colorPicker.setValue(Color.DARKBLUE);
	}
}
