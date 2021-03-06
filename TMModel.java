import java.io.*;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.*;
public class TMModel implements ITMModel
{
   // set information in our model
    //
    LOG log;
    public TMModel()throws ClassNotFoundException, IOException
    {
       log = new LOG();//Creates a new log file
       try
       {
          log.read();//sync up the log file with previous entries
       }catch(FileNotFoundException f)
       {
       }
    }
    public boolean startTask(String name)
    {
      if(log.contains(name))
      {
         LocalDateTime time;
         time = LocalDateTime.now();
         Task temp = log.getTask(name);
         if(temp.getStatus()==false)
         {
            temp.addTime(time);
            temp.changeStatus(true);
            log.addTask(temp);
         try
         {
            log.write();
         }catch(Exception e)
         {}
         }
         else
         {
            return false;
         }
      }
               else
               {
                  LocalDateTime time;
                  time = LocalDateTime.now();
                  Task temp = new Task(name, true);
                  temp.addTime(time);
                  log.addTask(temp);
                  try
                  {
                     log.write();
                  }catch(Exception e)
                  {}
               }

      return true;
    }
    public boolean stopTask(String name)
    {
      if(!log.contains(name))
      {
         return false;
      }
      else
      {
         LocalDateTime time;
         time = LocalDateTime.now();
         Task temp = log.getTask(name);
         if(temp.getStatus()==true)
         {
            temp.addTime(time);
            temp.changeStatus(false);
            log.addTask(temp);
            try
            {
            log.write();
            }catch(Exception e){}
         }
         else
         {
            return false;
         }

      }

      return true;
    }
    public boolean describeTask(String name, String description)
    {
      if(log.contains(name))
      {
         Task temp =log.getTask(name);
         temp.setDescription(description);
         log.addTask(temp);
         try{
         log.write();
         }catch(Exception e){}
      }
      else
      {
         Task temp = new Task(name, false);
         temp.setDescription("\n" + description);
         log.addTask(temp);
         try{
            log.write();
         }catch(Exception e){}
      }
      return true;
    }
    public boolean sizeTask(String name, String size)
    {
      if(log.contains(name))
      {
         if(size.equals("S") || size.equals("M") || size.equals("L") || size.equals("XL"))
         {
            Task t = log.getTask(name);
            t.size = size;
            log.addTask(t);
            try
            {
               log.write();
            
            }catch(Exception e){}
         }
         else
            System.out.println("Please use the correct sizing (S,M,L,XL)");
      }
      else
      {
         return false;
      }
      
      return true;
    }
    public boolean deleteTask(String name)
    {
      if(!log.contains(name))
         return false;
      log.delete(name);
         return true; 
    }
    public boolean renameTask(String oldName, String newName)
    {
      if(!log.contains(oldName))
         return false;
      Task temp = log.getTask(oldName);
      temp.name = newName;
      log.addTask(temp);
      try
      {
         log.write();
      }catch(Exception e){}
      return true;
    }

    // return information about our tasks
    //
    public String taskElapsedTime(String name)
    {
      String ret = "";
      ret = log.peek(name).totalTime();
      return ret;
    }
    public String taskSize(String name)
    {
      String ret = "";
      log.peek(name).getSize();
      return ret;
    }
    public String taskDescription(String name)
    {
      String ret = "";
      log.peek(name).getDescription();
      return ret;
    }

    // return information about some tasks
    //
    public String minTimeForSize(String size)
    {
      String ret = log.minTime(size);
      return ret;
    }
    public String maxTimeForSize(String size)
    {
      String ret = log.maxTime(size);
      return ret;
    }
    public String avgTimeForSize(String size)
    {
      String ret = log.averageTime(size);
      return ret;
    }

    public Set<String> taskNamesForSize(String size)
    {
      Set<String> set = new HashSet<String>();
      ArrayList<Task> list = log.getList();
      for(int i=0; i<list.size(); i++)
      {
         if(list.get(i).getSize().equals(size))
         {
            set.add(list.get(i).getName());
         }
      }
      return set;
    }

    // return information about all tasks
    //
    public String elapsedTimeForAllTasks()
    {
      String ret = "";
      ret = log.timeForAllTasks();
      return ret;
    }
    public Set<String> taskNames()
    {
      Set<String> set = new HashSet<String>();
      ArrayList<Task> list = log.getList();
      for(int i=0; i<list.size(); i++)
      {
         set.add(list.get(i).getName());
      }
      return set;
    }
    public Set<String> taskSizes()
    {
      Set<String> set = new HashSet<String>();
      ArrayList<Task> list = log.getList();
      for(int i=0; i<list.size(); i++)
      {
         set.add(list.get(i).getSize());
      }
      return set;
    }

}