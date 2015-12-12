/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapview;

import adminmodule.Edge;
import adminmodule.GlobalMapInfo;
import adminmodule.Location;
import adminmodule.Map;
import adminmodule.Point;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.JDBC;

/**
 *
 * @author GaoYifei
 */
public class MapModel {
    private ArrayList<Point> allPointList = new ArrayList<>();
    private ArrayList<Edge> allEdgeList = new ArrayList<>();
    private ArrayList<Location> allLocationList = new ArrayList<>(); 
    private ArrayList<Map> mapList = new ArrayList<>();
    private ArrayList<Location> favLocationList = new ArrayList<>();
    
     private JDBC dataBase = JDBC.getInstance();
     private int maxMapID = 0;
     private int maxLocID = 0;
     private int maxPointID = 0;
     
     
    public MapModel() {
        try {
            init();
        } catch (SQLException ex) {
            Logger.getLogger(MapModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init() throws SQLException, IOException{
       maxMapID = dataBase.getMaxMapID();
       maxLocID = dataBase.getMaxLocID();
       maxPointID = dataBase.getMaxPointID();
//       
//       for(int i = 1; i <= maxMapID; i++){
//           Map map = new Map();
//           map = dataBase.showMap(i);
//            getMapList().add(map);
//          
//       }
       
       GlobalMapInfo gm = dataBase.getGlobalMapInfo();
       this.setMapList(gm.maps); 
       getAllEdgeList().addAll(gm.interMapEdges);
       // store all data in one data structure 
       for(Map m : getMapList()){
            getAllLocationList().addAll(m.locList);
            getAllPointList().addAll(m.pointList);
            getAllEdgeList().addAll(m.edgeList);

       }
         for (Location l : getAllLocationList()) {
                if (l.favorite == 1) {
                    favLocationList.add(l);
                }
            }
       System.out.println();
    }

    public ArrayList<Location> getFavLocationList() {
        return favLocationList;
    }
    /**
     * @return the allPointList
     */
    public ArrayList<Point> getAllPointList() {
        return allPointList;
    }

    /**
     * @param allPointList the allPointList to set
     */
    public void setAllPointList(ArrayList<Point> allPointList) {
        this.allPointList = allPointList;
    }

    /**
     * @return the allEdgeList
     */
    public ArrayList<Edge> getAllEdgeList() {
        return allEdgeList;
    }

    /**
     * @param allEdgeList the allEdgeList to set
     */
    public void setAllEdgeList(ArrayList<Edge> allEdgeList) {
        this.allEdgeList = allEdgeList;
    }

    /**
     * @return the allLocationList
     */
    public ArrayList<Location> getAllLocationList() {
        return allLocationList;
    }

    /**
     * @param allLocationList the allLocationList to set
     */
    public void setAllLocationList(ArrayList<Location> allLocationList) {
        this.allLocationList = allLocationList;
    }

    /**
     * @return the mapList
     */
    public ArrayList<Map> getMapList() {
        return mapList;
    }

    /**
     * @param mapList the mapList to set
     */
    public void setMapList(ArrayList<Map> mapList) {
        this.mapList = mapList;
    }
    
}
