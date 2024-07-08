import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXMLPolicePageController
 */
public class FXMLPolicePageController implements Initializable {
    @FXML
    private TableView<Report> tableView;

    @FXML
    private TableView<Report> tableView1;

    @FXML
    private TableColumn<Report, String> tvReport;

    @FXML
    private TableColumn<Report, LocalDate> tvDate;

    @FXML
    private TableColumn<Report, LocalTime> tvTime;

    @FXML
    private TableColumn<Report, String> tvDescription;

    @FXML
    private TableColumn<Report, String> tvLocation;

    @FXML
    private TableColumn<Report, String> tvReport1;

    @FXML
    private TableColumn<Report, LocalDate> tvDate1;

    @FXML
    private TableColumn<Report, LocalTime> tvTime1;

    @FXML
    private TableColumn<Report, String> tvDescription1;

    @FXML
    private TableColumn<Report, String> tvLocation1;

    private final XmlParser xmlParser = new XmlParser(); // Replace with your XML parser class

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize TableView columns
        tvReport.setCellValueFactory(new PropertyValueFactory<>("report"));
        tvDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tvTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tvDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        tvReport1.setCellValueFactory(new PropertyValueFactory<>("report"));
        tvDate1.setCellValueFactory(new PropertyValueFactory<>("date"));
        tvTime1.setCellValueFactory(new PropertyValueFactory<>("time"));
        tvDescription1.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvLocation1.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Load data into TableView
        loadReportData();
    }

    private void loadReportData() {
        try {
            List<Report> reports = xmlParser.parseReports("ReportData.xml");
            tableView.getItems().addAll(reports);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to load report data from XML.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

        @FXML
    public void handleButtonBack(ActionEvent event){
        loadScene("FXMLLoginPage.fxml", event);
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
    public void handleButtonClear(ActionEvent event) {
        // Copy data from tableView to tableView1
        tableView1.getItems().addAll(tableView.getItems());

        // Clear tableView
        tableView.getItems().clear();
    }
}