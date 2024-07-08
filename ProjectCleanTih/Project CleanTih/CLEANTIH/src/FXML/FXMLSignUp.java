import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLSignUp implements Initializable{
    @FXML
    private Pane myPane;

    @FXML
    private TextField tfUsername;
    
    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfConfirm;

    @FXML
    private TextField tfFullname;

    @FXML
    private DatePicker dpBirthdate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(myPane);
        translate.setByX(275);
        translate.setDuration(Duration.millis(1000));
        translate.play();
    }

    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load " + fxmlFile + ". Please try again.");
            alert.showAndWait();
        }
    }

    public void handleButtonBack(ActionEvent event){
        loadScene("FXMLLoginPage.fxml", event);
    }

    @SuppressWarnings("unchecked")
    public void handleButtonCreate(ActionEvent event) {
        // Get the user's input data
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String phoneNumber = tfNumber.getText();
        String password = tfPassword.getText();
        String confirmPassword = tfConfirm.getText();
        String fullname = tfFullname.getText();
        LocalDate birthdate = dpBirthdate.getValue();

        // Check if the password and confirm password match
        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password and confirm password do not match.");
            alert.showAndWait();
            return;
        }

        // Create a User object to hold the data
        User user = new User(username, email, phoneNumber, password, confirmPassword, fullname, birthdate);
        // Read the existing XML file and deserialize the ArrayList of User objects
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("users", ArrayList.class);
        xstream.alias("user", User.class);

        ArrayList<User> users;
        File xmlFile = new File("DataMasyarakat.xml");

        if (xmlFile.exists()) {
            try (FileReader reader = new FileReader(xmlFile)) {
                users = (ArrayList<User>) xstream.fromXML(reader);
            } catch (IOException e) {
                users = new ArrayList<>(); // create a new list if there's an error reading the file
            }
        } else {
            users = new ArrayList<>(); // create a new list if the file doesn't exist
        }

        // Add the new user data to the list
        users.add(user);

        // Serialize the updated list back to the XML file
        String xml = xstream.toXML(users);

        // Write the updated XML to the file
        try (FileWriter writer = new FileWriter("DataMasyarakat.xml")) {
            writer.write(xml);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to save data to XML file.");
            alert.showAndWait();
        }

        // Load the login page
        loadScene("FXMLLoginPage.fxml", event);
    }
}