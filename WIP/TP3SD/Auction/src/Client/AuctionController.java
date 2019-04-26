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

public class AuctionController implements Initializable {
	
	private SceneMediator mediator;
	private ControllerMediator controllerMediator;
	
	private Offer selectedOffer;
	private Hashtable <Integer, Offer> currentOffers;
	private ArrayList <Bid> currentHistory;
	
	private ObservableList <String> currentOffersObservable;
	private ObservableList <String> currentHistoryObservable;
	
	
	/**
	 * References to graphic components in Offer tab
	 */
	@FXML
	private Label lblOfferNickname;
	@FXML
	private TextField txtOfferName, txtOfferDescription, txtOfferInitialPrice;
	@FXML
	private DatePicker dateOfferDeadline;
	
	/**
	 * References to graphic components in Auction tab
	 */
	@FXML
	private ListView <String> listOffers, listHistory;
	@FXML
	private Label lblAuctionName, lblAuctionDescription, lblCurrentBid, lblCurrentBidder, lblOfferNotifications;
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
	
	
	/* METHODS TO KEEP COHERENCE */
	void updateUserLoggedIn (String nickname) {
		lblOfferNickname.setText(nickname);
	}
	
	void updateOffers () {
		currentOffers = controllerMediator.getCurrentOffers();
		currentOffersObservable.setAll(offersPresentation());
		System.out.println("Observable changed...");
	}
	
	void updateSelectedOffer (int id) {
		selectedOffer = currentOffers.get(id);
	}
	
	void updateHistoryList () {
		if (selectedOffer == null)
			currentHistoryObservable.remove(0, currentHistoryObservable.size());
		else {
			currentHistory = selectedOffer.getHistory();
			currentHistoryObservable.setAll(historyPresentation());
		}
	}
	
	private void updateAuctionView () {
		updateOffers();
		lblAuctionName.setText(selectedOffer.getName());
		lblAuctionDescription.setText(selectedOffer.getDescription());
		lblCurrentBid.setText(String.format("%.2f", selectedOffer.getCurrentBid()));
		lblCurrentBidder.setText(selectedOffer.getCurrentBidder());
		txtNewBid.setText(Double.toString(selectedOffer.getCurrentBid() + 1));
		txtNewBid.requestFocus();
	}
	
	/* METHODS TO CREATE THE PRESENTATIONS OF LISTS */
	
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
			String s = "| " + bid.getNickname() + " bade up to " + bid.getBid();
			historyPresentation.add(s);
		}
		
		return historyPresentation;
	}
	
	/*
	FXML EVENT HANDLERS
	 */
	@FXML
	void auctionTab (Event event) {
		updateOffers();
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
		else if (isNotValidNumber(txtOfferInitialPrice.getText())) {
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
			boolean result = controllerMediator.addOffer(txtOfferName.getText(), txtOfferDescription.getText(), txtOfferInitialPrice.getText(), dateOfferDeadline.getValue());
			
			lblOfferNotifications.setText(result ? "Offer placed successfully!" : "There was a problem connecting to the server");
			lblOfferNotifications.setVisible(true);
			
//			updateOffers();
		}
	}
	
	@FXML
	void bidUp (ActionEvent event) {
		System.out.println("Bid up clicked!");
		if (txtNewBid.getText().isEmpty()) {
			lblAuctionDescription.setVisible(true);
			lblAuctionDescription.setText("Place some bid");
			txtNewBid.requestFocus();
		}
		else if (isNotValidNumber(txtNewBid.getText())) {
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
				boolean result = controllerMediator.addBid(selectedOffer.getId(), bid);
				lblOfferNotifications.setText(result ? "Bid placed successfully! You're the highest bidder!" : "There was a problem connecting to the server");
//				updateOffersList();
//				updateOffers();
				updateSelectedOffer(selectedOffer.getId());
				updateAuctionView();
				updateHistoryList();
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
		String selectedItem = listOffers.getSelectionModel().getSelectedItem();
		String strID = selectedItem.substring(0, 3);
		int id = Integer.parseInt(strID);
		
//		updateOffers();
		updateSelectedOffer(id);
		updateAuctionView();
		updateHistoryList();
	}
	
	@FXML
	void sliderMoved (MouseEvent e) {
		double min = Double.parseDouble(lblCurrentBid.getText()) + 1;
		double max = Double.parseDouble(lblCurrentBid.getText()) * 10;
		
		double sliderValue = sliderNewBid.getValue();		double proposalBid = sliderValue * (max - min) / 100 + min;
		txtNewBid.setText(String.format("%.2f", proposalBid));
		txtNewBid.requestFocus();
	}
	
	@FXML
	void keyTypedOnBid (KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			bidUp(new ActionEvent());
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
//		currentOffers = controllerMediator.getCurrentOffers();
		
		currentOffersObservable = FXCollections.observableArrayList();
		currentHistoryObservable = FXCollections.observableArrayList();
		
		listOffers.setItems(currentOffersObservable);
		listHistory.setItems(currentHistoryObservable);
	}
}
