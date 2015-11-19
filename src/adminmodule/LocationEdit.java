package adminmodule;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LocationEdit extends JFrame {

    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 40;
    Location l;
    
    private JTextField Name;
    private JTextField Description;
    private JButton okButton;
    private JButton cancelButton;
    private JComboBox box;

    public LocationEdit(Location l) {
        this.l = l;
        Information panel = new Information();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public class Information extends JPanel {

        
        private String options[] = {"DINING", "ATM", "ADMIN", "PARKING", "CLASSROOM", "RESTROOM"};

        /* private JRadioButton jrb1 = new JRadioButton("DINING");// 定义一个单选按钮
         private JRadioButton jrb2 = new JRadioButton("ATM");// 定义一个单选按钮
         private JRadioButton jrb3 = new JRadioButton("ADMIN");// 定义一个单选按钮
         private JRadioButton jrb4 = new JRadioButton("PARKING");*/
        public Information() {
            setLayout(new BorderLayout());

      // construct a panel
            JPanel panel = new JPanel();

            panel.setLayout(new GridLayout(10, 50));
            panel.add(new JLabel("Name:"));
            panel.add(Name = new JTextField(""));
            panel.add(new JLabel("Description:"));
            panel.add(Description = new JTextField(""));    
            panel.add(new JLabel("Category:"));
            JComboBox box = new JComboBox(options);
            if (l.locationID != -1)
                box.setSelectedItem(l.category.toString());
            panel.add(box);

            /*jrb1.setActionCommand("DINING");
             jrb2.setActionCommand("ATM");
             jrb3.setActionCommand("ADMIN");
             jrb4.setActionCommand("PARKING");

             panel.add(this.jrb1);// 加入组件
             panel.add(this.jrb2);
             panel.add(this.jrb3);
             panel.add(this.jrb4);
             ButtonGroup group = new ButtonGroup();
             group.add(this.jrb1);
             group.add(this.jrb2);
             group.add(this.jrb3);
             group.add(this.jrb4);*/
            add(panel, BorderLayout.CENTER);

            // create Ok and Cancel buttons
            okButton = new JButton("OK");
            
            okButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                   
                    System.out.println("Name = " + Name.getText() + ", Description = "
                  + Description.getText() + ", Category = "+ box.getSelectedItem());
                    dispose();
                    l.name = Name.getText();
                    l.description = Description.getText();
                    if (box.getSelectedItem() == "DINING") {
                        l.category = Location.Category.DINING;
                    } else if (box.getSelectedItem() == "ATM") {
                        l.category = Location.Category.ATM;
                    } else if (box.getSelectedItem() == "ADMIN") {
                        l.category = Location.Category.ADMIN;
                    }  else if (box.getSelectedItem() == "CLASSROOM") {
                        l.category = Location.Category.CLASSROOM;
                    } else if (box.getSelectedItem() == "RESTROOM") {
                        l.category = Location.Category.RESTROOM;
                    } else {
                        l.category = Location.Category.PARKING;
                    }
                    /*if(jrb1.isSelected()) l.category = Location.Category.DINING;
                     else if(jrb2.isSelected()) l.category = Location.Category.ATM;
                     else if(jrb3.isSelected()) l.category = Location.Category.ADMIN;
                     else l.category = Location.Category.PARKING;*/
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    dispose();
                }
            });

            // add these buttons to southern border
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

    
    
}
    public void setName(String s) {
            Name.setText(s);
            
        }
    
    public void setDescription(String s) {
        Description.setText(s);
    }
}
