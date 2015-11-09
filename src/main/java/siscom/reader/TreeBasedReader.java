package siscom.reader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TreeBasedReader {
	
	static int K = 96; //K bits
	
	List<String> tags;
	Set<String> M = new HashSet<String>();
	public int bitsTransmittedByTags=0;
	
	
	
	public TreeBasedReader(List<String> tags) {
		this.tags = tags;
	}
	
	public abstract Set<String> findTags();
		

}
