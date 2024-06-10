import Files.Grid;
import Files.Tiles;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        int screenWidth = 600;
        int screenHeight = 600;
        int strokeWeight = 1;

        Group root = new Group();

        Scene gameScene = new Scene(root, screenWidth, screenHeight);

        // Assuming you have a method draw_lines defined somewhere
        Grid.draw_lines(root, screenWidth, strokeWeight);
        Tiles.create_boxes(root, screenWidth);

        gameScene.setOnMouseClicked(event -> {
            if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
                double clickX = event.getX();
                double clickY = event.getY();

                int[] position = Tiles.clicked_box(clickX, clickY);
                Tiles.highlightBox(position[0], position[1]);
            }
        });

        window.setScene(gameScene);
        window.setTitle("Mine Sweeper");
        window.show();
    }
}
