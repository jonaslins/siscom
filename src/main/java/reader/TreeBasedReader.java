package reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class TreeBasedReader {
	
	List<String> tags;
	public int bitsTransmittedByTags=0;
	
	public TreeBasedReader(List<String> tags) {
		this.tags = tags;
	}
	
	public abstract Set<String> findTags();
	
	public List<String> getTagsFromPrefix(String prefix){
		
		if(prefix.equals("")) return tags;
		
		List<String> returnedTags = new ArrayList<String>(1000);
		for (String tag : tags) {
			if(tag.startsWith(prefix)){
				returnedTags.add(tag);
			}
		}
		
		return returnedTags;

	}
	

}
