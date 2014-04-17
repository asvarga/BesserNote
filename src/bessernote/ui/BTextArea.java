/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dan
 */
public class BTextArea  extends TextArea{
    public BTextArea(){
        this("");
    }
    
    public BTextArea(String s){
        super(s);
        setOnDragDetected(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e){
                BTextArea.this.relocate(, USE_PREF_SIZE);
            }
        });
    }
    
}
