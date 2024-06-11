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

        Grid.draw_lines(root, screenWidth, strokeWeight);
        Tiles[][] boxes = Tiles.create_boxes(root, screenWidth);

        gameScene.setOnMouseClicked(event -> {
            double click_x = event.getX();
             double click_y = event.getY();

            if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {

                Tiles.clicked_box(root, click_x, click_y, boxes);
            }

            if (event.getButton().equals(javafx.scene.input.MouseButton.SECONDARY)){ 
                Tiles.set_flag(root, click_x, click_y, boxes); 
            }

        });

        window.setScene(gameScene);
        window.setTitle("Mine Sweeper");
        window.show();
    }
}
