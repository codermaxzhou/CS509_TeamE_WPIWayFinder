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
import adminmodule.Point;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author GaoYifei
 */
public class HigginsPanel extends MainPanel{
    private Image background;

    private JTextField startPointField;
    private JTextField endPointField;
    private JLabel profile;
    private JLabel exchange;
    private JLabel search;
    private JButton searchButton;
    private JMenu sideMenu;
    private ArrayList<JLabel> pinList = new ArrayList<JLabel>();

    // Data varaibels 
    private ArrayList<Point> pointList = new ArrayList<>();
    private ArrayList<Edge> edgeList = new ArrayList<>();
    private ArrayList<Location> locationList = new ArrayList<>(); // Ëã·¨·µ»ØµÄedges
    private ArrayList<Edge> route = new ArrayList<>();
    public Map map1 = new Map();
    public Dijkstra dijstra = new Dijkstra(edgeList, pointList);
    private ArrayList<Location> pins = new ArrayList<>();
    private Location startLocation = null;
    private Location endLocation = null;

    // boolean varaibels 
    private boolean showRoute = false;
    private boolean showPins = false;
    private boolean drawRoutes = false;
    private boolean showAllPins = false;

    // Timer 
    public Timer timer;
    private int index = 0;


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
