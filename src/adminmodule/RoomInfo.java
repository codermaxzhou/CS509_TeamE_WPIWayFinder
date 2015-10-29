package adminmodule;


public class RoomInfo
{
   private String name;
   private String Description;
   private String Type;

   public RoomInfo(String aName, String aDescription)
   {
      name = aName;
      Description = aDescription;
   }

   public String getName()
   {
      return name;
   }

   public String getDescription()
   {
      return Description;
   }

   public void setName(String aName)
   {
      name = aName;
   }

   public void setDescription(String aDescription)
   {
      Description = aDescription;
   }
}
