//Poissont and exp methods taken from code on the internet
 /*    - See http://www.honeylocust.com/RngPack/ for an industrial
 *      strength random number generator in Java.
 @author Robert Sedgewick
 *  @author Kevin Wayne
 */
//@author Dagim Belete

import java.util.*;

public class Sim
{

	
	public static ArrayList<Double> bucket = new ArrayList<Double>(); //Stores the time taken for each job collected from all servers
	public static Random random = new Random();

	public static void main(String[] args){
		Server[] s = new Server[1000];//Create an array of servers and populate with 1000 server with a default constructor value of mu = 0.4
		for(int i = 0; i <s.length; i++){
			s[i] = new Server();
		}

		double lambda = 0.1;
		double at = poisson(lambda);
		//iterate 100000 jobs and place each job into a random server (Simplest implementation)
		//arrival time of jobs is poisson distributed
		for(int i = 0; i < 100000; i++){
			int index = random.nextInt(1000);
			s[index].add(new Job(i,at));
			at += poisson(lambda);
		}
		for (Server pl:s){ //After the jobs have been allocated run each surver (explained in Server.java)
			pl.run();
		}

		double sum = 0;
		for (double d:bucket){
			sum+=d;
		}
		double avg = sum/bucket.size();  //Calculate the mean response time over all jobs
		
		System.out.println(avg);

	}

	/*public static double sim(Server s, double lambda, double mu, int testAmount){
		ArrayList<Double> temp = new ArrayList<Double>();
		double at = poisson(lambda);
		double st = 0;
		double dt = Double.POSITIVE_INFINITY;
		double time = 0;
		
		
		for(int i = 0; i <= testAmount; i++){

			
			//event is an arrival
			if(at<=dt){
				
					
				if (s.isEmpty()){
					
					dt = at + exp(mu);

				}
				//System.out.println("at in: " + at);
				//System.out.println("st in: " + st);
				//System.out.println("dt before" + dt);
			    s.add(new Job(i,at));
				at += poisson(lambda);
				
				
		}
			// event is a departure
			else {

				
				Job j = s.remove();

				double tempt = dt - j.arrivalTime;
				//System.out.println("Tempt: " + tempt);
				//System.out.println("dt: " + dt);
				//System.out.println("ats: " + j.arrivalTime);
				temp.add(tempt);
				if (s.isEmpty()){
					dt = Double.POSITIVE_INFINITY;		
					
				}
				else{
					dt += exp(mu);
				}
				
				
				

			}
			
			
		}
		double sum = 0;
		for (double d:temp){
			sum+=d;
		}
		return sum/temp.size();
	}
	*/
	//Algorithm for generating poisson distribution ints from the internet.
	public static double poisson(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        if (Double.isInfinite(lambda))
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        // using algorithm given by Knuth
        // see http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        lambda = 1/lambda;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= expLambda);
        return k-1;
    }
   
    public static double uniform() {
        return random.nextDouble();
    }



}