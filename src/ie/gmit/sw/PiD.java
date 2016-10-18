package ie.gmit.sw;

public class PiD implements Comparable<PiD> {

	private int processNo;
	private int processBurstTime;
		
	public PiD(int processNo, int processBurstTime) 
	{
		super();
		this.processNo = processNo;
		this.processBurstTime = processBurstTime;
	}
	
	public int getProcessNo()
	{
		return processNo;
	}


	public void setProcessNo(int processNo) 
	{
		this.processNo = processNo;
	}


	public int getProcessBurstTime() 
	{
		return processBurstTime;
	}


	public void setProcessBurstTime(int processBurstTime) 
	{
		this.processBurstTime = processBurstTime;
	}

	@Override
	public int compareTo(PiD p)
	{
		int compareTime = ((PiD) p).getProcessBurstTime();
		return this.processBurstTime - compareTime;
	}
	
	@Override
	public String toString()
	{
		return "[Process Number] = " + this.processNo + " [Process Burst Time] = " + this.processBurstTime;	
	}



}
