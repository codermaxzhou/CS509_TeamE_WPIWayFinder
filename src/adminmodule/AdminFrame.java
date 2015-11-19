/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jdbc.JDBC;

/**
 *
 * @author Yihao
 */
public class AdminFrame extends JFrame implements MouseListener, ListSelectionListener {

    public enum Button {POINT, LOCATION, EDGE, NULL};
    public MapInfo mapinfo = new MapInfo();
    
    public ArrayList<Point> points = new ArrayList<>();
    public ArrayList<Location> locations = new ArrayList<>();
    public ArrayList<Edge> edges = new ArrayList<>();
    public ArrayList<Map> maps = new ArrayList<>();
    //delete point
    public ArrayList<Point> deletedPoints = new ArrayList<>();
    public ArrayList<Location> deletedLocations = new ArrayList<>();
    public ArrayList<Edge> deletedEdges = new ArrayList<>();
    
    public Button button = Button.NULL;
    
    public JDBC db = new JDBC();
    int radius = 10;
    int sIndex = 0;
    
    
    MapPanel map ;
    LeftPanel left;
    Point startpoint = null;
    Point endpoint = null;
    int tempMapID = -1;


    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("start...");
        AdminFrame f = new AdminFrame();
        f.init();
    }

    public void init() throws SQLException, IOException {
        this.setTitle("Administrator Module");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        map = new MapPanel(this);
        map.setPreferredSize(new Dimension(900, 800));
        //map.addMouseListener(this);
        this.getContentPane().add(map, BorderLayout.CENTER);

        RightPanel right = new RightPanel(this);
        this.getContentPane().add(right, BorderLayout.EAST);


        left = new LeftPanel(this);
        left.mapList.getSelectionModel().addListSelectionListener(this);
        this.getContentPane().add(left, BorderLayout.WEST);

        this.setVisible(true);
        Dimension d = new Dimension(1300, 850);
        //System.out.println(map.getWidth() + " : " + map.getHeight());
        this.setSize(d);
        this.setResizable(false);
        map.addMouseListener(this);
        loadMapInfo();
        //map.addMouseListener(new PopupTriggerListener());
    }

    
    public void loadMapInfo() throws SQLException, IOException {
        maps = db.showAllMap();
        
        for(Map m : maps) {
            if(!left.model.contains(m.name))
                left.model.addElement(m.name);
        }

        //mapinfo = db.getMapInfo(1);
        
        if(maps.size() > 0) {
            points = maps.get(sIndex).pointList;
            locations = maps.get(sIndex).locList;
            edges = maps.get(sIndex).edgeList;

            left.mapList.setSelectedIndex(sIndex);
        }
    }
    
    
    
    public void mapChanged(int index) {
        map.image = maps.get(index).image;
        
        points = maps.get(index).pointList;
        locations = maps.get(index).locList;
        edges = maps.get(index).edgeList;
        repaint();
    }

    public  void PassValue(Map p)
    {
        
        //map.image = p.image;
//        look for method in JList that adds or appends item
         //left.mapList.setListData(new String[] {"Hello"});
        left.model.addElement(p.name);
        maps.add(p);
        
        if(maps.size() == 1) left.mapList.setSelectedIndex(0);
        
    }
    
    public void deleteMap(Map mp) {
        
         maps.remove(mp);
        //System.out.println(deletedPoints);
    }
        
    public void deletePoint(Point pt) {
        ArrayList<Edge> copy = new ArrayList<>();
        copy = (ArrayList<Edge>) edges.clone();
        deletedPoints.add(pt);
        points.remove(pt);
   
        for (Edge e : copy) {
            if (e.startPoint.equals(pt)) {
                deletedEdge(e);
            }
            if(e.endPoint.equals(pt)) {
                deletedEdge(e);
            }
        }
        //System.out.println(deletedPoints);
    }
    
    public void deletedLocation(Location lc) {
        deletedLocations.add(lc);
        locations.remove(lc);
        deletePoint(lc.point);
    }
    
    public void deletedEdge(Edge ed) {
        deletedEdges.add(ed);
        edges.remove(ed);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    
    //CREATE METHOD HERE THAT YOUR FRAME WILL CALL WHEN IT IS CLOSING

    @Override
    public void mousePressed(MouseEvent e) {
        Point newpoint;
        Location newlocation;
        Edge newedge;
        int x = e.getX();
        int y = e.getY();
        
        /* right click on the map */
        if (e.isMetaDown()) {
            for (Point temp : points) {
                if (!((x < (temp.getX() - radius))
                        || (x > (temp.getX() + radius))
                        || (y < (temp.getY() - radius))
                        || (y > (temp.getY() + radius))) && (temp.location == null)) {
                        PopupMenu menu = new PopupMenu(temp, this);
                        menu.show(e.getComponent(), x, y);
                } else if (!((x < (temp.getX() - radius))
                        || (x > (temp.getX() + radius))
                        || (y < (temp.getY() - radius))
                        || (y > (temp.getY() + radius))) && (temp.location != null)) {
                        PopupMenu menu = new PopupMenu(temp.location, this);
                        menu.show(e.getComponent(), x, y);
                    
                }
            }

        }

        /* left click on the map */
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (button) {
                case POINT:
                    for (Point temp : points) {
                        if (!((x < (temp.getX() - radius))
                                || (x > (temp.getX() + radius))
                                || (y < (temp.getY() - radius))
                                || (y > (temp.getY() + radius)))) {
                            return;
                        }
                    }

                    newpoint = new Point(x, y, Point.Type.WAYPOINT);
                    newpoint.pointID = -1;
                    newpoint.map = maps.get(left.mapList.getSelectedIndex());
                    points.add(newpoint);
                    System.out.println("add point:" + newpoint.X + ", " + newpoint.Y);
                    System.out.println("point list size:" + points.size());
                    repaint();
                    break;

                case LOCATION:
                    for (Location temp : locations) {
                        if (!((x < (temp.point.getX() - radius))
                                || (x > (temp.point.getX() + radius))
                                || (y < (temp.point.getY() - radius))
                                || (y > (temp.point.getY() + radius)))) {
                            return;
                        }
                    }

                    newpoint = new Point(x, y, Point.Type.LOCATION);
                    newlocation = new Location(newpoint);
                    newpoint.map = maps.get(left.mapList.getSelectedIndex());
                    newpoint.location = newlocation;
                    newpoint.pointID = -1;
                    newlocation.locationID = -1;
                    newlocation.name = "test" + x;
                    newlocation.description = "desc" + y;
                    /*newlocation.category = Location.Category.DINING;*/
                    points.add(newpoint);
                    locations.add(newlocation);
                    System.out.println("add location:" + newpoint.X + ", " + newpoint.Y);
                    System.out.println("point list size:" + points.size());
                    System.out.println("location list size:" + locations.size());

                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            JFrame frame = new LocationEdit(newlocation);
                            frame.setTitle("Location Edit");
                            frame.setSize(500, 500);
                            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                            frame.setVisible(true);
                            System.out.println("add location:" + newpoint.X + ", " + newpoint.Y);
                            System.out.println("point list size:" + points.size());
                            System.out.println("location list size:" + locations.size());

                        }

                    });
                    repaint();
                    break;

                case EDGE:
                    for (Point temp : points) {
                        if (!((x < (temp.getX() - radius))
                                || (x > (temp.getX() + radius))
                                || (y < (temp.getY() - radius))
                                || (y > (temp.getY() + radius)))) {
                           

                            if ((startpoint == null) && (endpoint == null)) {
                                startpoint = temp;
                                tempMapID = maps.get(left.mapList.getSelectedIndex()).mapID;
                                return;
                            } else if ((startpoint != null) && (endpoint == null) && (temp != startpoint)) {
                                endpoint = temp;
                                newedge = new Edge(startpoint, endpoint);
                                newedge.edgeID = -1;
                                newedge.startMapID = tempMapID;
                                newedge.endMapID = maps.get(left.mapList.getSelectedIndex()).mapID;
                                if (newedge.startMapID != newedge.endMapID) {
                                    newedge.weight = 5;
                                    newedge.startPoint.type = Point.Type.CONNECTION;
                                    newedge.endPoint.type = Point.Type.CONNECTION;
                                }
                                edges.add(newedge);
                                System.out.println("edge list size:" + edges.size());
                                startpoint = null;
                                endpoint = null;
                                repaint();
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                    break;
            }
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        final Object selectedValue = left.mapList.getSelectedValue();
        if ( selectedValue != null ) {
            sIndex = left.mapList.getSelectedIndex();
            this.mapChanged(sIndex);
        }
        
        System.out.println(maps);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
          
}
