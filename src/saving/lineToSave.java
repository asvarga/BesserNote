/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import javafx.scene.shape.LineTo;

/**
 *
 * @author ddliu
 */
public class lineToSave implements PathElementSaveable{
    double x,y;

    public lineToSave(LineTo line){
        x = line.getX();
        y = line.getY();
    }

    public LineTo create(){
        return new LineTo(x, y);
    }

}
