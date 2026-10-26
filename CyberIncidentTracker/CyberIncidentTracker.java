package CyberIncidentTracker;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileReader;



public class CyberIncidentTracker {

    Scanner sc=new Scanner(System.in);
    ArrayList<CyberIncident> LogEntry= new ArrayList<>();
    HashMap<String, Integer> ipcount=new HashMap<>(); 
    int fcount=0;
   
    public void Menu()
    {
        int choice=0;
        while(choice!=6)
        {
        System.out.println("=============================");
        System.out.println("CYBER INCIDENT TRACKER");
        System.out.println("==============================");

        System.out.println("MENU");
        System.out.println("1. Load Security Logs\n2. View all log entries\n3. Analyse failed logins\n4. View suspicious ip addresses\n5. View Security reports\n6. Exit");
        System.out.print("enter your choice:");
        choice=sc.nextInt();
        sc.nextLine();

        switch(choice)
        {
            case 1:
                LoadSecurityLogs();
                break;

            case 2:
                ViewLogEntries();
                break;

            case 3:
                FailedLogins();
                break;

            case 4:
                Suspiciousip();
                break;

            case 5:
                SecurityReports();
                break;
            
            case 6:
                System.out.println("Exiting from Cyber Incident Tracker..... ");
                break;

            default:
                System.out.println("invalid choice");
        }
    }

    }

    class CyberIncident
    {
        LocalDateTime timestamp;
        String username,ip,event,reason,device_name;

        CyberIncident(LocalDateTime ts,String un,String ipa,String e,String r,String dn)
        {
            timestamp=ts;
            username=un;
            ip=ipa;
            event=e;
            reason=r;
            device_name=dn;    
        }
    }

    public void  LoadSecurityLogs()
    {
        
        LocalDateTime line2;
        String ts,user,ip,eve,reason,device;
        String linebu;
        
        System.out.println("------------------------------");
        System.out.println("-----Load Security Logs-------");
        System.out.println("------------------------------");

        System.out.print("Enter file name:");
        String file=sc.nextLine();

        try(BufferedReader br=new BufferedReader(new FileReader(file)))
        {
            String line=br.readLine();
            while(line!=null)
            {
                //parsing the log 
                linebu=line;
                line=br.readLine();
                String[] parts=linebu.split("\\|");
            
                 if(parts.length==6)
                {
                    try{
                ts=parts[0].replace(" ","T");
                line2=LocalDateTime.parse(ts);

                //parsing username 
                user=parts[1];
                ip=parts[2];
                eve=parts[3];
                reason=parts[4];
                device=parts[5];
                

                CyberIncident obj1=new CyberIncident(line2, user, ip, eve, reason, device);
                LogEntry.add(obj1);
                
                       }
                     catch(DateTimeParseException e)
                     {
                        System.out.println("Invalid Timestamp");


                     }
            
                }
                else
                {
                    continue;
                }


                
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("logs loaded successfully");
    
    }

    public void ViewLogEntries()
    {
        System.out.println("------------------------------");
        System.out.println("------View Log entries--------");
        System.out.println("------------------------------");

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for(int i=0; i<LogEntry.size(); i++)
        {
            CyberIncident t=LogEntry.get(i);

            System.out.print(t.timestamp.format(formatter) +"| ");
            System.out.print(t.username+"| ");
            System.out.print(t.ip+"| ");
            System.out.print(t.event+"| ");
            System.out.print(t.reason+"| ");
            System.out.print(t.device_name);

            System.out.println();

        }

        System.out.println("---------------------------------------------------------------");
    }

    public void CountFailedLogins()
    {
    
            String fip;
             ipcount.clear();
             fcount=0;
            for(int i=0; i<LogEntry.size(); i++)
            {
                 CyberIncident t=LogEntry.get(i);

            

                if(t.event.equals("FAILED_LOGIN"))
                {
                    fip=t.ip;
                    if(ipcount.containsKey(fip))
                    { 
                        ipcount.put(t.ip,ipcount.get(t.ip)+1);
                    }
                    else
                    {
                        ipcount.put(t.ip,1);
                    }
                    fcount++;
                }
            }


    }

    public void FailedLogins()
    {

        System.out.println("--------------------------------");
        System.out.println("-------Failed Logins------------");
        System.out.println("--------------------------------");

        if(LogEntry.size()==0)
        {
            System.out.println("enter the logs:");
        }
        else 
        {
        CountFailedLogins();
            for(Map.Entry<String, Integer> entry : ipcount.entrySet())
            {
                System.out.println(entry.getKey()+" : " +entry.getValue());
            }

            System.out.println("Total failed logins:"+fcount);
        }
    }
    

    public void Suspiciousip()
    {
        System.out.println("----------------------------------");
        System.out.println("------Suspicious IP addresses-----");
        System.out.println("----------------------------------");

        Boolean f_logins=false;

        if(LogEntry.isEmpty())
        {
            System.out.println("Load security logs");
        }
        else
        {
            CountFailedLogins();
        for(Map.Entry<String, Integer> entry: ipcount.entrySet())
        {
           
            if(entry.getValue()>=3)
            {
                System.out.println(entry.getKey() +" : "+ entry.getValue() + " failed logins");
                f_logins=true;
            }
            
        }
        
        if(f_logins==false)
        {
            System.out.println("No Suspicious IP Address detected");
        }
       
        }   
    }

    public void SecurityReports()
    {
        System.out.println("-------------------------------------------");
        System.out.println("------------Security Reports---------------");
        System.out.println("-------------------------------------------");

        int slogin=0;
        int suscount=0;
        int flogin=0;

       

        if(LogEntry.isEmpty())
        {
            System.out.println("Load security logs:");
        }
        else
        {
            CountFailedLogins();
        System.out.println("Total Log entries: "+LogEntry.size());

        for(int i=0; i<LogEntry.size(); i++)
        {
             CyberIncident t=LogEntry.get(i);
             if(t.event.equals("FAILED_LOGIN"))
             {
               flogin++;
             }
        }
        
        System.out.println("Failed Logins: "+flogin);

        for(int i=0; i<LogEntry.size(); i++)
        {
             CyberIncident t=LogEntry.get(i);
             if(t.event.equals("SUCCESSFUL_LOGIN"))
             {
               slogin++;
             }
        }
        System.out.println("Successful Logins: "+slogin);
        

        for(Map.Entry<String, Integer> entry: ipcount.entrySet())
        {
            if(entry.getValue()>=3)
            {
                suscount++;
            }
        }
        System.out.println("Suspicious Logins: "+suscount);


        if(suscount>=1)
        {
        System.out.println("Suspicious activity detected");
        }
        else
        {
            System.out.println("No suspicious activity detected");
        }

    }


    }

    public static void main(String[] args)
    {
        CyberIncidentTracker obj2=new CyberIncidentTracker();
        obj2.Menu();
    }

   
}
