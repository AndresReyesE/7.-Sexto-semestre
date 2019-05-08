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
	
	private int currentNumberOfGenerations, currentPopulationSize, currentTournamentSize, currentLevel, currentRadius;
	private double currentMutationRate;
	private boolean elitism;
	private Color bouncersColor;
	
	private SceneMediator mediator;
	private Model model;
	
	@FXML
	Spinner spinnerNumberOfGenerations, spinnerPopulationSize, spinnerTournamentSize, spinnerMutationRate, spinnerMazeLevel, spinnerRadius;
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
	public void closeClicked(MouseEvent event) {
		System.out.println("Closing program...");
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	public void updatePopulationSize(Event e) {
		currentPopulationSize = (int) spinnerPopulationSize.getValue();
		spinnerTournamentSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, currentPopulationSize, currentTournamentSize));
	}
	
	@FXML
	public void acceptClicked(Event event) {
		currentNumberOfGenerations = (int) spinnerNumberOfGenerations.getValue();
		currentPopulationSize = (int) spinnerPopulationSize.getValue();
		currentTournamentSize = (int) spinnerTournamentSize.getValue();
		currentMutationRate = (double) spinnerMutationRate.getValue();
		elitism = checkboxElitism.isSelected();
		
		currentLevel = (int) spinnerMazeLevel.getValue();
		currentRadius = (int) spinnerRadius.getValue();
		bouncersColor = colorPicker.getValue();
		
		model.createGeneticAlgorithm(currentNumberOfGenerations, currentPopulationSize, currentTournamentSize, currentMutationRate, elitism);
		model.setUserParameters(currentLevel, currentRadius, bouncersColor);
		model.createMaze();
		
		mediator.activate("Simulation view");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		currentNumberOfGenerations = 3;
		currentPopulationSize = 40;
		currentTournamentSize = 10;
		
		spinnerNumberOfGenerations.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 3));
		spinnerPopulationSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 10000, 40, 50));
		spinnerTournamentSize.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 40, 10, 5));
		spinnerMutationRate.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1, 0.15, 0.05));
		
		spinnerMazeLevel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1));
		spinnerRadius.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 32, 12));
	}
}
