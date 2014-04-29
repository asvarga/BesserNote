/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
import bessernote.ui.BWrapPane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 *
 * @author ddliu
 */
public class Saver {
    
    public File file;
    FileWriter fw;
    BufferedWriter bw;
    
    
    public Saver(File file) throws IOException{
        this.file = file;
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
    }
    /**
     * save() is a recursive function that takes as initial input the root of the scene graph. It returns a RootSave of the scene graph.
     * Every node gets converted into a save object. This is then written to XML.
     * @param node
     */
    public void save(Object node) throws IOException{
            if(node instanceof BTabPane){
                bw.write((new BTabPaneSave((BTabPane)node)).toString());
            }
            else if(node instanceof BTextArea){
                bw.write((new BTextAreaSave((BTextArea)node)).toString());
            }
            else if(node instanceof BScrollPane){
                bw.write((new BScrollPaneSave((BScrollPane)node)).toString());
            }
            else if (node instanceof BWrapPane){
                bw.write((new BWrapPaneSave((BWrapPane)node)).toString());
            }
            /*
            else if(node instanceof FlashCard){
                
            }
            */
            
        if (node instanceof Node){
            //Terminate
        }
        else if (node instanceof Parent){
            //Recur on all children
        }
        else{
            throw new IllegalArgumentException("Not a node or a parent.");
        }
        
        bw.close();
    }

    
    
}
