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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLHistoryPageController implements Initializable {

    @FXML
    private TableView<Report> tableView;

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

    private final XmlParser xmlParser = new XmlParser(); // Replace with your XML parser class

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize TableView columns
        tvReport.setCellValueFactory(new PropertyValueFactory<>("report"));
        tvDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tvTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tvDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

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
    public void handleButtonReport(ActionEvent event) {
        loadScene("FXMLReportPage.fxml", event);
    }

    @FXML
    public void handleButtonFAQ(ActionEvent event) {
        loadScene("FXMLFAQPage.fxml", event);
    }

    @FXML
    public void handleButtonHotline(ActionEvent event) {
        loadScene("FXMLHotlinePage.fxml", event);
    }

    @FXML
    public void handleButtonBack(ActionEvent event) {
        loadScene("FXMLMainPage.fxml", event);
    }

    @FXML
    private void handleButtonDelete(ActionEvent event) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getItems().remove(selectedIndex);
            updateXML(); // Panggil metode untuk menyimpan ulang ke XML setelah penghapusan dari TableView
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Report Selected");
            alert.setContentText("Please select a report in the table to delete.");
            alert.showAndWait();
        }
    }

    private void updateXML() {
        List<Report> reports = tableView.getItems(); // Ambil semua item dari TableView
    
        try {
            xmlParser.saveReports(reports, "ReportData.xml"); // Simpan ulang ke XML
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to update XML file.");
            alert.showAndWait();
            e.printStackTrace();
        }
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
}
