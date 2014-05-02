/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class NodeGUI extends BaseGUI {

    ComboBox combo;
    ShowOneGUI show1;
    public Button cancelButton;
    public Button createButton;
    
//    Pane target;
//    double tx;
//    double ty;

    public NodeGUI(double spacing) {

        super(null, spacing);
        _top = this;
        Text t = new Text("--- Node Creation GUI ---");

        combo = new ComboBox();
        //combo.getItems().addAll("ScrollPane", "Pane", "WrapPane", "Label", "HBox", "VBox", "TextArea", "TabPane", "FlashCard");
        combo.getItems().addAll("WrapPane", "ScrollPane", "Pane", "TextArea", "TabPane", "FlashCard");
        //"Pane" "Label" "Hbox" "VBox" deleted from indices 1, 3, 4, 5
        combo.setValue("WrapPane");
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                show1.showGUI(t1);
                _top.autosize();
            }
        });

        show1 = new ShowOneGUI(_top);
        show1.addGUI("WrapPane", new WrapPaneGUI(_top, spacing));
        show1.addGUI("ScrollPane", new ScrollPaneGUI(_top, spacing));
        show1.addGUI("Pane", new PaneGUI(_top, spacing));
        show1.addGUI("Label", new LabelGUI(_top, spacing));
        //show1.addGUI("HBox", new HBoxGUI(_top, spacing));
        //show1.addGUI("VBox", new VBoxGUI(_top, spacing));
        show1.addGUI("TextArea", new TextAreaGUI(_top, spacing));
        show1.addGUI("TabPane", new TabPaneGUI(_top, spacing));
        show1.addGUI("FlashCard", new FlashCardGUI2(_top, spacing));
        
        HBox h = new HBox(spacing);
        cancelButton = new Button("Cancel");
        createButton = new Button("Create");
        h.getChildren().addAll(cancelButton, createButton);
        h.prefWidthProperty().bind(this.prefWidthProperty());
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        HBox.setHgrow(createButton, Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        createButton.setMaxWidth(Double.MAX_VALUE);
        getChildren().addAll(t, combo, show1, h);
    }
    
    @Override
    public Node getNode(BUndoManager undoManager) {
        return show1.getNode(undoManager);
    }
    
    @Override
    public void editNode(Node n) {
        //n.setStyle("-fx-background-color: #" + color);
        show1.editNode(n);
    }
    
    @Override
    public void setPos(double x, double y) {
        show1.setPos(x, y);
    }
    
    @Override
    public void setSize(double x, double y) {
        show1.setSize(x, y);
    }
    
    /*
    Sets the value for the combo box. Called in dockingMenu.
    */
    public void setValue(String mode){
        combo.setValue(mode);
    }
    
    @Override
    public void setColor(String color){
        show1.setColor(color);
    }
    
//    public void setTarget(Pane node) {
//        target = node;
//    }
//    public void setTarget(double x, double y) {
//        tx = x;
//        ty = y;
//    }
//    public void setTarget(Pane node, double x, double y) {
//        target = node;
//        tx = x;
//        ty = y;
//    }

}
