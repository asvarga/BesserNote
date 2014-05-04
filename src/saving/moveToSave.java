/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import javafx.scene.shape.MoveTo;

/**
 *
 * @author ddliu
 */
public class moveToSave implements PathElementSaveable{
    double x,y;

    public moveToSave(MoveTo move){
        x = move.getX();
        y = move.getY();
    }

    public MoveTo create(){
        return new MoveTo(x, y);
    }

}
