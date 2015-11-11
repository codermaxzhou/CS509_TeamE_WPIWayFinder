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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import jdbc.JDBC;

/**
 *
 * @author GaoYifei
 */
public class MainPanel extends JPanel implements MouseListener, ActionListener{
	// UI varaibles
	private Image background;
	private JTextField startPointField;
	private JTextField endPointField;
        private JLabel profile;
        private JLabel exchange;
        private JLabel search;
        private JButton searchButton;
	private JMenu sideMenu;
        private ArrayList<JLabel> pinList = new ArrayList<JLabel>();
        private Image pinImage = new ImageIcon(this.getClass().getResource("/icons/marker.png")).getImage();
    
        
        
        // Data varaibels 
	private ArrayList<Point> pointList = new ArrayList<>();
	private ArrayList<Edge> edgeList = new ArrayList<>();
	private ArrayList<Location> locationList = new ArrayList<>(); // Ëã·¨·µ»ØµÄedges
        private ArrayList<Edge> route = new ArrayList<>();
	
	public Dijkstra dijstra = new Dijkstra(edgeList,pointList);
        private ArrayList<Location> pins = new ArrayList<>();
        private Location startLocation = null ;
        private Location endLocation = null  ; 
        private JDBC db = new JDBC();
                
        private Map map = new Map();
        private int mapIndex;
                        
        // boolean varaibels 
        private boolean showRoute = false;
        private boolean showPins = false;
        private boolean drawRoutes = false;
        private boolean showAllPins = false;
        
        // Timer 
        public Timer timer;
        private int index = 0;
        
	
        //private boolean drawDiningPins = false;
//	private final SLPanel panel = new SLPanel();
//	private final ThePanel p1 = new ThePanel("1", "data/img1.jpg");

	MainPanel() throws SQLException {
            
            mapIndex = 1;  // default is mapID 1
            this.init();
          
	}
        
         public void init() throws SQLException{
                
                map.mapID = mapIndex;
                
                MapInfo info = db.getMapInfo(mapIndex, map);
                pointList = info.points;
                edgeList = info.edges;
                locationList = info.locations;
                 
                
                if(mapIndex == 1){
		     background = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_1.png")).getImage();
                }
                else if(mapIndex == 2){
                      background = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_2.png")).getImage();
                }
                
                
                Image profileImage = new ImageIcon(this.getClass().getResource("/icons/user.png")).getImage();
                ImageIcon profileIcon = new ImageIcon(profileImage);
                profile = new JLabel();
                profile.setIcon(profileIcon);
                
                Image exchangeImage = new ImageIcon(this.getClass().getResource("/icons/exchange.png")).getImage();
                ImageIcon exchangeIcon = new ImageIcon(exchangeImage);
                exchange = new JLabel();
                exchange.setIcon(exchangeIcon);
                
                Image searchImage = new ImageIcon(this.getClass().getResource("/icons/search.png")).getImage();
                ImageIcon searchIcon = new ImageIcon(searchImage);
                search = new JLabel();
                search.setIcon(searchIcon);
                
		startPointField = new JTextField();
		endPointField = new JTextField();
		searchButton = new JButton();
		sideMenu = new JMenu();
		
		
		this.add(startPointField);
		this.add(endPointField);
		this.add(searchButton);
                this.add(search);
		this.add(sideMenu);
                this.add(profile);
                this.add(exchange);
                
		
	         BorderLayout layout;
                 layout = new BorderLayout();
                 this.setLayout(layout);
		//this.setLayout(null);
                 
                Font font = new Font("Roboto", Font.PLAIN, 16);
		
		startPointField.setColumns(20);
		startPointField.setBounds(40, 10, 150, 30);
		startPointField.setText("Start Point");
                startPointField.setFont(font);
		
		endPointField.setColumns(20);
		endPointField.setBounds(220, 10, 150, 30);
		endPointField.setText("End Point");
                endPointField.setFont(font);
		
		// adding Listener Here
                search.addMouseListener(this);
                exchange.addMouseListener(this);
                
                
                search.setBounds(380,10,30,30);
                profile.setBounds(10, 10, 30, 30);
                exchange.setBounds(190, 10, 30, 30);
		
		
                timer = new Timer(100,this);
                timer.setInitialDelay(300);
                
                this.setVisible(true);
               
                
         }
	 public void paintComponent(Graphics g) {  
	       g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);  

	    } 
	 public void paint(Graphics g){
		 super.paint(g);
		
                 if(showPins) {
                     for(Location p : pins) {
                         g.drawImage(pinImage, p.point.X - 5, p.point.Y - 5, 20, 20, null);
                         g.drawString(p.point.location.name,p.point.X - 30 , p.point.Y - 10);
                         
                         //g.drawString(TOOL_TIP_TEXT_KEY, index, WIDTH);
                     }
                 }
                 if(showRoute){
                     this.timer.start();
                     
                     
                    // for(Edge e : route) {
//                    g.fillOval(e.startPoint.X - 5, e.startPoint.Y - 5, 10, 10);
//                    g.fillOval(e.endPoint.X - 5, e.endPoint.Y - 5, 10, 10);
//                    g.drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
                    
              //  }
                 }
                 if(showAllPins){
                     for(Location l: locationList){
                         g.drawImage(pinImage,l.point.X - 5, l.point.Y - 5, 20, 20, null);
                         g.drawString(l.point.location.name,l.point.X - 30 , l.point.Y - 10);
                         
                     }
                 }
		
	 }
	
	 
	 
	public void drawRoute(Point start, Point end){
		 
 
		 
	}
	
	
        public void showSinglePin(String name) {
            this.showRoute = false;
            this.showPins = true;
            this.showAllPins = false;
            pins.clear();
            
            for(Location p: locationList){
                if(p.name.equals(name)) {
                    pins.add(p);
                    break;
                }
            }
            repaint();
        }
        
        
        public void showLocationPin(String category){
            
            
            
            this.showRoute = false;
            this.showPins = true;
            
            Graphics g = this.getGraphics();
            pins.clear();
           
            for(Location p: locationList){
                    switch (p.category) {
                        case CLASSROOM:
                            if (category.equals("CLASSROOM")) { 
                                pins.add(p);
                            }
                            break;
                        case RESTROOM:
                            if (category.equals("RESTROOM")) {
                                pins.add(p);
                            }
                            break;
                        default:
                            break;
                    }
            }
            
            repaint();
        }
	
        
	
	
	public ArrayList<Point> edgeToPoint(ArrayList<Edge> edgeList){
		ArrayList<Point> resultPointList = new ArrayList<Point>();
		for(int i = 0; i < edgeList.size(); i++)
		{   
			//System.out.println(i);
			resultPointList.add(edgeList.get(i).startPoint);
		}
		return resultPointList;
	}
        
        public void clearPins() {
            showRoute = false;
            showPins = false;
            showAllPins = false;
            repaint();
        }
        
        public void showPins(){
            
           showAllPins = true;
           showRoute = false;
           repaint();
        }
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		for(Location l: locationList){
//                if((e.getX() >= l.point.X - 10 || e.getX() <= l.point.X + 10 )
//                        && (e.getY() >= l.point.Y - 10 || e.getY() <= l.point.Y + 10))
//                {
//                    PopupMenu menu = new PopupMenu(l);
//                    
//                    menu.show(e.getComponent(), l.point.X, l.point.Y);
//                }   
//                }
            
            System.out.println("search!");
            if (e.getSource() == search) {

                String startPointString = startPointField.getText();
                String endPointString = endPointField.getText();
                System.out.println("The start Point name:" + startPointString);
                System.out.println("The end Point name:" + endPointString);
                if (startPointString.isEmpty() || endPointString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please input the location!");

                } else {

                    for (Location l : locationList) {
                        if (l.name.equals(startPointString)) {
                            startLocation = l;
                        } else if (l.name.equals(endPointString)) {
                            endLocation = l;
                        }
                    }

                    if (startLocation == null || endLocation == null) {
                        JOptionPane.showMessageDialog(null, "Wrong Location !");

                    } else {

                        Dijkstra algo = new Dijkstra(edgeList, pointList);
                        route = (ArrayList<Edge>) algo.calculate(startLocation.point, endLocation.point);
                        showRoute = true;
                        showPins = false;

                        repaint();
                    }
                }
            }
                
                // exchange button 
                if (e.getSource()== exchange){
                String tmp = null;
                tmp = startPointField.getText();
                startPointField.setText(endPointField.getText());
                endPointField.setText(tmp);

                }
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
            if(e.getSource() == search){
                search.setToolTipText("Search Route");
                search.setBounds(search.getX() - 3, search.getY() - 3, search.getWidth(), search.getHeight());
            }
            if(e.getSource() == exchange){
                exchange.setToolTipText("Exchange StartingPoint and Destination");
            }
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
            if(e.getSource() == search){
                search.setBounds(search.getX() + 3, search.getY() + 3, search.getWidth(), search.getHeight());
               // search.set
            }
		
	}
        
        
        
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
                if(e.getSource() == timer){
                    Edge edge = new Edge();
                    
                    edge = route.get(index);
                    Graphics g = this.getGraphics();
                    g.setColor(Color.red);
                   // Stroke stroke  = new BasicStroke(3.0f);
                   
                    if(index == 0){
                        g.drawString(startLocation.name, startLocation.point.X - 30, startLocation.point.Y - 30);
                        g.drawImage(pinImage, startLocation.point.X - 5, startLocation.point.Y - 20,20,20,null);
                    }
                    if(index == route.size() - 1){
                        g.drawString(endLocation.name, endLocation.point.X - 30, endLocation.point.Y - 30);
                        g.drawImage(pinImage, endLocation.point.X - 5, endLocation.point.Y - 20,20,20,null);
                    }
//                    
                    g.fillOval(edge.startPoint.X - 5, edge.startPoint.Y - 5, 10, 10);
                    g.fillOval(edge.endPoint.X - 5, edge.endPoint.Y - 5, 10, 10);
                 
                    g.drawLine(edge.startPoint.X, edge.startPoint.Y, edge.endPoint.X, edge.endPoint.Y);
                   
                    index ++;
                    if(index == route.size()){
                        this.timer.stop();
                        index = 0;
                    }
                }
	}

    /**
     * @return the mapIndex
     */
    public int getMapIndex() {
        return mapIndex;
    }

    /**
     * @param mapIndex the mapIndex to set
     */
    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }
	 
}

