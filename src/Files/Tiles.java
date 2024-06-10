package Files;

import java.util.*;

public class Tiles {

    private boolean bomb; 
    private boolean clicked; 
    private double x_pos; 
    private double y_pos; 


    public Tiles(boolean bomb, boolean clicked, double x_pos, double y_pos){ 
        this.bomb = bomb; 
        this.clicked = clicked; 
        this.x_pos = x_pos; 
        this.y_pos = y_pos; 
    }


    public static void create_boxes(int screen_width){ 

        double box_width = screen_width / 18; 
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

                double x_pos = (i * box_width) + 1; 
                double y_pos = (j * box_width) + 1; 

                boxes[i][j] = new Tiles(bomb, false, x_pos, y_pos); 
                System.out.println("X Pos: " + x_pos + "Y Pos: " + y_pos);
            }
        }

        System.out.println("Bomb Count: " + bomb_count);
    }
}

/*
public class Line {
    
       private int slope; 
       private int intercept; 
   
       //create simple class with name and weight of object 
       public Line(int slope, int intercept){ 
           this.slope = slope; 
           this.intercept = intercept; 
    }

 */