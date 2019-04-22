package Client;

import RemoteObjects.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

public class AuctionController {
	
//	private Model model;
	private SceneMediator mediator;
	private ControllerMediator controllerMediator;
	
	private Offer selectedOffer;
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
	private Label lblAuctionName, lblAuctionDescription, lblCurrentBid, lblCurrentBidder, lblOfferNotifications;
	@FXML
	private TextField txtNewBid;
	@FXML
	private Slider sliderNewBid;
	@FXML
	private Button btnBidUp;
	
	void setMediator (SceneMediator mediator) {
		this.mediator = mediator;
	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	void updateUserLoggedIn (String nickname) {
		lblOfferNickname.setText(nickname);
	}
	
	private boolean isValidNumber (String s) {
		return s.matches("\\d+(\\.\\d+)?");
	}
	
	@FXML
	void offerPlaced (ActionEvent event) {
		System.out.println("Offer placed button clicked!");
		if (txtOfferName.getText().isEmpty()) {
			lblOfferNotifications.setText("The product must have a name");
			lblOfferNotifications.setVisible(true);
			txtOfferName.requestFocus();
		}
		else if (txtOfferInitialPrice.getText().isEmpty()) {
			lblOfferNotifications.setText("The product must have an initial price");
			lblOfferNotifications.setVisible(true);
			txtOfferInitialPrice.requestFocus();
		}
		else if (!isValidNumber(txtOfferInitialPrice.getText())) {
			lblOfferNotifications.setText("The product price is not a valid number. Please enter a numeric value greater than 0");
			lblOfferNotifications.setVisible(true);
			txtOfferInitialPrice.requestFocus();
		}
		else if (dateOfferDeadline.getValue() == null) {
			lblOfferNotifications.setText("You must choose a date");
			lblOfferNotifications.setVisible(true);
			dateOfferDeadline.requestFocus();
		}
		else if (dateOfferDeadline.getValue().isBefore(LocalDate.now())) {
			lblOfferNotifications.setText("The date shouldn't be in the past, don't ya think?");
			lblOfferNotifications.setVisible(true);
			dateOfferDeadline.requestFocus();
		}
		else {
			lblOfferNotifications.setText("Offer placed successfully!");
			lblOfferNotifications.setVisible(true);
			
			controllerMediator.addOffer(txtOfferName.getText(), txtOfferDescription.getText(), txtOfferInitialPrice.getText(), dateOfferDeadline.getValue());
			updateOffersList();
		}
	}
	
	@FXML
	void keyTyped (KeyEvent e) {
		System.out.println("Some key typed: " + e.getCode());
		if (e.getCode().equals(KeyCode.ENTER))
			offerPlaced(new ActionEvent());
		else if (e.getCode().isLetterKey())
			lblOfferNotifications.setVisible(false);
	}
	
	void updateOffersList () {
		Hashtable <Integer, Offer> currentOffers = controllerMediator.getCurrentOffers();
		ArrayList <String> offersPresentation = new ArrayList<>();
		
		for (Offer offer : currentOffers.values()) {
			String s = String.format("%03d", offer.getId()) + ".- Product: " + offer.getName() + " | Current bid: " + offer.getCurrentBid() + " | active until " + offer.getDeadline();
			offersPresentation.add(s);
		}
		
		listOffers.getItems().remove(0, listOffers.getItems().size());
		listOffers.getItems().addAll(offersPresentation);
		listOffers.refresh();
	}
	
	@FXML
	void listClicked (MouseEvent event) {
//		System.out.println("List clicked...");
//		System.out.println("Element selected: " + listOffers.getSelectionModel().getSelectedItem());
		String selectedItem = (String) listOffers.getSelectionModel().getSelectedItem();
		String strID = selectedItem.substring(0, 3);
		int id = Integer.parseInt(strID);
		
		Hashtable <Integer, Offer> currentOffers = controllerMediator.getCurrentOffers();
		selectedOffer = currentOffers.get(id);
		updateAuctionView();
	}
	
	private void updateAuctionView () {
		lblAuctionName.setText(selectedOffer.getName());
		lblAuctionDescription.setText(selectedOffer.getDescription());
		lblCurrentBid.setText(String.format("%.2f", selectedOffer.getCurrentBid()));
		lblCurrentBidder.setText(selectedOffer.getCurrentBidder());
		txtNewBid.setText(Double.toString(selectedOffer.getCurrentBid() + 1));
		txtNewBid.requestFocus();
	}
	
	@FXML
	void sliderMoved (MouseEvent e) {
//		System.out.println("Slider moved");
//		System.out.println("" + sliderNewBid.getValue());
		double min = Double.parseDouble(lblCurrentBid.getText()) + 1;
		double max = Double.parseDouble(lblCurrentBid.getText()) * 10;
		
		double sliderValue = sliderNewBid.getValue();
		double proposalBid = sliderValue * (max - min) / 100 + min;
		txtNewBid.setText(String.format("%.2f", proposalBid));
		txtNewBid.requestFocus();
	}
	
	@FXML
	void bidUp (ActionEvent event) {
		System.out.println("Bid up clicked!");
		if (txtNewBid.getText().isEmpty()) {
			lblAuctionDescription.setVisible(true);
			lblAuctionDescription.setText("Place some bid");
			txtNewBid.requestFocus();
		}
		else if (!isValidNumber(txtNewBid.getText())) {
			lblAuctionDescription.setVisible(true);
			lblAuctionDescription.setText("The bid can only be conformed by numbers");
			txtNewBid.requestFocus();
		}
		else {
			double bid = Double.parseDouble(txtNewBid.getText());
			if (bid <= selectedOffer.getCurrentBid()) {
				lblAuctionDescription.setVisible(true);
				lblAuctionDescription.setText("Your bid gotta be greater than the current one");
				txtNewBid.requestFocus();
			}
			else {
				controllerMediator.addBid(selectedOffer.getId(), bid);
				updateOffersList();
				updateAuctionView();
			}
		}
		
	}
	
	
	
	@FXML
	void keyTypedOnBid (KeyEvent e) {
		System.out.println("Some key typed: " + e.getCode());
		if (e.getCode().equals(KeyCode.ENTER))
			bidUp(new ActionEvent());
	}
	
	
}
