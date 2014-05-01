/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.deprecated;

import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Dan
 */
public class FlashCard  extends Pane{
   String front;
   String back;
   boolean frontSide;
   Text text;
   
   
    public FlashCard(){
        this.front = "";
        this.back = "";
        this.text = new Text(front);
        this.frontSide = true;
        this.text.addEventFilter(KeyEvent.KEY_TYPED,new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                
            }
            
            
        });
    }
    
    /**
     * Displays the string on the opposing side
     */
    public void flip(){
        if(this.frontSide){
            this.text.setText(back);
        }
        else{
            this.text.setText(front);
        }
        this.frontSide = !frontSide;
    }
    
    
    
    
    
    
}
