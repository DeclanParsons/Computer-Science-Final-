package Files;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Grid {
    // simple 18x18 grid
    public static void draw_lines(Group root, int screen_width, int stroke_weight) {
        // assuming box 
        double line_dist = screen_width / 18.0; // Correct calculation for line distance

        for (double i = line_dist; i < screen_width; i += line_dist) {
            // horizontal line 
            Line line_x = new Line(0, i, screen_width, i);
            line_x.setStroke(Color.BLACK); // Set the color of the line
            line_x.setStrokeWidth(stroke_weight);
            // vertical line 
            Line line_y = new Line(i, 0, i, screen_width);
            line_y.setStroke(Color.BLACK); // Set the color of the line
            line_y.setStrokeWidth(stroke_weight);

            root.getChildren().add(line_x);
            root.getChildren().add(line_y);
        }

        System.out.println(line_dist);
    }
}
