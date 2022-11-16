package modelview;

import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 *
 * @author juan
 */
public class SignUpController implements Initializable {
    
    @FXML private TextField usernameField, passwordField, nameField, emailField;
    
    @FXML public void switchToLogIn() throws IOException {
        App.setRoot("LogIn");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
        nameField.setFocusTraversable(false);
        emailField.setFocusTraversable(false);
    }
    
}
