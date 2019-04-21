package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RegisterController {
//	Model model;
	private SceneMediator mediator;
	private ControllerMediator controllerMediator;
	private boolean signUp = true;
	
//	private List<String> cmlParameters;
	
	/**
	 * References to graphic components in Register tab
	 */
	@FXML
	private TextField txtName, txtNickname, txtEmail, txtAddress, txtPhone;
	@FXML
	private Label lblName, lblNickname, lblEmail, lblAddress, lblPhone, lblUserNotifications;
	@FXML
	private ToggleButton toggleLogin, toggleSignup;
	@FXML
	private Button btnLoginSignup;
	
	void setMediator (SceneMediator mediator) {
		this.mediator = mediator;
	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
//	void setModel (Model model) {
//		this.model = model;
//	}
	
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
		signUp = true;
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
		signUp = false;
		
	}
	
	@FXML
	void registerClicked (ActionEvent event) {
		if (signUp) {
			System.out.println("Sign-up Button clicked");
			if (txtName.getText().equals("")) {
				lblUserNotifications.setText("Name field cannot be empty");
				lblUserNotifications.setVisible(true);
				txtName.requestFocus();
			}
			else if (txtNickname.getText().equals("")) {
				lblUserNotifications.setText("Nickname field cannot be empty");
				lblUserNotifications.setVisible(true);
				txtNickname.requestFocus();
			}
			else {
				System.out.println("Data fetched by the AuctionController: " + txtName.getText() + ", " + txtNickname.getText() + " | " + txtEmail.getText() + " | " + txtAddress.getText() + " : " + txtPhone.getText());
				if (controllerMediator.signUp(txtName.getText(), txtNickname.getText(), txtEmail.getText(), txtAddress.getText(), txtPhone.getText())) {
					lblUserNotifications.setVisible(true);
					lblUserNotifications.setText("Account Successfully Registered!");
					loginToggled(new ActionEvent());
				}
				
				else
					lblUserNotifications.setText("The nickname " + txtNickname.getText() + " is already in use, please choose another one");
					
			}
		}
		else {
			System.out.println("Log-in Button clicked");
			if (txtNickname.getText().equals("")) {
				lblUserNotifications.setText("Nickname field cannot be empty");
				lblUserNotifications.setVisible(true);
				txtNickname.requestFocus();
			}
			else {
				System.out.println("Data fetched by the AuctionController: " + txtNickname.getText());
				if (controllerMediator.login(txtNickname.getText()))
					mediator.activate("Auction view");
				else {
					lblUserNotifications.setVisible(true);
					lblUserNotifications.setText("The user " + txtNickname.getText() + " is not associated with any account");
				}
			}
		}
	}
	
	@FXML
	void keyTyped (KeyEvent e) {
		System.out.println("Some key typed: " + e.getCode());
		if (e.getCode().equals(KeyCode.ENTER))
			registerClicked(new ActionEvent());
		else if (e.getCode().isLetterKey())
			lblUserNotifications.setVisible(false);
	}
	
}
