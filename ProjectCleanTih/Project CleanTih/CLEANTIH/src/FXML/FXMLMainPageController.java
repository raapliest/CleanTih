

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLMainPageController implements Initializable{
    @FXML
    private Pane myPane;

    @FXML
    boolean isPaneDown;

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
    public void handleButtonProfile(ActionEvent event){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(myPane);
        if (isPaneDown) {
            transition.setToY(-500); // Move the pane up
            isPaneDown = false;
        } else {
        transition.setToY(0); // Move the pane down
        isPaneDown = true;
    }
    transition.play();    

    }

    @FXML
    public void handleButtonReport(ActionEvent event){
        loadScene("FXMLReportPage.fxml", event);
    }

    @FXML
    public void handleButtonHistory(ActionEvent event){
        loadScene("FXMLHistoryPage.fxml", event);
    }

    @FXML
    public void handleButtonFAQ(ActionEvent event){
        loadScene("FXMLFAQPage.fxml", event);
    }

    @FXML
    public void handleButtonHotline(ActionEvent event){
        loadScene("FXMLHotlinePage.fxml", event);
    }

    @FXML
    public void handleButtonMoreProfile(ActionEvent event){
        loadScene("FXMLProfilePage.fxml", event);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myPane.setTranslateY(-500); // Initialize the pane to be off-screen
    }
}
