
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
    private Stage primaryStage;

    private Scene game_scene;
    private Scene lose_scene;
    private Scene win_scene; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.primaryStage = window;
        
        int screen_width = 600;
        int screen_height = 650;
        int stroke_weight = 1;

        Group root = new Group();
        game_scene = new Scene(root, screen_width, screen_height);

        Group lose_group = new Group(); 
        lose_scene = new Scene(lose_group, screen_width, screen_height); 

        Group win_group = new Group(); 
        win_scene = new Scene(win_group, screen_width, screen_height); 

        Grid.draw_lines(root, screen_width, stroke_weight);
        Tiles[][] boxes = Tiles.create_boxes(root, screen_width);

        int bomb_count = Tiles.b_num(); 

        // Create and position the Text element for displaying remaining flags
        flags_remaining_text = new Text("Flags remaining: " + bomb_count);
        flags_remaining_text.setFont(new Font(30)); // Adjust font size as needed
        flags_remaining_text.setLayoutX(325); // Position X
        flags_remaining_text.setLayoutY(screen_height - 15); // Position Y 30 pixels above the bottom
        root.getChildren().add(flags_remaining_text);

        // Create and position the Text element for displaying bomb count
        Text bombCountText = new Text("Bomb count: " + bomb_count);
        bombCountText.setFont(new Font(30)); // Adjust font size as needed
        bombCountText.setLayoutX(20); // Position X
        bombCountText.setLayoutY(screen_height - 15); // Position Y 60 pixels above the bottom
        root.getChildren().add(bombCountText);

        // Handle mouse clicks
        game_scene.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Tiles.clicked_box(root, clickX, clickY, boxes);

                if(Tiles.lose_con()){ 
                    lose_screen();
                }

                if(Tiles.win_con()){ 
                    win_screen(); 
                }
            }

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                flags_remaining = Tiles.set_flag(root, clickX, clickY, boxes);
                updateFlagsRemainingText();
            }
        });

        window.setScene(game_scene);
        window.setTitle("Mine Sweeper");
        window.show();
    }

    private void updateFlagsRemainingText() {
        flags_remaining_text.setText("Flags remaining: " + flags_remaining);
    }

    public void lose_screen() { 
        Text lose_text = new Text("You Lose!");
        lose_text.setFont(new Font(50));
        lose_text.setLayoutX(180); // Adjust position as needed
        lose_text.setLayoutY(325); // Adjust position as needed
        ((Group) lose_scene.getRoot()).getChildren().add(lose_text);
        primaryStage.setScene(lose_scene); 
    }

    public void win_screen() { 
        Text win_text = new Text("You Win!");
        win_text.setFont(new Font(50));
        win_text.setLayoutX(180); // Adjust position as needed
        win_text.setLayoutY(325); // Adjust position as needed
        ((Group) win_scene.getRoot()).getChildren().add(win_text);
        primaryStage.setScene(win_scene); 
    }
}

