package ie.gmit.sw;

import java.util.*;

public class Runner {

	public static void main(String[] args) 
	{
		Scanner console = new Scanner(System.in);
		
		List<PiD> processArray = new ArrayList<>();
		int totalProcesses;
		int option;
		int quantum;
				
		//Prompt the user for the total number of processes
		System.out.println("Enter the total number of processes: ");
		totalProcesses = console.nextInt();
		
		//Get the burst times for each process and add an object to the ArrayList
		System.out.println("\nEnter the burst times for each process: ");
		
		for(int i = 1; i <= totalProcesses; i++)
		{
			processArray.add(new PiD(i, console.nextInt()));
		}
		
		//Print the choices and prompt the user to enter an option
		System.out.println("\n1: FCFS - First Come First Serve \n2: SJF - Shortest Job First \n3: Round Robin \n4: Exit");
		System.out.println("Please choose a scheduling algorithm: ");
		option = console.nextInt();
		
		while(option != 4)
		{
			switch(option)
			{
				case 1:
					//Call fcfs() static method to calculate the the average wait time and print the information to the console
					fcfs(processArray, totalProcesses);
					break;
				case 2:
					//Call Collections.sort on processArray to order them from shortest burst time to longest
					//This uses the compareTo() method in the PiD class which has been overridden
					Collections.sort(processArray);
					
					//Call the fcfs() method which will use the same algorithm to calculate the average time
					//however the list will have already been sorted
					fcfs(processArray, totalProcesses);
					break;
				case 3:
					System.out.println("Please enter a time quantum: ");
					quantum = console.nextInt();
					
					int totalBurstTime = 0;

					//loop through the processArray and calculate the total burst time
					for(PiD process : processArray)
					{
						totalBurstTime += process.getProcessBurstTime();
					}
					
					System.out.println("Process No | Start Time | Burst Time | Wait Time");
					
					int startTime = 0;
					int burstTime = 0;
					int[] waitTimes = new int[totalProcesses];
					int i = 0;

					//while the totalburstTime > 0 then keep looping through the processArray
					while(totalBurstTime > 0)
					{
						i = 0;
						for(PiD process : processArray)
						{
							
							if(process.getProcessBurstTime() < quantum)
							{
								startTime += burstTime;
								burstTime = process.getProcessBurstTime();
								process.setProcessBurstTime(0);
							}
							else
							{
								startTime += burstTime;
								burstTime = quantum;
								process.setProcessBurstTime(process.getProcessBurstTime() - quantum);
							}
							
							
							System.out.printf("%10d %10d %10d %10d\n", process.getProcessNo(), startTime, burstTime, waitTimes[i]);							
							
							//Decrement the totalBurstTime
							totalBurstTime = totalBurstTime - burstTime;
							i++;
						} //end for
						
						//separate iterations in the console
						System.out.println("================================================");
					} //end while
					break;
				default:
					break;
			}
			
			System.out.println("Please choose a scheduling algorithm: ");
			option = console.nextInt();
		}
		
	} //end main()
	
	public static void fcfs (List<PiD> processArray, int totalProcesses)
	{
		int totalWaitTime = 0;
		float averageWaitTime;
		
		System.out.println("Process No | Burst Time | Wait Time");
		
		//Instantiate an array with the size of the total number of processes
		int[] waitTimes = new int[totalProcesses];
		//Initialize a counter variable to 0
		int count = 0;
		
		//Loop through the ArrayList and add up the wait time
		for(PiD process : processArray)
		{
			//print the process information
			System.out.printf("%10d %10d %10d\n", process.getProcessNo(), process.getProcessBurstTime(), waitTimes[count]);
			
			//increment the counter to calculate the wait time for the process
			//the wait time on the first process will always be 0
			count++;
			//if the counter is less than the number of processes then add to the wait time
			if(count < totalProcesses)
			{
				//The wait time = the previous wait time + the current burst time
				waitTimes[count] = waitTimes[count - 1] + process.getProcessBurstTime();
			}
			
		} //end for
		
		//Add up the total waiting time for each process
		for(int i = 0; i < totalProcesses; i++)
		{
			totalWaitTime += waitTimes[i];
		}
		System.out.println("\nThe total wait time = " + totalWaitTime);
		
		averageWaitTime = (float)totalWaitTime / totalProcesses;
		System.out.println("\nThe average wait time = " + averageWaitTime);
	} //end fcfs()

}
