import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


public class TagCloudGraph implements Serializable{
	
	//Instance Variables
	public DirectedSparseGraph< TGnode , TGedge > g;
	public Hashtable<String, TGnode> nodes;
	public Hashtable<String, TGedge> edges;
	public ArrayList<String> urls;
    
	//Constructor
	public TagCloudGraph( ArrayList<String> URLlist) throws IOException {
    	g = new DirectedSparseGraph< TGnode , TGedge> ();
    	nodes = new Hashtable<String, TGnode>();
    	edges = new Hashtable<String, TGedge>();
    	urls = URLlist;
    	
    	for( int x = 0; x < urls.size() ; x++ ){
    	
    		String url = urls.get(x);
    		Document doc1 = Jsoup.connect(url).get();
    		Element body = doc1.body();
    		String bodyText = body.text();
    		bodyText = bodyText.toLowerCase();					//Converts to lower case
    		bodyText = bodyText.replaceAll("[^a-z ]", "");  //Removes all non a-z characters
    		bodyText = bodyText.replaceAll("\\s+", " ");  //Condenses all more than one white space characters into one
    		String[] words = bodyText.split(" ");
		
    		TGnode urlNode = new TGnode(url);
    		nodes.put(url, urlNode);
    		g.addVertex(urlNode);
    		
    		for( int i = 0; i < words.length ; i++){
    			
    			if(nodes.containsKey( words[i] ) ){
    				TGnode returned_node = (TGnode) nodes.get( words[i] );
    				returned_node.addFreqency();
    				String edgekey = url+"->"+words[i];
    				if( edges.containsKey( edgekey ) ){
    					TGedge returned_edge = (TGedge) edges.get( edgekey);
    				}else{
    					TGedge temp = new TGedge( edgekey );
    					edges.put(edgekey, temp);
                                        System.out.println(edgekey);
    					g.addEdge( temp, urlNode, returned_node);
    					returned_node.addUrl(url);
    				}
    			}else{
    				TGnode temp = new TGnode(words[i]);
    				nodes.put(words[i], temp);
    				g.addVertex(temp);
    				TGedge temp1 = new TGedge(url+words[i]);
    				edges.put(url+"->"+ words[i], temp1);
    				g.addEdge(temp1, urlNode, temp);
    				temp.addUrl(url);
    			}
    		}
    		
    		for (int n = 0 ; n < words.length - 1 ; n++){
    			
    			String edgekey = words[n]+"->"+words[n+1];
    			if( edges.containsKey( edgekey ) ){
    				TGedge returned_edge = (TGedge) edges.get( edgekey);
    				returned_edge.addFrequency();
    			}else{
    				TGnode first_node =  (TGnode) nodes.get( words[n]);
    				TGnode second_node = (TGnode) nodes.get( words[n+1]);
    				TGedge temp = new TGedge( edgekey);
                                System.out.println(edgekey);
    				g.addEdge( temp, first_node, second_node );
    				first_node.addOut(words[n+1]);
    				second_node.addIn(words[n]);
    			}
    		}
            }
	}
	
	//Search Node method
	public String searchNode(String response){
            String answer;
		if( nodes.containsKey(response)){
    		TGnode temp = nodes.get(response);
    		answer = temp.report();
            }else{
    		answer = "Sorry, that word was not found";
            }
            return answer;
        }
        
        public String filter(int amount){
            ArrayList list = new ArrayList(nodes.entrySet());
            
            Collections.sort(list, new entryCompare());
           
            String answer = "";
            //Entries in list are objects, cast as Map.Entry, which then gets getValue()
            //called, which then casts that result as TGnode, which has .report, which returns string
            // which contains information about given node.
            for(int i = 0; i<amount; i++)
                answer +=
                      ((TGnode)((Map.Entry)list.get(i)).getValue()).report()
                        + "\n\n"; 
           
            return answer;
        }
        
        public String filterPhrase(int amount){
            ArrayList list = new ArrayList(edges.entrySet());
            
            Collections.sort(list, new phraseCompare());
           
            String answer = "";
            //Entries in list are objects, cast as Map.Entry, which then gets getValue()
            //called, which then casts that result as TGnode, which has .report, which returns string
            // which contains information about given node.
            for(int i = 0; i<amount; i++)
                answer +=
                      ((TGedge)((Map.Entry)list.get(i)).getValue()).report()
                        + "\n\n"; 
           
            return answer;
        }
        
        public static class entryCompare implements Comparator{
            
        @Override
            public int compare(Object o1, Object o2){
                Map.Entry e1 = (Map.Entry)o1;
                Map.Entry e2 = (Map.Entry)o2;
                
                TGnode n1 = (TGnode)e1.getValue();
                TGnode n2 = (TGnode)e2.getValue();
                return n1.compareTo(n2);
            }
        }
        
        public static class phraseCompare implements Comparator{
            
        @Override
            public int compare(Object o1, Object o2){
                Map.Entry e1 = (Map.Entry)o1;
                Map.Entry e2 = (Map.Entry)o2;
                
                TGedge n1 = (TGedge)e1.getValue();
                TGedge n2 = (TGedge)e2.getValue();
                return n1.compareTo(n2);
            }
        }
}