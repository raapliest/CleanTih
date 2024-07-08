import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLReportPageController {

    @FXML
    private TextField tfReport;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfTime;

    @FXML
    private TextField tfLocation;

    @FXML
    private TextArea taDescription;

    @SuppressWarnings("unchecked")
    @FXML
    private void handleButtonReport(ActionEvent event) {
        String report = tfReport.getText();
        String timeText = tfTime.getText();
        String description = taDescription.getText();
        String location = tfLocation.getText();

        // Validate input fields
        if (report.isEmpty() || timeText.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Parse time input
        try {
            LocalTime time = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm"));

            // Create Report object
            Report reportData = new Report(report, LocalDate.now(), time, description, location);

            // Serialize to XML
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("reports", ArrayList.class);
            xstream.alias("report", Report.class);

            ArrayList<Report> reports;
            File xmlFile = new File("ReportData.xml");

            if (xmlFile.exists()) {
                try (FileReader reader = new FileReader(xmlFile)) {
                    reports = (ArrayList<Report>) xstream.fromXML(reader);
                } catch (IOException e) {
                    reports = new ArrayList<>();
                }
            } else {
                reports = new ArrayList<>();
            }

            reports.add(reportData);

            String xml = xstream.toXML(reports);

            try (FileWriter writer = new FileWriter("ReportData.xml")) {
                writer.write(xml);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Report saved successfully.");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to save data to XML file.");
                alert.showAndWait();
            }

        } catch (DateTimeParseException e) {
            // Handle invalid time format
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid time format. Please enter time in HH:mm format.");
            alert.showAndWait();
        }
    }

    
    @FXML
    public void handleButtonHistory(ActionEvent event) {
        loadScene("FXMLHistoryPage.fxml", event);
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
}
