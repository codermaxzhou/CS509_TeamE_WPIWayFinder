/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;
import adminmodule.Location;
import adminmodule.MapInfo;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import jdbc.JDBC;
import javax.swing.WindowConstants;
import static javax.swing.text.StyleConstants.Bold;

/**
 *
 * @author GaoYifei
 */
public class SecRightSideBar extends javax.swing.JPanel implements MouseListener,ActionListener {
    
   private     ArrayList<Location> locationList = new ArrayList<Location>();
   private     ArrayList<JLabel> labelList = new ArrayList<JLabel>();
   private     ArrayList<JLabel> pinList = new ArrayList<JLabel>();
   private Boolean click = false;
   public MainPanel mainPanel ;
    /**
     * Creates new form SecRightSideBar
     */
    public SecRightSideBar() {

        initComponents();
        
    }
   
   
        
 
    public void ShowLocationName(String category) throws SQLException{
        this.removeAll();
//        ArrayList<Location> locationList = new ArrayList<Location>();
//        ArrayList<JLabel> labelList = new ArrayList<JLabel>();
       
        JDBC db = new JDBC();
        //System.out.println(Category);     
        MapInfo info = db.getMapInfo(1);
        locationList = info.locations;
        labelList.clear();
        
        
        
        for(Location l: locationList){
            
             switch(l.category)
                    {
                        case DINING:
                            if(category.equals("DINING")){
                            JLabel label = new JLabel();
                            label.setText(l.name);
                           
                            labelList.add(label);
                            
                              
                            
                            }
                            break;
                        case PARKING:
                           
                            if(category.equals("PARKING")){
                            JLabel label = new JLabel();
                            label.setText(l.name);
                            
                            labelList.add(label);
                            
                              
                            }
                       
                            
                           
                        default:
                            break;
//                
                     }
           
        }
        int y = 0;
        for(JLabel j: labelList){
           
            Font font = new Font("Roboto", Font.BOLD, 16);
            j.setFont(font);
            j.setBounds(60, 90 + y, 160, 30);
            j.setForeground(Color.white);
            j.addMouseListener(this);
            
            this.add(j);
            
            y = y + 30;
       
        }
       
//        JLabel label = new JLabel();
//        Font font = new Font("Roboto", Font.BOLD, 16);
//        label.setFont(font);
//        label.setBounds(60, 60, 160, 30);
//        label.setForeground(Color.white);
        //label.setFont(Font.BOLD);
        //label.setText("Just Testing");
        
        //this.add(label);
        BorderLayout layout;
        layout = new BorderLayout();
        this.setLayout(layout);
        
        
        this.validate();
        this.repaint();
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        this.setBackground(Color.red);
//    }
    @Override
    public void mouseClicked(MouseEvent e) {
        click = true;
        int n = 0;
        int m = 0;
        JLabel pinLabel = new JLabel();
        //pinList.removeAll(pinList);
        mainPanel.repaint();
        for (m= 0; m < labelList.size(); m++) {

            labelList.get(m).setForeground(Color.white);

            this.repaint();
        }

        for (n = 0; n < labelList.size(); n++) {
            if (e.getSource().equals(labelList.get(n))) {
                
                labelList.get(n).setForeground(Color.black);
                

                pinLabel.setText(labelList.get(n).getText());
                
                pinLabel.setBounds(locationList.get(n).point.X, locationList.get(n).point.Y, 100, 30);
                pinList.add(pinLabel);
                
                //System.out.println(locationList.get(n).point.X +","+ locationList.get(n).point.Y);
                mainPanel.add(pinLabel);
                //mainPanel.validate();
                mainPanel.repaint();  
                
                
                Image pinImage = new ImageIcon(this.getClass().getResource("/icons/marker.png")).getImage();
               
               
                
                mainPanel.getGraphics().drawImage(pinImage,locationList.get(n).point.X - 5 , locationList.get(n).point.Y -5,20,20, null);
                  
                
                this.repaint();
                
                
                
            }

           
        }
        
        
           
              
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        int n = 0;


        for (n = 0; n < labelList.size(); n++) {
            if (e.getSource().equals(labelList.get(n))) {
                
                labelList.get(n).setForeground(Color.black);

                this.repaint();
            }
        }      
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int m = 0;
        
        
        for (m = 0; m < labelList.size(); m++) {
            if (e.getSource().equals(labelList.get(m))) {
                
                if(click == false){
           
                labelList.get(m).setForeground(Color.white);

                this.repaint();
                }
              
            }
  
        }
        click = false;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}