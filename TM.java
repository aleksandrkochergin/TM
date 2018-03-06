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
      TMModel tmModel = new TMModel();
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
               usage();
            }
            else
            {
               if(!tmModel.startTask(args[1]))
                  usage();
            }
            break;
         case "size":
            if(args.length != 3)
            {
               System.out.println("Enter the arguments in the correct format"
               +"\nsize <task name> <S,M,L,XL> "
               + "\n Both task name and task size have to be contained inside parrentesis");
               
            }
            else
            {
               if(!tmModel.sizeTask(args[1],args[2]))
                  System.out.println("Task does not exist");               
            }
            break;
         case "stop":
            if(args.length<2 || args.length>2)
            {
               usage();   
            }
            else
            {
               String name = args[1];
               if(!tmModel.stopTask(name))
                  usage();
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
         case "delete":
            if(args.length != 2)
            {
               usage();
            }
            else
            {
               if(!tmModel.deleteTask(args[1]))
                  System.out.println("No task with such name found");
            }
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
               String name = args[1];
               tmModel.describeTask(name,args[2]);
               if(args.length ==4)
               {
                  size = args[3];
                  if(!tmModel.sizeTask(name, size))
                  {
                     System.out.println("Task doesn't exist");
                  }
               }

            }
            break;
         default:
            usage();
      } 
   }
   public static void main(String[] args)throws ClassNotFoundException, IOException//Main method, has a call to appMain, a non static main method
   {
      TM tm = new TM();
      tm.appMain(args);
      
      
   }
   public void usage()
   {
      System.out.println("Enter a command followed by data\n\n<start>"
      +"           \"name of task\"     <--starts a task\n<stop>           "
      +" \"name of tast\"     <--stops a task\n<describe> \"name\" \"description\""
      +"    <--describes a task\n<summary>         \"task name\"        <--summarizes a given task\n<summary>  "        
      +"                  <--summarizes all tasks");
   }
   
   public void summary(LOG log)throws ClassNotFoundException, FileNotFoundException, IOException//Prints out summary of all the tasks
   {
      System.out.println(log);
   }
  }
