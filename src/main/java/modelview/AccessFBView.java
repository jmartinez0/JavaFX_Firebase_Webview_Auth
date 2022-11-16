package modelview;

import com.mycompany.mvvmexample.App;
import viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Person;

public class AccessFBView implements Initializable {

    @FXML private TableView<Person> tableField;
    @FXML private TableColumn<Person, String> nameColumn, majorColumn;
    @FXML private TableColumn<Person, Integer> ageColumn;
    @FXML private TextField nameField, ageField, majorField;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        nameField.setFocusTraversable(false);
        majorField.setFocusTraversable(false);
        ageField.setFocusTraversable(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("major"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
    }
    
    @FXML public void logOut(ActionEvent event) throws IOException {
        App.setRoot("LogIn");
    }
    
    @FXML private void switchToWebContainer() throws IOException {
        App.setRoot("WebContainer");
    }
    
    


    @FXML public void updateData(ActionEvent event) {
        listOfUsers = tableField.getItems();
        Person selectedPerson = tableField.getSelectionModel().getSelectedItem();
        for (Person person : listOfUsers) {
            if (person.getName().equals(selectedPerson.getName())
                && person.getMajor().equals(selectedPerson.getMajor())
                && person.getAge() == selectedPerson.getAge()) {
                person.setName(nameField.getText());
                person.setMajor(majorField.getText());
                person.setAge(Integer.parseInt(ageField.getText()));
                tableField.setItems(listOfUsers);
                tableField.refresh();
            }
        }
    }
    
    @FXML public void rowSelected(MouseEvent event) {
        person = tableField.getSelectionModel().getSelectedItem();
        nameField.setText(person.getName());
        majorField.setText(person.getMajor());
        ageField.setText(String.valueOf(person.getAge()));
    }
    
 
    
    @FXML private void addRecord(ActionEvent event) {
        addData();
        readFirebase();
    }
    
    @FXML public void deleteRecord(ActionEvent event) {
        deleteData();
        readFirebase();
    }
 
    public void addData() {
        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }
    
    public void deleteData() {
        Person currentPerson = tableField.getSelectionModel().getSelectedItem();
        System.out.println(currentPerson);
        try {
            String docID = "";
            ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                    person = new Person(String.valueOf(document.getData().get("Name")),
                        document.getData().get("Major").toString(),
                        Integer.parseInt(document.getData().get("Age").toString()));
                if (person.equals(currentPerson)) {
                    docID = document.getId();
                    ApiFuture<WriteResult> writeResult = App.fstore.collection("References").document(docID).delete();
                    System.out.println("Update time : " + writeResult.get().getUpdateTime());
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(AccessFBView.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fail");
        }
    }
    
    public boolean readFirebase() {
        key = false;
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Outing....");
                tableField.getItems().clear();
                for (QueryDocumentSnapshot document : documents) {
                    person = new Person(String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString()));
                    listOfUsers = tableField.getItems();
                    listOfUsers.add(person);
                    tableField.setItems(listOfUsers);
                }
            } else {
                System.out.println("No data");
            }
            key = true;
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }
    
    /*
    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }*/

}
