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
		//atrandom(.5, 300);
		//roundrobin(1, 500);
		//heteroroundrobin(1, .5, 300, 5, true);
		//heteroroundround check check check
		//for(int i = 1; i<6; i++) {
			//heteroroundrobin(1, .5, 300, i, false, 200, 800);
		//}
		//probabilisticmodel(1, .5, 300, .62, 200, 800);
		double i = .41;
		while(i<.7) {
			System.out.println(i);
			probabilisticmodel(1, .5, 300, i, 200, 800);
			i = i+.01;
		}
		
	
		
	}
	
	public static double probabilisticmodel(double fast, double slow, double poi, double probability, int numfast, int numslow) {
		//intermission describes the number of servers before inputting 1 of the other kind
		//morefast says if we want to go Fast Fast Fast Slow or Slow Slow Slow Fast
		
		
		ArrayList<server> faster = new ArrayList<server>();
		ArrayList<server> slower = new ArrayList<server>();

		for(int i = 0; i < numfast; i++) { //declaring x fast servers
			faster.add(new server(fast));
		}
		for(int i = 0; i < numslow; i++) { //declaring y slow servers
			slower.add(new server(slow));
		}
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		int serverremoved = 0;
		int nextFastServer = 0;
		int nextSlowServer = 0;
		Random r = new Random();
		
		double[][]next_departures_fast = new double[numfast][2];
		double[][]next_departures_slow = new double[numslow][2];
		//just keep track of the nextdeparturefast nextdepartureslow
		
		for(int i = 0; i<numfast; i++) {//initialize the next fast server's list
			next_departures_fast[i][0] = Integer.MAX_VALUE;
			next_departures_fast[i][1] = i; //keeps track of which server it is in
		}
		
		for(int i = 0; i<numslow; i++) {//initialize the next slow server's list
			next_departures_slow[i][0] = Integer.MAX_VALUE;
			next_departures_slow[i][1] = i; //keeps track of which server it is in
		}
		
		
		
		
		for(int i = 0; i < 1000000; i++) {

			/*
			 * How this needs to work:
			 * Sort the events and have an idea of when the next departure is and what server it is in
			 * Compare next arrival with the first value in this array
			 * If next arrival < next departure then give it to the next array and increment time
			 * If next departure < next arrival then take away and increment time		 
			 */
			//really just need to insert if need be
			
			//deal with arrival according to some process - it's this process that will always change
			if(nextarrival < next_departures_fast[0][0] && nextarrival < next_departures_slow[0][0]){
				//generate random uniform variable
				time = nextarrival;
				
				if(r.nextFloat() > probability) {
					//give to fast (round robin)
					nextFastServer = (nextFastServer + 1)%numfast;
					faster.get(nextFastServer).addto(time, nextarrival);
					for(int j = 0; j < numfast; j++) {
						if(next_departures_fast[j][1] == nextFastServer) {
							next_departures_fast[j][0] = faster.get(nextFastServer).getnextdeparture();	
						}	
					}
				}
				else{
					nextSlowServer = (nextSlowServer + 1)%numslow;
					slower.get(nextSlowServer).addto(time, nextarrival);
					for(int j = 0; j < numslow; j++) {
						if(next_departures_slow[j][1] == nextSlowServer) {
							next_departures_slow[j][0] = slower.get(nextSlowServer).getnextdeparture();	
						}	
					}
					//give to slow (round robin)
				}
				
				nextarrival = time + getexp(poi);
			}
			
			//deal with departures - this part will be the same for all processes
			else {
				//System.out.println("Next Departures Fast: " + next_departures_fast[0][0]);
				//System.out.println("Next Departures Slow: " + next_departures_slow[0][0]);
				if(next_departures_fast[0][0] < next_departures_slow[0][0]) {
					//System.out.println("yellow");
					serverremoved = (int)next_departures_fast[0][1];
					time = faster.get(serverremoved).getnextdeparture();
					//System.out.print("Fast ");
					double timetakenforitem = time - faster.get(serverremoved).removefrom(time);
					next_departures_fast[0][0] = faster.get(serverremoved).getnextdeparture();
					removeditems++;
					movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				}
				else {
					//System.out.println("green");
					serverremoved = (int)next_departures_slow[0][1];
					time = slower.get(serverremoved).getnextdeparture();
					//System.out.println(next_departures_slow[0][1]);
					//System.out.print("Slow ");
					double timetakenforitem = time - slower.get(serverremoved).removefrom(time);
					next_departures_slow[0][0] = slower.get(serverremoved).getnextdeparture();
					removeditems++;
					movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				}
				
			}
			
			//get next departures for fast and slow
			sortbyColumn(next_departures_fast, 0);
			sortbyColumn(next_departures_slow, 0);

			
			
			/*
			 System.out.println("Next Departure: " + next_departures_list[0][0]);
			 System.out.println("Next Arrival: " + nextarrival);
			 System.out.println("Time: " + time);
			 System.out.println("--------");
					*/
		}

		//System.out.println(removeditems);
		System.out.println(movingavg);
		return 0;
	}
	//time to try the probabilistic one
	
	
	
	
	
	public static double heteroroundrobin(double fast, double slow, double poi, int intermission, boolean morefast, int numfast, int numslow) {
		//intermission describes the number of servers before inputting 1 of the other kind
		//morefast says if we want to go Fast Fast Fast Slow or Slow Slow Slow Fast
		
		
		ArrayList<server> faster = new ArrayList<server>();
		ArrayList<server> slower = new ArrayList<server>();

		for(int i = 0; i < numfast; i++) { //declaring x fast servers
			faster.add(new server(fast));
		}
		for(int i = 0; i < numslow; i++) { //declaring y slow servers
			slower.add(new server(slow));
		}
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		int serverremoved = 0;
		int nextFastServer = 0;
		int nextSlowServer = 0;
		int intermissioncounter = intermission;
		
		double[][]next_departures_fast = new double[numfast][2];
		double[][]next_departures_slow = new double[numslow][2];
		//just keep track of the nextdeparturefast nextdepartureslow
		
		for(int i = 0; i<numfast; i++) {//initialize the next fast server's list
			next_departures_fast[i][0] = Integer.MAX_VALUE;
			next_departures_fast[i][1] = i; //keeps track of which server it is in
		}
		
		for(int i = 0; i<numslow; i++) {//initialize the next slow server's list
			next_departures_slow[i][0] = Integer.MAX_VALUE;
			next_departures_slow[i][1] = i; //keeps track of which server it is in
		}
		
		
		
		
		for(int i = 0; i < 1000000; i++) {

			/*
			 * How this needs to work:
			 * Sort the events and have an idea of when the next departure is and what server it is in
			 * Compare next arrival with the first value in this array
			 * If next arrival < next departure then give it to the next array and increment time
			 * If next departure < next arrival then take away and increment time		 
			 */
			//really just need to insert if need be
			
			//deal with arrival according to some process - it's this process that will always change
			if(nextarrival < next_departures_fast[0][0] && nextarrival < next_departures_slow[0][0]){
				time = nextarrival;
				if(morefast) {
					if(intermissioncounter > 0) {
						//give to a fast
						nextFastServer = (nextFastServer + 1)%numfast;
						faster.get(nextFastServer).addto(time, nextarrival);
						for(int j = 0; j < numfast; j++) {
							if(next_departures_fast[j][1] == nextFastServer) {
								next_departures_fast[j][0] = faster.get(nextFastServer).getnextdeparture();	
							}	
						}
						intermissioncounter--;		
					}
					else {
						//give to a slow
						nextSlowServer = (nextSlowServer + 1)%numslow;
						slower.get(nextSlowServer).addto(time, nextarrival);
						for(int j = 0; j < numslow; j++) {
							if(next_departures_slow[j][1] == nextSlowServer) {
								next_departures_slow[j][0] = slower.get(nextSlowServer).getnextdeparture();	
							}	
						}
						intermissioncounter = intermission;
					}
				}
				else {
					if(intermissioncounter > 0) {
						//give to a slow
						nextSlowServer = (nextSlowServer + 1)%numslow;
						slower.get(nextSlowServer).addto(time, nextarrival);
						for(int j = 0; j < numslow; j++) {
							if(next_departures_slow[j][1] == nextSlowServer) {
								next_departures_slow[j][0] = slower.get(nextSlowServer).getnextdeparture();	
							}	
						}
						intermissioncounter--;
							
					}
					else {
						//give to a fast
						nextFastServer = (nextFastServer + 1)%numfast;
						faster.get(nextFastServer).addto(time, nextarrival);
						for(int j = 0; j < numfast; j++) {
							if(next_departures_fast[j][1] == nextFastServer) {
								next_departures_fast[j][0] = faster.get(nextFastServer).getnextdeparture();	
							}	
						}
						intermissioncounter = intermission;	
					}
					
				}
				
				nextarrival = time + getexp(poi);
			}
			
			//deal with departures - this part will be the same for all processes
			else {
				//System.out.println("Next Departures Fast: " + next_departures_fast[0][0]);
				//System.out.println("Next Departures Slow: " + next_departures_slow[0][0]);
				if(next_departures_fast[0][0] < next_departures_slow[0][0]) {
					//System.out.println("yellow");
					serverremoved = (int)next_departures_fast[0][1];
					time = faster.get(serverremoved).getnextdeparture();
					//System.out.print("Fast ");
					double timetakenforitem = time - faster.get(serverremoved).removefrom(time);
					next_departures_fast[0][0] = faster.get(serverremoved).getnextdeparture();
					removeditems++;
					movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				}
				else {
					//System.out.println("green");
					serverremoved = (int)next_departures_slow[0][1];
					time = slower.get(serverremoved).getnextdeparture();
					//System.out.println(next_departures_slow[0][1]);
					//System.out.print("Slow ");
					double timetakenforitem = time - slower.get(serverremoved).removefrom(time);
					next_departures_slow[0][0] = slower.get(serverremoved).getnextdeparture();
					removeditems++;
					movingavg = (movingavg*(removeditems-1) + timetakenforitem)/removeditems;
				}
				
			}
			
			//get next departures for fast and slow
			sortbyColumn(next_departures_fast, 0);
			sortbyColumn(next_departures_slow, 0);

			
			
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
	
	
	
	
	public static double atrandom(double exp, double poi) {
		
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
		
		
		for(int i = 0; i < 1000000; i++) {
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
