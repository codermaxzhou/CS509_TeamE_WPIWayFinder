/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Location;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author xiemingchen
 */
public class LocationThread extends Thread{
    ImageIcon oriImage;
    ImageIcon newImage;
    int WIDTH=50;
    int HEIGHT=50;
    private ArrayList<Location> locationlist;
    public LocationThread(ArrayList<Location> locationlist){
        this.locationlist=locationlist;
    }
    public void run(){
        for (Location l:locationlist){
            oriImage=new ImageIcon(l.path);
            newImage=new ImageIcon(oriImage.getImage().getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH));
            l.image = newImage.getImage();
            System.out.println("multithread working");
            
        }
        
        
        
        
    }
    
    
}
