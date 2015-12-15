/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ToolTipPanel extends JPanel {
    ToolTipPanel(Image img, String locName, String desc, int x, int y) {
        this.setLayout(new BorderLayout());
        this.add(new ToolTipImage(img,x,y), BorderLayout.CENTER);
        JTextArea jt = new JTextArea();
        jt.setOpaque(false);
        jt.setText(locName + "\n\n" + desc);
        this.add(jt, BorderLayout.SOUTH);
    }
}
