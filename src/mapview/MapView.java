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

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		//  mapÍ¼²ã
//		mapPanel = new MapPanel(this);
//        mapPanel.setPreferredSize(new Dimension(900, 800));
//        this.getContentPane().add(mapPanel, BorderLayout.CENTER);
//		
		this.init();
//		mainPanel = new MainPanel(backGround);
//		//this.add(mainPanel);
//                this.getContentPane().add(mainPanel, BorderLayout.WEST);
//		RightSideBar rightSideBar = new RightSideBar(this);
//                this.getContentPane().add(rightSideBar, BorderLayout.EAST);
//		
//                SecRightSideBar secRightSideBar = new SecRightSideBar(this);
//                this.getContentPane().add(secRightSideBar, BorderLayout.CENTER);
//        
//		this.setSize(1280,920);
//		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.addMouseListener(mainPanel);
//		
                
          
	}
        public void init() throws SQLException{
            
//            mapPanel = new MapPanel(this);
//            mapPanel.setPreferredSize(new Dimension(900,800));
           // Image backGround = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_1.png")).getImage();
            MainPanel mainPanel = new MainPanel();
            mainPanel.setPreferredSize(new Dimension(900,800));
            
            this.getContentPane().add(mainPanel, BorderLayout.WEST);
            RightSideBar rightSideBar = new RightSideBar();
            this.getContentPane().add(rightSideBar, BorderLayout.EAST);
	    SecRightSideBar secRightSideBar = new SecRightSideBar();
            this.getContentPane().add(secRightSideBar, BorderLayout.CENTER);
            this.setVisible(true);
            Dimension d = new Dimension(1300, 850);
            this.setSize(d);
            this.setResizable(false);
            
            this.addMouseListener(mainPanel);
            
           // communicate bettween class 
            rightSideBar.mainPanel = mainPanel;
            rightSideBar.secRightSideBar = secRightSideBar;
            secRightSideBar.mainPanel = mainPanel;
            
            this.addMouseListener(rightSideBar);
           // this.addMouseListener(secRightSideBar);
                    
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
}

// one file cannot have two public class 
class MainPanel extends JPanel implements MouseListener, ActionListener{
	// UI ÊôÐÔ
	private Image background;
	private JTextField startPointField;
	private JTextField endPointField;
        private JLabel profile;
        private JLabel exchange;
        
        private JButton searchButton;
	private JMenu sideMenu;
    // µØÍ¼ÊôÐÔ
	private ArrayList<Point> pointList = new ArrayList<>();
	private ArrayList<Edge> edgeList = new ArrayList<>();
	private ArrayList<Location> locationList = new ArrayList<>(); // Ëã·¨·µ»ØµÄedges
	private ArrayList<JLabel> pinList = new ArrayList<JLabel>();
	public Map map1 = new Map();
	public Dijkstra dijstra = new Dijkstra(edgeList,pointList);
	
        //private boolean drawDiningPins = false;
//	private final SLPanel panel = new SLPanel();
//	private final ThePanel p1 = new ThePanel("1", "data/img1.jpg");

	MainPanel() throws SQLException{
                JDBC db = new JDBC();
                
                Map m = new Map();
                m.mapID = 1;
                
                MapInfo info = db.getMapInfo(1, m);
                pointList = info.points;
                edgeList = info.edges;
                locationList = info.locations;
                 
                // Just for test
                /*locationList.get(0).point = pointList.get(0);
                locationList.get(1).point = pointList.get(1);
                locationList.get(2).point = pointList.get(2);
                locationList.get(3).point = pointList.get(3);*/
                 
		background = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_1.png")).getImage();
		
                Image profileImage = new ImageIcon(this.getClass().getResource("/icons/user.png")).getImage();
                ImageIcon profileIcon = new ImageIcon(profileImage);
                profile = new JLabel();
                profile.setIcon(profileIcon);
                
                Image exchangeImage = new ImageIcon(this.getClass().getResource("/icons/exchange.png")).getImage();
                ImageIcon exchangeIcon = new ImageIcon(exchangeImage);
                exchange = new JLabel();
                exchange.setIcon(exchangeIcon);
                
		startPointField = new JTextField();
		endPointField = new JTextField();
		searchButton = new JButton();
		sideMenu = new JMenu();
		
		
		this.add(startPointField);
		this.add(endPointField);
		this.add(searchButton);
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
		
		searchButton.setBounds(380, 10, 100, 30);
		searchButton.setText("Direction");
                searchButton.setFont(font);
		searchButton.setBackground(Color.BLUE);
		searchButton.setForeground(Color.WHITE);
               
		searchButton.addActionListener(this);  // ×¢²á¼àÌý 
                profile.setBounds(10, 10, 30, 30);
                exchange.setBounds(190, 10, 30, 30);
		
		sideMenu.setText("more options");
	        sideMenu.setBounds(1200,0,70,800);
	        sideMenu.setBackground(Color.GRAY);
	    
		// ³õÊ¼»¯map
		
		map1.mapID = 1;
		map1.image = background;
		map1.name = "Project Center1";
		map1.pointList = pointList;
                
                this.setVisible(true);
               
                
		
		
		
	}
	 public void paintComponent(Graphics g) {  
	       g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);  
//	        System.out.println(this.getHeight());
//	        System.out.println(this.getWidth());
	    } 
	 public void paint(Graphics g){
		 super.paint(g);
		 //g.draw3DRect(10, 100, 10, 10, true);
	
                 
		
	 }
	
	 
	 // ¿ªÊ¼»­Ïß ÕâÀïÊÇÓÃº¯ÊýÀ´ÊµÏÖÂð£¿ Ö»ÄÜ»­Á½µãÖ®¼äµÄÏß Èç¹ûÒª»­Ò»ÏµÁÐµãµÄÏß¿ÉÒÔÁ¬Ðøµ÷ÓÃÕâ¸ö·½·¨
	public void drawRoute(Point start, Point end){
		 
		//this.getGraphics().drawLine(420, 305, 564, 280);
		 // repaint ÊÇÖØÐÂµ÷ÓÃpaint£¨£©º¯Êý ²»ÄÜÂÒÓÃ 
		 
	}
	
	// give Edges from Algorithms then draw route
	public void drawManyRoute(ArrayList<Edge> resultEdge){
		/*ArrayList<Point> resultPoint = new ArrayList<Point>();
		resultPoint = edgeToPoint(resultEdge);
		// PointList ÊÇ»­µãµÄÊý¾Ý½á¹¹ ÔÚÕâÀï¿ÉÒÔ»»Ò»¸ö
		for(int i = 0; i < resultPoint.size() -1 ; i++){
                        
                        
                    
			System.out.println("result.size:"+resultPoint.size());
			this.getGraphics().drawLine(resultPoint.get(i).X, resultPoint.get(i).Y, resultPoint.get(i+1).X, resultPoint.get(i+1).Y);
			
		}*/
	
                for(Edge e : resultEdge) {
                    this.getGraphics().fillOval(e.startPoint.X - 5, e.startPoint.Y - 5, 10, 10);
                    this.getGraphics().fillOval(e.endPoint.X - 5, e.endPoint.Y - 5, 10, 10);
                    this.getGraphics().drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
                    
                }
	}
        
        public void showLocationPin(String category){
            
            
            // System.out.println(category); 
             Graphics g = this.getGraphics();
           
            
            for(Location p: locationList){
                
                Image pinImage = new ImageIcon(this.getClass().getResource("/icons/marker.png")).getImage();
                  //System.out.println("categoryList is "+p.category);
                  //this.getGraphics().drawOval(p.point.X, p.point.Y, 10, 10);
                    switch (p.category) {
                    case CLASSROOM:
                        if (category.equals("CLASSROOM")) {
                            
                            
//                            g.fillOval(p.point.X, p.point.Y, 10, 10); 
                            g.drawImage(pinImage, p.point.X - 5, p.point.Y - 5, 20, 20, null);
                           
                            
                        }
                        break;
                    case RESTROOM:
                        if (category.equals("RESTROOM")) {
                            
                           // g.fillOval(p.point.X, p.point.Y, 10, 10);
                            g.drawImage(pinImage, p.point.X - 5, p.point.Y - 5, 20, 20, null);
                        }
                    default:
                        break;
//                
                }
            }
        }
	
        
	public ArrayList<Edge> passThePointToDij(Point start, Point end){
		//resultList  = (ArrayList<Edge>) dijstra.calculate(start, end);
		//return resultList;
		return null;
	}
	//  ×ª»»edge µ½ point 
	public ArrayList<Point> edgeToPoint(ArrayList<Edge> edgeList){
		ArrayList<Point> resultPointList = new ArrayList<Point>();
		for(int i = 0; i < edgeList.size(); i++)
		{   
			//System.out.println(i);
			resultPointList.add(edgeList.get(i).startPoint);
		}
		return resultPointList;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(Location l: locationList){
                if((e.getX() >= l.point.X - 10 || e.getX() <= l.point.X + 10 )
                        && (e.getY() >= l.point.Y - 10 || e.getY() <= l.point.Y + 10))
                {
                    PopupMenu menu = new PopupMenu(l);
                    
                    menu.show(e.getComponent(), l.point.X, l.point.Y);
                }   
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
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
            System.out.println("search!");
		if(e.getSource() == searchButton ){
			String startPointString = startPointField.getText();
			String endPointString = endPointField.getText();
			System.out.println("The start Point name:"+startPointString);
			System.out.println("The end Point name:"+endPointString);
			if(startPointString.isEmpty()|| endPointString.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Please input the location!");
        
                        }
                        
                        Location start, end;
                        start = end = null;
                        
                        for(Location l : locationList) {
                            if(l.name.equals(startPointString)) start = l;
                            else if(l.name.equals(endPointString)) end = l;
                        }
                        
                        Dijkstra algo = new Dijkstra(edgeList, pointList);
                        ArrayList<Edge> route = (ArrayList<Edge>) algo.calculate(start.point, end.point);
                        this.drawManyRoute(route);
		}
		
	}
	 
}

