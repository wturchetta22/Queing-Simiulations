import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class sim {

	public static void main(String[] args) throws IOException {
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

		//things we need: lambda (easy), MRT (easy), QF (easy), QS (easy), R, Policy
		/*
		FileWriter writer = new FileWriter("test.csv");
		writer.append("lambda");
		writer.append(",");
		writer.append("MRT");
		writer.append(",");
		writer.append("QF");
		writer.append(",");
		writer.append("R");
		writer.append(",");
		writer.append("Probability");
		writer.append("\n");
		
		for(int i = 4; i<10; i++) {
			
			double MRT = heteroroundrobin(1.2*.909090, .909090, 200, i, true, 500, 500);
				
			List<List<String>> rows = Arrays.asList(
					Arrays.asList(".2", Double.toString(MRT), ".5", "2", String.valueOf(i), "false")
					);
			for (List<String> rowData : rows) {
			    writer.append(String.join(",", rowData));
			    writer.append("\n");
			}
			
			/*
			MRT = heteroroundrobin(1, .83333, 800, i, true, 500, 500);
			rows = Arrays.asList(
					Arrays.asList(".2", Double.toString(MRT), ".5", "2", String.valueOf(i), "true")
					);
			for (List<String> rowData : rows) {
			    writer.append(String.join(",", rowData));
			    writer.append("\n");
			}
			
		}
		
		FileWriter writer = new FileWriter("test.csv");
		writer.append("lambda");
		writer.append(",");
		writer.append("MRT");
		writer.append(",");
		writer.append("QF");
		writer.append(",");
		writer.append("R");
		writer.append(",");
		writer.append("Probability");
		writer.append("\n");
		
		double i = .01;
		while(i<.03) {
			System.out.println(i);
			double MRT = probRoundJoinModel(.90909*1.2, .90909, 200, i, 500, 500);
			List<List<String>> rows = Arrays.asList(
					Arrays.asList(".2", Double.toString(MRT), ".2", "2", Double.toString(i))
					);
			for (List<String> rowData : rows) {
			    writer.append(String.join(",", rowData));
			    writer.append("\n");
			}
			i = i+.01;
		}
		
		/*
		
	
		double i = .1;
		while(i<.25) {
			System.out.println(i);
			double MRT = probabilisticmodel(.862*1.2, .862, 800, i, 800, 200);
			List<List<String>> rows = Arrays.asList(
					Arrays.asList(".2", Double.toString(MRT), ".2", "2", Double.toString(i))
					);
			for (List<String> rowData : rows) {
			    writer.append(String.join(",", rowData));
			    writer.append("\n");
			}
			i = i+.01;
		}
		
		writer.flush();
		writer.close();
	*/
		
		
		//speeds(2, .8);
		
		samplepathproof(1, 1.4);
		
		
	}
	public static double speeds(double r, double qf) {
		
		//qf*mf + (1-qf)*ms = 1
		//mf = r*ms
		//ms(r*qf +(1-qf))
		double ms = 1.0/(r*qf + (1-qf));
		double a = asrandom(r*ms, ms, 200, (int)(qf*1000), (int)((1-qf)*1000));
		System.out.println(a);
		double b = asrandom(r*ms, ms, 500, (int)(qf*1000), (int)((1-qf)*1000));
		System.out.println(b);
		double c = asrandom(r*ms, ms, 800, (int)(qf*1000), (int)((1-qf)*1000));
		System.out.println(c);
		
		return 1.0/(r*qf + (1-qf));
	}
	
	
	
	public static double samplepathproof(double exp, double poi) {
		
		ArrayList<server> servers = new ArrayList<server>();
		for(int i = 0; i < 4; i++) { //declaring 4 servers - all the same speed
			servers.add(new server(exp));
		}
		
		double nextarrival = getexp(poi);
		double time = 0;
		double movingavg = 0;
		double removeditems = 0;
		Random r = new Random();
		int serverremoved = 0;
		int nextServer = 0;
		double mrtA = 0;
		double mrtB = 0;
		double removedA = 0;
		double removedB = 0;
		
		double[][]next_departures_list = new double[4][2];
		for(int i = 0; i<4; i++) {
			next_departures_list[i][0] = Integer.MAX_VALUE;
			next_departures_list[i][1] = i; //keeps track of which server it is in
		}
		
		ArrayList<Double> departure1 = new ArrayList<Double>(); //holds departure times for the nth job come in for servers 0,2
		ArrayList<Double> departure2 = new ArrayList<Double>(); //holds departure times for the nth job to come in for servers 1,3
		
		for(int i = 0; i<20000; i++) {
			departure1.add(getexp(exp));
			departure2.add(getexp(exp));
		}
		
		int numjobs0 = 0;
		int numjobs1 = 0;
		int numjobs2 = 0;
		int numjobs3 = 0;
		boolean changedyet = false;
		boolean Aempty = false;
		boolean Bempty = false;
		boolean start = false;
		
		
		
		
		for(int k = 0; k < 1000; k++) {
			System.out.println("server 0 length: " + servers.get(0).getlength());
			System.out.println("server 1 length: " + servers.get(1).getlength());
			System.out.println("server 2 length: " + servers.get(2).getlength());
			System.out.println("server 3 length: " + servers.get(3).getlength());



			if(start) {
				if(servers.get(0).getlength() == 0 && servers.get(2).getlength() == 0) {
					//System.out.println("here");
					numjobs0 = numjobs2;
					Aempty = true;
				}
				if(servers.get(1).getlength() == 0 && servers.get(3).getlength() == 0) {
					numjobs1 = numjobs3;
					Bempty = true;
				}
			}
			
			
			if(start && Aempty && Bempty) {
				System.out.println("broken");
				break;
			}
			
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
				//nope this is where a decision should be made if server one or two
				//ok so this works for them being coupled
				
				double nextDepartureTime;
				
				nextServer = (nextServer + 1)%2;
				if(nextServer == 0) {
					numjobs0++;
					nextDepartureTime = departure1.get(numjobs0);

				}
				else {
					numjobs1++;
					nextDepartureTime = departure2.get(numjobs1);
				}
				
				servers.get(nextServer).addto(time, nextarrival);
				servers.get(nextServer).setnextdeparture(time, nextDepartureTime);
				
				if(k>5 && changedyet == false) {
					System.out.println("Divergent");
					start = true;
					removedA = 0;
					removedB = 0;
					if(nextServer == 0) {
						servers.get(nextServer + 3).addto(time, nextarrival);
						numjobs3++;
						nextDepartureTime = departure2.get(numjobs3);
						servers.get(nextServer + 3).setnextdeparture(time, nextDepartureTime);
						
					}
					else if(nextServer == 1) {
						servers.get(nextServer + 1).addto(time, nextarrival);
						numjobs2++;
						nextDepartureTime = departure1.get(numjobs2);
						servers.get(nextServer + 1).setnextdeparture(time, nextDepartureTime);
					}
					changedyet = true;			
				}
				
				
				
				else {
					if(nextServer == 0) {
						numjobs2++;
						nextDepartureTime = departure1.get(numjobs2);
					}
					else {
						numjobs3++;
						nextDepartureTime = departure2.get(numjobs3);
					}
					
					servers.get(nextServer + 2).addto(time, nextarrival);
					servers.get(nextServer + 2).setnextdeparture(time, nextDepartureTime);
				}
				
				
				//System.out.println("next departure: " + servers.get(nextServer).getnextdeparture());
				nextarrival = time + getexp(poi);
			}
			
			else {
				
				serverremoved = (int)next_departures_list[0][1];
				System.out.println("Server Removed " + serverremoved);
				if(serverremoved == 0) {
					numjobs0++;
				}
				else if(serverremoved == 1) {
					numjobs1++;
				}
				else if(serverremoved == 2) {
					numjobs2++;
				}
				else if(serverremoved == 3) {
					numjobs3++;
				}

				
				if(serverremoved < 2) {
					time = servers.get(serverremoved).getnextdeparture();
					double nexttime = 0;
					if(serverremoved == 0) {
						nexttime = departure1.get(numjobs0);
						System.out.println(numjobs0);

					}
					else {
						nexttime = departure2.get(numjobs1);
						System.out.println(numjobs1);

					}
					double timetakenforitem = time - servers.get(serverremoved).removefromTest(time, nexttime);
					System.out.println("A " + serverremoved);
					System.out.println(timetakenforitem);
					System.out.println("________");
					next_departures_list[0][0] = servers.get(serverremoved).getnextdeparture();
					removedA++;
					mrtA = (mrtA*(removedA-1) + timetakenforitem)/removedA;

					
				}
				else {
					
					time = servers.get(serverremoved).getnextdeparture();
					double nexttime = 0;
					if(serverremoved == 2) {
						nexttime = departure1.get(numjobs2);
						System.out.println(numjobs2);
					}
					else {
						nexttime = departure2.get(numjobs3);
						System.out.println(numjobs3);

					}
					double timetakenforitem = time - servers.get(serverremoved).removefromTest(time, nexttime);
					System.out.println("B " + serverremoved);
					System.out.println(timetakenforitem);
					
					System.out.println("________");
					next_departures_list[0][0] = servers.get(serverremoved).getnextdeparture();
					removedB++;
					mrtB = (mrtB*(removedB-1) + timetakenforitem)/removedB;
					
				}
				
			}
			
			
			for(int j = 0; j < 4; j++) {
				if(next_departures_list[j][1] == nextServer) {
					next_departures_list[j][0] = servers.get(nextServer).getnextdeparture();	
				}
				
				else if(next_departures_list[j][1] == nextServer + 2) {
					next_departures_list[j][0] = servers.get(nextServer + 2).getnextdeparture();	
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

		//System.out.println(removeditems);
		System.out.println(mrtA);
		System.out.println(mrtB);
		return 0;
		
	}
	
	
	
	
	public static double asrandom(double fast, double slow, double poi, int numfast, int numslow) {
		//Probabilistically Choose the class of servers then when in each class run round robin in each class and take the round
				//Robin server as a candidate with two other randomly queried servers, join the shortest queue out of those three servers
				
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
				int[] candidates = new int[3]; //holds the index of servers first being candidate from round robin other 4 randomly selected
				int queueLen = 0; //to keep track of queue length for join the shortest queue implementation
				int nextFastServer = 0;
				int rrFastServer = 0; // keeps track of round robin index in the fast servers
				int nextSlowServer = 0;
				int rrSlowServer = 0; // keeps track of round robin index in the slow servers
				Random r = new Random();
				double probability = (numfast*1.0)/1000;
				
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

						
						if(r.nextFloat() < probability) {
							//give to fast (round robin)
							
							
							nextFastServer = r.nextInt(numfast);
							faster.get(nextFastServer).addto(time, nextarrival);
							for(int j = 0; j < numfast; j++) {
								if(next_departures_fast[j][1] == nextFastServer) {
									next_departures_fast[j][0] = faster.get(nextFastServer).getnextdeparture();	
								}	
							}
						}
						else{
							
							nextSlowServer = r.nextInt(numslow);
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

				System.out.println(movingavg);
				return movingavg;
				//return 0;
		
	}
	
	
	
	public static double probRoundJoinModel(double fast, double slow, double poi, double probability, int numfast, int numslow) {
		//Probabilistically Choose the class of servers then when in each class run round robin in each class and take the round
		//Robin server as a candidate with two other randomly queried servers, join the shortest queue out of those three servers
		
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
		int[] candidates = new int[3]; //holds the index of servers first being candidate from round robin other 4 randomly selected
		int queueLen = 0; //to keep track of queue length for join the shortest queue implementation
		int nextFastServer = 0;
		int rrFastServer = 0; // keeps track of round robin index in the fast servers
		int nextSlowServer = 0;
		int rrSlowServer = 0; // keeps track of round robin index in the slow servers
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
					queueLen = Integer.MAX_VALUE;
					rrFastServer = (rrFastServer + 1)%numfast;
					candidates[0] = rrFastServer;
					for (int q = 1; q<3; q++){
						candidates[q] = r.nextInt(numfast);
					}
					for(int a: candidates){ //join shortest queue one from round robin two others randomly queued
						if(faster.get(a).getlength() < queueLen){
							queueLen = faster.get(a).getlength();
							nextFastServer = a;
						}
					}
					faster.get(nextFastServer).addto(time, nextarrival);
					for(int j = 0; j < numfast; j++) {
						if(next_departures_fast[j][1] == nextFastServer) {
							next_departures_fast[j][0] = faster.get(nextFastServer).getnextdeparture();	
						}	
					}
				}
				else{
					queueLen = Integer.MAX_VALUE;
					rrSlowServer = (rrSlowServer + 1)%numslow;
					candidates[0] = rrSlowServer;
					for (int q = 1; q<3; q++){
						candidates[q] = r.nextInt(numslow);
					}
					for(int a: candidates){ //join shortest queue one from round robin two others randomly selected servers
						if(slower.get(a).getlength() < queueLen){
							queueLen = slower.get(a).getlength();
							nextSlowServer = a;
						}
					}
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

		System.out.println(movingavg);
		return movingavg;
		//return 0;
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
		return movingavg;
	}
	//time to try the probabilistic one
	
	
	
	
	
	public static double heteroroundrobin(double fast, double slow, double poi, int intermission, boolean morefast, int numfast, int numslow) {
		//fast is how fast the fast servers process items
		//slow similar
		//poi is how fast they arrive
		
		
		
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

		//System.out.println(removeditems);
		System.out.println(movingavg);
		return movingavg;
		
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
