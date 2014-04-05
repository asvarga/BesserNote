/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class BaseGUI extends VBox {
    
    public BaseGUI(double spacing) {
        
        super(spacing);
        setAlignment(Pos.BOTTOM_LEFT);
        setPadding(new Insets(spacing));
        //this.setMargin(this, Insets.EMPTY);
        
        setStyle("-fx-background-color: grey;"
                + "-fx-border-color: white;"); 
        
    }
    
}
