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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jdbc.JDBC;

/**
 *
 * @author Emma
 */
public class RightBar extends javax.swing.JPanel implements MouseListener, ActionListener, ListSelectionListener {

    // interface
    public MainPanel mainPanel = null;
    public SecRightSideBar secRightSideBar = null;
    public MapView mapView = null;
    public MapModel mapModel = new MapModel();

    private JLabel backLabel;
    private JLabel classroomLabel;
    private JLabel restroomLabel;
    private JLabel buildingLabel;
    private JLabel gymLabel;
    private JLabel diningLabel;
    private JLabel parkingLabel;
    private JLabel libraryLabel;

    private JButton clearButton;
    private JButton showButton;

    private JList floorList;
    private JScrollPane scrollPane;

    private ImageIcon buildingIcon;
    private ImageIcon gymIcon;
    private ImageIcon diningIcon;
    private ImageIcon parkingIcon;
    private ImageIcon libraryIcon;
    private ImageIcon classroomIcon;
    private ImageIcon restroomIcon;

    private boolean isCampus = true;
    private boolean buildingMove = false;
    private boolean gymMove = false;
    private boolean diningMove = false;
    private boolean parkingMove = false;
    private boolean libraryMove = false;
    private boolean classroomMove = false;
    private boolean restroomMove = false;

    private DefaultListModel listModel = new DefaultListModel();
    private ArrayList<Map> allMapList = new ArrayList<>();

    public RightBar() {
        
        this.setBackground(Color.GRAY);

        init();
        initComponent();
        

    }

    public void init() {
        allMapList = mapModel.getMapList();
        floorList = new JList();
        for (Map m : allMapList) {
            String mapName = m.name;
            listModel.addElement(mapName);

        }
        floorList.setModel(listModel);
        floorList.addListSelectionListener(this);
    }

    public void initComponent() {

        buildingLabel = new JLabel();
        buildingIcon = new ImageIcon(getClass().getResource("/icons/building.png"));
        buildingLabel.setIcon(buildingIcon);

        diningLabel = new JLabel();
        diningIcon = new ImageIcon(getClass().getResource("/icons/dining.png"));
        diningLabel.setIcon(diningIcon);

        gymLabel = new JLabel();
        gymIcon = new ImageIcon(getClass().getResource("/icons/gym.png"));
        gymLabel.setIcon(gymIcon);

        libraryLabel = new JLabel();
        libraryIcon = new ImageIcon(getClass().getResource("/icons/reading.png"));
        libraryLabel.setIcon(libraryIcon);

        parkingLabel = new JLabel();
        parkingIcon = new ImageIcon(getClass().getResource("/icons/parking.png"));
        parkingLabel.setIcon(parkingIcon);

        classroomLabel = new JLabel();
        classroomIcon = new ImageIcon(getClass().getResource("/icons/classrooml.png"));
        classroomLabel.setIcon(classroomIcon);

        restroomLabel = new JLabel();
        restroomIcon = new ImageIcon(getClass().getResource("/icons/restrooml.png"));
        restroomLabel.setIcon(restroomIcon);

        clearButton = new JButton();
        showButton = new JButton();
        clearButton.setText("Clear Pins");
        showButton.setText("Show Pins");

        floorList.setBorder(javax.swing.BorderFactory.createTitledBorder("Floor "));
        floorList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        floorList.setToolTipText("Choose Floor in Project Center");
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(floorList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(41, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buildingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(diningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gymLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(libraryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(parkingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(buildingLabel)
                        .addGap(68, 68, 68)
                        .addComponent(diningLabel)
                        .addGap(68, 68, 68)
                        .addComponent(gymLabel)
                        .addGap(68, 68, 68)
                        .addComponent(libraryLabel)
                        .addGap(68, 68, 68)
                        .addComponent(parkingLabel)
                        .addContainerGap(133, Short.MAX_VALUE))
        );

        buildingLabel.addMouseListener(this);
        diningLabel.addMouseListener(this);
        gymLabel.addMouseListener(this);
        libraryLabel.addMouseListener(this);
        parkingLabel.addMouseListener(this);
        classroomLabel.addMouseListener(this);
        restroomLabel.addMouseListener(this);

        clearButton.addMouseListener(this);
        showButton.addMouseListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        this.removeAll();
        if (isIsCampus()) {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap(41, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buildingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(diningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gymLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(libraryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(parkingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(showButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(25, 25, 25))
                    
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(buildingLabel)
                            .addGap(68, 68, 68)
                            .addComponent(diningLabel)
                            .addGap(68, 68, 68)
                            .addComponent(gymLabel)
                            .addGap(68, 68, 68)
                            .addComponent(libraryLabel)
                            .addGap(68, 68, 68)
                            .addComponent(parkingLabel)
                            .addGap(29, 29, 29)
                            .addComponent(clearButton)
                            .addGap(18, 18, 18)
                            .addComponent(showButton)
                            .addContainerGap(41, Short.MAX_VALUE))
                           
            );

        } else {

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap(41, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(classroomLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(restroomLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(showButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(25, 25, 25))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(classroomLabel)
                            .addGap(68, 68, 68)
                            .addComponent(restroomLabel)
                            .addGap(409, 409, 409)
                            .addComponent(clearButton)
                            .addGap(18, 18, 18)
                            .addComponent(showButton)
                            .addContainerGap(41, Short.MAX_VALUE))
            );

        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


    }


    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == showButton) {
            mainPanel.showPins();
        }

        if (e.getSource() == clearButton) {

            mainPanel.clearPins();
        }

        //category click 
        if (e.getSource() == buildingLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(0, 178, 219));

            mainPanel.showLocationPin("BUILDING");

            try {
                secRightSideBar.ShowLocationName("BUILDING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();

        }
        if (e.getSource() == diningLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(255, 102, 0));

            mainPanel.showLocationPin("DINING");

            try {
                secRightSideBar.ShowLocationName("DINING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();
        }
        if (e.getSource() == gymLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(255, 255, 2));

            mainPanel.showLocationPin("GYM");

            try {
                secRightSideBar.ShowLocationName("GYM");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();
        }

        if (e.getSource() == libraryLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(141, 179, 2));

            mainPanel.showLocationPin("LIBRARY");

            try {
                secRightSideBar.ShowLocationName("LIBRARY");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();
        }
        if (e.getSource() == parkingLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(170, 49, 103));

            mainPanel.showLocationPin("PARKING");

            try {
                secRightSideBar.ShowLocationName("PARKING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();
        }

        if (e.getSource() == classroomLabel) {
            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(231, 0, 102));
            mainPanel.showLocationPin("CLASSROOM");

            try {
                secRightSideBar.ShowLocationName("CLASSROOM");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            secRightSideBar.timer.start();
        } 
        else {
            if (e.getSource() == restroomLabel) {
                secRightSideBar.setVisible(true);
                secRightSideBar.setBackground(new java.awt.Color(38, 195, 194));
                mainPanel.showLocationPin("RESTROOM");

                try {
                    secRightSideBar.ShowLocationName("RESTROOM");
                } catch (SQLException ex) {
                    Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
                }
                secRightSideBar.timer.start();
            }


        }
            
        }
        


   

    @Override
    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
     // some animation here 

//        if(e.getSource() == classroomLabel){
//            //classroomMove = true;
//            
//        }
//        if(e.getSource() == restroomLabel){
//            restroomMove = true;
//        }
//        if(e.getSource() == buildingLabel){
//            System.out.print("entered");
//            
//            buildingLabel.setBounds(buildingLabel.getX() - 5, buildingLabel.getY() - 10,
//                buildingLabel.getHeight(), buildingLabel.getWidth());
//            buildingLabel.setToolTipText("Building");
//            
//            this.repaint();
//            //buildingMove = true;
//        }
//        if(e.getSource() == gymLabel){
//            gymMove = true;
//        }
//        if(e.getSource() == diningLabel){
//            diningMove = true;
//        }
//        if(e.getSource() == parkingLabel){
//            parkingMove = true;
//        }
//        if(e.getSource() == libraryLabel){
//            libraryMove = true;
//        }
//        
//    private JLabel classroomLabel;
//    private JLabel restroomLabel;
//    private JLabel buildingLabel;
//    private JLabel gymLabel;
//    private JLabel diningLabel;
//    private JLabel parkingLabel;
//    private JLabel libraryLabel;
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == floorList && !e.getValueIsAdjusting()) {
            // Get the current selection and place it in the
            // edit field
            String stringValue = (String) floorList.getSelectedValue();
            //System.out.println("list"+stringValue);

            for (Map m : allMapList) {
                if (stringValue.equals(m.name)) {

                    mainPanel.reloadMap(m);
//                    mainPanel.setMapIndex(m.mapID);
                    mainPanel.setShowAllPins(false);
                    mainPanel.setShowPins(false);
                    mainPanel.setDrawRoutes(false);
                    mainPanel.setShowRoute(false);
//                    try {
//                        mainPanel.init();
//                    } catch (SQLException ex) {
//                        Logger.getLogger(RightSideBar.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    mainPanel.repaint();
                }

            }

        }
    }

    /**
     * @return the isCampus
     */
    public boolean isIsCampus() {
        return isCampus;
    }

    /**
     * @param isCampus the isCampus to set
     */
    public void setIsCampus(boolean isCampus) {
        this.isCampus = isCampus;
    }

}
