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
                        log.addTask(t);
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
                     System.out.println("THERE IS NO SUCH TASK");
                  }
                  else
                  {
                     System.out.println(log.peek(name).toString());
                  }
               }
            }
            break;
         case "describe":
            if(args.length != 3 && args.length != 4)
            {
               System.out.println("Use describe command as follows:"
               +"\ndescribe <task name> <task description>"
               +"\nOR:"
               +"\ndescribe <task name> <task desciption> <XS,S,M,L,XL>");
            }
            else
            {
               String size = "";
               if(args.length ==4)
               {
                  if(args[3].equals("XS") || args[3].equals("S") || args[3].equals("M") || args[3].equals("L") || args[3].equals("XL"))
                     size = args[3];
                  else
                     System.out.println("Use correct sizing (XS,S,M,L,XL)");
               }
               String name = args[1];
               if(log.contains(name))
               {
                  Task temp =log.getTask(name);
                  temp.setDescription(args[2]);
                  if(args.length ==4)
                     temp.size = size;
                  log.addTask(temp);
                  log.write();
               }
               else
               {
                  Task temp = new Task(args[1], false);
                  temp.setDescription("\n" + args[2]);
                  temp.size = size;
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
