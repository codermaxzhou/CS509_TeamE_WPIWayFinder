/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.Color;
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
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdbc.JDBC;


//import aurelienribon.slidinglayout.SLPanel;




public class MapView extends JFrame{
    
	MainPanel mainPanel;
	MapPanel mapPanel;
	Image backGround = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_1.png")).getImage();
    

	// construction method 
	public MapView() throws SQLException{
		//  mapÍ¼²ã
//		mapPanel = new MapPanel(this);
//        mapPanel.setPreferredSize(new Dimension(900, 800));
//        this.getContentPane().add(mapPanel, BorderLayout.CENTER);
//		
		
		mainPanel = new MainPanel(backGround);
		this.add(mainPanel);
		
		
        
        
		this.setSize(1280,920);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addMouseListener(mainPanel);
		
	}
}

// one file cannot have two public class 
class MainPanel extends JPanel implements MouseListener, ActionListener{
	// UI ÊôÐÔ
	private Image image = null;
	private JTextField startPointField;
	private JTextField endPointField;
	private JButton searchButton;
	private JMenu sideMenu;
    // µØÍ¼ÊôÐÔ
	private ArrayList<Point> pointList = new ArrayList<>();
	private ArrayList<Edge> edgeList = new ArrayList<>();
	private ArrayList<Location> locationList = new ArrayList<>(); // Ëã·¨·µ»ØµÄedges
	
	public Map map1 = new Map();
	public Dijkstra dijstra = new Dijkstra(edgeList,pointList);
	

//	private final SLPanel panel = new SLPanel();
//	private final ThePanel p1 = new ThePanel("1", "data/img1.jpg");

	MainPanel(Image image) throws SQLException{
                JDBC db = new JDBC();
                MapInfo info = db.getMapInfo(1);
                pointList = info.points;
                edgeList = info.edges;
                locationList = info.locations;
            
		this.image = image;
		
		startPointField = new JTextField();
		endPointField = new JTextField();
		searchButton = new JButton();
		sideMenu = new JMenu();
		
		
		this.add(startPointField);
		this.add(endPointField);
		this.add(searchButton);
		this.add(sideMenu);
		
	
		this.setLayout(null);
		
		startPointField.setColumns(20);
		startPointField.setBounds(30, 10, 150, 30);
		startPointField.setText("Start Point");
		
		endPointField.setColumns(20);
		endPointField.setBounds(200, 10, 150, 30);
		endPointField.setText("End Point");
		
		searchButton.setBounds(360, 10, 100, 30);
		searchButton.setText("Direction");
		searchButton.setBackground(Color.MAGENTA);
		searchButton.setForeground(Color.BLUE);
		searchButton.addActionListener(this);  // ×¢²á¼àÌý 
		
		sideMenu.setText("more options");
	    sideMenu.setBounds(1200,0,70,800);
	    sideMenu.setBackground(Color.GRAY);
	    
		// ³õÊ¼»¯map
		
		map1.mapID = 1;
		map1.image = image;
		map1.name = "Project Center1";
		map1.pointList = pointList;
                
                
		
		// ÕâÀï¸É´àÓÃÒ»¸öÊý¾Ý½á¹¹ºÃÁË 
		
		/*for(int i = 0 ; i<4 ; i++){
			Point point = new Point(); // Point Ðè²»ÐèÒª¹¹Ôìº¯Êý ºÍ set get·½·¨ÄØ 
			point.pointID = i;
		    point.map = map1;
			
			pointList.add(point);
		}*/
		
		//  ÕâÀïµÄ¸³ÖµÎÒÏë»¹ÓÐ¸ü¼ò½àµÄ·½·¨ Òª²»È»Ò»¸öÒ»¸öÐ´Ì«Âé·³ÁË ¿ÉÒÔ°Ñ×ø±ê´æÔÚÊý×éÀï »òÕß´æÔÚhash±íÀï
		/*pointList.get(0).X = 196;
		pointList.get(0).Y = 224;
		pointList.get(1).X = 334;
		pointList.get(1).Y = 305;
		pointList.get(2).X = 334;
		pointList.get(2).Y = 220;
		pointList.get(3).X = 420;
		pointList.get(3).Y = 305;
		
		// Testing 
		Edge edge1 = new Edge();
		edge1.startPoint = pointList.get(0);
		edge1.endPoint = pointList.get(1);
		edge1.weight = 10;
		edgeList.add(edge1);
		
		Edge edge2 = new Edge();
		edge2.startPoint = pointList.get(1);
		edge2.endPoint = pointList.get(2);
		edge2.weight = 20;
		edgeList.add(edge2);
		
		Edge edge3 = new Edge();
		edge3.startPoint = pointList.get(2);
		edge3.endPoint = pointList.get(3);
		edge3.weight = 5;
		edgeList.add(edge3);
		
		// ÒòÎªpointList ³¤¶ÈÖ»ÓÐÈý ÏÈ²»Òª²âÊÔÕâ¸ö
//		Edge edge4 = new Edge();
//		edge4.startPoint = pointList.get(3);
//		edge4.endPoint = pointList.get(4);
//		edge4.weight = 30;
//		edgeList.add(edge4);*/
		
		
		
		
		
		
	}
	 protected void paintComponent(Graphics g) {  
	       g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);  
//	        System.out.println(this.getHeight());
//	        System.out.println(this.getWidth());
	    } 
	 public void paint(Graphics g){
		 super.paint(g);
		 //g.draw3DRect(10, 100, 10, 10, true);
	
		 
//		 g.drawOval(334, 220, 10, 10);
//		 g.drawOval(420, 305, 10, 10);
//		 g.drawOval(564, 280, 10, 10);
//		 g.drawOval(686, 280, 10, 10);
//		 g.drawOval(854, 280, 10, 10);
//		 g.drawOval(934, 280, 10, 10);
//		 g.drawOval(1055, 280, 10, 10);
//		 g.drawOval(1055, 378, 10, 10);
//		 g.drawOval(1055, 461, 10, 10);
//		 g.drawOval(294, 540, 10, 10);
//		 g.drawOval(294, 490, 10, 10);
//		 g.drawOval(403, 490, 10, 10);
//		 g.drawOval(562, 540, 10, 10);
//		 g.drawOval(771, 540, 10, 10);
//		 g.drawOval(1055, 540, 10, 10);
	
		
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
			System.out.println(i);
			resultPointList.add(edgeList.get(i).startPoint);
		}
		return resultPointList;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// µã»÷Êó±êÔò»á·µ»ØÊó±êµÄÎ»ÖÃ 
		System.out.println(e.getPoint());
		// µã»÷Êó±ê»Øµ÷ÓÃ»­³öÏß¶ÎµÄº¯Êý 
		this.drawRoute(pointList.get(0),pointList.get(1));
		
		this.drawManyRoute(edgeList);
		// TODO Auto-generated method stub
		
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
		if(e.getSource() == searchButton ){
			String startPointString = startPointField.getText();
			String endPointString = endPointField.getText();
			System.out.println("The start Point name:"+startPointString);
			System.out.println("The end Point name:"+endPointString);
			
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

