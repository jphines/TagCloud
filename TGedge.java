
import java.io.Serializable;

public class TGedge implements Serializable, Comparable{
	private int frequency;  
	private String name; 

	public TGedge(String name) {
		this.name = name;
		frequency = 1;
	}
	
	public int getFrequency(){
		return frequency;
    }
	
	public String toString() { 
		return "";
    }
	
	public void addFrequency() {
		frequency++;
	}
        
        public String report(){
            return name +"\n"+ "Frequency: "+frequency;
        }
        
        @Override
        public int compareTo(Object t) {
            int freq1 = this.getFrequency();
            int freq2 = ((TGedge)t).getFrequency();
            return ((Integer)freq2).compareTo((Integer)freq1);
    }
}