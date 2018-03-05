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
      return false;
    }
    public boolean describeTask(String name, String description)
    {
      return false;
    }
    public boolean sizeTask(String name, String size)
    {
      return false;
    }
    public boolean deleteTask(String name)
    {
      return false; 
    }
    public boolean renameTask(String oldName, String newName)
    {
      return false;
    }

    // return information about our tasks
    //
    public String taskElapsedTime(String name)
    {
      String ret = "";
      return ret;
    }
    public String taskSize(String name)
    {
      String ret = "";
      return ret;
    }
    public String taskDescription(String name)
    {
      String ret = "";
      return ret;
    }

    // return information about some tasks
    //
    public String minTimeForSize(String size)
    {
      String ret = "";
      return ret;
    }
    public String maxTimeForSize(String size)
    {
      String ret = "";
      return ret;
    }
    public String avgTimeForSize(String size)
    {
      String ret = "";
      return ret;
    }

    public Set<String> taskNamesForSize(String size)
    {
      return null;
    }

    // return information about all tasks
    //
    public String elapsedTimeForAllTasks()
    {
      String ret = "";
      return ret;
    }
    public Set<String> taskNames()
    {
      return null;
    }
    public Set<String> taskSizes()
    {
      return null;
    }

}