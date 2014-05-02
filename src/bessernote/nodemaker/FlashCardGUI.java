/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.deprecated.FlashCard;
import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BTextArea;
import javafx.scene.Node;
import javafx.scene.text.Text;
import undo.BUndoManager;

/**
 *
 * @author Dan
 */
public class FlashCardGUI extends BaseGUI{
    
    PlacementGUIRegion placement;
    public FlashCardGUI(Node top, double spacing){
        super(top,spacing);
        
        placement = new PlacementGUIRegion(top, spacing);
        Text t = new Text("--- TextArea GUI ---");


        getChildren().addAll(t, placement);
    }
    
    @Override
    public Node getNode(BUndoManager undoManager) {
//        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
//        pane.setPrefSize(100, 100);
//        return pane;
//        t.addEventFilter(MouseEvent.MOUSE_PRESSED, 
//            new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    System.out.println("click");
//                    t.requestFocus();
//                };
//            }
//        );
        FlashCard f = new FlashCard();
        
        System.out.println("flash card generated");
        return f;
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
