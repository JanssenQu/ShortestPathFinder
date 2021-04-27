import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("ShortestPathFinder");
        BorderPane border = new BorderPane();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton toggleBtn = new ToggleButton();
                grid.add(toggleBtn, i, j);
            }
        }

        Button runBtn = new Button("RUN!");

        runBtn.setOnAction(value ->  {
            System.out.println("got to play");
            for (Node child : grid.getChildren()) {
                if (child instanceof ToggleButton) {
                    ToggleButton b = (ToggleButton) child;
                    b.setSelected(false);
                }
            }
        });
        hBox.getChildren().add(runBtn);

        border.setTop(new Text("Click on the tiles to create usable paths"));
        border.setCenter(grid);
        border.setBottom(hBox);
        Scene scene = new Scene(border, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}