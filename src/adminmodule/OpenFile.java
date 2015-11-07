package adminmodule;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;   
import java.io.File;
import java.awt.event.*;   
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;   
import javax.swing.*;  

  public class OpenFile extends JPanel
{
    Map ms = new Map();
    
    private static final long serialVersionUID = 1L;
    BufferedImage bi = null;
    private JTextField Name;
    private JTextField Description;
    private JComboBox box = new JComboBox();
    private JCheckBox ckbox = new JCheckBox();
    JToolBar tb = new JToolBar();  
    AdminFrame admin;
    public OpenFile (AdminFrame admin)
    {
        final JFileChooser chooser = new JFileChooser ();
        FileNameExtensionFilter filter = new FileNameExtensionFilter ("Some Images", "jpg", "gif", "png", "jpeg");
        chooser.setFileFilter (filter);
        JButton openButton = new JButton ("Open");
        JButton saveButton = new JButton ("Save");
        JButton cancelButton = new JButton ("Cancel");
        
        this.admin = admin;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 50));
        panel.add(new JLabel("Name:"));
        panel.add(Name = new JTextField(""));
        panel.add(new JLabel("Description:"));
        panel.add(Description = new JTextField(""));
        panel.add(new JLabel("Type:"));
        panel.add(box);
        panel.add(new JLabel("InteriorMap"));
        panel.add(ckbox);
        tb.add(openButton);
        tb.add(saveButton);
        tb.add(cancelButton);
        JFrame jFrame = new JFrame ();
        jFrame.setLayout (new BorderLayout ());
        jFrame.setSize (500, 500);
        jFrame.add (this, BorderLayout.CENTER);
        tb.add(panel);
        jFrame.add (tb, BorderLayout.NORTH);
        jFrame.setLocationRelativeTo (null);
        jFrame.setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
        jFrame.setVisible (true);
             openButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                int returnVal = chooser.showOpenDialog (OpenFile.this);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        bi = ImageIO.read (new File (chooser.getSelectedFile ().getAbsolutePath ()));
                        
                        repaint ();
                    }
                    
                    catch (IOException e1)
                    {
                        e1.printStackTrace ();
                    }
                }
                
                
            }
        });
        saveButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                ms.edgeList = new ArrayList<Edge>();
                ms.pointList = new ArrayList<Point>();
                ms.locList = new ArrayList<Location>();
                ms.image = bi;
                ms.mapID = -1;
                ms.isInteriorMap = ckbox.isSelected();
                ms.name = Name.getText();
                if(ckbox.isSelected())
                    ms.isInteriorMap = true;
                else ms.isInteriorMap = false;
                ms.description = Description.getText();
                ms.path = chooser.getSelectedFile ().getAbsolutePath ();
                System.out.println(ms.path);
                System.out.println("Name = " + ms.name + ", Description = "
                  + ms.description + ms.path );
                jFrame.dispose();
                admin.PassValue(ms);

            }
        });
        cancelButton.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                
               jFrame.dispose();
            }
        });
        
    }
 
 public void paint ( Graphics g )
    {
        super.paint (g);
        g.drawImage (bi, 0, 0, this.getWidth (), this.getHeight (), null);
        g.dispose ();
    }

  }
