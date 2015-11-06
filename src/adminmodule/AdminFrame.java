/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import jdbc.JDBC;

/**
 *
 * @author Yihao
 */
public class AdminFrame extends JFrame implements MouseListener {

    public enum Button {POINT, LOCATION, EDGE, NULL};
    public MapInfo mapinfo = new MapInfo();
    public ArrayList<Point> points = new ArrayList<Point>();
    public ArrayList<Location> locations = new ArrayList<Location>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public Button button = Button.NULL;
    public JDBC db = new JDBC();
    int radius = 10;
    MapPanel map;
    Point startpoint = null;
    Point endpoint = null;

    public static void main(String[] args) throws SQLException {
        System.out.println("start...");
        AdminFrame f = new AdminFrame();
        f.init();
    }

    public void init() throws SQLException {
        loadMapInfo();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        map = new MapPanel(this);
        map.setPreferredSize(new Dimension(900, 800));
        //map.addMouseListener(this);
        this.getContentPane().add(map, BorderLayout.WEST);

        RightPanel right = new RightPanel(this);
        this.getContentPane().add(right, BorderLayout.EAST);

        LeftPanel left = new LeftPanel();
        this.getContentPane().add(left, BorderLayout.CENTER);

        this.setVisible(true);
        Dimension d = new Dimension(1300, 850);
        //System.out.println(map.getWidth() + " : " + map.getHeight());
        this.setSize(d);
        this.setResizable(false);
        map.addMouseListener(this);
        //map.addMouseListener(new PopupTriggerListener());
    }
    
    public void loadMapInfo() throws SQLException {
        mapinfo = db.getMapInfo(1);
        points = mapinfo.points;
        locations = mapinfo.locations;
        edges = mapinfo.edges;
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
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }
}
