

import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;


class Tasks{
	
	String name="";
	LocalDate deadline;
	String status;
	
	
	Tasks(String n,LocalDate d,String st)
	{
		name=n;
		deadline=d;
		status=st;
	}
}
    
	
public class smart_to_do_list {
	
	public void Menu()
	{
		
		String name="";
		LocalDate deadline=null;
		String status="Not Completed";
		int choice=0;
		
		
		ArrayList<Tasks> tasks=new ArrayList<>();
		Scanner sc=new Scanner(System.in);
		
		while(choice!=5)
		{
	    System.out.println();
		System.out.println("=====MENU=====");
		System.out.println("1. Add task\n2. View task\n3. Delete task\n4. Update Status\n5. Exit");
	
		System.out.print("enter your choice:");
		choice=sc.nextInt();
		System.out.println();
		
		switch(choice)
		{
		case 1:
			System.out.println("==========ADD TASKS=========");
			
			System.out.print("enter no of tasks:");
			int n=sc.nextInt();
			sc.nextLine();
			
			for(int i=1; i<=n; i++)
			{
				System.out.println();
				System.out.println("Task "+i);
				
				
			System.out.print("Enter task name:");
			name=sc.nextLine();
			
			
			System.out.print("Deadline(YYYY-MM-DD):");
			deadline=LocalDate.parse(sc.nextLine());
			
		    System.out.println("Status:"+status);
			
			
			Tasks obj1=new Tasks(name,deadline,status);
			tasks.add(obj1);
			System.out.println("Task saved");
			}
			System.out.println("=================");
			break;
			
		case 2:
			System.out.println("=========VIEW TASKS========");
			
			for(int j=0; j<tasks.size(); j++)
			{
			      Tasks t=tasks.get(j);
			      System.out.println();
			      
			      System.out.println("Task"+(j+1));
			      System.out.println("Task name:"+t.name);
			      System.out.println("Deadline:"+t.deadline);
			      System.out.println("Status:"+t.status);
			      
			}
			System.out.println("===================");
		    break;
			
			
		
		case 3:
			System.out.println("=====DELETION======");
			System.out.println("enter task no to delete:");
			int del=sc.nextInt();
			
			if(del>=1 && del<=tasks.size())
			{
				tasks.remove(del-1);
				System.out.print("task deleted successfully");
			}
		
			else
			{
				System.out.println("invalid task number");
			}
			
			break;
			
		case 4:
			System.out.println("========UPDATE STATUS=======");
			System.out.print("Enter task no to update status:");
			int up=sc.nextInt();
			
			if(up>=1 && up<=tasks.size())
			{
				tasks.get(up-1).status="Completed";
			}
			else
			{
				System.out.println("invalid task number");
			}
			break;
			
			
		case 5:
			System.out.println("come back soon to finish your tasks on time!!!");
			System.out.println("See yeah!! byeee");
			break;
		
		default:
			System.out.println("invalid choice");
			break;
			
		}
		}
	}
	
	    public static void main(String[] args)
	{
		System.out.println("=========SMART TO-DO-LIST=========");
		
		smart_to_do_list s=new smart_to_do_list();
		s.Menu();
	}
}
	
	
	


