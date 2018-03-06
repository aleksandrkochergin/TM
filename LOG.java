import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class LOG implements java.io.Serializable //Serializable log class that keeps track of all the tasks in an array list
{
   private ArrayList<Task> tasks;
   public LOG()
   {
      tasks = new ArrayList<Task>();
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
         s+= "  \n===============================";
      }
      
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
