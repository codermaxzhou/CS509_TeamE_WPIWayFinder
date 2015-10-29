package adminmodule;

public class Point {
    public enum Type { WAYPOINT, LOCATION };    // Enum for types of point, we can add more types later
    
    public Map map;                             // The map that the point belongs to
    public Type type;                           // Point type, right now there are two types
    public Location location;                   // If this point is a location, then this variable points to it
    public int X, Y;                            // X and Y coordinates of the point on the map
    public int pointID;                         // Database pointID primary key, may be useful later
    
    // Other notes: to get the location together with points from the database
    // we can use a JOIN query. Make sure to design the Point table in the database
    // with foreign keys to the map and location
    
    public Point() {
    
    }
    
    public Point(int x, int y, Type type) {
        this.X = x;
        this.Y = y;
        this.type = type;
    }
    
    public int getX() {
        return this.X;
    }
    
    public int getY() {
        return this.Y;
    }
}
