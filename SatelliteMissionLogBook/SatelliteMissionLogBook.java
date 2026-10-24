package SatelliteMissionLogBook;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class SatelliteMissionLogBook
{
    Scanner sc=new Scanner(System.in);
    ArrayList<Satellite> sat_list=new ArrayList<>();
    ArrayList<Health> health_list=new ArrayList<>();
    ArrayList<MissionEvent> mission_event_list=new ArrayList<>();
    ArrayList<String> anom_sat_list=new ArrayList<>();
    //anomaly count 
     int anomaly_count=0;
     int no_anomaly_count=0;

     //health check count for each satellite 
     int anomaly_health_check_count=0;
   

public void Menu()
{
    
    int choice=0;
    while(choice!=6)
    {
       
        System.out.println("---------MENU------------");
        System.out.println();
        System.out.println("1. Register Satellite\n2. Health records\n3. Anomaly Detection\n4. Mission Event Log\n5. Mission Summary\n6. Exit");
        System.out.println();

  System.out.print("enter your choice:");
  choice=sc.nextInt();
  sc.nextLine();
  System.out.println();

  switch(choice)
  {
    case 1:
        RegisterSatellite();
        break;
    
    case 2:
        HealthMonitoring();
        break;

    case 3:
        AnomalyDetection();
        break;

    case 4:
        MissionEventLog();
        break;
    
    case 5:
        MissionSummary();
        break;
    
    case 6:
        System.out.println("Exiting the program.....");
        break;

    default:
        System.out.println("invalid choice entered");
        break;
  }

  
}
}

class Satellite
{
 String sat_id;
 String sat_name,mission_type;
 LocalDate launch_date;

 Satellite(String id,String n,String mt,LocalDate ld)
 {
   sat_id=id;
   sat_name=n;
   mission_type=mt;
   launch_date=ld;
 }
}


public void RegisterSatellite()
{
    System.out.println("----------Register New Satellite-----------");

    

    System.out.print("Enter Satellite ID:");
    String sat_id=sc.nextLine();


    System.out.print("Enter Satellite name:");
    String sat_name=sc.nextLine();

    System.out.print("Enter Mission Type:");
    String mission_type=sc.nextLine();
    
    System.out.print("Enter Launch Date (YYYY-MM-DD):");
    LocalDate launch_date=LocalDate.parse(sc.nextLine());

    Satellite sat=new Satellite(sat_id, sat_name, mission_type, launch_date);
    sat_list.add(sat);


    MissionEvent event=new MissionEvent(sat_id, "Satellite registeration", "Satellite registered successfully", "Low", LocalDateTime.now());
    mission_event_list.add(event);


    System.out.println("Satellite registered successfully!");
    System.out.println("---------------------------------------------");

    
}

class Health
{
    String sat_id;
    double battery_level,temperature,signal_strength;
    LocalDateTime recording_time;

    Health(String sid,double bl,double t,double ss,LocalDateTime rt)
    {
        sat_id=sid;
        battery_level=bl;
        temperature=t;
        signal_strength=ss;
        recording_time=rt;
    }
}


public void HealthMonitoring()
{
    System.out.println("------------Health Monitoring------------");

   
    System.out.print("enter satellite ID:");
    String id=sc.nextLine();

    boolean sat_found=false;
   

    for(int i=0; i<sat_list.size(); i++)
    {
        if(id.equals(sat_list.get(i).sat_id))

        {
        sat_found=true;
        System.out.print("enter battery level:");
        Double battery_level=sc.nextDouble();

        System.out.print("enter temperature:");
        Double temperature=sc.nextDouble();

        System.out.print("enter signal strength:");
        Double signal_strength=sc.nextDouble();

        LocalDateTime recording_time=LocalDateTime.now();

        Health ht= new  Health(id, battery_level, temperature, signal_strength, recording_time);
        health_list.add(ht);

        System.out.println("Health status recorded successfully!");
        System.out.println("-----------------------------------------");

        
        MissionEvent event1=new MissionEvent(id, "Health check", "health status recorded successfully", "Low", LocalDateTime.now());
        mission_event_list.add(event1);
    }
    }
    if(sat_found==false)
    {
        System.out.println("Satellite ID not found");
    }

    
        
}

public void AnomalyDetection()
    {
        System.out.println("------------Anomaly Detection------------------");
        
        

        for(int i=0; i<health_list.size(); i++)
        {
            boolean anomaly_detected=false;
            String anomaly_des="";
            if(health_list.get(i).battery_level<20.0)
            {
                System.out.println("Anomaly detected in Satellite ID:"+health_list.get(i).sat_id);
                System.out.println("Battery level is below 20%");
                anomaly_des+="Battery level<20%;";
                anomaly_count++;
                anomaly_detected=true;
            }
            
            if(health_list.get(i).temperature>80.0)
            {
                System.out.println("Anomaly detected in Satellite ID:"+health_list.get(i).sat_id);
                System.out.println("Temperature is above 80°C");
                anomaly_des+="Temperature>80°C;";
                anomaly_count++;
                anomaly_detected=true;
            }

            if(health_list.get(i).signal_strength<50.0)
            {
                System.out.println("Anomaly detected in Satellite ID:"+health_list.get(i).sat_id);
                System.out.println("Signal strength is below 50.0");
                anomaly_des+="Signal strength<50.0;";
                anomaly_count++;
                anomaly_detected=true;
            }
            
            if(health_list.get(i).recording_time.isBefore(LocalDateTime.now().minusDays(1)))
            {
                System.out.println("Anomaly detected in Satellite ID:"+health_list.get(i).sat_id);
                System.out.println("Health status not recorded in the last 24 hours");
                anomaly_des+="Health status not recorded in the last 24 hours;";
                anomaly_count++;
                anomaly_detected=true;
            }
            

            if(anomaly_detected==false)
        {
            System.out.println("No anomalies detected in any satellite health status:"+health_list.get(i).sat_id);
            no_anomaly_count++;
        }
        
       

        if(anomaly_detected==true)
        {
            anomaly_health_check_count++;
            MissionEvent event2=new MissionEvent((health_list.get(i).sat_id), "Anomaly Detection", anomaly_des, "High", LocalDateTime.now());
            mission_event_list.add(event2);
           
        }
        
           if (anomaly_detected == true && !anom_sat_list.contains(health_list.get(i).sat_id))
           {
               anom_sat_list.add(health_list.get(i).sat_id);
           }



            


        }

        
       
    }


    class MissionEvent
    {
        
        String satellite_id,event_type,eve_description,severity;
        LocalDateTime timestamp;

        MissionEvent(String s_id,String et,String ed,String sev,LocalDateTime ts )
        {
            satellite_id=s_id;
            event_type=et;
            eve_description=ed;
            severity=sev;
            timestamp=ts;

        }
    }
    

        
    public void  MissionEventLog()
    {
       
        System.out.println("------------Mission event Log------------------");
        System.out.println("enter satellite Id to log events:");
        String id=sc.nextLine();
        
            boolean satellitefound=false;
            boolean eventfound=false;

            for(int i=0; i<sat_list.size(); i++)
            {
                if(id.equals(sat_list.get(i).sat_id))
                {
                    satellitefound=true;
                    for(int j=0; j<mission_event_list.size(); j++)
                    {
                        if(id.equals(mission_event_list.get(j).satellite_id))
                        {
                            eventfound=true;
                             System.out.println("satellite id:"+sat_list.get(i).sat_id);
                             System.out.println("Event type:"+mission_event_list.get(j).event_type);
                             System.out.println("Event description:"+mission_event_list.get(j).eve_description);
                             System.out.println("Event severity:"+mission_event_list.get(j).severity);
                             System.out.println("Event timestamp:"+mission_event_list.get(j).timestamp);
                             System.out.println("---------------------------------------------");
                             System.out.println();
                        }
                    }
                    if(eventfound==false)
                    {
                        System.out.println("No events found for the satellite ID:"+id);
                    }
                }
                

            }

            if(satellitefound==false)
            {
                System.out.println("Satellite ID not found");
            }

       
    }
    

    public void MissionSummary()
    {
        System.out.println("------------Mission Summary------------------");
        System.out.println("Registered Satellites:"+sat_list.size());
        System.out.println("Satellites Monitored:"+health_list.size());
        System.out.println("Mission Events:"+mission_event_list.size());
        System.out.println("Anomalies Detected:"+anom_sat_list.size());
        System.out.println("No Anomaly Detected:"+no_anomaly_count);

    }

    
public static void main(String[] args)
{
  System.out.println("-----Welcome to Satellite Mission LogBook--------");
  SatelliteMissionLogBook obj=new SatelliteMissionLogBook();
  obj.Menu();
}




}
