import Files.Grid;
import Files.Tiles;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private Text flags_remaining_text;
    private int flags_remaining; // Assuming you start with 10 flags, adjust as needed

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        
        int screenWidth = 600;
        int screenHeight = 800;
        int strokeWeight = 1;

        Group root = new Group();

        Scene gameScene = new Scene(root, screenWidth, screenHeight);

    
        Grid.draw_lines(root, screenWidth, strokeWeight);
        Tiles[][] boxes = Tiles.create_boxes(root, screenWidth);

        int bomb_count = Tiles.b_num(); 

        // Create and position the Text element for displaying remaining flags
        flags_remaining_text = new Text("Flags remaining: " + bomb_count);
        flags_remaining_text.setFont(new Font(20)); // Adjust font size as needed
        flags_remaining_text.setLayoutX(10); // Position X
        flags_remaining_text.setLayoutY(screenHeight - 30); // Position Y 30 pixels above the bottom
        root.getChildren().add(flags_remaining_text);

        // Create and position the Text element for displaying bomb count
        Text bombCountText = new Text("Bomb count: " + bomb_count);
        bombCountText.setFont(new Font(20)); // Adjust font size as needed
        bombCountText.setLayoutX(10); // Position X
        bombCountText.setLayoutY(screenHeight - 60); // Position Y 60 pixels above the bottom
        root.getChildren().add(bombCountText);

        // Handle mouse clicks
        gameScene.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Tiles.clicked_box(root, clickX, clickY, boxes);
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                flags_remaining = Tiles.set_flag(root, clickX, clickY, boxes);
                updateFlagsRemainingText();
            }
        });

        window.setScene(gameScene);
        window.setTitle("Mine Sweeper");
        window.show();
    }

    private void updateFlagsRemainingText() {
        flags_remaining_text.setText("Flags remaining: " + flags_remaining);
    }
}
