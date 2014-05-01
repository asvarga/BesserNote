/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BEditableTab;
import bessernote.ui.BWrapPane;

/**
 *
 * @author ddliu
 */
public class BEditableTabSave {
        private BWrapPaneSave savedContent;
        private String text;
        
        BEditableTabSave(BEditableTab tab){
            savedContent = new BWrapPaneSave((BWrapPane)tab.getContent());
            text = tab.getText();
        }
       
        public BEditableTab create(){
            BEditableTab returnMe = new BEditableTab(text);
            returnMe.setContent(savedContent.create());
            return returnMe;
        }
}
