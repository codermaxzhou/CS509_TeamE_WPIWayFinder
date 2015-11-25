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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import jdbc.JDBC;
import net.java.balloontip.CustomBalloonTip;
import net.java.balloontip.examples.complete.Utils;
import net.java.balloontip.utils.ToolTipUtils;

/**
 *
 * @author GaoYifei
 */
public class MainPanel extends JPanel implements MouseListener, ActionListener {

    
    // Reference
    MapView mapView;
    // UI varaibles
    private Image background;
    private Image mapImage;
    private final JTextField startPointField = new JTextField();
    private final JTextField endPointField = new JTextField();
    private final JLabel profile = new JLabel();
    private final JLabel leftArrow = new JLabel();
    private final JLabel rightArrow = new JLabel();
    
    
    private final JLabel exchange = new JLabel();
    private final JLabel search = new JLabel();
    private final JLabel home = new JLabel();
    private final JButton searchButton = new JButton();
    private JButton nextButton = new JButton("next routing");
    

    private ArrayList<JLabel> pinList = new ArrayList<JLabel>();
    private final Image pinImage = new ImageIcon(this.getClass().getResource("/icons/marker.png")).getImage();

    // Data varaibels 
    private ArrayList<Point> pointList = new ArrayList<>();
    private ArrayList<Edge> edgeList = new ArrayList<>();
    private ArrayList<Location> locationList = new ArrayList<>(); // Ëã·¨·µ»ØµÄedges
    private ArrayList<Edge> route = new ArrayList<>();
    private ArrayList<Edge> multiRoute = new ArrayList<>();
    private ArrayList<Integer> multiMapIndex = new ArrayList<>();

    private ArrayList<Map> allMapList = new ArrayList<>();
    private ArrayList<Location> allLocationList = new ArrayList<>();
    private ArrayList<Edge> allEdgeList = new ArrayList<>();
    private ArrayList<Point> allPointList = new ArrayList<>();

    private Dijkstra algo;
    private ArrayList<Location> pins = new ArrayList<>();
    private Location startLocation = null;
    private Location endLocation = null;
    private final JDBC db = new JDBC();

    private Map map = new Map();
    private int mapIndex;

    // boolean varaibels 
    private boolean showRoute = false;
    private boolean showPins = false;
    private boolean drawRoutes = false;
    private boolean showAllPins = false;
    private boolean clear = true;
    private boolean drawMultiRoutes = false;
    private boolean showWhenClick = false;
    private int clicked = 0;

    AutoSuggestor startAutoSuggestor;
    AutoSuggestor endAutoSuggestor;
    
    private ArrayList<CustomBalloonTip> tipList = new ArrayList<>();

    // Timer 
    public Timer timer;
    private int index = 0;

    // other class
    public MapModel mapModel;
    JFrame frame;

    //private boolean drawDiningPins = false;
//	private final SLPanel panel = new SLPanel();
//	private final ThePanel p1 = new ThePanel("1", "data/img1.jpg");
    MainPanel(JFrame frame) throws SQLException {

        this.frame = frame;
        mapIndex = 1;  // default is mapID 1
        this.init();

    }

    MainPanel() throws SQLException {

        mapIndex = 1;  // default is mapID 1
        this.init();

    }

    public void init() throws SQLException {

        
        MapInfo info = db.getMapInfo(mapIndex, map); // why do we need map as parameter?
        
        
        pointList = info.points;
        edgeList = info.edges;
        locationList = info.locations;

        mapModel = new MapModel();
        allMapList = mapModel.getMapList();
        allEdgeList = mapModel.getAllEdgeList();
        allPointList = mapModel.getAllPointList();
        allLocationList = mapModel.getAllLocationList();
        algo = new Dijkstra(allEdgeList, allPointList);
        map = allMapList.get(mapIndex);
        
        
        for (Map m : allMapList) {

            if (m.mapID == mapIndex) {
                mapImage = m.image;

            }
        }
//
//        Image profileImage = new ImageIcon(this.getClass().getResource("/icons/user.png")).getImage();
//        ImageIcon profileIcon = new ImageIcon(profileImage);

      //  profile.setIcon(profileIcon);

        Image exchangeImage = new ImageIcon(this.getClass().getResource("/icons/exchange.png")).getImage();
        ImageIcon exchangeIcon = new ImageIcon(exchangeImage);

        exchange.setIcon(exchangeIcon);

        Image searchImage = new ImageIcon(this.getClass().getResource("/icons/search.png")).getImage();
        ImageIcon searchIcon = new ImageIcon(searchImage);

        search.setIcon(searchIcon);
        
        Image homeImage = new ImageIcon(this.getClass().getResource("/icons/home.png")).getImage();
        ImageIcon homeIcon = new ImageIcon(homeImage);
        
        home.setIcon(homeIcon);
        
        Image leftImage = new ImageIcon(this.getClass().getResource("/icons/CircleLeft.png")).getImage();
        ImageIcon leftIcon = new ImageIcon(leftImage);
        leftArrow.setIcon(leftIcon);
        
        Image rightImage = new ImageIcon(this.getClass().getResource("/icons/CircleRight.png")).getImage();
        ImageIcon rightIcon = new ImageIcon(rightImage);
        rightArrow.setIcon(rightIcon);
        
        

        this.add(startPointField);
        this.add(endPointField);
        this.add(searchButton);
        this.add(search);
        this.add(profile);
        this.add(exchange);
        this.add(home);
        this.add(leftArrow);
        this.add(rightArrow);
        

        BorderLayout layout;
        layout = new BorderLayout();
        this.setLayout(layout);
        //this.setLayout(null);

        Font font = new Font("Roboto", Font.PLAIN, 16);

        startPointField.setColumns(20);
        startPointField.setBounds(40, 10, 150, 30);
        startPointField.setText("Start Point");
        startPointField.setFont(font);
        startPointField.setForeground(Color.gray);

        endPointField.setColumns(20);
        endPointField.setBounds(220, 10, 150, 30);
        endPointField.setText("End Point");
        endPointField.setFont(font);
        endPointField.setForeground(Color.gray);

        startAutoSuggestor = new AutoSuggestor(startPointField, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
        endAutoSuggestor = new AutoSuggestor(endPointField, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);

        // adding Listener Here
        search.addMouseListener(this);
        exchange.addMouseListener(this);
        home.addMouseListener(this);
        leftArrow.addMouseListener(this);
        rightArrow.addMouseListener(this);
        this.addMouseListener(this);
        

        search.setBounds(380, 10, 30, 30);
        home.setBounds(10, 10, 30, 30);
        exchange.setBounds(190, 10, 30, 30);
        leftArrow.setBounds(850, 10, 50, 50);
        rightArrow.setBounds(900, 10, 50, 50);

        timer = new Timer(100, this);
        timer.setInitialDelay(300);

        this.setVisible(true);

    }

    public void setMapModel(MapModel model) {
        this.mapModel = model;

        ArrayList<String> suggestions = new ArrayList<>();

        for (Location l : model.getAllLocationList()) {
            suggestions.add(l.name);
        }

        startAutoSuggestor.setDictionary(suggestions);
        endAutoSuggestor.setDictionary(suggestions);
    }

    public void changeMap(int mapIndex) {
        for (Map m : allMapList) {

            if (m.mapID == mapIndex) {
                mapImage = m.image;

            }
        }
        this.repaint();
    }

    public void reloadMap(int mapIndex) {
       // map.mapID = mapIndex;
        map = allMapList.get(mapIndex - 1);
        this.mapIndex = mapIndex;
        this.showAllPins = false;
        this.showPins = false;
        
        MapInfo info = null;
        try {
            info = db.getMapInfo(mapIndex, map); // why do we need map as parameter?
        } catch (SQLException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        pointList = info.points;
        edgeList = info.edges;
        locationList = info.locations;
        mapImage = map.image;
//        for (Map m : allMapList) {
//
//            if (m.mapID == mapIndex) {
//                mapImage = m.image;
//
//            }
//        }
        
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(mapImage, 0, 0, this.getWidth(), this.getHeight(), this);

//        Image profileImage = new ImageIcon(this.getClass().getResource("/icons/user.png")).getImage();
//        ImageIcon profileIcon = new ImageIcon(profileImage);
//
//        profile.setIcon(profileIcon);
//
//        Image exchangeImage = new ImageIcon(this.getClass().getResource("/icons/exchange.png")).getImage();
//        ImageIcon exchangeIcon = new ImageIcon(exchangeImage);
//
//        exchange.setIcon(exchangeIcon);
//
//        Image searchImage = new ImageIcon(this.getClass().getResource("/icons/search.png")).getImage();
//        ImageIcon searchIcon = new ImageIcon(searchImage);
//
//        search.setIcon(searchIcon);
//        
//        search.setBounds(380, 10, 30, 30);
//        profile.setBounds(10, 10, 30, 30);
//        exchange.setBounds(190, 10, 30, 30);
//        
//         Font font = new Font("Roboto", Font.PLAIN, 16);
//
//        startPointField.setColumns(20);
//        startPointField.setBounds(40, 10, 150, 30);
//        startPointField.setText("Start Point");
//        startPointField.setFont(font);
//        startPointField.setForeground(Color.gray);
//        
////        startAutoSuggestor = new AutoSuggestor(startPointField, frame, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 1f);
//
//        endPointField.setColumns(20);
//        endPointField.setBounds(220, 10, 150, 30);
//        endPointField.setText("End Point");
//        endPointField.setFont(font);
//        endPointField.setForeground(Color.gray);
//        
//        
//        this.add(startPointField);
//        this.add(endPointField);
//        this.add(searchButton);
//        this.add(search);
//        this.add(profile);
//        this.add(exchange);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (isShowPins()) {
            for (Location p : pins) {
                g.drawImage(pinImage, p.point.X - 5, p.point.Y - 5, 20, 20, null);
                g.drawString(p.point.location.name, p.point.X - 30, p.point.Y - 10);

                //g.drawString(TOOL_TIP_TEXT_KEY, index, WIDTH);
            }
        }
        if (isShowRoute()) {
            this.timer.start();

            // for(Edge e : route) {
//                    g.fillOval(e.startPoint.X - 5, e.startPoint.Y - 5, 10, 10);
//                    g.fillOval(e.endPoint.X - 5, e.endPoint.Y - 5, 10, 10);
//                    g.drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
            //  }
        }
        if (isShowAllPins()) {
            for (Location l : locationList) {
                g.drawImage(pinImage, l.point.X - 5, l.point.Y - 5, 20, 20, null);
                g.drawString(l.point.location.name, l.point.X - 30, l.point.Y - 10);

            }
        }
        
        if(showWhenClick == true){
            
            
        }

        if (isDrawMultiRoutes()) {

            Edge startEdge = getMultiRoute().get(0);
            Edge endEdge = getMultiRoute().get(getMultiRoute().size() - 1);
            int diviation = 10;

            if (startEdge.startMapID == mapIndex) {
                g.drawImage(pinImage, startEdge.startPoint.X - diviation, startEdge.startPoint.Y - diviation, 20, 20, null);
            }
            if (endEdge.endMapID == mapIndex) {
                g.drawImage(pinImage, endEdge.endPoint.X - diviation, endEdge.endPoint.Y - diviation, 20, 20, null);
            }

            int size = getMultiRoute().size();
            for (int i = 0; i <= size - 1; i++) {
                Edge e = getMultiRoute().get(i);
                //String type = route.get(i).startPoint.type.equals("CONNECTION");

                if (e.startMapID == mapIndex && e.endMapID == mapIndex) {
                    g.drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
                }

                if (getMultiRoute().get(i).startPoint.type.name().equals("CONNECTION")) {
                    //System.out.print("this is the connection of edge !");

                    g.drawImage(pinImage, e.startPoint.X - diviation, e.startPoint.Y - diviation, 20, 20, null);
                    g.drawString("Connection", e.startPoint.X - diviation, e.startPoint.Y - diviation);
                  
                }

            }
            setDrawMultiRoutes(false);

        }
        
        

    }

    // multi map routing 
    public void drawMultiRoute(Location start, Location end) {
        
        Graphics g = this.getGraphics();
        g.setColor(Color.red);

        //getMultiMapIndex().clear();
        setMultiRoute((ArrayList<Edge>) algo.calculate(start.point, end.point));

        for (Edge e : getMultiRoute()) {
            if (!multiMapIndex.contains(e.startMapID)) {
                getMultiMapIndex().add(e.startMapID);
            }
            if (!multiMapIndex.contains(e.endMapID)) {
                getMultiMapIndex().add(e.endMapID);
            }

        }

        setDrawMultiRoutes(true);

        setShowRoute(false);
        setShowPins(false);
        repaint();
    }

    public void drawRoute(Location start, Location end) {

    }

    public void showClickPin(String name) {
        this.setShowRoute(false);
        this.setShowPins(true);
        this.setShowAllPins(false);

        if (clear) {
            pins.clear();
        }

        for (Location p : locationList) {
            if (p.name.equals(name)) {
                pins.add(p);
                break;
            }

        }
        this.repaint();
    }

    public void showSinglePin(String name) {
        this.setShowRoute(false);
        this.setShowPins(true);
        this.setShowAllPins(false);

        pins.clear();
        
        for(CustomBalloonTip tip : tipList) {
            ToolTipUtils.toolTipToBalloon(tip);
            tip.setVisible(false);
            tip.closeBalloon();
        }
        
        tipList.clear();

        for (Location p : allLocationList) {
            // 判断location的mapID 就可以判断显示那张地图 

            if (p.name.equals(name)) {
                // hardcode need to be improved 
                for (Map m : allMapList) {
                    if (p.point.map.mapID == m.mapID) {
                        mapIndex = m.mapID;
                        try {
                            this.init();
                        } catch (SQLException ex) {
                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                pins.add(p);
                break;
            }
        }
        
        for(Location p : pins) {
                CustomBalloonTip tip = new CustomBalloonTip(this, 
                    new ToolTipPanel(p.image, p.name, p.description),
                    new Rectangle(p.point.X - 5, p.point.Y - 5, 20, 20),
                    Utils.createBalloonTipStyle(),
                    Utils.createBalloonTipPositioner(), 
                    null);

                tipList.add(tip);

                ToolTipUtils.balloonToToolTip(tip, 200, 3000);
        }
        
        repaint();
    }

    /**
     *
     * @param category
     */
    public void showLocationPin(String category) {

        this.setShowRoute(false);
        this.setShowPins(true);

        Graphics g = this.getGraphics();
        pins.clear();
        
        for(CustomBalloonTip tip : tipList) {
            ToolTipUtils.toolTipToBalloon(tip);
            tip.setVisible(false);
            tip.closeBalloon();
        }
        
        tipList.clear();

        for (Location p : locationList) {
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
                case ATM:
                    if (category.equals("ATM")) {
                        pins.add(p);
                    }
                    break;
                case ADMIN:
                    if (category.equals("ADMIN")) {
                        pins.add(p);
                    }
                    break;
                case DINING:
                    if (category.equals("DINING")) {
                        pins.add(p);
                    }
                    break;
                case PARKING:
                    if (category.equals("PARKING")) {
                        pins.add(p);
                    }
                    break;
                
                default:
                    break;
            }
        }
        
        for(Location p : pins) {
            CustomBalloonTip tip = new CustomBalloonTip(this, 
<<<<<<< HEAD
                new ToolTipPanel("/Users/GaoYifei/Desktop/1.png", p.name, p.description),
=======
                new ToolTipPanel(p.image, p.name, p.description),
>>>>>>> yihao
                new Rectangle(p.point.X - 5, p.point.Y - 5, 20, 20),
                Utils.createBalloonTipStyle(),
                Utils.createBalloonTipPositioner(), 
                null);
            
            tipList.add(tip);
            
            ToolTipUtils.balloonToToolTip(tip, 200, 3000);
        }

        repaint();
    }

    public void clearPins() {
        setShowRoute(false);
        setShowPins(false);
        setShowAllPins(false);
        repaint();
    }

    public void showPins() {

        setShowAllPins(true);
        setShowRoute(false);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        String startPointString = startPointField.getText();
        String endPointString = endPointField.getText();

        if (e.getSource() != search && e.getSource() != exchange) {
            
            
            int radius = 50;
            boolean inrange = false;

            int x = e.getX();
            int y = e.getY();

            for (Location temp : locationList) {
                if (((x > (temp.point.X - radius))
                        && (x < (temp.point.X + radius))
                        && (y > (temp.point.Y - radius))
                        && (y < (temp.point.Y + radius)))) {

                    clicked++;
              
                    if (clicked % 2 == 1) {
                        startPointField.setText(temp.name);
                        clear = true;

                        showClickPin(temp.name);
                    } else {

                        endPointField.setText(temp.name);
                        clear = false;
                        showClickPin(temp.name);

                    }
                    
                    // emma new code 
                    Enter enterMenu = new Enter(temp, this);
                    
                    enterMenu.setRightBar(mapView.getRightBar());
                    enterMenu.setSecRightBar(mapView.getSecRightSideBar());
                    enterMenu.show(e.getComponent(), x, y);

                }

            }

        }

        // System.out.println("search!");
        if (e.getSource() == search) {

            this.setShowRoute(false);
            startLocation = null;
            endLocation = null;

            System.out.println("The start Point name:" + startPointString);
            System.out.println("The end Point name:" + endPointString);
            if (startPointString.isEmpty() || endPointString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please input the location!");

            } else {
                for (Location l : allLocationList) {
                    if (l.name.equals(startPointString)) {
                        startLocation = l;

                    } else if (l.name.equals(endPointString)) {
                        endLocation = l;
                    }
                }
//                System.out.println("The start Point name:" + startLocation.name);
//                System.out.println("The end Point name:" + endLocation.name);

                if (startLocation == null || endLocation == null) {
                    JOptionPane.showMessageDialog(null, "Wrong Location !");

                } //                     multi map route 
                else if (startLocation.point.map.mapID != endLocation.point.map.mapID) {
                    
                    if (startLocation.point.map.mapID != mapIndex) {
                        this.reloadMap(startLocation.point.map.mapID);
                    }
                    drawMultiRoute(startLocation, endLocation);

                } else if (startLocation.point.map.mapID == endLocation.point.map.mapID) {
                   
                    if (startLocation.point.map.mapID != mapIndex) {
                        this.reloadMap(startLocation.point.map.mapID);
                    }
                    
                    route = (ArrayList<Edge>) algo.calculate(startLocation.point, endLocation.point);
                    setShowRoute(true);
                    setShowPins(false);

                    repaint();
                }

            }
        }

        // exchange button 
        if (e.getSource() == exchange) {
            String tmp = null;
            tmp = startPointField.getText();
            startPointField.setText(endPointField.getText());
            endPointField.setText(tmp);

        }

        if (e.getSource() == nextButton) {
            // System.out.print("next Button Clicked");
            mapIndex = 2;
            this.setMapIndex(mapIndex);
            setDrawMultiRoutes(true);
            this.setShowAllPins(false);
            this.setShowPins(false);
            this.setDrawRoutes(false);
            this.setShowRoute(false);
            try {
                this.init();
            } catch (SQLException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.repaint();
            this.remove(nextButton);
        }

//        if(e.getX() < connectionX + 20 && e.getX() > connectionX - 20 && e.getY() < connectionY + 20 && e.getY() > connectionY - 20){
//            System.out.print("click the connection button");
//        }
        // right click 
        if (e.isMetaDown()) {

            int x = e.getX();
            int y = e.getY();
            int radius = 30;
            for (Point temp : allPointList) {
                if (!((x < (temp.getX() - radius))
                        || (x > (temp.getX() + radius))
                        || (y < (temp.getY() - radius))
                        || (y > (temp.getY() + radius))) && temp.type.name() == "CONNECTION") {
                    Connection connection = new Connection(temp, this);
                    //connection.mainPanel = this;
                    connection.show(e.getComponent(), x, y);

                }
            }

        }
        
        if(e.getSource() == rightArrow){
            for(Map m: allMapList){
                if(m.description.equals(this.map.description) && m.floor == (this.map.floor + 1)){
                    System.out.println("mapID is " + m.mapID);
                    this.reloadMap(m.mapID);
                    break;             //  m  will update by reload , so we need to break the loop 
                }
            }
           // this.reloadMap(this.map.);
            
        }
        if(e.getSource() == leftArrow){
            for(Map m: allMapList){
                if(m.description.equals(this.map.description) && m.floor == this.map.floor - 1 ){
                    this.reloadMap(m.mapID);
                    break;
                }
            }
            
            
        }
        // added by emma
        if (e.getSource() == home){
            
           // background = new ImageIcon(this.getClass().getResource("/maps/campus_map.png")).getImage();
            this.reloadMap(1);  // campus mapID is always 1 
            mapView.getRightBar().setIsCampus(true);
            
            mapView.getSecRightSideBar().removeAll();
            mapView.getSecRightSideBar().setVisible(false);
            mapView.getRightBar().repaint();
                   
            this.repaint();
            
            
        
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
        if (e.getSource() == search) {
            search.setToolTipText("Search Route");
            search.setBounds(search.getX() - 3, search.getY() - 3, search.getWidth(), search.getHeight());
        }
        if (e.getSource() == exchange) {
            exchange.setToolTipText("Exchange StartingPoint and Destination");
            exchange.setBounds(exchange.getX() - 1, exchange.getY() - 1, exchange.getWidth(), exchange.getHeight());

        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == search) {
            search.setBounds(search.getX() + 3, search.getY() + 3, search.getWidth(), search.getHeight());

            // search.set
        }
        if (e.getSource() == exchange) {
            exchange.setToolTipText("Exchange StartingPoint and Destination");
            exchange.setBounds(exchange.getX() + 1, exchange.getY() + 1, exchange.getWidth(), exchange.getHeight());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == timer) {
            Edge edge = new Edge();

            edge = route.get(index);
            Graphics g = this.getGraphics();
            g.setColor(Color.red);
            // Stroke stroke  = new BasicStroke(3.0f);

            if (index == 0) {
                g.drawString(startLocation.name, startLocation.point.X - 30, startLocation.point.Y - 30);
                g.drawImage(pinImage, startLocation.point.X - 5, startLocation.point.Y - 20, 20, 20, null);
            }
            if (index == route.size() - 1) {
                g.drawString(endLocation.name, endLocation.point.X - 30, endLocation.point.Y - 30);
                g.drawImage(pinImage, endLocation.point.X - 5, endLocation.point.Y - 20, 20, 20, null);
            }
//                    
            g.fillOval(edge.startPoint.X - 5, edge.startPoint.Y - 5, 10, 10);
            g.fillOval(edge.endPoint.X - 5, edge.endPoint.Y - 5, 10, 10);

            g.drawLine(edge.startPoint.X, edge.startPoint.Y, edge.endPoint.X, edge.endPoint.Y);

            index++;
            if (index == route.size()) {
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

    /**
     * @return the showRoute
     */
    public boolean isShowRoute() {
        return showRoute;
    }

    /**
     * @param showRoute the showRoute to set
     */
    public void setShowRoute(boolean showRoute) {
        this.showRoute = showRoute;
    }

    /**
     * @return the showPins
     */
    public boolean isShowPins() {
        return showPins;
    }

    /**
     * @param showPins the showPins to set
     */
    public void setShowPins(boolean showPins) {
        this.showPins = showPins;
    }

    /**
     * @return the drawRoutes
     */
    public boolean isDrawRoutes() {
        return drawRoutes;
    }

    /**
     * @param drawRoutes the drawRoutes to set
     */
    public void setDrawRoutes(boolean drawRoutes) {
        this.drawRoutes = drawRoutes;
    }

    /**
     * @return the showAllPins
     */
    public boolean isShowAllPins() {
        return showAllPins;
    }

    /**
     * @param showAllPins the showAllPins to set
     */
    public void setShowAllPins(boolean showAllPins) {
        this.showAllPins = showAllPins;
    }

    /**
     * @return the drawMultiRoutes
     */
    public boolean isDrawMultiRoutes() {
        return drawMultiRoutes;
    }

    /**
     * @param drawMultiRoutes the drawMultiRoutes to set
     */
    public void setDrawMultiRoutes(boolean drawMultiRoutes) {
        this.drawMultiRoutes = drawMultiRoutes;
    }

    /**
     * @return the multiMapIndex
     */
    public ArrayList<Integer> getMultiMapIndex() {
        return multiMapIndex;
    }

    /**
     * @param multiMapIndex the multiMapIndex to set
     */
    public void setMultiMapIndex(ArrayList<Integer> multiMapIndex) {
        this.multiMapIndex = multiMapIndex;
    }

    /**
     * @return the multiRoute
     */
    public ArrayList<Edge> getMultiRoute() {
        return multiRoute;
    }

    /**
     * @param multiRoute the multiRoute to set
     */
    public void setMultiRoute(ArrayList<Edge> multiRoute) {
        this.multiRoute = multiRoute;
    }
    
     public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }
    
    public MapView getMapView() {
        return mapView;
    }

}
