package michal.projects;

import javafx.application.Application;
import javafx.stage.Stage;
import michal.projects.gui.GUI;
import michal.projects.loggers.MyLogger;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        MyLogger.loggerConfig();
        launch(args);
    }

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) {
        new GUI(stage);
    }
}
