import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

public class FXMLLoginPageController implements Initializable {
    @FXML
    private Pane myPane;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

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
            // Handle specific types of exceptions
            String errorMessage = "Failed to load " + fxmlFile + ". ";
            if (e.getCause() != null) {
                errorMessage += "Cause: " + e.getCause().getMessage();
            } else {
                errorMessage += "Error message: " + e.getMessage();
            }
    
            e.printStackTrace(); // Print the stack trace for debugging
    
            // Show an alert with the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonSignUp(ActionEvent event) {
        loadScene("FXMLSignUp.fxml", event);
    }

    @FXML
    public void handleButtonLogin(ActionEvent event) {
        // Get the user's input data
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        // Check if the user is a police officer
        if (username.equals("police") && password.equals("police123")) {
            // Redirect to the police page
            loadScene("FXMLPolicePage.fxml", event);
        } else {
            // Read the XML file and deserialize the ArrayList of User objects
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("users", ArrayList.class);
            xstream.alias("user", User.class);

            ArrayList<User> users;
            File xmlFile = new File("DataMasyarakat.xml");

            if (xmlFile.exists()) {
                try (FileReader reader = new FileReader(xmlFile)) {
                    users = (ArrayList<User>) xstream.fromXML(reader);

                    // Regular user authentication
                    boolean isValid = false;
                    User loggedInUser = null;
                    for (User user : users) {
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            isValid = true;
                            loggedInUser = user;
                            break;
                        }
                    }

                    if (isValid) {
                        // Login successful, load the main application page
                        FXMLProfilePageController.loggedInUser = loggedInUser; // Pass the logged-in user
                        loadScene("FXMLMainPage.fxml", event);
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid username or password.");
                        alert.showAndWait();
                    }
                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to read data from XML file.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Data file does not exist.");
                alert.showAndWait();
            }
        }
    }
}
