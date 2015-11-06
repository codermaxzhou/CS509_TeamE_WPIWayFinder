package adminmodule;

import java.awt.Image;
import java.util.ArrayList;

public class Map {
    public ArrayList<Edge> edgeList;        // List of all edges on the map
    public ArrayList<Point> pointList;      // List of all points on the map
    public ArrayList<Location> locList;     // List of all locations on the map, we can use this to populate the sidebar later
    public Image image;                     // Map's image
    public String name;                     // Map name
    public String description;              // Map description
    public Boolean isInteriorMap;           // Flag indicating whether this is the campus map or a building map
    public int mapID;                       // Map's ID
    public String path;
    
    // For those doing the admin module, you may have to draw shapes "centered" at the
    // <X,Y> coordinates of points. There is an easy way to check if the <X,Y> coordinate
    // corresponding to a user's click event is within the area of a shap, this way we can
    // allow users to click on points to draw edges and maybe even edit existing points
    // by right clicking. Edges should also be drawn with enough thickness.

    public Map(int i, String fl, String computer_science, String usersxiemingchenDesktop5096th_floorpng, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
