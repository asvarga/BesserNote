/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Dan
 */
public class BTextArea  extends TextArea implements ChildSpecifier {
        
    public BTextArea(){
        this("");
        this.setStyle("-fx-background-color: #663666");
        setOnDragDetected(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
//                BTextArea.this.relocate(, USE_PREF_SIZE);
            }
        });
    }
    
    public BTextArea(String s){
        super(s);
        setOnDragDetected(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
//                BTextArea.this.relocate(, USE_PREF_SIZE);
            }
        });
    }
    
    @Override
    public List<Node> specifyChildren() {
        return new ArrayList<Node>();
    }

    @Override
    public Node specifySelf() {
        return this;
    }
    
}
