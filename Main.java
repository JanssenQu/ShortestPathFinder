import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    private boolean[][] walls;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // define the panes
        BorderPane border = new BorderPane();

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(5);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        VBox labels = new VBox();

        // labels
        Label source = new Label("Source: S");
        Label destination = new Label("Destination: D");
        labels.getChildren().add(source);
        labels.getChildren().add(destination);

        // create message
        TextField message = new TextField();
        message.setEditable(false);
        message.setPrefWidth(250);
        message.setText("Click on the tiles to build walls!");

        // title
        Image image = new Image(new FileInputStream("Shortest-Path-Finder.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        imageView.setX(150);

        walls = new boolean[10][12];
        AtomicBoolean isRan = new AtomicBoolean(false);

        // create grid full of buttons
        for (int i = 0; i < 12; i++) {

            for (int j = 0; j < 10; j++) {

                ToggleButton toggleBtn = new ToggleButton();

                // label and disable the source and destination tiles
                if (i == 0 && j == 0) {
                    toggleBtn.setText("S");
                    toggleBtn.setDisable(true);
                }

                else if (i == 11 && j == 9) {
                    toggleBtn.setText("D");
                    toggleBtn.setDisable(true);
                }

                grid.add(toggleBtn, i, j);
            }
        }

        // create the clear grid button
        Button runBtn = new Button("run");

        runBtn.setOnAction(value ->  {

            if (!isRan.get()) {

                isRan.set(true);

                // save all the walls position in a 2d array
                for (Node child : grid.getChildren()) {

                    ToggleButton b = (ToggleButton) child;

                    if (b.isSelected()) {
                        walls[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)] = true;
                    }
                }

                PathFinder pathFinder = new PathFinder(walls);

                // find the tiles that are part of the shortest path and draw it red
                for (Node child : grid.getChildren()) {

                    ToggleButton b = (ToggleButton) child;

                    for (PathFinder.Cell cell : pathFinder.getPath()) {

                        if (GridPane.getRowIndex(child) == cell.getRowNum() && GridPane.getColumnIndex(child) == cell.getColNum()) {
                            b.setStyle("-fx-background-color: #FF0000");
                        }
                    }
                }

                // found a path if it the num of moves is greater than 0
                if (pathFinder.getNumMoves() != 0) {
                    message.setText("Used " + pathFinder.getNumMoves() + " tiles to get to the destination.");
                }
                else {
                    message.setText("Impossible to find a path!");
                }

            }
            else {
                message.setText("Clear the grid!");
            }

        });
        hBox.getChildren().add(runBtn);


        Button clearBtn = new Button("clear");

        clearBtn.setOnAction(value ->  {

            if (isRan.get()) {

                // untoggle all the tiles
                for (Node child : grid.getChildren()) {

                    if (child instanceof ToggleButton) {

                        ToggleButton b = (ToggleButton) child;
                        b.setStyle("");
                        b.setSelected(false);
                    }
                }
                isRan.set(false);
                message.setText("Click on the tiles to build walls!");;
            }
            else {
                message.setText("The grid is already cleared!");
            }

        });
        hBox.getChildren().add(clearBtn);
        hBox.getChildren().add(message);

        // assign the panes to their respected border
        border.setTop(imageView);
        border.setLeft(labels);
        border.setCenter(grid);
        border.setBottom(hBox);

        // scene and window
        primaryStage.setTitle("ShortestPathFinder");
        Scene scene = new Scene(border, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("maze.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}