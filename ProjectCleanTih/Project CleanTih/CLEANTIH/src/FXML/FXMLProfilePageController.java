

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLProfilePageController implements Initializable {
    @FXML
    private Pane logOutPane;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfFullname;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextArea taAddress;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    public static User loggedInUser;

    @FXML
    private void handleButtonLogOut(ActionEvent event) {
        FadeTransition logOutPaneTransition = new FadeTransition(Duration.millis(500), logOutPane);
        logOutPaneTransition.setFromValue(0.0);
        logOutPaneTransition.setToValue(1.0);
        logOutPaneTransition.setCycleCount(1);

        // Make the logOutPane visible before playing the animation
        logOutPane.setVisible(true);

        // Play the animation
        logOutPaneTransition.play();
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

    public void handleButtonYes(ActionEvent event) {
        loadScene("FXMLLoginPage.fxml", event);
    }

    public void handleButtonBack(ActionEvent event) {
        loadScene("FXMLMainPage.fxml", event);
    }

    public void handleButtonNo(ActionEvent event) {
        FadeTransition logOutPaneTransition = new FadeTransition(Duration.millis(500), logOutPane);
        logOutPaneTransition.setFromValue(1.0);
        logOutPaneTransition.setToValue(0.0);
        logOutPaneTransition.setCycleCount(1);
        logOutPaneTransition.setOnFinished(e -> logOutPane.setVisible(false));
        logOutPaneTransition.play();
    }

    @FXML
    private void handleButtonEdit(ActionEvent event) {
        tfUsername.setEditable(true);
        tfEmail.setEditable(true);
        tfFullname.setEditable(true);
        dpBirthdate.setEditable(true);
        taAddress.setEditable(true);
        tfNumber.setEditable(true);

        btnEdit.setDisable(true);
        btnSave.setDisable(false);
    }

@FXML
    private void handleButtonSave(ActionEvent event) {
        loggedInUser.setUsername(tfUsername.getText());
        loggedInUser.setEmail(tfEmail.getText());
        loggedInUser.setFullname(tfFullname.getText());
        loggedInUser.setBirthdate(dpBirthdate.getValue());
        loggedInUser.setAddress(taAddress.getText());
        loggedInUser.setPhoneNumber(tfNumber.getText());

        saveUserDataToFile(loggedInUser);

        tfUsername.setEditable(false);
        tfEmail.setEditable(false);
        tfFullname.setEditable(false);
        dpBirthdate.setEditable(false);
        taAddress.setEditable(false);
        tfNumber.setEditable(false);

        btnEdit.setDisable(false);
        btnSave.setDisable(true);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("User data updated successfully!");
        alert.showAndWait();
    }

    private void saveUserDataToFile(User user) {
        XStream xstream = new XStream(new DomDriver());
        xstream.allowTypes(new Class[]{User.class});
        xstream.alias("user", User.class);
    
        try {
            // Load existing users from file
            File file = new File("DataMasyarakat.xml");
            if (!file.exists()) {
                file.createNewFile();
            }
    
            ArrayList<User> usersList;
            if (file.length() != 0) {
                try (FileReader reader = new FileReader(file)) {
                    Object object = xstream.fromXML(reader);
                    if (object instanceof ArrayList) {
                        usersList = (ArrayList<User>) object;
                    } else {
                        usersList = new ArrayList<>();
                    }
                }
            } else {
                usersList = new ArrayList<>();
            }
    
            // Print existing users for debugging
            System.out.println("Existing users: ");
            for (User u : usersList) {
                System.out.println(u.getUsername());
            }
    
            // Update user in the list
            boolean userUpdated = false;
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).getUsername().equals(user.getUsername())) {
                    usersList.set(i, user);
                    userUpdated = true;
                    break;
                }
            }
    
            if (!userUpdated) {
                usersList.add(user);
            }
    
            // Print updated users for debugging
            System.out.println("Updated users: ");
            for (User u : usersList) {
                System.out.println(u.getUsername());
            }
    
            // Save updated list back to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                xstream.toXML(usersList, fos);
            }
    
            System.out.println("Your edit saved successfully.");
    
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to save user data.");
            alert.showAndWait();
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logOutPane.setVisible(false);

        if (loggedInUser != null) {
            tfUsername.setText(loggedInUser.getUsername());
            tfEmail.setText(loggedInUser.getEmail());
            tfFullname.setText(loggedInUser.getFullname());
            dpBirthdate.setValue(loggedInUser.getBirthdate());
            taAddress.setText(loggedInUser.getAddress());
            tfNumber.setText(loggedInUser.getPhoneNumber());

            tfUsername.setEditable(false);
            tfEmail.setEditable(false);
            tfFullname.setEditable(false);
            dpBirthdate.setEditable(false);
            taAddress.setEditable(false);
            tfNumber.setEditable(false);

            btnEdit.setDisable(false);
            btnSave.setDisable(true);
        } else {
            // Handle the case where the user is not found
        }
    }
}