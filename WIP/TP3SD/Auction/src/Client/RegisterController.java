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
	
	private SceneMediator mediator;
	private ControllerMediator controllerMediator;
	private boolean signUp = true;
	
	/**
	 * References to graphic components in Register tab
	 */
	@FXML
	private TextField txtName, txtNickname, txtEmail, txtAddress, txtPhone;
	@FXML
	private Label lblName, lblEmail, lblAddress, lblPhone, lblUserNotifications;
	@FXML
	private ToggleButton toggleSignUp;
	@FXML
	private Button btnLoginSignUp;
	
	void setMediator (SceneMediator mediator) {
		this.mediator = mediator;
	}
	
	void setControllerMediator (ControllerMediator cm) {
		this.controllerMediator = cm;
	}
	
	
	@FXML
	void signUpToggled (ActionEvent event) {
		if (toggleSignUp.isSelected()) {
			txtName.setVisible(true);
			txtEmail.setVisible(true);
			txtAddress.setVisible(true);
			txtPhone.setVisible(true);
			
			lblName.setVisible(true);
			lblEmail.setVisible(true);
			lblAddress.setVisible(true);
			lblPhone.setVisible(true);
			
			toggleSignUp.setText("Have an account? Log In instead!");
			btnLoginSignUp.setText("Sign up!");
			signUp = true;
		}
		else {
			txtName.setVisible(false);
			txtEmail.setVisible(false);
			txtAddress.setVisible(false);
			txtPhone.setVisible(false);
			
			lblName.setVisible(false);
			lblEmail.setVisible(false);
			lblAddress.setVisible(false);
			lblPhone.setVisible(false);
			
			toggleSignUp.setText("Don't have an account yet? Create one!");
			btnLoginSignUp.setText("Log in!");
			signUp = false;
		}
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
					toggleSignUp.setSelected(false);
					signUpToggled(new ActionEvent());
					txtNickname.requestFocus();
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
		if (e.getCode().equals(KeyCode.ENTER))
			registerClicked(new ActionEvent());
		else if (e.getCode().isLetterKey())
			lblUserNotifications.setVisible(false);
	}
}
