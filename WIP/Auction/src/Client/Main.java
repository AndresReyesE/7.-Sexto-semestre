package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class represents the application to be run by the client. It creates an instance of the necessary objects and links them all together through the mediators' setters
 * In specific it creates:
 * An (FXML scene) instance for each View
 * A Controller for each view
 * A Model
 * A SceneMediator that connects the Views and the Controllers
 * A ControllerMediator that connects the Controllers and Model
 *
 * Those instances stay alive for the lifetime of the application and never is created another instance of these classes to encourage the coherence
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("RegisterView.fxml"));
		Parent register = registerLoader.load();
		RegisterController registerController = registerLoader.getController();
		
		FXMLLoader auctionLoader = new FXMLLoader(getClass().getResource("AuctionView.fxml"));
		Parent auction = auctionLoader.load();
		AuctionController auctionController = auctionLoader.getController();
		
		Scene scene = new Scene(register, 800, 500);
		
		SceneMediator mediator = new SceneMediator(scene);
		ControllerMediator controllerMediator = new ControllerMediator();
		
		String host = getParameters().getRaw().size() < 1 ? null : getParameters().getRaw().get(0);
		Model model = new Model(host);
		
		mediator.addColleague("Register view", register);
		mediator.addColleague("Auction view", auction);
		
		controllerMediator.addColleague("Auction controller", auctionController);
		controllerMediator.addColleague("Register controller", registerController);
		controllerMediator.addColleague("Model", model);
		
		model.setControllerMediator(controllerMediator);
		
		registerController.setMediator(mediator);
		registerController.setControllerMediator(controllerMediator);
		
		auctionController.setMediator(mediator);
		auctionController.setControllerMediator(controllerMediator);

		model.registerToServer();
		
		primaryStage.setTitle("ReyeSiordiAlvarez Auctions");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(e -> {
			System.out.println("Closing program...");
			model.disconnectFromServer();
			Platform.exit();
			System.exit(0);
		});
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
