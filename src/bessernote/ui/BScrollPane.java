/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 */
public class BScrollPane extends ScrollPane implements ChildSpecifier {
        
    public BScrollPane() {
        Pane p = new Pane();
        p.setMinWidth(200);
        p.setMinHeight(200);
        this.setContent(p);
    }
    
    @Override
    public List<Node> specifyChildren() {
        try {
            return ((Pane) this.getContent()).getChildrenUnmodifiable();
        } catch (Exception e) {
            System.out.println("ERROR: BScrollPane's content must be of type Parent.");
            return new ArrayList<>();
        }
    }
    
//    @Override
//    public ObservableList<Node> getChildren() {
//        return ((Pane) this.getContent()).getChildren();
//    }
    
    @Override
    public Node specifySelf() {
        return this;
    }

}
