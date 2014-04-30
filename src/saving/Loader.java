/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.BesserNote;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author ddliu
 * Loader takes as input an XML file. It deserializes the XML file into a RootSave.
 */
public class Loader {
    
    XStream xstream = new XStream();
    RootSave loaded;
    
    public Loader(File file){
        xstream.alias("scrollPane", BScrollPaneSave.class);
        xstream.alias("tab", BEditableTabSave.class);
        xstream.alias("tabPane", BTabPaneSave.class);
        xstream.alias("wrapPane", BWrapPaneSave.class);
        xstream.alias("textarea", BTextAreaSave.class);
        xstream.alias("root", RootSave.class);
        loaded = (RootSave) xstream.fromXML(file);
    }
    
    /*
    loadNew() launches a new instance of BesserNote with the objects deserialized from the XML file.
    */
    public void loadNew(){
        Application app2 = new BesserNote();
        Stage anotherStage = new Stage();
        try {
            app2.start(anotherStage);
        } catch (Exception ex) {
            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
        
    }
    
    
}
