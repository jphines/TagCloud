import java.io.Serializable;
import java.util.ArrayList;

public class TGnode implements Serializable, Comparable{
	int frequency;
    String name;
    ArrayList<String> urls;
    ArrayList<String> connectIn;
    ArrayList<String> connectOut;
    
    public TGnode(String name) {
    	this.name = name;
    	frequency = 1;
    	urls = new ArrayList<String>();
    	connectIn = new ArrayList<String>();
    	connectOut = new ArrayList<String>();
    }
    
    public String toString() {
    	return name+" :"+frequency;      
    }
    
    public int getFrequency(){
    	return frequency;
    }
    
    public void addFreqency(){
    	frequency++;
    }
    
    public void addUrl(String url){
    	urls.add(url);
    }
    
    public void addIn(String in){
    	connectIn.add(in);
    }
    
    public void addOut(String out){
    	connectOut.add(out);
    }
    
    public String report(){
    	String output = new String();
    	output = name + " \n";
    	output += "Frequency: "+frequency+"\n";
    	output += "URLS: ";
    	for(int i = 0; i< urls.size() ; i++)
    		output += urls.get(i)+" ";
    	output += "\nIN's: ";
    	for(int i = 0; i< connectIn.size(); i++)
    		output += connectIn.get(i)+" ";
    	output += "\nOUT's: ";
    	for(int i = 0; i< connectOut.size(); i++)
    		output += connectOut.get(i)+" ";
    	return output;
    }

    @Override
    public int compareTo(Object t) {
        int freq1 = this.getFrequency();
        int freq2 = ((TGnode)t).getFrequency();
        return ((Integer)freq2).compareTo((Integer)freq1);
    }
}

