/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BEditableTab;
import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
import bessernote.ui.BWrapPane;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 */
public class Saver {
    
    public File file;
    FileWriter fw;
    BufferedWriter bw;
    XStream xstream = new XStream();
    
    public Saver(File file) throws IOException{
        this.file = file;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        xstream.alias("scrollPane", BScrollPaneSave.class);
        xstream.alias("tab", BEditableTabSave.class);
        xstream.alias("tabPane", BTabPaneSave.class);
        xstream.alias("wrapPane", BWrapPaneSave.class);
        xstream.alias("textarea", BTextAreaSave.class);
        xstream.alias("root", RootSave.class);
        }
    
    /**
     * save() is a recursive function that takes as initial input the root of the scene graph. It returns a RootSave of the scene graph.
     * Every node gets converted into a save object. This is then written to XML.
     * @param node
     */
    public void save(Node node) throws IOException{
        RootSave saveObj = new RootSave((Pane) node);
        //xstream.toXML(saveObj, fw);
        xstream.toXML(saveObj, fw);
    }
    

    
    
}
