/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;



/**
 *
 * @author ddliu
 */

/**
*
* @author utente
*/
public class EditableTab extends Tab{
    
    private Label label = new Label();
    private TextField textField = new TextField();
    
    public EditableTab(String text){
      label.setText(text);
      this.setGraphic(label);
      
     label.setOnMouseClicked(new EventHandler<MouseEvent>() {  
  @Override  
  public void handle(MouseEvent event) {  
    System.out.println("Handled");
    if (event.getClickCount()==2) {  
      textField.setText(label.getText());  
      EditableTab.this.setGraphic(textField);  
      textField.selectAll();  
      textField.requestFocus();  
    }  
  }  
}); 


textField.setOnAction(new EventHandler<ActionEvent>() {  
  @Override  
  public void handle(ActionEvent event) {  
    label.setText(textField.getText());  
    EditableTab.this.setGraphic(label);  
  }  
});


textField.focusedProperty().addListener(new ChangeListener<Boolean>() {  
  @Override  
  public void changed(ObservableValue<? extends Boolean> observable,  
      Boolean oldValue, Boolean newValue) {  
    if (! newValue) {  
      label.setText(textField.getText());  
      EditableTab.this.setGraphic(label);            
    }  
  }  
});  
        
   
    }
    
    
     
}