/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import adminmodule.Location;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Yihao
 */
public class LocationThread extends Thread {
    ImageIcon oriImage;
    ImageIcon newImage;
    int WIDTH=150;
    int HEIGHT=150;
    private ArrayList<Location> locationlist;
    public LocationThread(ArrayList<Location> locationlist){
        this.locationlist=locationlist;
    }
    public void run(){
        for (Location l:locationlist){
            if (l.path.equals("null")) {
                l.image = new ImageIcon(this.getClass().getResource("/icons/Inst-Second-FulClr-Rev.gif")).getImage().getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH);
                //System.out.println("null");
            } else {
                try {
                    oriImage=new ImageIcon(l.path);
                    newImage=new ImageIcon(oriImage.getImage().getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH));
                    l.image = newImage.getImage();
                } catch(Exception e) {
                    l.image = new ImageIcon(this.getClass().getResource("/icons/Inst-Second-FulClr-Rev.gif")).getImage().getScaledInstance(150, 150, BufferedImage.SCALE_SMOOTH);
                }
            }
        }
    }
    
}
