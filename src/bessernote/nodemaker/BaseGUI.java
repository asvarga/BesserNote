/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    
    public BaseGUI() {
        
        super();
        setAlignment(Pos.BOTTOM_LEFT);
        
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
    }
    
    public BaseGUI(double spacing) {
        
        super(spacing);
        setAlignment(Pos.BOTTOM_LEFT);
        setPadding(new Insets(spacing));
        
        setStyle("-fx-background-color: grey;"
                + "-fx-border-color: white;"); 
        setAlignment(Pos.CENTER);
    }

    public Node getNode() {
        return null;
    }

    public void editNode(Node n) {
        
    }
    
}
