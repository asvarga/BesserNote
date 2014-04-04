/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 *
 * @author avarga
 */
public class NodeMaker extends Region {
    
    NodeMaker() {
        
        setWidth(200);
        setHeight(300);
        
        Rectangle r = new Rectangle(0, 0, getWidth(), getHeight()); 
        r.setFill(Color.GREY);
        
        Text t = new Text(10, 20, "Node Creation GUI\nAdd shit here.\nPress Escape.");
        
        getChildren().addAll(r, t);
    }
    
}
