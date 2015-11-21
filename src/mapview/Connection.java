/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Point;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author GaoYifei
 */
public class Connection extends JPopupMenu {

    public MainPanel mainPanel;

    public Connection(Point point) {

        JMenuItem next = new JMenuItem("NextMap");

        JMenuItem pre = new JMenuItem("PreMap");

        ActionListener popListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                if (event.getSource() == next) {
                    mainPanel.setMapIndex(2);

                    try {
                        mainPanel.init();
                    } catch (SQLException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mainPanel.setDrawMultiRoutes(true);
                    mainPanel.repaint();

                }
                if (event.getSource() == pre) {
                    mainPanel.setMapIndex(1);

                    try {
                        mainPanel.init();
                    } catch (SQLException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mainPanel.setDrawMultiRoutes(true);
                    mainPanel.repaint();

                }
            }
        };
        next.addActionListener(popListener);
        this.add(next);
        pre.addActionListener(popListener);
        this.add(pre);

    }

}
