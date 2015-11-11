/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import java.awt.Graphics;
import java.awt.Image;
import java.sql.SQLException;
import javax.swing.ImageIcon;

/**
 *
 * @author GaoYifei
 */
public class HigginsPanel extends MainPanel{
    private Image background;
    
    public HigginsPanel() throws SQLException{
          this.init();
          System.out.print("Higgins !");
    }
     public void init(){
        background = new ImageIcon(this.getClass().getResource("/maps/refined_higgins_floor_1.png")).getImage();
	this.setVisible(true);
    }
     
     
     
      public void paintComponent(Graphics g) {  
	       g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);  

	    } 
}
