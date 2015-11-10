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
    
    public Button button = Button.NULL;
    
    public JDBC db = new JDBC();
    int radius = 10;
    
    
    MapPanel map ;
    LeftPanel left;
    Point startpoint = null;
    Point endpoint = null;


    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("start...");
        AdminFrame f = new AdminFrame();
        f.init();
    }

    public void init() throws SQLException, IOException {
        
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
            points = maps.get(0).pointList;
            locations = maps.get(0).locList;
            edges = maps.get(0).edgeList;

            left.mapList.setSelectedIndex(0);
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


    /*
     class PopupTriggerListener extends MouseAdapter {
     public void mousePressed(MouseEvent ev) {
     if (ev.isPopupTrigger()) {
     menu.show(ev.getComponent(), ev.getX(), ev.getY());
     }
     }

     public void mouseReleased(MouseEvent ev) {
     if (ev.isPopupTrigger()) {
     menu.show(ev.getComponent(), ev.getX(), ev.getY());
     }
     }

     public void mouseClicked(MouseEvent ev) {
     }
     } */
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
        if (e.isPopupTrigger()) {
            for (Location temp : locations) {
                if (!((x < (temp.point.getX() - radius))
                        || (x > (temp.point.getX() + radius))
                        || (y < (temp.point.getY() - radius))
                        || (y > (temp.point.getY() + radius)))) {
                        PopupMenu menu = new PopupMenu(temp);
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
                                return;
                            } else if ((startpoint != null) && (endpoint == null) && (temp != startpoint)) {
                                endpoint = temp;
                                newedge = new Edge(startpoint, endpoint);
                                newedge.edgeID = -1;
                                newedge.startMapID = maps.get(left.mapList.getSelectedIndex()).mapID;
                                newedge.endMapID = maps.get(left.mapList.getSelectedIndex()).mapID;
                                edges.add(newedge);
                                System.out.println("add edge weight:" + newedge.weight);
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
        int sIndex = left.mapList.getSelectedIndex();
        this.mapChanged(sIndex);
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
