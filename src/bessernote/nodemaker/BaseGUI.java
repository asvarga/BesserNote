/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.beans.binding.Bindings;
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
    
    protected Node _top;
    
    public BaseGUI(Node top) {
        super();
        _top = top;
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
    }
    
    public BaseGUI(Node top, double spacing) {
        super(spacing);
        _top = top;
        setPadding(new Insets(spacing));
        
        setStyle("-fx-background-color: grey;"
                + "-fx-border-color: white;"); 
        setAlignment(Pos.CENTER);
    }
    
    public BaseGUI(Node top, double spacing, boolean padded) {
        super(spacing);
        _top = top;
        setStyle("-fx-background-color: grey;"); 
        setAlignment(Pos.CENTER);
        if (padded) {
            setPadding(new Insets(spacing));  
            setStyle("-fx-border-color: white;");
        }
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
