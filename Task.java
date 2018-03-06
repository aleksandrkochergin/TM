import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class Task implements java.io.Serializable//Serializable class Task that has all the features of a task and keeps track of all the time stamps
{
   private String name;
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
      s+= "\nTime stamps:\n";
      s+= "\nTotal time spent: " + totalTime();
      return s;
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
         
         for(int i = 0; i<times.size(); i=i+2)
         {
            
            int h1 = Integer.parseInt(times.get(i).toString().substring(11,13));
            int m1 = Integer.parseInt(times.get(i).toString().substring(14,16));
            int s1 = Integer.parseInt(times.get(i).toString().substring(17,19));
            
            int h2 = Integer.parseInt(times.get(i+1).toString().substring(11,13));
            int m2 = Integer.parseInt(times.get(i+1).toString().substring(14,16));
            int s2 = Integer.parseInt(times.get(i+1).toString().substring(17,19));
            
            if(s2<s1)
            {
               m2--;
               s2+=60;
            }
            s2-=s1;
            if(m2<m1)
            {
               h2--;
               m2+=60;
            }
            m2-=m1;
            if(h2<h1)
            {
               h2+=24;
            }   
            h2-=h1;
            totalh+=h2;
            totalm+=m2;
            totals+=s2;
         }
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
   public boolean getStatus()
   {
      return status;
   }
}
