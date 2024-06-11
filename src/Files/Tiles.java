package Files;

import java.util.Random;
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

    public Tiles(boolean bomb, boolean clicked, double x_pos, double y_pos, double box_size, boolean has_flag){ 
        this.bomb = bomb; 
        this.clicked = clicked; 
        this.x_pos = x_pos; 
        this.y_pos = y_pos; 
        this.box_size = box_size; 
        this.has_flag = has_flag; 
    }

    public static Tiles[][] create_boxes(Group root, int screen_width){ 

        box_size = screen_width / 18.0; 
        Tiles[][] boxes = new Tiles[18][18]; 

        int max = 8; 
        int min = 1; //1 in 10 chance to have bomb 

        boolean bomb; 
        int bomb_count = 0; 

        Random rand = new Random();

        for(int i = 0; i < 18; i++){ 
            for(int j = 0; j < 18; j++){ 

                if((rand.nextInt(max - min + 1) + min) == 1){ 
                    bomb = true; 
                    bomb_count++; 
                    
                }else{ 
                    bomb = false; 
                }

                double x_pos = i * box_size; 
                double y_pos = j * box_size; 

                boxes[i][j] = new Tiles(bomb, false, x_pos, y_pos, box_size, false); 
                System.out.println("X Pos: " + x_pos + " Y Pos: " + y_pos);
            }
        }
        
        System.out.println("Bomb Count: " + bomb_count);
        return boxes; 
    }

    public static void clicked_box(Group root, double click_x, double click_y, Tiles[][] boxes){ 
        System.out.println("Click Position X: " + click_x + " Click Position Y: " + click_y);

        int x = (int)(click_x / box_size); 
        int y = (int)(click_y / box_size); 

        System.out.println("Possible Box: " + x + " " + y);

        if(boxes[x][y].clicked){
            return;
        } else {
            boxes[x][y].clicked = true; 
            Rectangle rectangle = new Rectangle(boxes[x][y].x_pos, boxes[x][y].y_pos, box_size, box_size);

            if(boxes[x][y].bomb && boxes[x][y].has_flag == false){ 
                System.out.println("you dead");
            } else { 
                rectangle.setFill(Color.GREEN);

                int num_bombs = near_bombs(boxes, x, y);

                Text text = new Text(String.valueOf(num_bombs));
                text.setFont(Font.font("Verdana", FontWeight.BOLD, box_size / 2));
                text.setFill(Color.BLACK);
                // Center the text in the rectangle
                text.setX(boxes[x][y].x_pos + (box_size - text.getBoundsInLocal().getWidth()) / 2);
                text.setY(boxes[x][y].y_pos + (box_size + text.getBoundsInLocal().getHeight()) / 2);

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


    public static void set_flag(Group root, double click_x, double click_y, Tiles[][] boxes){ 
        int x = (int)(click_x / box_size); 
        int y = (int)(click_y / box_size); 

        boxes[x][y].has_flag = true; 

        Rectangle rectangle = new Rectangle(boxes[x][y].x_pos, boxes[x][y].y_pos, box_size, box_size);

        rectangle.setFill(Color.ORANGE);

        root.getChildren().add(rectangle);


    }




}
