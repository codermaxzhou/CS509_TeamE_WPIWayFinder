/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Yihao
 */
public class PopupMenu extends JPopupMenu {

    public PopupMenu(Location location) {
        JMenuItem item = new JMenuItem("Edit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item Edit");
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        LocationEdit frame = new LocationEdit(location);
                        frame.setName(location.name);
                        frame.setDescription(location.description);
                        //System.out.println(temp.name);
                        frame.setTitle("Location Edit");
                        frame.setSize(500, 500);
                        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        frame.setVisible(true);
                    }

                });
            }
        });
        this.add(item);

        item = new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item Delete not complete yet");
            }
        });
        this.add(item);
    }

}
