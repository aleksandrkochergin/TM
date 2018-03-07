import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class LOG implements java.io.Serializable //Serializable log class that keeps track of all the tasks in an array list
{
   private ArrayList<Task> tasks;
   public LOG()
   {
      tasks = new ArrayList<Task>();
      try
      {
      read();
      }catch(Exception e){}
   }
   public boolean contains(String name)
   {
      if(tasks.size() == 0)
         return false;
      for(int i=0; i<tasks.size(); i++)
      {
         if(tasks.get(i).getName().equals(name))
            return true;
      }
      return false;
   }
   public String toString()
   {
      String s = "";
      for(int i=0; i<tasks.size(); i++)
      {
         s= s+ tasks.get(i).toString();
         s+= "  \n=====================================";
      }
      
      return s;
   }
   public String minTime(String size)
   {
      
      long min = 0;
      for(int i=0; i<1;i++)
      {
         
         
         if(tasks.get(i).getSize().equals(size))
         {
            min = tasks.get(i).time();
         }
      }
      
      for(int j=0; j<2;j++)
      {
         if(tasks.get(j).getSize().equals(size) && tasks.get(j).time()<min)
         {
            min = tasks.get(j).time();
         }
      }
      
      return convert(min);
   }
   public String maxTime(String size)
   {
      long max = 0;
      for(int i=0; i<tasks.size();i++)
      {
         if(tasks.get(i).getSize().equals(size))
         {
            max = tasks.get(i).time();
         }
      }
      for(int i=0; i<tasks.size();i++)
      {
         if(tasks.get(i).getSize().equals(size) && tasks.get(i).time()>max)
         {
            max = tasks.get(i).time();
         }
      }
      return convert(max);
   }
   public int howManySize(String s)
   {
      int count = 0;
      for(int i=0; i<tasks.size();i++)
      {
         if(tasks.get(i).getSize().equals(s))
            count++;
      }
      return count;
   }
   public String averageTime(String size)
   {
      long average = 0;
      int count = 0;
      for(int i=0; i<tasks.size();i++)
      {
         if(tasks.get(i).getSize().equals(size))
         {
            average += tasks.get(i).time();
            count++;
         }
      }
      average = average/count;
      return convert(average);
   }
   public String timeForAllTasks()
   {
      long total = 0;
      for(int i=0; i<tasks.size();i++)
      {
         total+=tasks.get(i).time();
      }
      return convert(total);
   }

   public String convert(long elapsedSeconds)
   {
      int totalh=0, totalm=0,totals=0;
      totalh = (int) elapsedSeconds / 3600;
      int remainder = (int) elapsedSeconds  - totalh * 3600;
      totalm = remainder / 60;
      remainder = remainder - totalm * 60;
      totals = remainder;
      String s = Integer.toString(totalh) + " : " + Integer.toString(totalm) + " : " + Integer.toString(totals);
      return s;
   }
   public void delete(String name)
   {
      System.out.println("test");
      tasks.remove(getTask(name));
      try
      {write();
      }catch(Exception e){}
   }
   public void write()throws ClassNotFoundException, IOException//writes the array list containing tasks to a file
   {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tasks.dat"));
      out.writeObject(tasks);
      out.close();
   }   
   public void read()throws ClassNotFoundException, IOException//reads the array list containig tasks from a file
   { 
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("tasks.dat"));
      tasks = (ArrayList<Task>)in.readObject();
      in.close();
   } 
   public Task getTask(String name)//returns task after deleting it
   {
      for(int i=0; i<tasks.size(); i++)
      {
         if(tasks.get(i).getName().equals(name))
         {   
            Task t = tasks.get(i);
            tasks.remove(i);
            return t;
         }
      }
      return null;
   }
   public Task peek(String name)//returns task without deleting it
   {
      for(int i=0; i<tasks.size(); i++)
      {
         if(tasks.get(i).getName().equals(name))
         {   
            Task t = tasks.get(i);
            return t;
         }
      }
      return null;
   }
   public void addTask(Task t)
   {
      try
      {
         tasks.add(t);
      }catch(NullPointerException e)
      {
      }
   }
   public ArrayList<Task> getList()
   {
      return tasks;
   }
}
