package adminmodule;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class LocationEdit extends JFrame {
    

    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 40;
    Location l;
    
    private JTextField Name;
    private JTextField Description;
    private JButton okButton;
    private JButton cancelButton;
    private JComboBox box;
    private JToolBar tb = new JToolBar(); 
    private String fileName;
    BufferedImage bi = null;

    public LocationEdit(Location l) {
        
        this.l = l;
        Information panel = new Information();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public class Information extends JPanel {

        
        private String options[] = {"BUILDING", "DINING", "GYM", "LIBRARY", "PARKING", "CLASSROOM", "RESTROOM"};

        /* private JRadioButton jrb1 = new JRadioButton("DINING");// 定义一个单选按钮
         private JRadioButton jrb2 = new JRadioButton("ATM");// 定义一个单选按钮
         private JRadioButton jrb3 = new JRadioButton("ADMIN");// 定义一个单选按钮
         private JRadioButton jrb4 = new JRadioButton("PARKING");*/
        public Information() {
            final JFileChooser chooser = new JFileChooser ();
            FileNameExtensionFilter filter = new FileNameExtensionFilter ("Some Images", "jpg", "gif", "png", "jpeg");
            chooser.setFileFilter (filter);
            setLayout(new BorderLayout());
            //buttons for select picture for location
            JButton openButton = new JButton ("Open");
            JButton saveButton = new JButton ("Save");
            JButton cancelButton = new JButton ("Cancel");

      // construct a panel
            JPanel panel = new JPanel();

            panel.setLayout(new GridLayout(10, 50));
            panel.add(new JLabel("Name:"));
            panel.add(Name = new JTextField(""));
            panel.add(new JLabel("Description:"));
            panel.add(Description = new JTextField(""));  
            //########mxie
            panel.add(new JLabel("Category:"));
            JComboBox box = new JComboBox(options);
            if (l.locationID != -1)
                box.setSelectedItem(l.category.toString());
            panel.add(box);
            panel.add(new JLabel("Choose Map:"));
            
            
            
            //add image to the location
            tb.add(openButton);
            tb.add(saveButton);
            tb.add(cancelButton); 
            panel.add(tb);
            
            
            openButton.addActionListener (new ActionListener ()
            {
                //private final Object chooser;
                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    int returnVal = chooser.showOpenDialog (Information.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        try {
                            bi = ImageIO.read (new File (chooser.getSelectedFile ().getAbsolutePath ()));
                        } catch (IOException ex) {
                            Logger.getLogger(LocationEdit.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String path = chooser.getSelectedFile ().getAbsolutePath ();
                        String [] fileNames = path.split("/");
                        fileName = fileNames[fileNames.length-1];
                        repaint ();
                    }


                }
            });


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
                    } else if (box.getSelectedItem() == "GYM") {
                        l.category = Location.Category.GYM;
                    } else if (box.getSelectedItem() == "LIBRARY") {
                        l.category = Location.Category.LIBRARY;
                    }  else if (box.getSelectedItem() == "CLASSROOM") {
                        l.category = Location.Category.CLASSROOM;
                    } else if (box.getSelectedItem() == "RESTROOM") {
                        l.category = Location.Category.RESTROOM;
                    } 
                     else if (box.getSelectedItem() == "BUILDING") {
                        l.category = Location.Category.BUILDING;
                    }
                     else if (box.getSelectedItem() == "PARKING") {
                        l.category = Location.Category.PARKING;
                    }
                    if(chooser.getSelectedFile() == null)
                        l.path = "null";
                    else
                        l.path = chooser.getSelectedFile ().getAbsolutePath ();
                    /*if(jrb1.isSelected()) l.category = Location.Category.DINING;
                     else if(jrb2.isSelected()) l.category = Location.Category.ATM;
                     else if(jrb3.isSelected()) l.category = Location.Category.ADMIN;
                     else l.category = Location.Category.PARKING;*/
                }
            });

            JButton cancelButton1 = new JButton("Cancel");
            cancelButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    dispose();
                }
            });

            // add these buttons to southern border
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton1);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    public void paint ( Graphics g )
    {
        super.paint (g);
        g.drawImage (bi, 200, 300, this.getWidth ()/5, this.getHeight ()/5, null);
        g.dispose ();
    }

    
    
}
    public void setName(String s) {
            Name.setText(s);
            
        }
    
    public void setDescription(String s) {
        Description.setText(s);
    }
}