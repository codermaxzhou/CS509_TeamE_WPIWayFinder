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
import adminmodule.GlobalMapInfo;
import adminmodule.Location;
import adminmodule.Map;
import adminmodule.MapInfo;
import adminmodule.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
   
   private int maxPointID = 0;
   private int maxLocID = 0;
   private int maxMapID = 0;
   private int maxEdgeID = 0;
   
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
           
           query = "SELECT MAX(edgeID) AS MAXEDGE FROM Edge;";
           rs = stmt.executeQuery(query);
           if(rs.next()) maxEdgeID = rs.getInt("MAXEDGE") + 1;
           
           query = "SELECT MAX(mapID) AS MAXPOINT FROM Map;";
           rs = stmt.executeQuery(query);
           if(rs.next()) maxMapID = rs.getInt("MAXPOINT") + 1;
       } catch (ClassNotFoundException | SQLException ex) {
           System.out.println("Problem creating connection.");
       }
   }
   
   public MapInfo getMapInfo(int MapID, Map map) throws SQLException {
       String query = "SELECT locationID, pointID, category, name, description, path, mapID FROM Location WHERE MapID = " + MapID + ";";
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
           temp.path = rs.getString("path");
           switch(rs.getString("category")) {
               case "DINING": temp.category = Location.Category.DINING;
                              break;
               case "ATM": temp.category = Location.Category.ATM;
                           break;
               case "CLASSROOM": temp.category = Location.Category.CLASSROOM;
                                break;
               case "RESTROOM": temp.category = Location.Category.RESTROOM;
                                break;
               default: temp.category = Location.Category.PARKING;
           }
           
           locMap.put(rs.getInt("pointID"), temp); //Why is this "pointID", not "locationID"?
           L.add(temp);
       }
       
       query = "SELECT pointID, X, Y, locationID, mapID, type FROM Point WHERE MapID = " + MapID + ";";
       stmt = conn.createStatement();   
       rs = stmt.executeQuery(query);

       while(rs.next()) {
           Point temp = new Point();
           temp.X = rs.getInt("X");
           temp.Y = rs.getInt("Y");
           Location partner = locMap.get(rs.getInt("pointID"));
           temp.location = partner;
//           if(partner != null) {
//               partner.point = temp;
//               temp.type = Point.Type.LOCATION;
//           } else
//               temp.type = Point.Type.WAYPOINT;
           
           switch(rs.getString("type")) {
               case "LOCATION": temp.type = Point.Type.LOCATION;
                                partner.point = temp;
                              break;
               case "CONNECTION": temp.type = Point.Type.CONNECTION;
                           break;
               case "WAYPOINT": temp.type = Point.Type.WAYPOINT;
                           break;
           }
           
           temp.map = null;
           temp.pointID = rs.getInt("pointID");
           ptMap.put(rs.getInt("pointID"), temp);
           P.add(temp);
           temp.map = map;
       }
       
//       modified the sql by Yifei (select edge by start point mapID instead of start point mapID and end point mapID )
       query = "SELECT edgeID, startpointID, endpointID, weight, startmapID, endmapID FROM Edge WHERE startmapID = " + MapID + " AND endmapID = " + MapID + ";";
       stmt = conn.createStatement();
       rs = stmt.executeQuery(query);

       while(rs.next()) {
           Edge temp = new Edge();
           temp.weight = rs.getDouble("weight");
           // modified by Yifei , get endMapID from database instead of map.mapID
           temp.endMapID = rs.getInt("endmapID");
           temp.startMapID = map.mapID;
           temp.startPoint = ptMap.get(rs.getInt("startpointID"));
           //TODO: cannot access for the connection point  24th point since the endPoint belong to the other map which not in the ptMap
           temp.endPoint   = ptMap.get(rs.getInt("endpointID")); 
           temp.edgeID = rs.getInt("edgeID");
           E.add(temp);
       }
       
       LocationThread obj = new LocationThread(L);
       obj.start();
       
       MapInfo info = new MapInfo();
       info.locations = L;
       info.edges = E;
       info.points = P;
       
       return info;
   }
   
   public boolean saveLocations(ArrayList<Location> A, int mid, String condition) throws SQLException {
       String query;
       
       for(int i = 0; i < A.size(); ++i) {
           Location l = A.get(i);
           if(l.locationID != -1) continue;
           query = "INSERT INTO Location (LocationID, PointID, category, name, description, mapID, path) ";
           query += "VALUES(" + getMaxLocID() + ", " + getMaxPointID() + ", \"" + l.category.toString() + "\", \"" + 
                    l.name + "\", \"" + l.description + "\", " + l.point.map.mapID +  ", \"" + l.path + "\");";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
           
           l.locationID = getMaxLocID();
           l.point.pointID = getMaxPointID();
           
           query = "INSERT INTO Point (PointID, x, y, locationID, mapID, type) ";
           query += "VALUES(" + (maxPointID++) + ", " + l.point.X + ", " + 
                    l.point.Y + ", " + (maxLocID++) + ", " + l.point.map.mapID + ", \"" + l.point.type.toString() + "\");";
           
           stmt.executeUpdate(query);
       }
       
       if(!condition.equals("")) {
           query = "DELETE FROM Location WHERE " + condition + ";";
           stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
       
       return true;
   }
   
   public boolean savePoints(ArrayList<Point> A, int mid, String condition) throws SQLException {
       
       String query;
       
       for(int i = 0; i < A.size(); ++i) {
           Point p  = A.get(i);
           if(p.pointID != -1) continue;
           p.pointID = getMaxPointID();
           query = "INSERT INTO Point (PointID, x, y, locationID, mapID, type) ";
           query += "VALUES(" + (maxPointID++) + ", " + p.X + ", " + 
                    p.Y + ", " + -1 + ", " + p.map.mapID + ", \"" + p.type.toString() + "\");";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
        
       if(!condition.equals("")) {
           query = "DELETE FROM Point WHERE " + condition + ";";
           stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
       
       return true;
   }
   
   public boolean saveEdges(ArrayList<Edge> A, int mid, String condition) throws SQLException {
       String query;
       
       for(Edge e : A) {
           if(e.edgeID != -1) continue;
           e.edgeID = getMaxEdgeID();
           query = "INSERT INTO Edge (edgeID, startpointID, endpointID, weight, startmapID, endmapID) ";
           query += "VALUES(" + (maxEdgeID++) + ", " + e.startPoint.pointID + ", " + e.endPoint.pointID + ", " + 
                    e.weight + ", " + e.startMapID + ", " + e.endMapID + ");";
           Statement stmt = conn.createStatement();
           stmt.executeUpdate(query);
       }
       
       if(!condition.equals("")) {
           query = "DELETE FROM Edge WHERE " + condition + ";";
           stmt = conn.createStatement();
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
            query+="locationID="+l.locationID+",category=\""+l.category+"\",name=\""+l.name+"\",description=\""+l.description+"\",mapID="+ l.point.map.mapID +  ", path=\"" + l.path + "\"";
            query+=" where locationID=" + l.locationID + ";";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
           }
       }
       
       return true;
   }
   
   public boolean updatePoint(ArrayList<Point> A) throws SQLException{
       String query = null;
       for(int i=0;i<A.size();++i){
           Point p=A.get(i);
           if (p.pointID != -1) {
            query="UPDATE Point SET ";
            query+="pointID="+p.pointID+",X="+p.X+",Y="+p.Y+",mapID="+ p.map.mapID+",type=\""+p.type.toString()+"\"";
            query+=" where pointID=" + p.pointID + ";";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
           }
       }
       
       return true;
   }
   
   
   public boolean deleteALL(String tableName) throws SQLException{
       String query=null;
       query="TRUNCATE TABLE "+tableName+";";
       Statement stmt = conn.createStatement();
       stmt.executeUpdate(query);
       return true;
   }
   
   
   public boolean addMap(ArrayList<Map> maps) throws SQLException, FileNotFoundException, IOException{       
       String query;
       for(Map m : maps) {
           if(m.mapID == -1) {
                query = "";
                int isBldgMap = m.isInteriorMap ? 1 : 0;

                query = "INSERT INTO Map (mapID, name, description, path, floor, isInteriorMap, locationID) ";
                query += "VALUES('" + maxMapID + "', '" + m.name + "', '" + m.description + "', '" + m.path + "', " + m.floor+","+isBldgMap + "," + m.locationID + ");";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(query);

                m.mapID = getMaxMapID();

                /* QUESTION:should these be removed? */
                PreparedStatement ps = null;
                conn.setAutoCommit(false);
                File file = new File(m.path);
                FileInputStream fis = new FileInputStream(file);
                ps = conn.prepareStatement("UPDATE Map SET image = ? WHERE mapID = " + getMaxMapID());
                ps.setBinaryStream(1, fis, (int) file.length());
                ps.executeUpdate();
                conn.commit();
                
                for(Edge e : m.edgeList) {
                    e.startMapID = getMaxMapID();
                    e.endMapID = getMaxMapID();
                }

                setMaxMapID(getMaxMapID() + 1);
                
                ps.close();
                fis.close();
                conn.setAutoCommit(true);
           }
           
           
               this.saveLocations(m.locList, m.mapID, m.deletedLocation);

               this.savePoints(m.pointList, m.mapID, m.deletedPoint);

               this.saveEdges(m.edgeList, m.mapID, m.deletedEdge);
           
           if(m.locList != null) this.updateLocation(m.locList);
           if(m.locList != null) this.updatePoint(m.pointList);
           
           m.deletedEdge = "";
           m.deletedLocation = "";
           m.deletedPoint = "";
       }
           
       query = "SELECT mapID FROM map;";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       while(rs.next()){
           int temp = rs.getInt("mapID");
           int flag = 0;
           for (Map m : maps) {
               if(m.mapID == temp) {
                   flag = 1;
                   break;
               }
           }
           if(flag == 0) {
               
               query = "DELETE FROM map WHERE mapID="+temp+";";
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
               
               query = "DELETE FROM Point WHERE mapID="+temp+";";
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
               
               query = "DELETE FROM location WHERE mapID="+temp+";";
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
               
               query = "DELETE FROM edge WHERE startmapID = " + temp + " OR endmapID = " + temp +";";
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
           }
       }
       
       return true;
   }
   
   public GlobalMapInfo getGlobalMapInfo() throws SQLException, IOException {
       ArrayList<Map> maps = showAllMap();
       ArrayList<Edge> interMapEdges = new ArrayList<>();
       HashMap<Integer, Point> dictionary = new HashMap<>();
       
       for(Map m : maps) {
           for(Point p : m.pointList) {
               dictionary.put(p.pointID, p);
           }
       }
       
       String query= "SELECT startpointID, endpointID, weight, startmapID, endmapID From edge WHERE startmapID != endmapID;";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       
       while(rs.next()){
           Edge temp = new Edge();
           temp.endMapID = rs.getInt("endmapID");
           temp.startMapID = rs.getInt("startmapID");
           temp.startPoint = dictionary.get(rs.getInt("startpointID"));
           temp.endPoint = dictionary.get(rs.getInt("endpointID"));
           temp.weight = rs.getDouble("weight");
           
           interMapEdges.add(temp);
       }
       
       GlobalMapInfo gm = new GlobalMapInfo();
       gm.interMapEdges = interMapEdges;
       gm.maps = maps;
       
       return gm;
   }
   
   public ArrayList<Map> showAllMap() throws SQLException, MalformedURLException, IOException{
       String query= "SELECT MapID, name, description, path, floor, isInteriorMap, image, locationID From Map;";
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(query);
       
       ArrayList<Map> mapList = new ArrayList<>();
       
       while(rs.next()){
           Map temp = new Map();
           temp.mapID = rs.getInt("mapID");
           temp.description = rs.getString("description");
           temp.name = rs.getString("name");
           int isBldgMap = rs.getInt("isInteriorMap");
           temp.isInteriorMap = (isBldgMap == 1);
           temp.path = rs.getString("path");
           temp.floor = rs.getInt("floor");
           temp.locationID = rs.getInt("locationID");
           
           //TODO remove
           //InputStream binaryStream = rs.getBinaryStream("image");
           temp.image = ImageIO.read(new FileInputStream(temp.path));
           
           MapInfo info = this.getMapInfo(temp.mapID, temp);
           temp.edgeList = info.edges;
           temp.pointList = info.points;
           temp.locList = info.locations;
           mapList.add(temp);
      }
       return mapList;
       
   }
   public Map showMap(int searchID) throws SQLException, IOException{
       String query;
       query="SELECT name,description,isInteriorMap,floor,path, locationID FROM Map";
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
       m.locationID = rs.getInt("locationID");
       
       
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
        
        
        /*Point p = new Point(8, 9, Point.Type.LOCATION);
        p.pointID = -1;
        ArrayList<Point> A = new ArrayList<>();
        A.add(p);
        db.savePoints(A);*/
        
        ArrayList<Map> list = new ArrayList<>();
        Map m = new Map();
        m.name = "Project Center 1";
        m.description = "Building for projects";
        m.path = "/Users/Yihao/Desktop/map.png";
        m.isInteriorMap = true;
        list.add(m);
        db.addMap(list);
        
    }
    
     /**
     * @return the maxPointID
     */
    public int getMaxEdgeID() {
        return maxEdgeID;
    }

    /**
     * @return the maxPointID
     */
    public int getMaxPointID() {
        return maxPointID;
    }

    /**
     * @param maxPointID the maxPointID to set
     */
    public void setMaxPointID(int maxPointID) {
        this.maxPointID = maxPointID;
    }

    /**
     * @return the maxLocID
     */
    public int getMaxLocID() {
        return maxLocID;
    }

    /**
     * @param maxLocID the maxLocID to set
     */
    public void setMaxLocID(int maxLocID) {
        this.maxLocID = maxLocID;
    }

    /**
     * @return the maxMapID
     */
    public int getMaxMapID() {
        return maxMapID;
    }

    /**
     * @param maxMapID the maxMapID to set
     */
    public void setMaxMapID(int maxMapID) {
        this.maxMapID = maxMapID;
    }
    
}
