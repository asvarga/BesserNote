/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker.placement;

import bessernote.nodemaker.BaseGUI;
import bessernote.ui.BNumberField;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class PlacementGUI extends BaseGUI {
    
    boolean chooseSize;
    
    BNumberField xNum;
    BNumberField yNum;
    BNumberField wNum;
    BNumberField hNum;
    
    public PlacementGUI(double spacing) {
        this(spacing, false);
    }
    
    public PlacementGUI(double spacing, boolean chooseSize) {
        
        super(spacing);
        
        this.chooseSize = chooseSize;
        
        Text t = new Text("--- Placement GUI ---");
        
        HBox h = new HBox();
        h.getChildren().add(new Text("X: "));
        xNum = new BNumberField("0");
        h.getChildren().add(xNum);
        
        HBox h2 = new HBox();
        h2.getChildren().add(new Text("Y: "));
        yNum = new BNumberField("0");
        h2.getChildren().add(yNum);
        
        getChildren().addAll(t, h, h2);
        
        if (chooseSize) {
            HBox h3 = new HBox();
            h3.getChildren().add(new Text("Width: "));
            wNum = new BNumberField("100");
            h3.getChildren().add(wNum);

            HBox h4 = new HBox();
            h4.getChildren().add(new Text("Height: "));
            hNum = new BNumberField("100");
            h4.getChildren().add(hNum);
            
            getChildren().addAll(h3, h4);
        }
    }
    
    @Override
    public void editNode(Node n) {
        n.setLayoutX(xNum.getNum());
        n.setLayoutY(yNum.getNum());
        if (chooseSize) {
            ((Region) n).setPrefWidth(wNum.getNum());
            ((Region) n).setPrefHeight(hNum.getNum());
        }
    }
    
    @Override
    public void setPos(double x, double y) {
        xNum.setText(Double.toString(x));
        yNum.setText(Double.toString(y));
    }
    
    @Override
    public void setSize(double x, double y) {
        if (chooseSize) {
            wNum.setText(Double.toString(x));
            hNum.setText(Double.toString(y));
        }
    }
    
}