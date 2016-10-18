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
					//Get the time quantum from the user
					System.out.println("Please enter a time quantum: ");
					quantum = console.nextInt();
					
					//initialize a totalBurstTime variable to control the while loop
					//this will be decremented every time each process runs 1 quantum
					int totalBurstTime = 0;
					int count = 0;
					
					//Put the process burst times into an array so that you can don't have to decrement the initial values
					int[] processBurstTimes = new int[totalProcesses];

					//loop through the processArray and calculate the total burst time
					for(PiD process : processArray)
					{
						totalBurstTime += process.getProcessBurstTime();
						
						//put the the burst times into the array as the ArrayList loops
						processBurstTimes[count] = process.getProcessBurstTime();
						count++;
					}
					
					System.out.println("Process No | Start Time | Burst Time | Wait Time");
					
					//startTime - the time at which a process goes into the CPU
					int startTime = 0;
					//burstTime - a variable to split the entire burstTime into quantums and the remainder
					int burstTime = 0;
					//waitTimes - an array for calculating the wait times of each process
					int[] waitTimes = new int[totalProcesses];
					//timeSpent - an array for adding up the time executed as the loop cycles
					int[] timeSpent = new int[totalProcesses];
					int i = 0;

					//while the totalburstTime > 0 then keep looping through the processArray
					while(totalBurstTime > 0)
					{
						//reset i to 0 after each process runs for 1 quantum
						i = 0;

						for(PiD process : processArray)
						{	
							if(processBurstTimes[i] < quantum)
							{
								//increment startTime
								startTime += burstTime;
								burstTime = processBurstTimes[i];
								
								//increment timeSpent running for each process
								timeSpent[i] += burstTime;
								//if the time is less than one quantum it will finish after one cycle
								processBurstTimes[i] = 0;
							}
							else
							{
								//increment startTime
								startTime += burstTime;
								burstTime = quantum;

								//increment timeSpent running for each process
								timeSpent[i] += burstTime;
								//decrement the processBurstTime by one quantum
								processBurstTimes[i] = processBurstTimes[i] - quantum;
							}
							
							if(timeSpent[i] > 0 && i == 0)
							{
								//if the time spent is greater than 0 and the processNo is 1
								waitTimes[i] = startTime + burstTime - timeSpent[i];
							}
							else if(i > 0 && timeSpent[i] <= quantum && burstTime > 0)
							{
								//if the the processNo is greater than 1 AND
								//the time spent is less than/equal to the quantum and burstTime of current iteration is > 0
								waitTimes[i] = startTime;
							}
							else if (burstTime > 0)
							{
								//if the burstTime is > 0
								//but the process time spent is greater than the quantum
								waitTimes[i] = startTime + burstTime - timeSpent[i];
							}
							else if (burstTime == 0)
							{
								//if the burstTime is = 0 then the process has finished
								//the wait time remains the same
								waitTimes[i] = waitTimes[i];
							}
							
							if(burstTime > 0)
							System.out.printf("%10d %10d %10d %10d\n", process.getProcessNo(), startTime, burstTime, waitTimes[i]);							
							
							//Decrement the totalBurstTime
							totalBurstTime = totalBurstTime - burstTime;
							i++;
						} //end for
						
						//separate iterations in the console
						System.out.println("================================================");
					} //end while
					
					int totalWaitTime = 0;
					float averageWaitTime;
					
					for(i = 0; i < totalProcesses; i++)
					{
						totalWaitTime += waitTimes[i];
					}
					
					System.out.println("\nThe total wait time = " + totalWaitTime);
					
					averageWaitTime = (float)totalWaitTime / totalProcesses;
					System.out.println("\nThe average wait time = " + averageWaitTime);
					break;
				default:
					break;
			}
			
			System.out.println("\nPlease choose a scheduling algorithm: ");
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
