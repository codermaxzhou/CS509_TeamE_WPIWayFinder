/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ToolTipImage extends JPanel {
    private Image img;
   // private String defaultpath="";
    
    public ToolTipImage (Image img, int x, int y) {
        this.setMinimumSize(new Dimension(x, y));
        this.setPreferredSize(new Dimension(x, y));
        this.setMaximumSize(new Dimension(x, y));
        
        this.img = img;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(img != null) g.drawImage(img, 0, 0, null); 
 
    }
}
