//import files and the java fx libraries 
package Files;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Grid {
    //creating the 18x18 grid 
    public static void draw_lines(Group root, int screen_width, int stroke_weight) {

        //create 18x18 based on screen height and width (height - text space)
        double line_dist = screen_width / 18.0; 

        for (double i = 0; i <= screen_width; i += line_dist) {
            //horizontal line 
            Line line_x = new Line(0, i, screen_width, i);
            line_x.setStroke(Color.BLACK);
            line_x.setStrokeWidth(stroke_weight);

            //vertical line 
            Line line_y = new Line(i, 0, i, screen_width);
            line_y.setStroke(Color.BLACK);
            line_y.setStrokeWidth(stroke_weight);

            //add lines to the root 
            root.getChildren().add(line_x);
            root.getChildren().add(line_y);
        }
    }
}
