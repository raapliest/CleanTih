import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FXMLHotlinePageController implements Initializable {
    @FXML
    private Pane paneMessagePolice;

    @FXML
    private Pane paneMessageAmbulance;

    @FXML
    private Pane paneCallPolice;

    @FXML
    boolean paneDownMessagePolice;

    @FXML
    boolean paneDownMessageAmbulance;

    @FXML
    boolean paneCallDownPolice;

    @FXML 
    private ImageView loadingPolice;

    @FXML
    public void handleButtonHistory(ActionEvent event) {
        loadScene("FXMLHistoryPage.fxml", event);
    }

    @FXML
    public void handleButtonFAQ(ActionEvent event) {
        loadScene("FXMLFAQPage.fxml", event);
    }

    @FXML
    public void handleButtonReport(ActionEvent event) {
        loadScene("FXMLReportPage.fxml", event);
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
    public void buttonMessagePolice(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(paneMessagePolice);
        if (paneDownMessagePolice) {
            transition.setToY(-2000);
            paneDownMessagePolice = false;
        }
        else{
            transition.setToY(0);
            paneDownMessagePolice = true;
        }
        transition.play();

    }

    @FXML
    public void buttonMessageAmbulance(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(paneMessageAmbulance);
        if (paneDownMessageAmbulance) {
            transition.setToY(-2000);
            paneDownMessageAmbulance = false;
        }
        else{
            transition.setToY(0);
            paneDownMessageAmbulance = true;
        }
        transition.play();

    }

    @FXML
    public void buttonCallPolice(ActionEvent event){
        TranslateTransition transition =new TranslateTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(paneCallPolice);
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(loadingPolice);
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        
        if (paneCallDownPolice) {
            transition.setToY(-2000);
            paneCallDownPolice = false;
        }
        else{
            transition.setToY(0);
            paneCallDownPolice = true;
        }
        transition.play();
        rotate.play();

    }

    @Override
    public void initialize(URL urg0, ResourceBundle arg1){
        paneMessagePolice.setTranslateY(-2000);
        paneMessageAmbulance.setTranslateY(-2000);
        paneCallPolice.setTranslateY(-2000);
    }

    
    
}
