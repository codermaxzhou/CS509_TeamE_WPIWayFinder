/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

/**
 *
 * @author Emma
 */
import adminmodule.Location;
import adminmodule.LocationEdit;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Enter extends  JPopupMenu{
    
        private RightBar rightBar ;
        private MapView mapView;
        
        
    
        public Enter(Location location) {
        JMenuItem item = new JMenuItem("Enter");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightBar.inner();
            }
        });
        this.add(item);
    }
        
    public void setRightBar(RightBar rightBar) {
        this.rightBar = rightBar;
    }
}
