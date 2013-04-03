
public class Outcast {

	private WordNet worknet;
	
    public Outcast(WordNet wordnet) {
    	this.worknet = wordnet;
    }
    
    public String outcast(String[] nouns) {
        
    	String outcast = null;
    	int max = 0;
    	
    	for(String noun : nouns) {
    		int distance = 0;
    		for(String noun2 : nouns) {
    			if(noun != noun2) {
    				distance += this.worknet.distance(noun, noun2);
    			}
    		}
    		
    		if(distance > max) {
    			max = distance;
    			outcast = noun;
    		}
    	}
  
        return outcast;
    }
    
    public static void main(String[] args) {
        
    	Outcast out = new Outcast(new WordNet("synsets.txt", "hypernyms.txt"));
    	String[] nouns = new String[]{"horse", "zebra", "cat", "bear", "table"};
    	
    	System.out.println(out.outcast(nouns));
    }
}
