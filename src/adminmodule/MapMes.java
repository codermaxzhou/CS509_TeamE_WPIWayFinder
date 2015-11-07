/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adminmodule;

/**
 *
 * @author Castro
 */
public class MapMes {
    public String name;                     // Map name
    public String description;              // Map description
    public MapMes(String aName, String aDescription)
   {
      name = aName;
      description = aDescription;
   }
    public String getName()
   {
      return name;
   }

   public String getDescription()
   {
      return description;
   }
}
