<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Dijkstra;
import adminmodule.Edge;
import adminmodule.Location;
import adminmodule.Map;
import adminmodule.MapInfo;

import adminmodule.Point;
import adminmodule.PopupMenu;
import java.awt.BasicStroke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import static javafx.scene.paint.Color.color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import jdbc.JDBC;


//import aurelienribon.slidinglayout.SLPanel;




public class MapView extends JFrame{
    
	
	
        
    
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
                MapView mapView = new MapView();
                
    
		
	}
	// construction method 
	public MapView() throws SQLException{
	    // default panel load
            MainPanel mainPanel = new MainPanel();
	    this.init(mainPanel);

	}
        public void init(MainPanel panel) throws SQLException{
          
            
            
            panel.setPreferredSize(new Dimension(980,800));
            
            this.getContentPane().add(panel, BorderLayout.WEST);
            RightSideBar rightSideBar = new RightSideBar();
            rightSideBar.setPreferredSize(new Dimension(150,800));
            
            this.getContentPane().add(rightSideBar, BorderLayout.EAST);
	    SecRightSideBar secRightSideBar = new SecRightSideBar();
            secRightSideBar.setPreferredSize(new Dimension(170,800));
           
            this.getContentPane().add(secRightSideBar, BorderLayout.CENTER);
            
            
            HigginsPanel higginsPanel = new HigginsPanel();
            
            Dimension d = new Dimension(1300, 850);
            this.setSize(d);
            this.setResizable(false);
            this.setVisible(true);
            
            this.addMouseListener(panel);
            
           // communicate bettween class 
            rightSideBar.mainPanel = panel;
            rightSideBar.secRightSideBar = secRightSideBar;
            rightSideBar.higginsPanel = higginsPanel;
            rightSideBar.mapView = this;
            
            secRightSideBar.mainPanel = panel;
             
            
            this.addMouseListener(rightSideBar);
           // this.addMouseListener(secRightSideBar);
                    
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
 
}

=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Dijkstra;
import adminmodule.Edge;
import adminmodule.Location;
import adminmodule.Map;
import adminmodule.MapInfo;

import adminmodule.Point;
import adminmodule.PopupMenu;
import java.awt.BasicStroke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import static javafx.scene.paint.Color.color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import jdbc.JDBC;


//import aurelienribon.slidinglayout.SLPanel;





public class MapView extends JFrame{
    
	private MainPanel mainPanel = new MainPanel();
	private RightSideBar rightSideBar = new RightSideBar();
        private SecRightSideBar secRightSideBar = new SecRightSideBar();
        private MapModel mapModel = new MapModel();
        
    
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
            rightSideBar.setPreferredSize(new Dimension(150, 800));

            this.getContentPane().add(rightSideBar, BorderLayout.EAST);
            secRightSideBar.setPreferredSize(new Dimension(170, 800));

            this.getContentPane().add(secRightSideBar, BorderLayout.CENTER);

            HigginsPanel higginsPanel = new HigginsPanel();

            Dimension d = new Dimension(1300, 850);
            this.setSize(d);
            this.setResizable(false);
            this.setVisible(true);
            this.addMouseListener(panel);
            
           // communicate bettween class 
            rightSideBar.mainPanel = panel;
            rightSideBar.secRightSideBar = secRightSideBar;
            rightSideBar.higginsPanel = higginsPanel;
            rightSideBar.mapView = this;
            rightSideBar.mapModel = mapModel;
            
            secRightSideBar.mainPanel = panel;
            secRightSideBar.mapModel = mapModel;
            
            mainPanel.mapModel = mapModel;
            
            this.addMouseListener(rightSideBar);
           // this.addMouseListener(secRightSideBar);
                    
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
}

>>>>>>> bd33ec58093447b203b76d7d581c1ff67b58645a
