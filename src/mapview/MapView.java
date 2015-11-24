/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;


import java.awt.BorderLayout;

import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


//import aurelienribon.slidinglayout.SLPanel;





public class MapView extends JFrame{
        
        private MapModel mapModel = new MapModel();
	private MainPanel mainPanel = new MainPanel(this);
	private RightSideBar rightSideBar = new RightSideBar();
        
        private SecRightSideBar secRightSideBar = new SecRightSideBar();
        private RightBar rightBar = new RightBar();
      
        
    
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
                MapView mapView = new MapView();
                
    
		
	}
	// construction method 
	public MapView() throws SQLException{
	    // default panel load
//            MainPanel mainPanel = new MainPanel();
	    this.init(mainPanel);

	}
        public void init(MainPanel panel) throws SQLException{
          
            
            
            panel.setPreferredSize(new Dimension(980, 800));

            this.getContentPane().add(panel, BorderLayout.WEST);
            //rightSideBar.setPreferredSize(new Dimension(150, 800));
            rightBar.setPreferredSize(new Dimension(150, 800));

            //this.getContentPane().add(rightSideBar, BorderLayout.EAST);
            this.getContentPane().add(rightBar, BorderLayout.EAST);
            getSecRightSideBar().setPreferredSize(new Dimension(170, 800));

            this.getContentPane().add(getSecRightSideBar(), BorderLayout.CENTER);

           

            Dimension d = new Dimension(1300, 850);
            this.setSize(d);
            this.setResizable(false);
            this.setVisible(true);
            this.addMouseListener(panel);
            
           // communicate bettween class 
            rightSideBar.mainPanel = panel;
            rightSideBar.secRightSideBar = getSecRightSideBar();
           
            rightSideBar.mapView = this;
            rightSideBar.mapModel = mapModel;
            
            
            rightBar.mainPanel = panel;
            rightBar.secRightSideBar = getSecRightSideBar();
            rightBar.mapView = this;
            rightBar.mapModel = mapModel;
            
            secRightSideBar.mainPanel = panel;
            secRightSideBar.mapModel = mapModel;
            
            mainPanel.setMapModel(mapModel);
            mainPanel.setMapView(this);
            
            this.addMouseListener(rightSideBar);
           // this.addMouseListener(secRightSideBar);
                    
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        
        public RightBar getRightBar() {
            return this.rightBar;
        }

    /**
     * @return the secRightSideBar
     */
    public SecRightSideBar getSecRightSideBar() {
        return secRightSideBar;
    }

    
}

