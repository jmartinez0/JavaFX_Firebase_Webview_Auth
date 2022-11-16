package modelview;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author juan
 */
public class LogInController implements Initializable {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label incorrectUsernameLabel;
    static UserRecord user;
    
    @FXML public void switchToSignUp() throws IOException {
        App.setRoot("SignUp");
    }
    
    @FXML public void switchToAccessFBView() throws IOException {
        App.setRoot("AccessFBView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        incorrectUsernameLabel.setVisible(false);
        
    }
    
    public void logIn() throws IOException, FirebaseAuthException{
        try {
            String name = usernameField.getText();
            //String password = passwordField.getText();
            user = FirebaseAuth.getInstance().getUser(name);
            App.setRoot("AccessFBView");
        } catch (FirebaseAuthException ex) {
            incorrectUsernameLabel.setVisible(true);
            usernameField.setStyle("-fx-border-color: crimson;");
        } catch (IllegalArgumentException iae){
            incorrectUsernameLabel.setVisible(true);
            usernameField.setStyle("-fx-border-color: crimson;");
        }
    }
}
