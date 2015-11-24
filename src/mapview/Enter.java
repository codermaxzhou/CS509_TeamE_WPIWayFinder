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
import adminmodule.Map;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Enter extends  JPopupMenu{
    
        private RightBar rightBar ;
        private MapView mapView;
        private ArrayList<Map> mapList;
        
        
        
    
        public Enter(Location location, MainPanel mainPanel) {
        JMenuItem item = new JMenuItem("Enter");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                rightBar.inner();
                
               // mainPanel.reloadMap(location.point.map.mapID);
                rightBar.setIsCampus(false);
                rightBar.repaint();
                mapList = mainPanel.mapModel.getMapList();
                for(Map m : mapList){
                    if(location.name.equals(m.name)){
                        mainPanel.reloadMap(m.mapID);
                    }
                }
                
                
            }
        });
        this.add(item);
    }
        
    public void setRightBar(RightBar rightBar) {
        this.rightBar = rightBar;
    }
}
