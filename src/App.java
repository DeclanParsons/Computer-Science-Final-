import Files.Grid;
import Files.Tiles;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        int screen_width = 600;
        int screen_height = 600;
        int stroke_weight = 1;

        Group root = new Group();
        
        Scene game_scene = new Scene(root, screen_width, screen_height);  // Use the variables here

        // Assuming you have a method draw_lines defined somewhere
        Grid.draw_lines(root, screen_width, stroke_weight);
        Tiles.create_boxes(screen_width);

        game_scene.setOnMouseClicked(event -> { // click pos
            if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
                double click_x = event.getX();
                double click_y = event.getY();
                System.out.println("Left mouse clicked at: x=" + click_x + ", y=" + click_y);
            }
        });

        window.setScene(game_scene);
        window.setTitle("Mine Sweeper");
        window.show();
    }
}
