//import files package 
package Files;

//import random for bomb creator 
import java.util.Random;

//import javafx libraries 
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;


public class Tiles {

    //init all the variables used in the object 
    //has bomb? 
    private boolean bomb; 
    //is clicked?
    private boolean clicked; 
    //has flag? 
    private boolean has_flag; 
    //boxes[x][] pos 
    private double x_pos; 
    //boxes[][y] pos
    private double y_pos; 
    //size of box (grid lines)
    private static double box_size; 
    //object init 
    private static Tiles[][] boxes;

    //init bomb count 
    static int bomb_count = 0; 
    //init flag count 
    static int flags_remaining = 0; 
    //init num tiles clicked
    static int tiles_clicked; 

    //win game or lose game bools 
    static boolean game_lose = false; 
    static boolean game_win = false; 

    //constructor for object 
    public Tiles(boolean bomb, boolean clicked, double x_pos, double y_pos, double box_size, boolean has_flag){ 
        this.bomb = bomb; 
        this.clicked = clicked; 
        this.x_pos = x_pos; 
        this.y_pos = y_pos; 
        this.box_size = box_size; 
        this.has_flag = has_flag; 
    }

    //return bomb count for App
    public static int b_num(){ 
        return bomb_count; 
    }

    //return lose bool for App
    public static boolean lose_con(){ 
        return game_lose; 
    }

    //return win bool for App 
    public static boolean win_con(){ 
        return game_win; 
    }

    //create the Tiles[][] object to create all boxes 
    public static Tiles[][] create_boxes(Group root, int screen_width){ 
        bomb_count = 0; // Reset bomb count each time boxes are created

        box_size = screen_width / 18.0; 

        //init size (18x18 grid)
        Tiles[][] boxes = new Tiles[18][18]; 

        //max and min variables for rng (1 in 6 chance to have tile with bomb)
        int max = 6; 
        int min = 1; 

        Random rand = new Random();

        for(int i = 0; i < 18; i++){ 
            for(int j = 0; j < 18; j++){ 
                boolean bomb = (rand.nextInt(max - min + 1) + min) == 1;
                if(bomb) { 
                    bomb_count++; 
                }

                double x_pos = i * box_size; 
                double y_pos = j * box_size; 

                //create the instance of objext at position, give bomb if rng 
                boxes[i][j] = new Tiles(bomb, false, x_pos, y_pos, box_size, false); 
                System.out.println("X Pos: " + x_pos + " Y Pos: " + y_pos);
            }
        }
        //init the flags remaining to be equal to the bomb count 
        flags_remaining = bomb_count; 

        //tiles that need to be clicked to win = 324 (18x18) - bomb count 
        tiles_clicked = 324 - bomb_count; 

        //return the object to App
        return boxes; 
    }

    //function for left click 
    public static void clicked_box(Group root, double click_x, double click_y, Tiles[][] boxes){ 

        //if the click is within the game boundaries 
        if(click_y < 600){ 

            //get the boxes[x][y] position of the click 
            int x = (int)(click_x / box_size); 
            int y = (int)(click_y / box_size); 

            //Use the tile at position 
            Tiles tile = boxes[x][y];

            //create a rectangle object for later use 
            Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);

            if(tile.clicked){
                if(tile.has_flag){ 
                    //if clicked and had flag remove the flag
                    //increase the flag count by 1
                    //recolour the tile 
                    tile.has_flag = false; 
                    flags_remaining++; 
                    rectangle.setFill(Color.WHITE);
                }
            }else{
                //if it wasnt clicked, set true 
                tile.clicked = true; 

                //if the tile has bomb and doesnt have flag then trigger lose condition 
                if(tile.bomb && !tile.has_flag){ 
                    game_lose = true; 

                } else { 
                    //if false then reduce from tiles clicked counter and colour tile green 
                    tiles_clicked--; 
                    rectangle.setFill(Color.GREEN);

                    //check for near bombs given pos and return as int 
                    int num_bombs = near_bombs(boxes, x, y);

                    //display the number of bombs surrounding object as text on box 
                    Text text = new Text(String.valueOf(num_bombs));
                    text.setFont(Font.font("Verdana", FontWeight.BOLD, box_size / 2));
                    text.setFill(Color.BLACK);
                    // Center the text in the rectangle
                    text.setX(tile.x_pos + (box_size - text.getBoundsInLocal().getWidth()) / 2);
                    text.setY(tile.y_pos + (box_size + text.getBoundsInLocal().getHeight()) / 2);

                    //add rectangle colour and text to root 
                    root.getChildren().add(rectangle);
                    root.getChildren().add(text);
                }
            }
            
            //and if there are no more tiles to click you win the game 
            if(tiles_clicked == 0){ 
                game_win = true; 
            }
    }
}


    //checking the near bombs 
    public static int near_bombs(Tiles[][] boxes, int x, int y){ 
        //number of surrounding bombs variable 
        int num_bombs = 0; 
        
        //positions around the center to search, top left, top middle, top right...
        int[][] surrounding = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

        //for every position create the new x and new y position 
        for (int[] offset : surrounding) {
            int new_x = x + offset[0];
            int new_y = y + offset[1];
            
            //check if the new x and y are withing boundaries 
            if (new_x >= 0 && new_x < 18 && new_y >= 0 && new_y < 18) { 
                //if the boxes[][] at new position has bomb increase counter 
                if (boxes[new_x][new_y].bomb) { 
                    num_bombs++; 
                }
            }
        } 
        //return after loop 
        return num_bombs; 
    }

    //setting the flag (right click)
    public static int set_flag(Group root, double click_x, double click_y, Tiles[][] boxes) { 
    
        //get boxes[][] pos
        int x = (int)(click_x / box_size); 
        int y = (int)(click_y / box_size); 
    
        //use instance of single box 
        Tiles tile = boxes[x][y];
    
        //if the tile has a flag and is clicked again 
        if (tile.has_flag) { 
            //remove flag, add to counter, remove clicked 
            tile.has_flag = false; 
            flags_remaining++;
            tile.clicked = false;
    
            //recolour rectangle 
            Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);
            rectangle.setFill(Color.WHITE);
            root.getChildren().add(rectangle);
            
            //if tile wasnt clicked and flags left 
            } else if (!tile.clicked && flags_remaining > 0) { 
                //place flag and remove from counter 
                tile.has_flag = true; 
                tile.clicked = true; 
                flags_remaining--;
        
                //update rectangle and display as orange 
                Rectangle rectangle = new Rectangle(tile.x_pos, tile.y_pos, box_size, box_size);
                rectangle.setFill(Color.ORANGE);
                root.getChildren().add(rectangle);
            }

        //return the number of flags left for the App
        return flags_remaining; 
    }
}
