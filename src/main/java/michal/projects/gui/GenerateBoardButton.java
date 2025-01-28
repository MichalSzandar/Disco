package michal.projects.gui;

import java.util.Random;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import michal.projects.board_elements.Board;
import michal.projects.board_elements.Tile;
import michal.projects.loggers.MyLogger;
import michal.projects.utils.MyAlerts;
import michal.projects.utils.Utils;

public class GenerateBoardButton extends Button {
    public GenerateBoardButton(String name, TextField speedField, TextField pField, TextField mField, TextField nField, VBox root, Random random) {
        super(name);
        setOnAction(event -> {
            try {
                int m = Integer.parseInt(mField.getText());
                int n = Integer.parseInt(nField.getText());
                double propability = Double.parseDouble(pField.getText());
                int speed = Integer.parseInt(speedField.getText());

                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

                double width = primaryScreenBounds.getWidth()/(double)m;
                double height = (primaryScreenBounds.getHeight()-100)/(double)n;

                if (m <= 0 || n <= 0) {
                    MyLogger.logger.log(Level.WARNING, "invalid input");
                    MyAlerts.displayErrorAlert("invalid input");
                    throw new NumberFormatException("invalid input");
                }

                for (Node node : root.getChildren()) {
                    if (node instanceof Board) {
                        Board parent = (Board)node;
                        parent.stopAllTiles();
                        Platform.runLater(()->parent.getChildren().clear());
                        Platform.runLater(()->root.getChildren().remove(node));
                    }
                }
                         
                Board board = new Board(m, n, propability, speed);
                MyLogger.logger.log(Level.INFO, "Board created");

                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        Rectangle rec = new Rectangle(width, height);
                        rec.setFill(Utils.generateRandomColor(random));
                        board.add(rec, i, j);
                        Tile cell = new Tile(i, j, rec, random, board.getLock());
                        board.setTile(i, j, cell);
                        MyLogger.logger.log(Level.INFO, "tile created on x:" + i + ", y:" + j);
                    }
                }
                root.getChildren().add(board);

                for (int i = 0; i< m; i++) {
                    for (int j = 0; j<n; j++) {
                        Thread thread = new Thread(board.getTile(i, j));
                        thread.setDaemon(true);
                        thread.start();
                    }
                }
            } catch (NumberFormatException e) {
                MyLogger.logger.log(Level.WARNING, "Invalid input. Please enter positive integers for M and N.");
                MyAlerts.displayErrorAlert("Invalid input. Please enter positive integers for number of rows and columns");
            }
        });
    }
}
