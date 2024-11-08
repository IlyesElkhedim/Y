package fr.univ_lyon1.info.m1.microblog;

import java.io.IOException;

import fr.univ_lyon1.info.m1.microblog.config.InitialLoadConfig;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) {
        final Y y = new Y();
        JfxView v = new JfxView(y, stage, 600, 600);
        y.addView(v);
        v.addMessage(null, "Hello");
        //y.createExampleMessages();

        InitialLoadConfig configLoader = new InitialLoadConfig(y);
        try {
            configLoader.loadConfiguration("src/resources/Messages.json"); 
        } catch (IOException e) {
            System.err.println("Failed to load initial configuration: " + e.getMessage());
        }

        // Second view (uncomment to activate)
        // y.addView(new JfxView(y, new Stage(), 400, 400));
    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
