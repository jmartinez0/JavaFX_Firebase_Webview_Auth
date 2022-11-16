package modelview;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author juan
 */
public class SignUpController implements Initializable {
    
    @FXML private TextField usernameField, nameField, emailField;
    @FXML private PasswordField passwordField;
    
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
    
    public void signUp() {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setUid(usernameField.getText())
                .setPassword(passwordField.getText())
                .setDisplayName(nameField.getText().trim())
                .setEmail(emailField.getText().trim());

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
        } catch (FirebaseAuthException ex) {
           // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not create new user");
        } 
        try {
            App.setRoot("LogIn");
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
