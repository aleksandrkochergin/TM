import java.time.LocalDateTime;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.*;
public class Task implements java.io.Serializable//Serializable class Task that has all the features of a task and keeps track of all the time stamps
{
   public String name;
   private boolean status;
   private ArrayList<LocalDateTime> times;
   private String description;
   String size;
   public Task()
   {
      name = "";
      description = "";
      status = false;
      times = new ArrayList<LocalDateTime>();
      size = "UNKNOWN";
   } 
   public Task(String n, boolean s)
   {
      name = n;
      description ="";
      status = s;
      times = new ArrayList<LocalDateTime>();
      size = "UNKNOWN";
   }
   public void setDescription(String s)
   {
      description += "\n\t" + s;
   }
   public String toString()
   {
      String s = "\nName of Task: " + name;
      s+= "\nDescription: " + description ;
      s+="\nSize : " + size;
      if(status)
         s+= "\nStatus: in progress";
      else
         s+= "\nStatus: not in progress";   
      s+= "\nTotal time spent: " + totalTime();
      
      LOG log = new LOG();
      if(log.howManySize(size) >= 2)
      {
         s+=("\n-----------");
         s+=("\nStats:");
         s+=("\nMin time for size " + size + ":     " + log.minTime(size));
         s+=("\nMax time for size " + size + ":     " + log.maxTime(size));
         s+=("\nAverage time for size " + size + ": " + log.averageTime(size));
      }
      
      return s;
   }
   public long time()
   {
      long elapsedSeconds = 0;
      for(int i=0; i<times.size(); i=i+2)
      {
         elapsedSeconds += ChronoUnit.SECONDS.between(times.get(i),times.get(i+1));

      }
      return elapsedSeconds;
   }
   public String totalTime()//Calculates the total time spent on a certain task and converts it to a regular format
   {
      int totalh =0;
      int totalm =0;
      int totals =0;
      String s = "";
      if(status == true)
      {
         return "TASK STILL IN PROGRESS";
      }
      else
      {
         
         long elapsedSeconds= 0;
         for(int i = 0; i<times.size(); i=i+2)
         {
            elapsedSeconds +=  ChronoUnit.SECONDS.between(times.get(i),times.get(i+1));
         }
         totalh = (int) elapsedSeconds / 3600;
         int remainder = (int) elapsedSeconds  - totalh * 3600;
         totalm = remainder / 60;
         remainder = remainder - totalm * 60;
         totals = remainder;
      }
      s = Integer.toString(totalh) + " : " + Integer.toString(totalm) + " : " + Integer.toString(totals);
      return s;
   }
   public void addTime(LocalDateTime t)
   {
      times.add(t);
   }
   public ArrayList<LocalDateTime> getTime()
   {
      return times;
   }  
   public String getSize()
   {
      return size;
   }
   public void setName(String n)
   {
      name = n;
   }
   public void changeStatus(boolean s)
   {
      status = s;
   }
   public String getName()
   {
      return name;
   }
   public String getDescription()
   {
      return description;
   }
   public boolean getStatus()
   {
      return status;
   }
}
