import java.util.*;

public class server {
	
	ArrayList<Double> holder;
	double mu;
	double nextdeparture;
	
	
	public server(double exp) {
		holder = new ArrayList<Double>();
		mu = exp;
		nextdeparture = Integer.MAX_VALUE;
	}
	

	public boolean addto(double time, double arrival) {
		holder.add(time);
		/*
		if(holder.size() == 1) {//means we need to generate a new departure time
			nextdeparture = time + getexp();
		}
		*/
		return false; //don't generate a new departure time
		
	}
	
	
	
	
	
	public void output() {
		System.out.print("Current Line: ");
		for(int i = 0; i < holder.size(); i++) {
			System.out.print(holder.get(i) + ", ");
		}
		System.out.println();
	}
	
	
	public double removefrom(double time) {
		if(holder.size() == 1) {
			nextdeparture = Integer.MAX_VALUE;
		}
		else {
			nextdeparture = time + getexp();
		}
		//output();
		//System.out.println(nextdeparture);
		
		return holder.remove(0);
	}
	
	public void setnextdeparture(double time, double var) {
		if(holder.size() == 1) {
			nextdeparture = time + var;
		}
	}
	
	public double removefromTest(double time, double exp) {
		if(holder.size() == 1) {
			nextdeparture = Integer.MAX_VALUE;
		}
		else {
			nextdeparture = time + exp;
		}
		//output();
		//System.out.println(nextdeparture);
		
		return holder.remove(0);
	}
	
	
	
	public double getnextdeparture() {
		return nextdeparture;
	}
	
	public int getlength() {
		return holder.size();
	}
	
	public double getexp() {
		
		
		Random r = new Random();
		return -(Math.log(1 - Math.random())/(mu));
		
	}
	
	
	//two methods -> one in order to add to the list and one to take away

}
