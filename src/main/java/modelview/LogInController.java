package modelview;

import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author juan
 */
public class LogInController implements Initializable {
    
    @FXML private TextField usernameField, passwordField;
    
    @FXML public void switchToSignUp() throws IOException {
        App.setRoot("SignUp");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }
    
}
