/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aejedgematching;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Usuario
 */
public class TriangleShape extends Path2D.Double{
    
    public static final Color[] colors = {Color.LIGHT_GRAY, 
        new Color(255, 0, 0), 
        new Color(255, 255, 0), 
        new Color(0, 255, 0), 
        new Color(0, 255, 255), 
        new Color(0, 0, 255), 
        new Color(255, 0, 255), 

        new Color(255, 102, 102), 
        new Color(255, 255, 153), 
        new Color(102, 255, 102), 
        new Color(153, 255, 255), 
        new Color(102, 102, 255), 
        new Color(255, 153, 255), 

        new Color(204, 102, 0), 
        new Color(153, 204, 0), 
        new Color(0, 204, 102), 
        new Color(0, 153, 204), 
        new Color(102, 0, 204), 
        new Color(204, 0, 153), 
        
        new Color(51, 0, 0), 
        new Color(51, 51, 0), 
        new Color(0, 51, 0), 
        new Color(0, 51, 51), 
        new Color(0, 0, 51), 
        new Color(51, 0, 51), 
        
        Color.WHITE
        
    };

    private final int color;
    
    public TriangleShape(Point2D[] points, int color) {
        this.color = color;
        moveTo(points[0].getX(), points[0].getY());
        lineTo(points[1].getX(), points[1].getY());
        lineTo(points[2].getX(), points[2].getY());
        closePath();
    }

    public Color getColorFicha(){
        try{
            return colors[color];
        }catch(Exception ex){
            return Color.WHITE;
        }
    }
}
