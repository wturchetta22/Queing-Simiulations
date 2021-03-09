import java.util.*;
public class sim {

	public static void main(String[] args) {
		//System.out.println("yellow");
		// TODO Auto-generated method stub
	/*
		server fun = new server();
		fun.addto(.5);
		System.out.println(fun.removefrom());
		*/
		System.out.println(tester(6, .5));
		//System.out.println(getexp(2));

	}
	
	public static double tester(double exp, double poi) {
		server holder = new server();
		ArrayList <Double> times = new ArrayList<Double>();
		double nextarrival = getpoi(poi);
		double nextdeparture = getexp(exp);
		double time = 0;
		
		for(int i = 0; i < 100000; i++) {
			if(holder.getlength() == 0) {
				time = time + getpoi(poi);
				holder.addto(time);
				nextarrival = getpoi(poi);
				nextdeparture = getexp(exp);
			}
			
			else if(nextarrival < nextdeparture) {
				time = time + nextarrival;
				holder.addto(time);
				nextdeparture = nextdeparture - nextarrival;
				nextarrival = getpoi(poi);
			}
			
			else if(nextdeparture <= nextarrival) {
				time = time + nextdeparture;
				double timetakenforitem = time - holder.removefrom();
				times.add(timetakenforitem);
				nextarrival = nextarrival - nextdeparture;
				nextdeparture = getexp(exp);
				
			}
			
		}
		
		return getaverage(times);
		
	}
	
	public static double getaverage(List<Double> times) {
		double count = 0;
		
		for(int i = 0; i < times.size(); i++) {
			count += times.get(i);
		}
		return (count/times.size());
		
	}
	
	
	public static double getexp(double mu) {
		Random r = new Random();
		return -(Math.log(1 - r.nextDouble())/(mu));
		
	}
	
	
	
	public static double getpoi(double mean) {
		
	    Random r = new Random();
	    double L = Math.exp(-mean);
	    int k = 0;
	    double p = 1.0;
	    do {
	        p = p * r.nextDouble();
	        k++;
	    } while (p > L);
	    return k - 1;
		
	}
	
	

}
