/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Yihao
 */
public class AdminFrame extends JFrame implements MouseListener {

    public enum Button {

        POINT, LOCATION, EDGE, NULL
    };

    public ArrayList<Point> points = new ArrayList<Point>();
    public ArrayList<Location> locations = new ArrayList<Location>();
    public ArrayList<Edge> edges = new ArrayList<Edge>();
    public Button button = Button.NULL;

    int radius = 10;
    MapPanel map;
    Point startpoint = null;
    Point endpoint = null;

    public static void main(String[] args) {
        System.out.println("start...");
        AdminFrame f = new AdminFrame();
        f.init();
    }

    public void init() {
        map = new MapPanel(this);
        map.setPreferredSize(new Dimension(900, 800));
        //map.addMouseListener(this);
        this.getContentPane().add(map, BorderLayout.CENTER);

        RightPanel right = new RightPanel(this);
        this.getContentPane().add(right, BorderLayout.EAST);

        LeftPanel left = new LeftPanel();
        this.getContentPane().add(left, BorderLayout.WEST);

        this.setVisible(true);
        Dimension d = new Dimension(1300, 850);
        this.setSize(d);
        this.setResizable(false);
    }

    @Override
    /* left click on the map */
    public void mouseClicked(MouseEvent e) {

        Point newpoint;
        Location newlocation;
        Edge newedge;
        int x = e.getX() + map.getHorizontalScrollBar().getValue();
        int y = e.getY() + map.getVerticalScrollBar().getValue();

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
                points.add(newpoint);
                locations.add(newlocation);
                System.out.println("add location:" + newpoint.X + ", " + newpoint.Y);
                System.out.println("point list size:" + points.size());
                System.out.println("location list size:" + locations.size());
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
                        }
                        else if ((startpoint != null) && (endpoint == null) && (temp != startpoint)) {
                            endpoint = temp;
                            newedge = new Edge(startpoint, endpoint);
                            edges.add(newedge);
                            System.out.println("add edge weight:" + newedge.weight);
                            System.out.println("edge list size:" + edges.size());
                            startpoint = null;
                            endpoint = null;
                            repaint();
                        } else {
                            return;
                        }
                    }
                }
            break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {

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
