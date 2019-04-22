package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
//		Parent root = FXMLLoader.load(getClass().getResource("AuctionView.fxml"));
		
		FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("RegisterView.fxml"));
		Parent register = registerLoader.load();
		RegisterController registerController = registerLoader.getController();
		
		FXMLLoader auctionLoader = new FXMLLoader(getClass().getResource("AuctionView.fxml"));
		Parent auction = auctionLoader.load();
		AuctionController auctionController = auctionLoader.getController();
		
		Scene scene = new Scene(register, 800, 500);
		
		SceneMediator mediator = new SceneMediator(scene);
		ControllerMediator controllerMediator = new ControllerMediator();
		
		mediator.addColleague("Register view", register);
		mediator.addColleague("Auction view", auction);
		
		
		String host = getParameters().getRaw().size() < 1 ? null : getParameters().getRaw().get(0);
		Model model = new Model(host);
		model.setControllerMediator(controllerMediator);
//		model.setAuctionController(auctionController);
		
		
		registerController.setMediator(mediator);
//		registerController.setModel(model);
		registerController.setControllerMediator(controllerMediator);
		
		auctionController.setMediator(mediator);
		auctionController.setControllerMediator(controllerMediator);
//		auctionController.setModel(model);
		controllerMediator.addColleague("Auction controller", auctionController);
		controllerMediator.addColleague("Register controller", registerController);
		controllerMediator.addColleague("Model", model);
		
		
		
		primaryStage.setTitle("RS Auctions");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
