import java.util.*;
public class Job
{
	public int jobId;
	public double arrivalTime;
	//Job class with jobId and arrival time

	public Job()
	{
		jobId = 0;
		arrivalTime = 0;
		
	}
	public Job(int id, double at){
		jobId = id;
		arrivalTime = at;
		
	}

}