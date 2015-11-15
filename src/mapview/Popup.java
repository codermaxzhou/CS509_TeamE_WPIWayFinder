/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

/**
 *
 * @author T440
 */
import adminmodule.Location;
import adminmodule.LocationEdit;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Popup extends  JPopupMenu{
    
        public Popup(Location location) {
        JMenuItem item = new JMenuItem("Enter");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        this.add(item);

    }
    
    
    
    
    
    
    
}
