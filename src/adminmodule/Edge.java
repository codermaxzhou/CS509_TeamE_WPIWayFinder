package adminmodule;

public class Edge {
    public Point startPoint;   
    // The start point of the edge
    public Point endPoint;              // The end point of the edge
    public int startMapID, endMapID;    // The start and end map ID's corresponding to the two maps
                                        // Note that an edge may be between points on different maps
    public double weight;               // Weight of the edge
    public int edgeID;                  // Edge ID from the DB entry
    
    public Edge() {
    
    }
    
    public Edge(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.weight = Math.sqrt((Math.abs(startPoint.getX()-endPoint.getX()))^2 + (Math.abs(startPoint.getY()-endPoint.getY()))^2);
    }
}
    