/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ToolTipImage extends JPanel {
    private BufferedImage img;
    
    public ToolTipImage (String path) {
        this.setMinimumSize(new Dimension(150, 150));
        this.setPreferredSize(new Dimension(150, 150));
        this.setMaximumSize(new Dimension(150, 150));
        
        try {                
          img = ImageIO.read(new File(path));
        } catch (IOException ex) { }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);         
    }
}
