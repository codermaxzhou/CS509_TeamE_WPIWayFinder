/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import adminmodule.Edge;
import adminmodule.Location;
import adminmodule.Map;
import adminmodule.MapInfo;
import adminmodule.Point;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author xiemingchen
 */
public class JDBC {
   String JDBC_DRIVER= "com.mysql.jdbc.Driver";  
   String DB_URL = "jdbc:mysql://localhost/map509";
   String USER = "root";
   String PASS = "";
   
   int maxPointID = 0;
   int maxLocID = 0;
   int maxMapID=0;
   Connection conn = null;
    private Statement stmt;
    private int mapID;
    private int description;
    private int name;
    private int isInteriorMap;
    private int path;
   
   public JDBC() {
       try {
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           Statement stmt = conn.createStatement();
           
           String query = "SELECT MAX(locationID) AS MAXLOCATION FROM Location;";
           ResultSet rs = stmt.executeQuery(query);
           if(rs.next()) maxLocID = rs.getInt("MAXLOCATION") + 1;
           
           query = "SELECT MAX(pointID) AS MAXPOINT FROM Point;";
           rs = stmt.executeQuery(query);
           if(rs.next()) maxPointID = rs.getInt("MAXPOINT") + 1;
           query = "SELECT MAX(mapID) AS MAXPOINT FROM Map;";
           rs = stmt.executeQuery(query);
           if(rs.next()) maxMapID = rs.getInt("MAXPOINT") + 1;
       } catch (ClassNotFoundException | SQLException ex) {
           System.out.println("Problem creating connection.");
       }
   }
   
   public MapInfo getMapInfo(int MapID) throws SQLException {
       String query = "SELECT locationID, pointID, category, name, description, mapID FROM Location WHERE MapID = " + MapID + ";";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       
       ArrayList<Location> L = new ArrayList<>();
       ArrayList<Point> P = new ArrayList<>();
       ArrayList<Edge> E = new ArrayList<>();
       
       HashMap<Integer, Location> locMap = new HashMap<>();
       HashMap<Integer, Point> ptMap = new HashMap<>();
       
       while(rs.next()) {
           Location temp = new Location();
           temp.description = rs.getString("Description");
           temp.locationID = rs.getInt("locationID");
           temp.name = rs.getString("name");
           temp.point = null;
           switch(rs.getString("category")) {
               case "DINING": temp.category = Location.Category.DINING;
                              break;
               case "ATM": temp.category = Location.Category.ATM;
                           break;
               default: temp.category = Location.Category.PARKING;
           }
           
           locMap.put(rs.getInt("pointID"), temp);
           L.add(temp);
       }
       
       query = "SELECT pointID, X, Y, locationID, mapID FROM Point WHERE MapID = " + MapID + ";";
       stmt = conn.createStatement();
       rs = stmt.executeQuery(query);

       while(rs.next()) {
           Point temp = new Point();
           temp.X = rs.getInt("X");
           temp.Y = rs.getInt("Y");
           Location partner = locMap.get(rs.getInt("pointID"));
           temp.location = partner;
           if(partner != null) {
               partner.point = temp;
               temp.type = Point.Type.LOCATION;
           } else
               temp.type = Point.Type.WAYPOINT;
           temp.map = null;
           temp.pointID = rs.getInt("pointID");
           ptMap.put(rs.getInt("pointID"), temp);
           P.add(temp);
       }
       
       query = "SELECT edgeID, startpointID, endpointID, weight, startmapID, endmapID FROM Edge WHERE startmapID = " + MapID + " AND endmapID = " + MapID + ";";
       stmt = conn.createStatement();
       rs = stmt.executeQuery(query);

       while(rs.next()) {
           Edge temp = new Edge();
           temp.weight = rs.getDouble("weight");
           temp.endMapID = 1;
           temp.startMapID = 1;
           temp.startPoint = ptMap.get(rs.getInt("startpointID"));
           temp.endPoint   = ptMap.get(rs.getInt("endpointID"));
           temp.edgeID = rs.getInt("edgeID");
           E.add(temp);
       }
       
       MapInfo info = new MapInfo();
       info.locations = L;
       info.edges = E;
       info.points = P;
       
       return info;
   }
   
   public boolean saveLocations(ArrayList<Location> A) throws SQLException {
       String query;
       
       for(int i = 0; i < A.size(); ++i) {
           Location l = A.get(i);
           if(l.locationID != -1) continue;
           query = "INSERT INTO Location (LocationID, PointID, category, name, description, mapID) ";
           query += "VALUES(" + maxLocID + ", " + maxPointID + ", \"" + l.category.toString() + "\", \"" + 
                    l.name + "\", \"" + l.description + "\", 1);";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
           
           l.locationID = maxLocID;
           l.point.pointID = maxPointID;
           
           query = "INSERT INTO Point (PointID, x, y, locationID, mapID) ";
           query += "VALUES(" + (maxPointID++) + ", " + l.point.X + ", " + 
                    l.point.Y + ", " + (maxLocID++) + ", 1);";
           
           stmt.executeUpdate(query);
       }
       
       return true;
   }
   
   public boolean savePoints(ArrayList<Point> A) throws SQLException {
       String query;
       
       for(int i = 0; i < A.size(); ++i) {
           Point p  = A.get(i);
           if(p.pointID != -1) continue;
           p.pointID = maxPointID;
           query = "INSERT INTO Point (PointID, x, y, locationID, mapID) ";
           query += "VALUES(" + (maxPointID++) + ", " + p.X + ", " + 
                    p.Y + ", " + -1 + ", 1);";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
       
       return true;
   }
   
   public boolean saveEdges(ArrayList<Edge> A) throws SQLException {
       String query;
       
       for(Edge e : A) {
           if(e.edgeID != -1) continue;
           query = "INSERT INTO Edge (startpointID, endpointID, weight, startmapID, endmapID) ";
           query += "VALUES(" + e.startPoint.pointID + ", " + e.endPoint.pointID + ", " + 
                    e.weight + ", " + 1 + ", 1);";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
       
       return true;
   }
   //########################
   public boolean updateLocation(ArrayList<Location> A) throws SQLException{
       String query = null;
       for(int i=0;i<A.size();++i){
           Location l=A.get(i);
           if (l.locationID != -1) {
            query="UPDATE Location SET ";
            query+="locationID="+l.locationID+",category=\""+l.category+"\",name=\""+l.name+"\",description=\""+l.description+"\",mapID="+"1 ";
            query+="where locationID=" + l.locationID + ";";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
           }
       }
       
       return true;
   }
   
   
   public boolean deleteALL(String tableName) throws SQLException{
       String query=null;
       query="DELETE FROM"+tableName+";";
       Statement stmt = conn.createStatement();
       stmt.executeUpdate(query);
       return true;
   }
   
   public boolean addMap(String name,String desc,String path,boolean isInteriorMap) throws SQLException{
       String query=null;
       int isInteriorMap_int=0;
       if (isInteriorMap)
           isInteriorMap_int=1;
           
       
       query = "INSERT INTO Map (mapID,name,description,path,isInteriorMap ) ";
       query += "VALUES(" + (maxMapID++) + ", " + name + ", " + desc+","+path+","+isInteriorMap_int+";)";
       Statement stmt = conn.createStatement();
       stmt.executeUpdate(query);
       return true;
   }
   
   public ArrayList<Map> showAllMap() throws SQLException, MalformedURLException, IOException{
       String query= "SELECT MapID,name,description,isInteriorMap From Map;";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       ArrayList<Map> m=new ArrayList<Map>();
       while(rs.next()){
           Map temp=new Map();
           temp.mapID=rs.getInt(mapID);
           temp.description=rs.getString(description);
           temp.name=rs.getString(name);
           int isInteriorMap_int=rs.getInt(isInteriorMap);
           if (isInteriorMap_int==0)
               temp.isInteriorMap=false;
           else
               temp.isInteriorMap=true;
           temp.path=rs.getString(path);
           temp.image=ImageIO.read(new URL(temp.path));
           m.add(temp);
      }
       return m;
       
   }
   public Map showMap(int searchID) throws SQLException, IOException{
       String query;
       query="SELECT name,description,isInteriorMap,path FROM Map";
       query+="WHERE mapID="+searchID+";";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       Map m=new Map();
       m.description=rs.getString(description);
       m.name=rs.getString(name);
       int isInteriorMap_int=rs.getInt(isInteriorMap);
       m.isInteriorMap=false;
       if (isInteriorMap_int==1)
           m.isInteriorMap=true;
       m.path=rs.getString(path);
       m.image=ImageIO.read(new URL(m.path));
       
       
       //m.image = new image(path is from rs.getString("Path"))
       return m;
       
       
   }
   //###################

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        
        JDBC db = new JDBC();
  //      Map m;
  //     m = new Map("fl","computer science",true,"/Users/xiemingchen/Desktop/509/6th floor.png");
 //      Map.addMap(m);
        
        
        Point p = new Point(8, 9, Point.Type.LOCATION);
        p.pointID = -1;
        ArrayList<Point> A = new ArrayList<>();
        A.add(p);
        db.savePoints(A);
        
        
   /*Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      
      //insert data
      

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql,sql1,sql_delete;
      sql1="INSERT INTO point (pointID, x,y,locationID,mapID)" + "VALUES (1, 2, 2,2,1)";
      sql_delete="delete from  point";
      stmt.executeUpdate(sql1);
      sql = "SELECT pointID FROM point";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("pointID");
         //String first = rs.getString("first");
         //String last = rs.getString("last");

         //Display values
         System.out.print("ID: " + id);
         //System.out.print(", Age: " + age);
         //System.out.print(", First: " + first);
         //System.out.println(", Last: " + last);
      }
      stmt.executeUpdate(sql_delete);
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");*/

    }
    
}
