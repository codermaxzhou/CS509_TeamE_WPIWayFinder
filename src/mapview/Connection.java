/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Edge;
import adminmodule.Point;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private ArrayList<Integer> multiMapIndex;
    private ArrayList<Edge> edgeList;
    int nextMapIndex;
    int preMapIndex;

    public Connection(Point point, MainPanel mainPanel) {

        JMenuItem next = new JMenuItem("NextMap");

        JMenuItem pre = new JMenuItem("PreMap");

        edgeList = mainPanel.getMultiRoute();
        multiMapIndex = mainPanel.getMultiMapIndex();

        ActionListener popListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                if (event.getSource() == next) {
                    for(int i: multiMapIndex){
                         System.out.print(multiMapIndex.get(i));
                    }
                   
                    for (Edge e : edgeList) {
                        if ("CONNECTION".equals(e.startPoint.type.name()) && "CONNECTION".equals(e.endPoint.type.name())) {
                            if (e.startMapID == mainPanel.getMapIndex()) {
                                for (int i : multiMapIndex) {
                                    if (multiMapIndex.get(i) == e.startMapID) {
                                        nextMapIndex = multiMapIndex.get(i + 1);
                                    }
                                }

                                mainPanel.setMapIndex(nextMapIndex);
                                try {
                                    mainPanel.init();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                mainPanel.setDrawMultiRoutes(true);
                                mainPanel.repaint();
                                break;
                            }
                        }

                    }

                }
                if (event.getSource() == pre) {
                    for (Edge e : edgeList) {
                        if (e.endPoint.type.name() == "CONNECTION" && e.endMapID == mainPanel.getMapIndex()) {
                            mainPanel.setMapIndex(e.startMapID);
                            try {
                                mainPanel.init();
                            } catch (SQLException ex) {
                                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            mainPanel.setDrawMultiRoutes(true);
                            mainPanel.repaint();
                            break;
                        }
                    }

                }
            }
        };
        next.addActionListener(popListener);
        this.add(next);
        pre.addActionListener(popListener);
        this.add(pre);

    }

}
