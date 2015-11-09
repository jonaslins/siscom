package siscom.reader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class QTReader extends TreeBasedReader{
	
	public QTReader(List<String> tags) {
		super(tags);	
	}

	@Override
	public Set<String> findTags() {
		Queue<String> Q = new LinkedList<String>();
	
		Q.add("0");
		Q.add("1");
		return QT(Q);
	}
	
	//QT Original
	public Set<String> QT(Queue<String> Q){
		while(!Q.isEmpty()){
			String query = Q.remove();
			//bitsTransmittedByReader+=query.length();
			List<String> returnedTags = getTagsFromPrefix(query);
			
			if(returnedTags.size()==1){
				
				//System.out.println(query+"\t"+returnedTags.toArray()[0]);
				M.addAll(returnedTags);
				bitsTransmittedByTags += K; //k bits
								
			}else if(returnedTags.size()>1){
				
				//System.out.println(query+"\tcollision");
				bitsTransmittedByTags += (K * returnedTags.size());
				Q.add(query+"0");
				Q.add(query+"1");
				
			}else{
				
				//System.out.println(query+"\tno response");
				
			}
			
		}
		
		return M;
	}
	
	//QT RECURSIVE WITHOUT QUEUE
	public Set<String> QT(String Q){
		//if(Q.length()==96) return M;
		
		List<String> returnedTags = getTagsFromPrefix(Q);
		
		if(returnedTags.size()==1){
			//System.out.println(Q+"\t"+returnedTags.toArray()[0]);
			M.addAll(returnedTags);
			bitsTransmittedByTags +=K;//k bits
			
		}else if(returnedTags.size()>1){
			
			//System.out.println(Q+"\tcollision");
			bitsTransmittedByTags += (K * returnedTags.size());
			QT(Q+"0");
			QT(Q+"1");
			
		}else{
			//System.out.println(Q+"\tno response");
		}
		
		return M;
	}
	
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
