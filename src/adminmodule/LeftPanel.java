/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *
 * @author Yihao
 */
public class LeftPanel extends javax.swing.JPanel {

    private AdminFrame mainFrame;
    public JList mapList;
    public DefaultListModel model;
    
    /**
     * Creates new form RightPanel
     */
    public LeftPanel(AdminFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        mapList = jList1;
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteMapButton = new javax.swing.JButton();
        addMapButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>();

        deleteMapButton.setText("Delete Map");
        deleteMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMapButtonActionPerformed(evt);
            }
        });

        addMapButton.setText("Add Map");
        addMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMapButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Maps");

        model = new DefaultListModel();
        JList list = new JList(model);
        jList1.setModel(model

            //    String[] strings = { "Map 1" };
            //    public int getSize() { return strings.length; }
            //    public String getElementAt(int i) { return strings[i]; }
        );
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(231, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181)
                .addComponent(addMapButton)
                .addGap(18, 18, 18)
                .addComponent(deleteMapButton)
                .addGap(40, 40, 40))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMapButtonActionPerformed
        // TODO add your handling code here:
        int result  =JOptionPane.showConfirmDialog(null,"Are you sure to delete this map?",null,JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION)
        {
            mainFrame.deleteMap(mainFrame.maps.get(mapList.getSelectedIndex()));
            
            model.remove(mapList.getSelectedIndex());
            
//        System.out.println("Yes!!!");
        }
    
    }//GEN-LAST:event_deleteMapButtonActionPerformed

    private void addMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMapButtonActionPerformed
        // TODO add your handling code here:zz
        
               new OpenFile(mainFrame);
                /*OpenFile f = new OpenFile();
               f.setTitle("Add Map");
               f.setSize (500, 500);
               f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
               f.setVisible(true);*/
            
       
    }//GEN-LAST:event_addMapButtonActionPerformed
public class mapList{
   
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMapButton;
    private javax.swing.JButton deleteMapButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
