package Files;

import java.util.Random;

import App;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;

public class Tiles {

    private boolean bomb; 
    private boolean clicked; 
    private boolean has_flag; 
    private double x_pos; 
    private double y_pos; 
    private static double box_size; 
    private static Tiles[][] boxes;

    static int bomb_count = 0; 
    static int flags_remaining = 0;

    public Tiles(boolean bomb, boolean clicked, double x_pos, double y_pos, double box_size, boolean has_flag){ 
        this.bomb = bomb; 
        this.clicked = clicked; 
        this.x_pos = x_pos; 
        this.y_pos = y_pos; 
        this.box_size = box_size; 
        this.has_flag = has_flag; 
    }

    public static int b_num(){ 
        return bomb_count; 
    }


    public static Tiles[][] create_boxes(Group root, int screen_width){ 
        box_size = screen_width / 18.0; 
        Tiles[][] boxes = new Tiles[18][18]; 

        int max = 8; 
        int min = 1; // 1 in 10 chance to have bomb 

        Random rand = new Random();

        for(int i = 0; i < 18; i++){ 
            for(int j = 0; j < 18; j++){ 

                boolean bomb = (rand.nextInt(max - min + 1) + min) == 1;
                if(bomb) { 
                    bomb_count++; 
                }

                double x_pos = i * box_size; 
                double y_pos = j * box_size; 

                boxes[i][j] = new Tiles(bomb, false, x_pos, y_pos, box_size, false); 
                System.out.println("X Pos: " + x_pos + " Y Pos: " + y_pos);
            }
        }
        
        flags_remaining = bomb_count; // Set the flags to match the bomb count
        System.out.println("Bomb Count: " + bomb_count);
        return boxes; 
    }

    public static void clicked_box(Group root, double click_x, double click_y, Tiles[][] boxes){ 
        System.out.println("Click Position X: " + click_x + " Click Position Y: " + click_y);

        int x = (int)(click_x / box_size); 
        int y = (int)(click_y / box_size); 

        System.out.println("Possible Box: " + x + " " + y);

        Tiles tile = boxes[x][y];
        Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);

        if(tile.clicked){
            if(tile.has_flag){ 
                tile.has_flag = false; 
                flags_remaining++; 
                rectangle.setFill(Color.WHITE);
            }
        } else {
            tile.clicked = true; 

            if(tile.bomb && !tile.has_flag){ 
                System.out.println("you dead");
                rectangle.setFill(Color.RED);
            } else { 
                rectangle.setFill(Color.GREEN);

                int num_bombs = near_bombs(boxes, x, y);

                Text text = new Text(String.valueOf(num_bombs));
                text.setFont(Font.font("Verdana", FontWeight.BOLD, box_size / 2));
                text.setFill(Color.BLACK);
                // Center the text in the rectangle
                text.setX(tile.x_pos + (box_size - text.getBoundsInLocal().getWidth()) / 2);
                text.setY(tile.y_pos + (box_size + text.getBoundsInLocal().getHeight()) / 2);

                root.getChildren().add(rectangle);
                root.getChildren().add(text);
            }
        }
    }

    public static int near_bombs(Tiles[][] boxes, int x, int y){ 
        int num_bombs = 0; 
        int[][] surrounding = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

        for (int[] offset : surrounding) {
            int new_x = x + offset[0];
            int new_y = y + offset[1];
            
            if (new_x >= 0 && new_x < 18 && new_y >= 0 && new_y < 18) { // Bounds checking
                if (boxes[new_x][new_y].bomb) { 
                    num_bombs++; 
                }
            }
        } 

        return num_bombs; 
    }

    public static int set_flag(Group root, double click_x, double click_y, Tiles[][] boxes) { 
        int x = (int)(click_x / box_size); 
        int y = (int)(click_y / box_size); 
    
        Tiles tile = boxes[x][y];
    
        if (tile.has_flag) { // Remove flag
            tile.has_flag = false; 
            flags_remaining++;
            tile.clicked = false;
    
            Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);
            rectangle.setFill(Color.WHITE);
            root.getChildren().add(rectangle);
    
        } else if (!tile.clicked && flags_remaining > 0) { // Place flag
            tile.has_flag = true; 
            tile.clicked = true; 
            flags_remaining--;
    
            Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);
            rectangle.setFill(Color.ORANGE);
            root.getChildren().add(rectangle);
        }

        return flags_remaining; 
    }
}
