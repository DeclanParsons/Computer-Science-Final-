//import the other files 
import Files.Grid;
import Files.Tiles;

//import the java fx libraries 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    //create the flags remainging text and values 
    private Text flags_remaining_text;
    private int flags_remaining;

    //create the stage 
    private Stage primaryStage;

    //create the scenes for game, lose and win screen 
    private Scene game_scene;
    private Scene lose_scene;
    private Scene win_scene; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.primaryStage = window;
        
        //create the screen size variables 
        int screen_width = 600;
        int screen_height = 650;

        //used for the grid line strike 
        int stroke_weight = 1;

        //create groups and scenes for each init scenes 
        Group root = new Group();
        game_scene = new Scene(root, screen_width, screen_height);

        Group lose_group = new Group(); 
        lose_scene = new Scene(lose_group, screen_width, screen_height); 

        Group win_group = new Group(); 
        win_scene = new Scene(win_group, screen_width, screen_height); 

        //draw the lines of the array 
        Grid.draw_lines(root, screen_width, stroke_weight);

        //create the boxes arr with bombs spawned 
        Tiles[][] boxes = Tiles.create_boxes(root, screen_width);

        //return the bomb count from tiles to add to screen as text 
        int bomb_count = Tiles.b_num(); 

        //define everything for the flags remaining text 
        flags_remaining_text = new Text("Flags remaining: " + bomb_count);
        flags_remaining_text.setFont(new Font(30)); 
        flags_remaining_text.setLayoutX(325); 
        flags_remaining_text.setLayoutY(screen_height - 15); 
        root.getChildren().add(flags_remaining_text);

        //define everything for the bomb count text 
        Text bombCountText = new Text("Bomb count: " + bomb_count);
        bombCountText.setFont(new Font(30)); 
        bombCountText.setLayoutX(20); 
        bombCountText.setLayoutY(screen_height - 15); 
        root.getChildren().add(bombCountText);

        //mouse clicks 
        game_scene.setOnMouseClicked(event -> {
            double clickX = event.getX();
            double clickY = event.getY();

            //if left click, click the box at (x, y) pos. If bomb die. If all pos clicked win. 
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Tiles.clicked_box(root, clickX, clickY, boxes);

                if(Tiles.lose_con()){ 
                    lose_screen();
                }

                if(Tiles.win_con()){ 
                    win_screen(); 
                }
            }

            //if right click, place flag and update text 
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                flags_remaining = Tiles.set_flag(root, clickX, clickY, boxes);
                updateFlagsRemainingText();
            }
        });

        //set defualt scenes 
        window.setScene(game_scene);
        window.setTitle("Mine Sweeper");
        window.show();
    }

    //update the flag counter text 
    private void updateFlagsRemainingText() {
        flags_remaining_text.setText("Flags remaining: " + flags_remaining);
    }

    //setting the lose / win screen based on win / lose condition 
    public void lose_screen() { 
        Text lose_text = new Text("You Lose!");
        lose_text.setFont(new Font(50));
        lose_text.setLayoutX(180); 
        lose_text.setLayoutY(325); 
        ((Group) lose_scene.getRoot()).getChildren().add(lose_text);
        primaryStage.setScene(lose_scene); 
    }

    public void win_screen() { 
        Text win_text = new Text("You Win!");
        win_text.setFont(new Font(50));
        win_text.setLayoutX(180); 
        win_text.setLayoutY(325);
        ((Group) win_scene.getRoot()).getChildren().add(win_text);
        primaryStage.setScene(win_scene); 
    }
}

