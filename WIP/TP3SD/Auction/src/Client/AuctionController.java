package Client;

import RemoteObjects.Bid;
import RemoteObjects.Offer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Controller linked to the Auction view. Handles the user's input for offer a new product and perform a bid over a existing offer. Calls the ControllerMediator to receive information (principally
 * from the Model). Update the view whenever necessary.
 */
public class AuctionController implements Initializable {
	
	private SceneMediator mediator;
	private ControllerMediator controllerMediator;
	
	private Offer selectedOffer;
	
	/* COLLECTIONS REQUIRED FOR SHOWING INFORMATION IN CLIENT SIDE */
	private Hashtable <Integer, Offer> currentOffers;
	private ArrayList <Bid> currentHistory;
	private ArrayList <Offer> currentUserOffers;
	
	/* OBSERVABLE COLLECTIONS TO BE ATTACHED TO THEIR CORRESPONDENT LIST VIEW */
	private ObservableList <String> currentOffersObservable;
	private ObservableList <String> currentHistoryObservable;
	private ObservableList <String> currentUserOffersObservable;
	
	
	/**
	 * References to graphic components in Offer tab
	 */
	@FXML
	private Label lblOfferNickname, lblOfferNotifications;
	@FXML
	private TextField txtOfferName, txtOfferDescription, txtOfferInitialPrice;
	@FXML
	private DatePicker dateOfferDeadline;
	
	/**
	 * References to graphic components in Auction tab
	 */
	@FXML
	private ListView <String> listOffers, listHistory, listUserOffers;
	@FXML
	private Label lblAuctionName, lblAuctionDescription, lblCurrentBid, lblCurrentBidder, lblAuctionNotifications;
	@FXML
	private TextField txtNewBid;
	@FXML
	private Slider sliderNewBid;
	
	/**
	 * SETTERS OF REQUIRED MEDIATORS
	 */
	void setMediator (SceneMediator mediator) {
		this.mediator = mediator;
	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	/* AUXILIARY METHODS */
	private boolean isNotValidNumber(String s) {
		return !s.matches("\\d+(\\.\\d+)?");
	}
	
	
	/* METHODS TO KEEP VIEW COHERENCE */
	void updateUserLoggedIn (String nickname) {
		lblOfferNickname.setText(nickname);
	}
	
	void updateView() {
		updateOffersList();
		updateUserOffersList();
		if (selectedOffer != null) {
			updateSelectedOffer(selectedOffer.getId());
			updateAuctionView();
			updateHistoryList();
		}
		lblAuctionNotifications.setVisible(false);
	}
	
	
	private void updateSelectedOffer (int id) {
		selectedOffer = currentOffers.get(id);
	}
	
	private void updateAuctionView () {
		lblAuctionName.setText(selectedOffer.getName());
		lblAuctionDescription.setText(selectedOffer.getDescription());
		lblCurrentBid.setText(String.format("%.2f", selectedOffer.getCurrentBid()));
		lblCurrentBidder.setText(selectedOffer.getCurrentBidder());
		txtNewBid.setText(Double.toString(selectedOffer.getCurrentBid() + 1));
		txtNewBid.requestFocus();
	}
	
	private void updateOffersList () {
		currentOffers = controllerMediator.getLocalOffers();
		currentOffersObservable.setAll(offersPresentation());
	}
	
	private void updateHistoryList () {
		if (selectedOffer == null)
			currentHistoryObservable.remove(0, currentHistoryObservable.size());
		else {
			currentHistory = selectedOffer.getHistory();
			currentHistoryObservable.setAll(historyPresentation());
			FXCollections.reverse(currentHistoryObservable);
		}
	}
	
	private void updateUserOffersList () {
		currentUserOffers = controllerMediator.getUserOffers();
		currentUserOffersObservable.setAll(userOffersPresentation());
	}
	
	/*
	METHODS TO CREATE THE PRESENTATIONS OF LISTS
	These methods use the collections previously declared to export a text version of the objects. These string will be displayed in the list views
	* */
	
	private ArrayList <String> offersPresentation () {
		ArrayList <String> offersPresentation = new ArrayList<>();
		
		for (Offer offer : currentOffers.values()) {
			String s = String.format("%03d", offer.getId()) + ".- Product: " + offer.getName() + " | Current bid: " + offer.getCurrentBid() + " | active until " + offer.getDeadline();
			offersPresentation.add(s);
		}
		
		return offersPresentation;
	}
	
	private ArrayList <String> historyPresentation () {
		ArrayList <String> historyPresentation = new ArrayList<>();
		
		for (Bid bid : currentHistory) {
			String s = bid.getNickname() + " bade up to " + bid.getBid();
			historyPresentation.add(s);
		}
		
		return historyPresentation;
	}
	
	private ArrayList <String> userOffersPresentation () {
		ArrayList <String> userOffersPresentation = new ArrayList<>();
		
		for (Offer offer : currentUserOffers) {
			String s = "Product: " + offer.getName() + "\nDescription" + offer.getDescription() + "\nCurrently bidden at: " + offer.getCurrentBid() + " by " + offer.getCurrentBidder() + "\nFor sale until: " + offer.getDeadline();
			userOffersPresentation.add(s);
		}
		
		return userOffersPresentation;
	}
	
	/*
	FXML EVENT HANDLERS
	 */
	@FXML
	void tab (Event event) {
		updateView();
	}
	
	@FXML
	void offerPlaced (ActionEvent event) {
		System.out.println("Offer placed button clicked!");
		if (txtOfferName.getText().isEmpty()) { //if user didn't specify a name of the product
			lblOfferNotifications.setText("The product must have a name");
			lblOfferNotifications.setVisible(true);
			txtOfferName.requestFocus();
		}
		else if (txtOfferInitialPrice.getText().isEmpty()) { //if user didn't specify an initial price
			lblOfferNotifications.setText("The product must have an initial price");
			lblOfferNotifications.setVisible(true);
			txtOfferInitialPrice.requestFocus();
		}
		else if (isNotValidNumber(txtOfferInitialPrice.getText())) { //if the price ain't a number
			lblOfferNotifications.setText("The product price is not a valid number. Please enter a numeric value greater than 0");
			lblOfferNotifications.setVisible(true);
			txtOfferInitialPrice.requestFocus();
		}
		else if (dateOfferDeadline.getValue() == null) { //if the user didn't specify a date as deadline
			lblOfferNotifications.setText("You must choose a date");
			lblOfferNotifications.setVisible(true);
			dateOfferDeadline.requestFocus();
		}
		else if (dateOfferDeadline.getValue().isBefore(LocalDate.now())) { //if the date is in the past
			lblOfferNotifications.setText("The date shouldn't be in the past, don't ya think?");
			lblOfferNotifications.setVisible(true);
			dateOfferDeadline.requestFocus();
		}
		else { //if all the data is valid
			boolean result = controllerMediator.addOffer(txtOfferName.getText(), txtOfferDescription.getText(), txtOfferInitialPrice.getText(), dateOfferDeadline.getValue());
			
			lblOfferNotifications.setText(result ? "Offer placed successfully!" : "There was a problem connecting to the server");
			lblOfferNotifications.setVisible(true);
			
			if (result) {
				txtOfferName.clear();
				txtOfferDescription.clear();
				txtOfferInitialPrice.clear();
			}
		}
	}
	
	@FXML
	void bidUp (ActionEvent event) {
		lblAuctionNotifications.setVisible(false);
		System.out.println("Bid up clicked!");
		if (txtNewBid.getText().isEmpty()) {
			lblAuctionNotifications.setVisible(true);
			lblAuctionNotifications.setText("Place some bid");
			txtNewBid.requestFocus();
		}
		else if (isNotValidNumber(txtNewBid.getText())) {
			lblAuctionNotifications.setVisible(true);
			lblAuctionNotifications.setText("The bid can only be conformed by numbers");
			txtNewBid.requestFocus();
		}
		else {
			double bid = Double.parseDouble(txtNewBid.getText());
			if (bid <= selectedOffer.getCurrentBid()) {
				lblAuctionNotifications.setVisible(true);
				lblAuctionNotifications.setText("Your bid gotta be greater than the current one");
				txtNewBid.requestFocus();
			}
			else {
				boolean result = controllerMediator.addBid(selectedOffer.getId(), bid);
				lblAuctionNotifications.setVisible(true);
				lblAuctionNotifications.setText(result ? "Bid placed successfully! You're the highest bidder!" : "There was a problem connecting to the server");
			}
		}
	}
	
	@FXML
	void keyTyped (KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			offerPlaced(new ActionEvent());
		else if (e.getCode().isLetterKey())
			lblOfferNotifications.setVisible(false);
	}
	
	@FXML
	void listClicked (MouseEvent event) {
		lblAuctionNotifications.setVisible(false);
		String selectedItem = listOffers.getSelectionModel().getSelectedItem();
		String strID = selectedItem.substring(0, 3);
		int id = Integer.parseInt(strID);
		
		updateSelectedOffer(id);
		updateView();
	}
	
	@FXML
	void sliderMoved (MouseEvent e) {
		lblAuctionNotifications.setVisible(false);
		double min = Double.parseDouble(lblCurrentBid.getText()) + 1;
		double max = Double.parseDouble(lblCurrentBid.getText()) * 10;
		
		double sliderValue = sliderNewBid.getValue();
		double proposalBid = sliderValue * (max - min) / 100 + min;
		txtNewBid.setText(String.format("%.2f", proposalBid));
		txtNewBid.requestFocus();
	}
	
	@FXML
	void keyTypedOnBid (KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			bidUp(new ActionEvent());
		else if (e.getCode().isLetterKey())
			lblAuctionNotifications.setVisible(false);
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		currentOffersObservable = FXCollections.observableArrayList();
		currentHistoryObservable = FXCollections.observableArrayList();
		currentUserOffersObservable = FXCollections.observableArrayList();
		
		listOffers.setItems(currentOffersObservable);
		listHistory.setItems(currentHistoryObservable);
		listUserOffers.setItems(currentUserOffersObservable);
		
		dateOfferDeadline.setValue(LocalDate.now());
	}
}
