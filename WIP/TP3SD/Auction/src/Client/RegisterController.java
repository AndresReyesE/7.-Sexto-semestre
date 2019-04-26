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
		}
	}
	
	@FXML
	void registerClicked (ActionEvent event) {
		if (toggleSignUp.isSelected()) {
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
				int result = controllerMediator.signUp(txtName.getText(), txtNickname.getText(), txtEmail.getText(), txtAddress.getText(), txtPhone.getText());
				
				switch (result) {
					case 1:
						lblUserNotifications.setVisible(true);
						lblUserNotifications.setText("Account Successfully Registered!");
						toggleSignUp.setSelected(false);
						signUpToggled(new ActionEvent());
						txtNickname.requestFocus();
						break;
					case 0:
						lblUserNotifications.setVisible(true);
						lblUserNotifications.setText("That nickname is already taken, please choose another one");
						txtNickname.requestFocus();
						break;
					case -1:
						lblUserNotifications.setVisible(true);
						lblUserNotifications.setText("There was a problem connecting to the server, please try again");
				}
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
				int result = controllerMediator.login((txtNickname.getText()));
				
				switch (result) {
					case 1:
						mediator.activate("Auction view");
						break;
					case 0:
						lblUserNotifications.setVisible(true);
						lblUserNotifications.setText("The user " + txtNickname.getText() + " is not associated with any account");
 					    break;
					case -1:
						lblUserNotifications.setVisible(true);
						lblUserNotifications.setText("There was a problem connecting to the server, please try again");
						
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
