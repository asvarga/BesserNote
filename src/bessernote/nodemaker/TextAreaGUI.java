/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextArea;
import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dan
 */
public class TextAreaGUI extends BaseGUI {
    
    PlacementGUIRegion placement;

    public TextAreaGUI (double spacing) {
        super(spacing);

        Text t = new Text("--- TextArea GUI ---");

        placement = new PlacementGUIRegion(spacing);
         

        getChildren().addAll(t, placement);
    }

    @Override
    public Node getNode() {
//        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
//        pane.setPrefSize(100, 100);
//        return pane;
        TextArea  t = new TextArea();
//        t.addEventFilter(MouseEvent.MOUSE_PRESSED, 
//            new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    System.out.println("click");
//                    t.requestFocus();
//                };
//            }
//        );
        System.out.println("text area generated");
        return t;
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
    }
    
    @Override
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
    
    @Override
    public void setSize(double x, double y) {
        placement.setSize(x, y);
    }
}
