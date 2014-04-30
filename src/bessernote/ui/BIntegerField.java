/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javax.swing.event.ChangeListener;

/**
 *
 * @author avarga
 */
public class BIntegerField extends TextField {
        
    public BIntegerField() {
        super();
    }
    
    public BIntegerField(String s) {
        super(s);
    }
    
    @Override 
    public void replaceText(int start, int end, String text) {
//        System.out.println(start);
//        System.out.println(end);
//        System.out.println(text);
        
        removeString(this.getText().substring(start, end));
        if (addString(text)) {
            super.replaceText(start, end, text);
        } else {
            super.replaceText(start, end, "");
        }
        
    }
 
    @Override 
    public void replaceSelection(String text) {
//        System.out.println(text);
        
        removeString(this.getSelectedText());
        if (addString(text)) {
            super.replaceSelection(text);
        } else {
            super.replaceSelection("");
        }
    }
    
    private void removeString(String s) {
        
    }
    
    private boolean addString(String s) {
        return s.matches("[0-9]*");
    }
    
    public int getInt() {
        // TODO: memoize
        try {
            return Integer.parseInt(this.getText());
        } catch (Exception e) {
            return 0;
        }
    }
}
