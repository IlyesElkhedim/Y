package fr.univ_lyon1.info.m1.microblog.config;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.Y;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class responsible for loading initial configuration data into the application model.
 * This includes loading users and messages from a JSON file.
 */
public class InitialLoadConfig {

    private final Y y;

    /**
     * Constructor that initializes the configuration loader with the application model.
     *
     * @param y the instance of the application's model
     */
    public InitialLoadConfig(final Y y) {
        this.y = y;
    }

    /**
     * Loads the JSON file and collects the informations to create the messages.
     *
     * @param configFilePath Path to the configuration file in JSON format.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void loadConfiguration(final String configFilePath) throws IOException {
        JSONParser jsonP = new JSONParser();

        try {
            JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(configFilePath));

            List<String> users = (List<String>) jsonO.get("users");
            for (String userId : users) {
                y.createUser(userId);
            }
    
            List<Map<String, String>> messages = (List<Map<String, String>>) jsonO.get("messages");
            for (Map<String, String> messageData : messages) {
                String user = messageData.get("user");
                String content = messageData.get("content");
                y.add(new Message(user, content));
                y.notifyObservers();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
