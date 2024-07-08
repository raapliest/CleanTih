import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private XStream xstream;

    public XmlParser() {
        xstream = new XStream(new DomDriver());
        xstream.alias("reports", ArrayList.class);
        xstream.alias("report", Report.class);
    }

    @SuppressWarnings("unchecked")
    public List<Report> parseReports(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return (List<Report>) xstream.fromXML(reader);
        }
    }

    public void saveReports(List<Report> reports, String filePath) throws IOException {
        String xml = xstream.toXML(reports);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(xml);
        }
    }

    public User loadUser(String filePath) throws IOException {
        File xmlFile = new File(filePath);
        try (FileReader reader = new FileReader(xmlFile)) {
            return (User) xstream.fromXML(reader);
        }
    }
}