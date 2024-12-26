package michal.projects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class MyAlerts {
    public static void displayErrorAlert(String msg)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(msg);
        alert.setTitle("Error");
        alert.showAndWait();
    }
}
