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
        private SecRightSideBar secRightBar;
        private ArrayList<Map> mapList;
        
        
        
    
   public Enter(Map locationMap, Location loc, MainPanel mainPanel) {
        JMenuItem item = new JMenuItem("Enter building");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
                mapList = mainPanel.mapModel.getMapList();
                
                mainPanel.reloadMap(locationMap);  
            }
        });
        this.add(item);
        
        item = new JMenuItem("Set as start point");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.startPointField.setText(loc.name);
                mainPanel.clear = true;
                mainPanel.showSinglePin(loc.name);
            }
        });
        this.add(item);   
        
        item = new JMenuItem("Set as end point");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        mainPanel.endPointField.setText(loc.name);
                        mainPanel.clear = false;
                        mainPanel.showSinglePin(loc.name);
            }
        });
        this.add(item);   
        
        item = new JMenuItem("Save as favorite");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
                mapList = mainPanel.mapModel.getMapList();
                
                mainPanel.reloadMap(locationMap);  
            }
        });
        this.add(item);         
    }
        
   
      public Enter(Location loc, MainPanel mainPanel) {
        JMenuItem item = new JMenuItem("Set as start point");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.startPointField.setText(loc.name);
                mainPanel.clear = true;
                mainPanel.showSinglePin(loc.name);
            }
        });
        this.add(item);   
        
        item = new JMenuItem("Set as end point");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        mainPanel.endPointField.setText(loc.name);
                        mainPanel.clear = false;
                        mainPanel.showSinglePin(loc.name);
            }
        });
        this.add(item);   
        
        item = new JMenuItem("Save as favorite");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(item);         
    }
      
      
    public void setRightBar(RightBar rightBar) {
        this.rightBar = rightBar;
    }

    /**
     * @param secRightBar the secRightBar to set
     */
    public void setSecRightBar(SecRightSideBar secRightBar) {
        this.secRightBar = secRightBar;
    }
}
