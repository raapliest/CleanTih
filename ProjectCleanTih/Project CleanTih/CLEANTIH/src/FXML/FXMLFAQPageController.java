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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLFAQPageController implements Initializable{

    @FXML
    private Pane paneKlitih;

    @FXML
    boolean paneDownKlitih;

    @FXML
    private Pane paneReport;

    @FXML
    boolean paneDownReport;

    @FXML 
    private Pane paneHotline;

    @FXML
    boolean paneDownHotline;

    @FXML
    public void handleButtonHistory(ActionEvent event) {
        loadScene("FXMLHistoryPage.fxml", event);
    }

    @FXML
    public void handleButtonReport(ActionEvent event) {
        loadScene("FXMLReportPage.fxml", event);
    }

    @FXML
    public void handleButtonHotline(ActionEvent event) {
        loadScene("FXMLHotlinePage.fxml", event);
    }

    @FXML
    public void handleButtonBack(ActionEvent event){
        loadScene("FXMLMainPage.fxml", event);
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

    @FXML
    public void handleButtonKlitih(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(1));
        transition.setNode(paneKlitih);
        if (paneDownKlitih) {
            transition.setToY(-2000);
            paneDownKlitih = false;
        }
        else{
            transition.setToY(0);
            paneDownKlitih = true;
        }
        transition.play();
    }

    @FXML
    public void handleButtonHReport(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(1));
        transition.setNode(paneReport);
        if (paneDownReport) {
            transition.setToY(-2000);
            paneDownReport = false;
        }
        else{
            transition.setToY(0);
            paneDownReport = true;
        }
        transition.play();
    }

    @FXML
    public void handleButtonWHotline(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(1));
        transition.setNode(paneHotline);
        if (paneDownHotline) {
            transition.setToY(-2000);
            paneDownHotline = false;
        }
        else{
            transition.setToY(0);
            paneDownHotline = true;
        }
        transition.play();
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        paneKlitih.setTranslateY(-2000);
        paneReport.setTranslateY(-2000);
        paneHotline.setTranslateY(-2000);
    }

    
}