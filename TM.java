//Aleksandr Kochergin
//CSC 131

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
public class TM
{
   
   public void appMain(String[] args)throws ClassNotFoundException, IOException //Avoids using the main method
   {
           
      LOG log = new LOG();//Creates a new log file
      try
      {
         log.read();//sync up the log file with previous entries
      }catch(FileNotFoundException f)
      {
      }
      String command = "null";
      if(args.length > 0)
      {
         command = args[0];
      }
      switch(command)//Checks the arguments passed through the command line
      {
         case "start":
            if(args.length<2 || args.length>2)
            {
               System.out.println("Enter the command and the name of the task as parameters");
            }
            else
            {
               String name = args[1];
               if(log.contains(name))
               {
                  start(name, log);
               }
               else
               {
                  LocalDateTime time;
                  time = LocalDateTime.now();
                  Task temp = new Task(args[1], true);
                  temp.addTime(time);
                  log.addTask(temp);
                  log.write();
               }
               
            }
            break;
         case "size":
            if(args.length != 3)
            {
               System.out.println("Enter the arguments in the correct format"
               +"\nsize <task name> <XS,S,M,L,XL> "
               + "\n Both task name and task size have to be contained inside parrentesis");
               
            }
            else
            {
               if(log.contains(args[1]))
               {
                  if(args[2].equals("XS") || args[2].equals("S") || args[2].equals("M") || args[2].equals("L") || args[2].equals("XL"))
                  {
                        Task t = log.getTask(args[1]);
                        t.size = args[2];
                        log.write();
                  }
                  else
                     System.out.println("Please use the correct sizing (XS,S,M,L,XL)");
              }
              else
              {
                  System.out.println("Can't size a task that hasn't been initialized or descibed");
              }
               
            }
            break;
         case "stop":
            if(args.length<2 || args.length>2)
            {
               System.out.println("Enter the command and the name of the task as parameters");
            }
            else
            {
               String name = args[1];
               if(!log.contains(name))
               {
                  System.out.println("UNABLE TO STOP A TASK THAT DOESN'T EXIST");
               }
               else
               {
                  stop(name,log);
               }
            }
            
            break;
         case "summary":
            if(log.getList().size()==0)
            {
               System.out.println("There are no tasks in the history");
            }
            else
            {
               if(args.length == 1)
               {
                  summary(log);
               }
               if(args.length == 2)
               {
                  String name = args[1];
                  if(!log.contains(name))
                  {
                     System.out.println("There is no such task");
                  }
                  else
                  {
                     System.out.println(log.peek(name).toString());
                  }
               }
            }
            break;
         case "describe":
            if(args.length<3 || args.length>3)
            {
               System.out.println("Please enter <describe> followed by name and description inside quotation marks");
            }
            else
            {
               String name = args[1];
               if(log.contains(name))
               {
                  Task temp =log.getTask(name);
                  temp.setDescription(args[2]);
                  log.addTask(temp);
                  log.write();
               }
               else
               {
                  Task temp = new Task(args[1], false);
                  temp.setDescription(args[2]);
                  log.addTask(temp);
                  log.write();
               }
            }
            break;
         default:
            System.out.println("Enter a command followed by data\n\n<start>           \"name of task\"     <--starts a task\n<stop>            \"name of tast\"     <--stops a task\n<describe> \"name\" \"description\"      <--describes a task\n<summary>         \"task name\"        <--summarizes a given task\n<summary>                            <--summarizes all tasks");
      } 
   }
   public static void main(String[] args)throws ClassNotFoundException, IOException//Main method, has a call to appMain, a non static main method
   {
      TM tm = new TM();
      tm.appMain(args);
      
      
   }
   public void stop(String name, LOG log)throws ClassNotFoundException, FileNotFoundException, IOException//Records the time a certain task was stopped
   {
      LocalDateTime time;
      time = LocalDateTime.now();
      Task temp = log.getTask(name);
      if(temp.getStatus()==true)
      {
         temp.addTime(time);
         temp.changeStatus(false);
         log.addTask(temp);
         log.write();
      }
      else
      {
         System.out.println("The task has already been stopped");
      }
   }
   public void summary(LOG log)throws ClassNotFoundException, FileNotFoundException, IOException//Prints out summary of all the tasks
   {
      System.out.println(log);
   }
   public void start(String name, LOG log) throws ClassNotFoundException, FileNotFoundException, IOException //Starts tasks that are already found in the log
   {
      LocalDateTime time;
      time = LocalDateTime.now();
      Task temp = log.getTask(name);
      if(temp.getStatus()==false)
      {
         temp.addTime(time);
         temp.changeStatus(true);
         log.addTask(temp);
         log.write();
      }
      else
      {
         System.out.println("The task has already been started");
      }

   }
}
class LOG implements java.io.Serializable //Serializable log class that keeps track of all the tasks in an array list
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
         s+= "  \n";
      }
      
      return s;
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
class Task implements java.io.Serializable//Serializable class Task that has all the features of a task and keeps track of all the time stamps
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
      description = s;
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
      for(int i= 0; i<times.size(); i++)
      {
         if(i%2==0)
            s+= "Started: "+times.get(i)+ "  ";
         else
            s+= "Stopped: " +times.get(i) + "  ";
      }
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
