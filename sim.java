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
		//System.out.println(getpoi(100));
		//System.out.println(tester(1, .4));
		//System.out.println(getexp(2));
		//System.out.println(getavg(1));
		//atrandom(1, 500);
		//roundrobin(1, 500);
	
		
	}
	
	public static double atrandom(double exp, double poi) {
		server holder = new server(exp);
		
		ArrayList<server> servers = new ArrayList<server>();
		for(int i = 0; i < 1000; i++) { //declaring 1000 servers
			servers.add(new server(exp));
		}
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		Random r = new Random();
		int serverremoved = 0;
		int nextServer = 0;
		
		double[][]next_departures_list = new double[1000][2];
		for(int i = 0; i<1000; i++) {
			next_departures_list[i][0] = Integer.MAX_VALUE;
			next_departures_list[i][1] = i; //keeps track of which server it is in
		}
		
		
		for(int i = 0; i < 100000; i++) {
			/*
			 * How this needs to work:
			 * Sort the events and have an idea of when the next departure is and what server it is in
			 * Compare next arrival with the first value in this array
			 * If next arrival < next departure then give it to the next array and increment time
			 * If next departure < next arrival then take away and increment time		 
			 */
			//really just need to insert if need be
			
			
			if(nextarrival < next_departures_list[0][0]){
				time = nextarrival;
				//this is where a random number between 0-1000 should be made
				nextServer = r.nextInt(1000);
				servers.get(nextServer).addto(time, nextarrival);
				//System.out.println("next departure: " + servers.get(nextServer).getnextdeparture());
				nextarrival = time + getexp(poi);
			}
			
			else {
				serverremoved = (int)next_departures_list[0][1];
				time = servers.get(serverremoved).getnextdeparture();
				double timetakenforitem = time - servers.get(serverremoved).removefrom(time);
				next_departures_list[0][0] = servers.get(serverremoved).getnextdeparture();
				removeditems++;
				movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				
			}
			
			for(int j = 0; j < 1000; j++) {
				if(next_departures_list[j][1] == nextServer) {
					next_departures_list[j][0] = servers.get(nextServer).getnextdeparture();	
				}	
			}
			sortbyColumn(next_departures_list, 0);

			/*
			 System.out.println("Next Departure: " + next_departures_list[0][0]);
			 System.out.println("Next Arrival: " + nextarrival);
			 System.out.println("Time: " + time);
			 System.out.println("--------");
					*/
		}

		System.out.println(removeditems);
		System.out.println(movingavg);
		return 0;
	}
	
	
	
	
	public static double roundrobin(double exp, double poi) {
		server holder = new server(exp);
		
		ArrayList<server> servers = new ArrayList<server>();
		for(int i = 0; i < 1000; i++) { //declaring 1000 servers
			servers.add(new server(exp));
		}
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		Random r = new Random();
		int serverremoved = 0;
		int nextServer = 0;
		
		double[][]next_departures_list = new double[1000][2];
		for(int i = 0; i<1000; i++) {
			next_departures_list[i][0] = Integer.MAX_VALUE;
			next_departures_list[i][1] = i; //keeps track of which server it is in
		}
		
		
		for(int i = 0; i < 100000; i++) {
			/*
			 * How this needs to work:
			 * Sort the events and have an idea of when the next departure is and what server it is in
			 * Compare next arrival with the first value in this array
			 * If next arrival < next departure then give it to the next array and increment time
			 * If next departure < next arrival then take away and increment time		 
			 */
			//really just need to insert if need be
			
			
			if(nextarrival < next_departures_list[0][0]){
				time = nextarrival;
				//this is where a random number between 0-1000 should be made
				nextServer = (nextServer + 1)%1000;
				servers.get(nextServer).addto(time, nextarrival);
				//System.out.println("next departure: " + servers.get(nextServer).getnextdeparture());
				nextarrival = time + getexp(poi);
			}
			
			else {
				serverremoved = (int)next_departures_list[0][1];
				time = servers.get(serverremoved).getnextdeparture();
				double timetakenforitem = time - servers.get(serverremoved).removefrom(time);
				next_departures_list[0][0] = servers.get(serverremoved).getnextdeparture();
				removeditems++;
				movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				
			}
			
			for(int j = 0; j < 1000; j++) {
				if(next_departures_list[j][1] == nextServer) {
					next_departures_list[j][0] = servers.get(nextServer).getnextdeparture();	
				}	
			}
			sortbyColumn(next_departures_list, 0);

			/*
			 System.out.println("Next Departure: " + next_departures_list[0][0]);
			 System.out.println("Next Arrival: " + nextarrival);
			 System.out.println("Time: " + time);
			 System.out.println("--------");
					*/
		}

		System.out.println(removeditems);
		System.out.println(movingavg);
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	public static void sortbyColumn(double arr[][], int col) {
		Arrays.sort(arr, new Comparator<double[]>() { 
            
	          @Override              
	          // Compare values according to columns 
	          public int compare(final double[] entry1,  
	                             final double[] entry2) { 
	  
	            // To sort in descending order revert  
	            // the '>' Operator 
	            if (entry1[col] > entry2[col]) 
	                return 1; 
	            else
	                return -1; 
	          } 
	        });  // 
	}
	
	
	
	
	
	public static double tester(double exp, double poi) {
		server holder = new server(exp);
		
		ArrayList<server> servers = new ArrayList<server>();
		for(int i = 0; i < 1000; i++) { //declaring 1000 servers
			servers.add(new server(exp));
		}
		
		
		
		
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		
		for(int i = 0; i < 1000000; i++) {
			
			if(nextarrival < holder.getnextdeparture()) {
				time = nextarrival;
				holder.addto(time, nextarrival);
				nextarrival = time + getexp(poi);
			}
			
			else {
				time = holder.getnextdeparture();
				double timetakenforitem = time - holder.removefrom(time);
				removeditems++;
				movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
			}
			
			/*
			 * Debugging code
			 * System.out.println("Next Departure: " + holder.getnextdeparture());
			 * System.out.println("Next Arrival: " + nextarrival);
			 * holder.output();
			 * System.out.println("Time: " + time);
			 * System.out.println("--------");
			*/
			
		}
		
		
		//System.out.println(holder.getlength());
		System.out.println(1/(exp - poi));
		return movingavg;
		//return getaverage(times);
		
	}
	
	public static double getaverage(List<Double> times) {
		double count = 0;
		
		for(int i = 0; i < times.size(); i++) {
			//System.out.println(times.get(i));
			count += times.get(i);
		}
		return (count/times.size());
		
	}
	
	
	public static double getexp(double mu) {
		Random r = new Random();
		return -(Math.log(1 - r.nextDouble())/(mu));
		
	}
	
	
	
	public static double getpoi(double mean) {
		
		mean = 1/mean;
		
	    double L = Math.exp(-mean);
	    int k = 0;
	    double p = 1.0;
	    do {
	    	k++;
	    	double u = Math.random();
	        p = p * u;
	        
	    } while (p > L);
	    return k - 1;
		
	}
	
	public static double getavg(double mean) {
		ArrayList<Double> pois = new ArrayList<Double>();
		for(int i = 0; i<1000000; i++) {
			pois.add(getexp(mean));
		}
		return getaverage(pois);
		
		
	}
	
	

}
