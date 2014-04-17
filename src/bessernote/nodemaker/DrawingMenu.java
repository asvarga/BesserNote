/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author ddliu
 * The drawing menu which has options for lines, circles, and (later) rectangles.
 * It also has a color picker. 
 * And it switches on graphics mode.
 */
public class DrawingMenu extends VBox{
    
    public DrawingMenu() throws FileNotFoundException {
                
        super();
        setAlignment(Pos.BOTTOM_LEFT);
        
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
        
        //// Add buttons ////
        ToggleButton cursor = new ToggleButton();
        ImageView cursorImage = new ImageView(new Image(new FileInputStream("images/cursor.jpg")));
        cursor.setGraphic(cursorImage);
        
        ToggleButton line = new ToggleButton();
        ImageView lineImage = new ImageView(new Image(new FileInputStream("images/draw.jpg")));
        line.setGraphic(lineImage);
        
        ToggleButton circle = new ToggleButton();
        ImageView circleImage = new ImageView(new Image(new FileInputStream("images/circle.jpg")));
        circle.setGraphic(circleImage);
        
        //// Color Picker ////
        ColorPicker cp;
        cp = new ColorPicker();
        //color = cp.getValue();
        
        this.getChildren().addAll(cursor, line, circle, cp);
        
    }

    public Node getNode() {
        return null;
    }

    public void editNode(Node n) {
        
    }
    
    public void setPos(double x, double y) {
        
    }
    
    public void setSize(double x, double y) {
        
    }
}
    
