package adminmodule;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Map {
    public ArrayList<Edge> edgeList;        // List of all edges on the map
    public ArrayList<Point> pointList;      // List of all points on the map
    public ArrayList<Location> locList;     // List of all locations on the map, we can use this to populate the sidebar later
    public String name;
    public String description;
    public Image image;                     // Map's image
    public int floor;
    public int locationID;
    public String deletedEdge = "";
    public String deletedLocation = "";
    public String deletedPoint = "";
    
   
    public Boolean isInteriorMap;           // Flag indicating whether this is the campus map or a building map
    public int mapID;                       // Map's ID
    public String path;
    
    public Map() {}
    
    public void addDeletedPointID(int id) {
        if(deletedPoint.equals("")) deletedPoint += "pointID = " + id;
        else deletedPoint += " OR pointID = " + id; 
    }
    
    public void addDeletedEdgeID(int id) {
        if(deletedEdge.equals("")) deletedEdge += "edgeID = " + id;
        else deletedEdge += " OR edgeID = " + id; 
    }
    
    public void addDeletedLocID(int id) {
        if(deletedLocation.equals("")) deletedLocation += "locationID = " + id;
        else deletedLocation += " OR locationID = " + id; 
    }

    public Map(String name,String desc,Boolean isInteriorMap,String path) throws MalformedURLException, IOException{
      //  this.mapID=mapID;
        this.name=name;
        this.description=desc;
        this.path=path;
        this.image=ImageIO.read(new URL(path));
        this.isInteriorMap=isInteriorMap;
        
    }
    // For those doing the admin module, you may have to draw shapes "centered" at the
    // <X,Y> coordinates of points. There is an easy way to check if the <X,Y> coordinate
    // corresponding to a user's click event is within the area of a shap, this way we can
    // allow users to click on points to draw edges and maybe even edit existing points
    // by right clicking. Edges should also be drawn with enough thickness.
}