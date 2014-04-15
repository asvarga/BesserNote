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
public class NumberField extends TextField {
    
    boolean hasDecimal = false;
    
    public NumberField() {
        super();
    }
    
    public NumberField(String s) {
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
        if (s.contains(".")) {
            hasDecimal = false;
        }
    }
    
    private boolean addString(String s) {
        if (hasDecimal) {
            if (s.matches("[0-9]")) {
                return true;
            }
        } else {
            int first = s.indexOf(".");
            int last = s.lastIndexOf(".");
            if (s.matches("[0-9\\.]*") && first == last) {    // 0 or 1 '.'s
                if (s.contains(".")) {
                    hasDecimal = true;
                }
                return true;
            }
        }
        return false;
    }
}
