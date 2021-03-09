import java.util.*;

public class server {
	
	ArrayList<Double> holder;
	
	public server() {
		holder = new ArrayList<Double>();
	}

	public void addto(double time) {
		holder.add(time);
	}
	
	public double removefrom() {
		return holder.remove(0);
	}
	
	public int getlength() {
		return holder.size();
	}
	
	
	//two methods -> one in order to add to the list and one to take away

}
