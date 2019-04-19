package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Controller {
	
	/**
	 * References to graphic components in Register tab
	 */
	@FXML
	private TextField txtName, txtNickName, txtEmail, txtAddress, txtPhone;
	
	@FXML
	private Label lblName, lblNickname, lblEmail, lblAddress, lblPhone;
	
	@FXML
	private ToggleButton toggleLogin, toggleSignup;
	
	@FXML
	private Button btnLoginSignup;
	
	/**
	 * References to graphic components in Offer tab
	 */
	@FXML
	private Label lblOfferRegisteredAs, lblOfferNickname, lblOfferName, lblOfferDescription, lblOfferInitialPrice, lblOfferDeadline;
	
	@FXML
	private TextField txtOfferName, txtOfferDescription, txtOfferInitialPrice;
	
	@FXML
	private DatePicker dateOfferDeadline;
	
	@FXML
	private Button btnPlaceOffer;
	
	/**
	 * References to graphic components in Auction tab
	 */
	@FXML
	private ListView listOffers;
	
	@FXML
	private VBox vboxHistory;
	
	@FXML
	private Label lblAuctionName, lblAuctionDescription, lblCurrentBid, lblCurrentBidder;
	
	@FXML
	private TextField txtNewBid;
	
	@FXML
	private Slider sliderNewBid;
	
	@FXML
	private Button btnBidUp;
	
	@FXML
	void signupToggled (ActionEvent event) {
		txtName.setVisible(true);
		txtEmail.setVisible(true);
		txtAddress.setVisible(true);
		txtPhone.setVisible(true);
		
		lblName.setVisible(true);
		lblEmail.setVisible(true);
		lblAddress.setVisible(true);
		lblPhone.setVisible(true);
		
		btnLoginSignup.setText("Sign up!");
	}
	
	@FXML
	void loginToggled (ActionEvent event) {
		txtName.setVisible(false);
		txtEmail.setVisible(false);
		txtAddress.setVisible(false);
		txtPhone.setVisible(false);
		
		lblName.setVisible(false);
		lblEmail.setVisible(false);
		lblAddress.setVisible(false);
		lblPhone.setVisible(false);
		
		btnLoginSignup.setText("Log in!");
		
		
	}
	
	
	@FXML
	void offerPlaced (ActionEvent event) {
//		System.out.println("Clic");
//		l = new ArrayList<>();
//		l.add(txtOfferName.getText());
//		list.getItems().addAll(l);
//		list.refresh();
//		String selected = (String) list.getSelectionModel().getSelectedItem();
	}
}
