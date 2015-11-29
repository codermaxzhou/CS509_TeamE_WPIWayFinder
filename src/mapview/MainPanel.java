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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Image startIcon = new ImageIcon(this.getClass().getResource("/icons/start.png")).getImage();
    private Image endIcon = new ImageIcon(this.getClass().getResource("/icons/end.png")).getImage();
    private Image connctionStartIcon = new ImageIcon(this.getClass().getResource("/icons/connection_start.png")).getImage();
    private Image connctionEndIcon = new ImageIcon(this.getClass().getResource("/icons/connection_end.png")).getImage();
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
    //private int mapIndex;

    // boolean varaibels 
    private boolean showRoute = false;
    private boolean showPins = false;
    private boolean drawRoutes = false;
    private boolean showAllPins = false;
    private boolean clear = true;
    private boolean drawMultiRoutes = false;
    private boolean showWhenClick = false;
    private int clicked = 0;
    private Font locationFont = new Font("Roboto", Font.BOLD, 12);
    private Font mapINFOFont = new Font("Roboto", Font.BOLD, 24);
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
        //mapIndex = 1;  // default is mapID 1
        this.init();

    }

    MainPanel() throws SQLException {

        //mapIndex = 1;  // default is mapID 1
        this.init();

    }

    public void init() throws SQLException {

        //MapInfo info = db.getMapInfo(mapIndex, getMap()); // why do we need map as parameter?
        mapModel = new MapModel();
        allMapList = mapModel.getMapList();
        allEdgeList = mapModel.getAllEdgeList();
        allPointList = mapModel.getAllPointList();
        allLocationList = mapModel.getAllLocationList();
        algo = new Dijkstra(allEdgeList, allPointList);

        //setMap(allMapList.get(mapIndex - 1));  // map 默认是1 
        map = allMapList.get(0);
        mapImage = getMap().image;
        pointList = map.pointList;
        edgeList = map.edgeList;
        locationList = map.locList;

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

        //  JLabel label = new JLabel(map.description);
        startPointField.setColumns(20);
        startPointField.setBounds(40, 10, 150, 30);
        startPointField.setText("Start Point");
        startPointField.setFont(font);
        startPointField.setForeground(Color.gray);

        endPointField.setColumns(20);
        endPointField.setBounds(240, 10, 150, 30);
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
        startPointField.addMouseListener(this);
        endPointField.addMouseListener(this);

        search.setBounds(400, 8, 40, 40);
        home.setBounds(5, 10, 40, 30);
        exchange.setBounds(200, 5, 40, 40);
        leftArrow.setBounds(850, 5, 50, 40);
        rightArrow.setBounds(900, 5, 50, 40);

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

    public void reloadMap(Map mapToLoad) {
        for (CustomBalloonTip tip : tipList) {
            ToolTipUtils.toolTipToBalloon(tip);
            tip.setVisible(false);
            tip.closeBalloon();
        }

        tipList.clear();

        setMap(allMapList.get(allMapList.indexOf(mapToLoad)));
        //this.mapIndex = mapIndex;
//        this.showRoute = false;
//        this.drawMultiRoutes = false;
//        this.drawRoutes = false;
        this.showRoute = false;
        this.showAllPins = false;
        this.showPins = false;
        mapView.getSecRightSideBar().removeAll();
        mapView.getSecRightSideBar().setVisible(false);

//        MapInfo info = null;
//        try {
//            info = db.getMapInfo(mapIndex, map); // why do we need map as parameter?
//        } catch (SQLException ex) {
//            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
        pointList = getMap().pointList;
        edgeList = getMap().edgeList;
        locationList = getMap().locList;
        mapImage = getMap().image;

        this.repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(mapImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(mapINFOFont);
        g.setColor(Color.lightGray);
        g.drawString(getMap().description,600, 33);

        if (isShowPins()) {         

            for (Location p : pins) {
                g.setColor(Color.black);
                g.setFont(locationFont);
                g.drawImage(pinImage, p.point.X - 5, p.point.Y - 5, 20, 20, null);
                g.drawString(p.name, p.point.X + 10, p.point.Y - 25);

            }
        }
        if (isShowRoute()) {
            this.timer.start();

        }
        if (isShowAllPins()) {
            for (Location l : locationList) {
                g.drawImage(pinImage, l.point.X - 5, l.point.Y - 5, 20, 20, null);
                //g.drawString(l.point.location.name, l.point.X - 30, l.point.Y - 10);

            }
        }

        if (isDrawMultiRoutes()) {

            //Edge startEdge = getMultiRoute().get(0);
            //Edge endEdge = getMultiRoute().get(getMultiRoute().size() - 1);
            int diviation = 10;

            if (startLocation.point.map.mapID == getMap().mapID) {
                g.drawImage(startIcon, startLocation.point.X - diviation, startLocation.point.Y - diviation, 20, 20, null);
            }
            if (endLocation.point.map.mapID == getMap().mapID) {
                g.drawImage(endIcon, endLocation.point.X - diviation, endLocation.point.Y - diviation, 20, 20, null);
            }

            int size = getMultiRoute().size();
            for (int i = 0; i <= size - 1; i++) {
                Edge e = getMultiRoute().get(i);
                //String type = route.get(i).startPoint.type.equals("CONNECTION");

                if (e.startMapID == getMap().mapID && e.endMapID == getMap().mapID) {
                    g.drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
                }

                if (e.startPoint.type.name().equals("CONNECTION")
                        && e.startMapID == getMap().mapID) {

                    g.drawImage(connctionStartIcon, e.startPoint.X - diviation, e.startPoint.Y - diviation, 20, 20, null);

                }

                if (e.endPoint.type.name().equals("CONNECTION")
                        && e.endMapID == getMap().mapID) {

                    g.drawImage(connctionEndIcon, e.endPoint.X - diviation, e.endPoint.Y - diviation, 20, 20, null);

                }

            }

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
        // reload map if current Map is not the routing map 
        if (this.getMultiRoute().get(0).startMapID != getMap().mapID) {
            for (Map m : allMapList) {
                if (m.mapID == this.getMultiRoute().get(0).startMapID) {
                    this.reloadMap(m);
                    break;
                }
            }

        }

        setDrawMultiRoutes(true);
        setShowAllPins(false);
        setShowRoute(false);
        setShowPins(false);
        repaint();
    }

//    public void showClickPin(String name) {
//        this.setShowRoute(false);
//        this.setShowPins(true);
//        this.setShowAllPins(false);
//
////        if (clear) {
////            pins.clear();
////        }
//        pins.clear();
//        for (Location p : locationList) {
//            if (p.name.equals(name)) {
//                pins.add(p);
//                break;
//            }
//
//        }
//        this.repaint();
//    }

    public void showSinglePin(String name) {
        this.setDrawMultiRoutes(false);
        this.setShowRoute(false);
        this.setShowPins(true);
        this.setShowAllPins(false);

        pins.clear();

        for (CustomBalloonTip tip : tipList) {
            ToolTipUtils.toolTipToBalloon(tip);
            tip.setVisible(false);
            tip.closeBalloon();
        }

        tipList.clear();

//        for (Location p : allLocationList) {
//            // 判断location的mapID 就可以判断显示那张地图 
//
//            if (p.name.equals(name)) {
//                // hardcode need to be improved 
//                for (Map m : allMapList) {
//                    if (p.point.map.mapID == m.mapID) {
//                        //mapIndex = m.mapID;
//                        try {
//                            this.init();
//                        } catch (SQLException ex) {
//                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//
//                pins.add(p);
//                break;
//            }
//        }
        for (Location p : locationList) {
            if (p.name.equals(name)) {
                pins.add(p);
                break;
            }
        }

        for (Location p : pins) {
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
        this.setDrawMultiRoutes(false);

        Graphics g = this.getGraphics();
        pins.clear();

        for (CustomBalloonTip tip : tipList) {
            ToolTipUtils.toolTipToBalloon(tip);
            tip.setVisible(false);
            tip.closeBalloon();
        }

        tipList.clear();

        for (Location p : locationList) {
            if (p.category.toString().equals(category)) {
                pins.add(p);
            }

        }

        for (Location p : pins) {
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

    public void clearPins() {
        setShowRoute(false);
        setShowPins(false);
        setShowAllPins(false);
        setDrawMultiRoutes(false);
        repaint();
    }

    public void showPins() {

        setShowAllPins(true);
        setShowRoute(false);
        setDrawMultiRoutes(false);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        String startPointString = startPointField.getText();
        String endPointString = endPointField.getText();
        if (e.getSource() != search && e.getSource() != exchange && e.getSource() != rightArrow && e.getSource() != leftArrow && e.getSource() != home) {

            int radius = 30;
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
                        showSinglePin(temp.name);
                    } else {
                        endPointField.setText(temp.name);
                        clear = false;
                        showSinglePin(temp.name);

                    }

                    Map locationMap = null;

                    for (Map m : allMapList) {
                        if (temp.locationID == m.locationID && m.floor == 1) {
                            locationMap = m;
                            break;
                        }
                    }

                    if (locationMap != null) {
                        Enter enterMenu = new Enter(locationMap, this);

                        enterMenu.setRightBar(mapView.getRightBar());
                        enterMenu.setSecRightBar(mapView.getSecRightSideBar());
                        enterMenu.show(e.getComponent(), x, y);
                    }

                    // emma new code 
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

                    if (startLocation.point.map.mapID != getMap().mapID) {
                        this.reloadMap(startLocation.point.map);
                    }
                    drawMultiRoute(startLocation, endLocation);

                } else if (startLocation.point.map.mapID == endLocation.point.map.mapID) {

                    if (startLocation.point.map.mapID != getMap().mapID) {
                        this.reloadMap(startLocation.point.map);
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

//        if (e.getSource() == nextButton) {
//            // System.out.print("next Button Clicked");
//           // mapIndex = 2;
//            //this.setMapIndex(mapIndex);
//            setDrawMultiRoutes(true);
//            this.setShowAllPins(false);
//            this.setShowPins(false);
//            this.setDrawRoutes(false);
//            this.setShowRoute(false);
//            try {
//                this.init();
//            } catch (SQLException ex) {
//                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            this.repaint();
//            this.remove(nextButton);
//        }
//        if(e.getX() < connectionX + 20 && e.getX() > connectionX - 20 && e.getY() < connectionY + 20 && e.getY() > connectionY - 20){
//            System.out.print("click the connection button");
//        }
        // right click 
//        if (e.isMetaDown()) {
//
//            int x = e.getX();
//            int y = e.getY();
//            int radius = 30;
//            for (Point temp : allPointList) {
//                if (!((x < (temp.getX() - radius))
//                        || (x > (temp.getX() + radius))
//                        || (y < (temp.getY() - radius))
//                        || (y > (temp.getY() + radius))) && temp.type.name() == "CONNECTION") {
//                    Connection connection = new Connection(temp, this);
//                    //connection.mainPanel = this;
//                    connection.show(e.getComponent(), x, y);
//
//                }
//            }
//
//        }
        if (e.getSource() == rightArrow) {
            if (isDrawMultiRoutes()) {
                Map nextMap = null;

                int lowestIndex = -1;
                int i = 0;

                for (Edge edge : getMultiRoute()) {
                    if (edge.startMapID == getMap().mapID || edge.endMapID == getMap().mapID) {
                        lowestIndex = i;
                        break;
                    }
                    i++;
                }

                int ID = -1, currIndex = -1;
                i = 0;

                for (Edge edge : getMultiRoute()) {
                    if (edge.startMapID == getMap().mapID && edge.endMapID != getMap().mapID) {
                        ID = edge.endMapID;
                        currIndex = i;
                    } else if (edge.startMapID != getMap().mapID && edge.endMapID == getMap().mapID) {
                        ID = edge.startMapID;
                        currIndex = i;
                    }

                    i++;
                }

                if (ID != -1 && (currIndex >= lowestIndex)) {
                    for (Map m : allMapList) {
                        if (m.mapID == ID) {
                            nextMap = m;
                        }
                    }
                }
                // the last map 
                if (getMultiRoute().get(getMultiRoute().size() - 1).endMapID == getMap().mapID) {
                    nextMap = null;
                }

                if (nextMap != null) {
                    this.reloadMap(nextMap);
                }
            } else {
                for (Map m : allMapList) {
                    if (m.locationID == getMap().locationID && m.floor == getMap().floor + 1) {
                        this.reloadMap(m);
                        break;             //  m  will update by reload , so we need to break the loop 
                    }
                }
            }
            // this.reloadMap(this.map.);

        }
        if (e.getSource() == leftArrow) {
            if (isDrawMultiRoutes()) {
                Map prevMap = null;

                int lowestIndex = -1;
                int i = 0;

                for (Edge edge : getMultiRoute()) {
                    if (edge.startMapID == getMap().mapID || edge.endMapID == getMap().mapID) {
                        lowestIndex = i;
                        break;
                    }
                    i++;
                }

                int ID = -1, currIndex = -1;
                i = 0;

                for (Edge edge : getMultiRoute()) {
                    if (edge.startMapID == getMap().mapID && edge.endMapID != getMap().mapID && ID == -1) {
                        ID = edge.endMapID;
                        currIndex = i;
                    } else if (edge.startMapID != getMap().mapID && edge.endMapID == getMap().mapID && ID == -1) {
                        ID = edge.startMapID;
                        currIndex = i;
                    }

                    i++;
                }

                if (ID != -1 && currIndex <= lowestIndex) {
                    for (Map m : allMapList) {
                        if (m.mapID == ID) {
                            prevMap = m;
                        }
                    }
                }

                if (this.getMultiRoute().get(0).startMapID == getMap().mapID) {
                    prevMap = null;
                }

                if (prevMap != null) {
                    this.reloadMap(prevMap);
                }
            } else {
                for (Map m : allMapList) {
                    if (m.locationID == getMap().locationID && m.floor == getMap().floor - 1) {
                        this.reloadMap(m);
                        break;
                    }
                }
            }

        }
        // added by emma
        if (e.getSource() == home) {

            // background = new ImageIcon(this.getClass().getResource("/maps/campus_map.png")).getImage();
            Map campusMap = null;

            for (Map m : allMapList) {
                if (!m.isInteriorMap) {
                    campusMap = m;
                    break;
                }
            }

            this.reloadMap(campusMap);  // campus mapID is always 1 
            mapView.getRightBar().setIsCampus(true);

            mapView.getSecRightSideBar().removeAll();
            mapView.getSecRightSideBar().setVisible(false);
            mapView.getRightBar().repaint();

            this.repaint();

        }

        if (e.getSource() == startPointField) {
            startPointField.setFocusable(true);
            startPointField.setText("");

        }

        if (e.getSource() == endPointField) {
            endPointField.setFocusable(true);
            endPointField.setText("");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == rightArrow) {
            rightArrow.setBounds(rightArrow.getX() + 3, rightArrow.getY() + 3, rightArrow.getWidth(), rightArrow.getHeight());
        }
        if (e.getSource() == leftArrow) {
            leftArrow.setBounds(leftArrow.getX() + 3, leftArrow.getY() + 3, leftArrow.getWidth(), leftArrow.getHeight());
        }
        if (e.getSource() == home) {
            home.setBounds(home.getX() + 3, home.getY() + 3, home.getWidth(), home.getHeight());
        }
        if (e.getSource() == search) {
            search.setBounds(search.getX() + 3, search.getY() + 3, search.getWidth(), search.getHeight());
        }
        if (e.getSource() == exchange) {
            exchange.setBounds(exchange.getX() + 3, exchange.getY() + 3, exchange.getWidth(), exchange.getHeight());
        }

        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == rightArrow) {
            rightArrow.setBounds(rightArrow.getX() - 3, rightArrow.getY() - 3, rightArrow.getWidth(), rightArrow.getHeight());
        }
        if (e.getSource() == leftArrow) {
            leftArrow.setBounds(leftArrow.getX() - 3, leftArrow.getY() - 3, leftArrow.getWidth(), leftArrow.getHeight());
        }
        if (e.getSource() == home) {
            home.setBounds(home.getX() - 3, home.getY() - 3, home.getWidth(), home.getHeight());
        }
        if (e.getSource() == search) {
            search.setBounds(search.getX() - 3, search.getY() - 3, search.getWidth(), search.getHeight());
        }
        if (e.getSource() == exchange) {
            exchange.setBounds(exchange.getX() - 3, exchange.getY() - 3, exchange.getWidth(), exchange.getHeight());
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == search) {
            search.setToolTipText("Search Route");

        }
        if (e.getSource() == exchange) {
            exchange.setToolTipText("Exchange StartingPoint and Destination");

        }
        if (e.getSource() == rightArrow) {
            if (this.drawMultiRoutes) {
                rightArrow.setToolTipText("Next routing path");
            } else {
                rightArrow.setToolTipText("Next Floor");
            }
        }
        if (e.getSource() == leftArrow) {
            if (this.drawMultiRoutes) {
                leftArrow.setToolTipText("Previous Routing Path");
            } else {
                leftArrow.setToolTipText("Previous Floor");
            }
        }
        if (e.getSource() == home) {
            home.setToolTipText("return to campus map");
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
//        if (e.getSource() == search) {
//            search.setBounds(search.getX() + 3, search.getY() + 3, search.getWidth(), search.getHeight());
//
//        }
//        if (e.getSource() == exchange) {
//            exchange.setToolTipText("Exchange StartingPoint and Destination");
//            exchange.setBounds(exchange.getX() + 1, exchange.getY() + 1, exchange.getWidth(), exchange.getHeight());
//        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == timer) {
            Edge edge = new Edge();

            edge = route.get(index);
            Graphics g = this.getGraphics();
            g.setColor(Color.red);
            g.setFont(locationFont);
            
            // Stroke stroke  = new BasicStroke(3.0f);

            if (index == 0) {
                g.drawString(startLocation.name, startLocation.point.X + 10, startLocation.point.Y - 25);
                g.drawImage(startIcon, startLocation.point.X +5, startLocation.point.Y - 20, 30, 30, null);
            }
            if (index == route.size() - 1) {
                g.drawString(endLocation.name, endLocation.point.X + 10, endLocation.point.Y - 25);
                g.drawImage(endIcon, endLocation.point.X +5, endLocation.point.Y - 20, 30, 30, null);
            }

            g.drawLine(edge.startPoint.X, edge.startPoint.Y, edge.endPoint.X, edge.endPoint.Y);

            index++;
            if (index == route.size()) {
                this.timer.stop();
                index = 0;
            }
        }
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

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

}
