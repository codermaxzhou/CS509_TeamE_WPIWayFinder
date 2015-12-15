package mapview;

import adminmodule.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
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
    private JLabel clearLabel;
    private JLabel showLabel;
    private JLabel favoriteLabel;
    private boolean threadrunning = false;
 //   private JLabel cameraLabel;

//    private JButton clearButton;
//    private JButton showButton;

    private JList floorList;
    private JScrollPane scrollPane;

    private ImageIcon buildingIcon;
    private ImageIcon gymIcon;
    private ImageIcon diningIcon;
    private ImageIcon parkingIcon;
    private ImageIcon libraryIcon;
    private ImageIcon classroomIcon;
    private ImageIcon restroomIcon;
    private ImageIcon clearIcon;
    private ImageIcon showIcon;
    private ImageIcon favoriteIcon;
 //   private ImageIcon cameraIcon;

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

       // this.setBackground(Color.GRAY);

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
        this.setBackground(new Color(159,193,255));
        buildingLabel = new JLabel();
        buildingIcon = new ImageIcon(getClass().getResource("/icons/building.png"));
        buildingLabel.setIcon(buildingIcon);
        buildingLabel.setBounds(35, 50, 60, 60);

        diningLabel = new JLabel();
        diningIcon = new ImageIcon(getClass().getResource("/icons/dining.png"));
        diningLabel.setIcon(diningIcon);
        diningLabel.setBounds(35, 180, 60, 60);
        
        gymLabel = new JLabel();
        gymIcon = new ImageIcon(getClass().getResource("/icons/gym.png"));
        gymLabel.setIcon(gymIcon);
        gymLabel.setBounds(35, 310, 60, 60);
        
        libraryLabel = new JLabel();
        libraryIcon = new ImageIcon(getClass().getResource("/icons/reading.png"));
        libraryLabel.setIcon(libraryIcon);
        libraryLabel.setBounds(35, 440, 60, 60);

        parkingLabel = new JLabel();
        parkingIcon = new ImageIcon(getClass().getResource("/icons/parking.png"));
        parkingLabel.setIcon(parkingIcon);
        parkingLabel.setBounds(35, 570, 60, 60);

        classroomLabel = new JLabel();
        classroomIcon = new ImageIcon(getClass().getResource("/icons/classrooml.png"));
        classroomLabel.setIcon(classroomIcon);
        classroomLabel.setBounds(35, 50, 60, 60);

        restroomLabel = new JLabel();
        restroomIcon = new ImageIcon(getClass().getResource("/icons/restrooml.png"));
        restroomLabel.setIcon(restroomIcon);
        restroomLabel.setBounds(35, 180, 60, 60);
 
        
        clearLabel = new JLabel();
        clearIcon = new ImageIcon(getClass().getResource("/icons/cancel.png"));
        clearLabel.setIcon(clearIcon);
        clearLabel.setBounds(85, 680, 35, 35);
        
        showLabel = new JLabel();
        showIcon = new ImageIcon(getClass().getResource("/icons/show.png"));
        showLabel.setIcon(showIcon);
        showLabel.setBounds(50, 680, 35, 35);
        
        favoriteLabel = new JLabel();
        favoriteIcon = new ImageIcon(getClass().getResource("/icons/star.png"));
        favoriteLabel.setIcon(favoriteIcon);
        favoriteLabel.setBounds(15, 680, 35, 35);
        
        //camera
//        cameraLabel = new JLabel();
//        cameraIcon = new ImageIcon(getClass().getResource("/icons/show.png"));
//        cameraLabel.setIcon(cameraIcon);
//        cameraLabel.setBounds(30, 750, 35, 35);
//        clearButton = new JButton();
//        clearButton.setText("Clear Pins");
        //clearButton.setBounds(50, 700, 15, 60);
//        
//        showButton = new JButton();
//        showButton.setText("Show Pins");
        //showButton.setBounds(50, 750, 15, 60);
        
       
        

//        floorList.setBorder(javax.swing.BorderFactory.createTitledBorder("Floor "));
//        floorList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
//        floorList.setToolTipText("Choose Floor in Project Center");
//        scrollPane = new JScrollPane();
//        scrollPane.setViewportView(floorList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(null);
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

        clearLabel.addMouseListener(this);
        showLabel.addMouseListener(this);
        favoriteLabel.addMouseListener(this);
        //       camera
  //      cameraLabel.addMouseListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        this.removeAll();
        if (isIsCampus()) {
            this.add(buildingLabel);
            this.add(diningLabel);
            this.add(gymLabel);
            this.add(libraryLabel);
            this.add(parkingLabel);
            this.add(showLabel);
            this.add(clearLabel);
            this.add(favoriteLabel);
           
                
            
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(null);
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
                                    .addComponent(showLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(25, 25, 25))));
                                    //camera
                           //         .addComponent(cameraLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

           
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
                            .addComponent(clearLabel)
                            .addGap(18, 18, 18)
                            .addComponent(showLabel)
                            .addContainerGap(41, Short.MAX_VALUE))
            );

        } else {
            
            this.add(classroomLabel);
            this.add(restroomLabel);
            this.add(showLabel);
            this.add(clearLabel);
            this.add(favoriteLabel);
            

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(null);
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
                                    .addComponent(showLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(clearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(clearLabel)
                            .addGap(18, 18, 18)
                            .addComponent(showLabel)
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
        if (e.getSource() == showLabel) {
            mainPanel.showPins();
        }

        if (e.getSource() == clearLabel) {

            mainPanel.clearPins();
        }
        
        if (e.getSource() == favoriteLabel) {

            mainPanel.showFavPins();
        }
         if (e.getSource() == showLabel) {
            mainPanel.showPins();
        }
        

        if (e.getSource() == clearLabel) {

            mainPanel.clearPins();
        }
   
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == clearLabel){
            clearLabel.setBounds(clearLabel.getX() + 3, clearLabel.getY() + 3, clearLabel.getWidth(), clearLabel.getHeight());
        }
        if(e.getSource() == showLabel){
            showLabel.setBounds(showLabel.getX() + 3, showLabel.getY() + 3, showLabel.getWidth(), showLabel.getHeight());
        }
        if(e.getSource() == favoriteLabel){
            favoriteLabel.setBounds(favoriteLabel.getX() + 3, favoriteLabel.getY() + 3, favoriteLabel.getWidth(), favoriteLabel.getHeight());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == clearLabel){
            clearLabel.setBounds(clearLabel.getX() - 3, clearLabel.getY() - 3, clearLabel.getWidth(), clearLabel.getHeight());
        }
        if(e.getSource() == showLabel){
            showLabel.setBounds(showLabel.getX() - 3, showLabel.getY() - 3, showLabel.getWidth(), showLabel.getHeight());
        }
        if(e.getSource() == favoriteLabel){
            favoriteLabel.setBounds(favoriteLabel.getX() - 3, favoriteLabel.getY() - 3, favoriteLabel.getWidth(), favoriteLabel.getHeight());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == buildingLabel){
            buildingLabel.setBounds(buildingLabel.getX() + 5, buildingLabel.getY() + 5, buildingLabel.getWidth(), buildingLabel.getHeight());
        }
        
        if(e.getSource() == diningLabel){
            diningLabel.setBounds(diningLabel.getX() + 5, diningLabel.getY() + 5, diningLabel.getWidth(), diningLabel.getHeight());
        }
        if(e.getSource() == gymLabel){
            gymLabel.setBounds(gymLabel.getX() + 5, gymLabel.getY() + 5, gymLabel.getWidth(), gymLabel.getHeight());
        }
        if(e.getSource() == libraryLabel){
            libraryLabel.setBounds(libraryLabel.getX() + 5, libraryLabel.getY() + 5, libraryLabel.getWidth(), libraryLabel.getHeight());
        }
        if(e.getSource() == parkingLabel){
            parkingLabel.setBounds(parkingLabel.getX() + 5, parkingLabel.getY() + 5, parkingLabel.getWidth(), parkingLabel.getHeight());
        }
        if(e.getSource() == classroomLabel){
            classroomLabel.setBounds(classroomLabel.getX() + 5, classroomLabel.getY() + 5, classroomLabel.getWidth(), classroomLabel.getHeight());
        }
        if(e.getSource() == restroomLabel){
            restroomLabel.setBounds(restroomLabel.getX() + 5, restroomLabel.getY() + 5, restroomLabel.getWidth(), restroomLabel.getHeight());
        }

        
        // some animation here 

        if(e.getSource() == classroomLabel){
            //classroomMove = true;
            
            classroomLabel.setToolTipText("Classroom");
            
        }
        if(e.getSource() == restroomLabel){
            restroomLabel.setToolTipText("Restroom");
        }
        if(e.getSource() == buildingLabel){
            buildingLabel.setToolTipText("Building");
           
        }
        if(e.getSource() == gymLabel){
            gymLabel.setToolTipText("Gym");
        }
        if(e.getSource() == diningLabel){
            diningLabel.setToolTipText("Dining");
        }
        if(e.getSource() == parkingLabel){
            parkingLabel.setToolTipText("Parking");
        }
        if(e.getSource() == libraryLabel){
            libraryLabel.setToolTipText("Library");
        }
        if(e.getSource() == clearLabel){
            clearLabel.setToolTipText("Clear Pins");
        }
        if(e.getSource() == showLabel){
            showLabel.setToolTipText("Show All Pis");
        }
        if(e.getSource() == favoriteLabel){
            favoriteLabel.setToolTipText("Show Favorite Locations");
        }

        
       


        //category click 
        if (e.getSource() == buildingLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(0, 178, 219));
            mainPanel.clearPins();
            mainPanel.showLocationPin("BUILDING");

            try {
                secRightSideBar.ShowLocationName("BUILDING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
           // secRightSideBar.timer.start();

        }
        if (e.getSource() == diningLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(255, 102, 0));
            mainPanel.clearPins();
            mainPanel.showLocationPin("DINING");

            try {
                secRightSideBar.ShowLocationName("DINING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            //secRightSideBar.timer.start();
        }
        if (e.getSource() == gymLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(255, 236, 34));
            mainPanel.clearPins();
            mainPanel.showLocationPin("GYM");

            try {
                secRightSideBar.ShowLocationName("GYM");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            //secRightSideBar.timer.start();
        }

        if (e.getSource() == libraryLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(141, 179, 2));
            mainPanel.clearPins();
            mainPanel.showLocationPin("LIBRARY");

            try {
                secRightSideBar.ShowLocationName("LIBRARY");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            //secRightSideBar.timer.start();
        }
        if (e.getSource() == parkingLabel) {

            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(170, 49, 103));
            mainPanel.clearPins();
            mainPanel.showLocationPin("PARKING");

            try {
                secRightSideBar.ShowLocationName("PARKING");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            //secRightSideBar.timer.start();
        }

        if (e.getSource() == classroomLabel) {
            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(231, 0, 102));
            mainPanel.clearPins();
            mainPanel.showLocationPin("CLASSROOM");

            try {
                secRightSideBar.ShowLocationName("CLASSROOM");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
           // secRightSideBar.timer.start();
        }

        if (e.getSource() == restroomLabel) {
            secRightSideBar.setVisible(true);
            secRightSideBar.setBackground(new java.awt.Color(38, 195, 194));
            mainPanel.clearPins();
            mainPanel.showLocationPin("RESTROOM");

            try {
                secRightSideBar.ShowLocationName("RESTROOM");
            } catch (SQLException ex) {
                Logger.getLogger(RightBar.class.getName()).log(Level.SEVERE, null, ex);
            }
           // secRightSideBar.timer.start();
        }
        
        if((e.getSource() == buildingLabel || e.getSource() == gymLabel || e.getSource() == diningLabel || e.getSource() == parkingLabel || e.getSource() == libraryLabel || e.getSource() == restroomLabel || e.getSource() == classroomLabel) && !threadrunning) {
            Thread panelHide = new Thread(new Runnable() {
                @Override
                public void run() {
                    threadrunning = true;
                    while(true) {
                        Point p = mainPanel.getMousePosition();
                        if(p != null && p.getX() < 850) {
                            break;
                        }
                    }
                    mapView.getSecRightSideBar().setVisible(false);
                    threadrunning = false;
                }
            });

            panelHide.start();  
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == buildingLabel){
            buildingLabel.setBounds(buildingLabel.getX() - 5, buildingLabel.getY() - 5, buildingLabel.getWidth(), buildingLabel.getHeight());
        }
        if(e.getSource() == diningLabel){
            diningLabel.setBounds(diningLabel.getX() - 5, diningLabel.getY() - 5, diningLabel.getWidth(), diningLabel.getHeight());
        }
        if(e.getSource() == gymLabel){
            gymLabel.setBounds(gymLabel.getX() - 5, gymLabel.getY() - 5, gymLabel.getWidth(), gymLabel.getHeight());
        }
        if(e.getSource() == libraryLabel){
            libraryLabel.setBounds(libraryLabel.getX() - 5, libraryLabel.getY() - 5, libraryLabel.getWidth(), libraryLabel.getHeight());
        }
        if(e.getSource() == parkingLabel){
            parkingLabel.setBounds(parkingLabel.getX() - 5, parkingLabel.getY() - 5, parkingLabel.getWidth(), parkingLabel.getHeight());
        }
        if(e.getSource() == classroomLabel){
            classroomLabel.setBounds(classroomLabel.getX() - 5, classroomLabel.getY() - 5, classroomLabel.getWidth(), classroomLabel.getHeight());
        }
        if(e.getSource() == restroomLabel){
            restroomLabel.setBounds(restroomLabel.getX() - 5, restroomLabel.getY() - 5, restroomLabel.getWidth(), restroomLabel.getHeight());
        }

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
