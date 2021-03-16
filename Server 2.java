import java.util.*;

public class Server
{
	public double mu; //mu for a server
	public Deque<Job> queue;//internal queue of a server
	public static Random random;
	// default constructor sets mu to be 0.4
	public Server(){
		queue = new ArrayDeque<Job>();
		mu = 0.4;
		random = new Random();
	}
	//Possible to make servers with specific passed in mu
	public Server(double m){
		queue = new ArrayDeque<Job>();
		mu = m;
		random = new Random();
	}
	//Run goes through the jobs coming into the queue and give each job a service time a
	// and calculates how much time spent in the server
	public void run(){
		double at = queue.poll().arrivalTime; 
		double dt = Double.POSITIVE_INFINITY;
		double time = 0;
		ArrayDeque<Double> temp = new ArrayDeque<Double>(); //temporary queue used to hold value jobs that have arrived
		
		while(!queue.isEmpty() || !temp.isEmpty()){
			//Event is an arrival
			if(at<=dt){
				if (dt == Double.POSITIVE_INFINITY){
					dt = at+exp(mu);
				}
				temp.add(at);
				at = queue.poll().arrivalTime;
			}
			//Event is a departure
			else{
				double j = temp.poll();
				double tempt = dt - j; //calculate total wait time
				Sim.bucket.add(tempt);// add total time to bucket array list in Sim class
				if (temp.isEmpty()){
					dt = Double.POSITIVE_INFINITY;
				}
				else{
					dt += exp(mu);
				}
			}
		}
	}


	//Methods written for accessing the queue within a server
	public void add(Job a){
		queue.offer(a);
	}
	public Job remove(){
		return queue.poll();
	}
	public Job peek(){
		return queue.peek();
	}
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	//Exponential distribution generator taken from online source
	public static double exp(double mu) {
        if (!(mu > 0.0))
            throw new IllegalArgumentException("mu must be positive: " + mu);
        return -(Math.log(1 - uniform()) / mu);
    }
    public static double uniform() {
        return random.nextDouble();
    }


}