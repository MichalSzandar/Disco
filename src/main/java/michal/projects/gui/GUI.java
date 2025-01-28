package michal.projects.gui;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI 
{
    private Random random;

    public GUI(Stage stage)
    {
        random = new Random();
        Label mLabel = new Label("Enter columns:");
        TextField mField = new TextField();
        Label nLabel = new Label("Enter rows:");
        TextField nField = new TextField();
        Label speedLabel = new Label("Enter speed: ");
        TextField speedField = new TextField();
        Label pLabel = new Label("Enter propability: ");
        TextField pField = new TextField();

        HBox inputBox = new HBox(10, mLabel, mField, nLabel, nField, pLabel, pField, speedLabel, speedField);
        inputBox.setPadding(new Insets(10));

        VBox root = new VBox(10, inputBox);
        GenerateBoardButton generateButton = new GenerateBoardButton("Generate Board", speedField, pField, mField, nField, root, random);
        inputBox.getChildren().add(generateButton);
        Scene scene = new Scene(root, 600, 400);

        stage.setScene(scene);
        stage.setTitle("Dyskoteka");
        stage.show();
    }
}
