/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextArea;
import bessernote.nodemaker.placement.PlacementGUI;
/**
 *
 * @author Dan
 */
public class TextAreaGUI extends BaseGUI{

    public TextAreaGUI (double spacing) {
        super(spacing);

        Text t = new Text("--- TextArea GUI ---");

         PlacementGUI placement = new PlacementGUI(spacing);
         

        getChildren().addAll(t, placement);
    }

    @Override
    public Node getNode() {
//        Pane pane = new Pane();
//        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
//        pane.setPrefSize(100, 100);
//        return pane;
        TextArea  t = new TextArea();
        System.out.println("text area generated");
        return t;
    }
}
