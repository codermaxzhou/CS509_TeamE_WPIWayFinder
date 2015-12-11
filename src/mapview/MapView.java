/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


//import aurelienribon.slidinglayout.SLPanel;





public class MapView extends JFrame{
        
        private MapModel mapModel = new MapModel();
	private MainPanel mainPanel = new MainPanel(this);
	private RightSideBar rightSideBar = new RightSideBar();
        
        private SecRightSideBar secRightSideBar = new SecRightSideBar();
        private RightBar rightBar = new RightBar();
        private JLayeredPane lpane = new JLayeredPane();
        
        private JPanel panelBlue = new JPanel();
    
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
                MapView mapView = new MapView();
                
    
		
	}
	// construction method 
	public MapView() throws SQLException{
	    // default panel load
//            MainPanel mainPanel = new MainPanel();
	    this.init();

	}
        public void init() throws SQLException{
            this.setPreferredSize(new Dimension(1150, 800));
            this.setLayout(new BorderLayout());
            mainPanel.setBounds(0, 0, 1000, 800);
            this.add(lpane, BorderLayout.CENTER);
            lpane.setBounds(0, 0, 1000, 800);
            //rightSideBar.setPreferredSize(new Dimension(150, 800));
            rightBar.setPreferredSize(new Dimension(150, 800));
            //this.getContentPane().add(rightSideBar, BorderLayout.EAST);
            this.add(rightBar, BorderLayout.EAST);
            getSecRightSideBar().setBounds(850, 0, 150, 800);
            
        panelBlue.setBackground(Color.BLUE);
        panelBlue.setBounds(0, 0, 1000, 800);
        panelBlue.setOpaque(true);
            //this.getContentPane().add(getSecRightSideBar(), BorderLayout.CENTER);
            lpane.add(mainPanel, new Integer(0), 0);
            lpane.add(getSecRightSideBar(), new Integer(1), 0);
            getSecRightSideBar().setVisible(false);

           

            Dimension d = new Dimension(1300, 850);
            this.setSize(d);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.addMouseListener(mainPanel);
            
           // communicate bettween class 
            rightSideBar.mainPanel = mainPanel;
            rightSideBar.secRightSideBar = getSecRightSideBar();
           
            rightSideBar.mapView = this;
            rightSideBar.mapModel = mapModel;
            
            
            rightBar.mainPanel = mainPanel;
            rightBar.secRightSideBar = getSecRightSideBar();
            rightBar.mapView = this;
            rightBar.mapModel = mapModel;
            
            secRightSideBar.mainPanel = mainPanel;
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

