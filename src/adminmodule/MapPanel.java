package adminmodule;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yihao
 */
public class MapPanel extends JScrollPane {

    int radius = 10;
    AdminFrame frame;
    Image image;

    //private final JScrollPane scrollPane;
    public MapPanel(AdminFrame frame) {
        this.frame = frame;
        this.setLayout(new ScrollPaneLayout());
        image = new ImageIcon(this.getClass().getResource("/maps/refined_project_floor_1.png")).getImage();
        
        //JLabel label = new JLabel(image);
        //this.getViewport().add(label);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        System.out.println(this.getWidth() + "," + this.getHeight());
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        //g.drawOval(x-radius, y-radius, 2*radius, 2*radius);
        g.setColor(Color.red);
        //g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
        for (Point p : frame.points) {
            int x = p.X - this.getHorizontalScrollBar().getValue();
            int y = p.Y - this.getVerticalScrollBar().getValue();
            switch (p.type) {
                case WAYPOINT:
                    if (!(x < 0 || y < 0)) {
                        g.fillOval(x-5, y-5, 10, 10);
                    }
                    break;
                case LOCATION:
                    if (!(x < 0 || y < 0)) {
                        g.fillRect(x-5, y-5, 10, 10);
                    }
                    break;
            }
        }
        for (Edge e : frame.edges) {
            g.drawLine(e.startPoint.X, e.startPoint.Y, e.endPoint.X, e.endPoint.Y);
        }
    }

}
