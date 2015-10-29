package adminmodule;

public class Location {
    public enum Category { DINING, ATM, ADMIN, PARKING };   // we will expand this list later
    
    public Point point;                                     // The location's point on the map
    public Category category;                               // Location type
    public String name;                                     // Name of the location
    public String description;                              // Description of the location
    public int locationID;                                  // The primary key of this location from the DB
    
    public Location(Point point) {
        this.point = point;
    }
}
