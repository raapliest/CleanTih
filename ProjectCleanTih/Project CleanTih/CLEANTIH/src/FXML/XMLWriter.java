import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLWriter {
    private XStream xstream = new XStream(new DomDriver());

    public void saveUser(User user, String filePath) {
        List<User> users = readUsers(filePath);
        users.add(user);
        try (FileWriter writer = new FileWriter(filePath)) {
            xstream.toXML(users, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> readUsers(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return (List<User>) xstream.fromXML(file);
        } else {
            return new ArrayList<>();
        }
    }
}
