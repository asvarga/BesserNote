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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author ddliu
 * Loader takes as input an XML file. It deserializes the XML file into a RootSave.
 */
public class Loader {
    
    XStream xstream = new XStream();
    RootSave loaded;
    Pane rootSheet;
    
    public Loader(File file){
        xstream.alias("scrollPane", BScrollPaneSave.class);
        xstream.alias("tab", BEditableTabSave.class);
        xstream.alias("tabPane", BTabPaneSave.class);
        xstream.alias("wrapPane", BWrapPaneSave.class);
        xstream.alias("textarea", BTextAreaSave.class);
        xstream.alias("root", RootSave.class);
        loaded = (RootSave) xstream.fromXML(file);
        loaded.printChildren();
    }
    
    /*
    toSheet() takes loaded and converts it into the pane (rootSheet) that we then insert into BesserNote.
    */
    public void toSheet(){
        //Create the higheset level object
        rootSheet = loaded.create();
        //Create the children.
        for(Savable saveChild: loaded.getChildren()){
            //All the children are Savable
            //rootSheet.getChildren().add(toSheetHelper(saveChild));
        }
        
        
    }
    
    public Parent unpack(Savable saveObj){
        //Unpack the top level
        Parent unpacked = saveObj.create();
        if(saveObj.getChildren().isEmpty()){
            //terminate
            return unpacked;
        }
        else if (!saveObj.getChildren().isEmpty()){
            //Recur
            for(Savable saveChild: saveObj.getChildren()){
                //Recur and add the child to the top level object
                //unpacked.getChildren().add(saveChild.create());
            }
        }
        //The whole thing is over, return the created object.
        return unpacked;
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
