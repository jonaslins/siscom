package main.java.reader;

import java.util.HashSet;
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
		Set<String> M = new HashSet<String>();
	
		Q.add("");
		return QT(Q, M);
	}
	
	//QT Original
	public Set<String> QT(Queue<String> Q, Set<String> M){
		//if(Q.length()==96) return M;
		int count = 0;
		while(!Q.isEmpty()){
			String query = Q.remove();
			//bitsTransmittedByReader+=query.length();
			List<String> returnedTags = getTagsFromPrefix(query);
			
			if(returnedTags.size()==1){
				
				//System.out.println(query+"\t"+returnedTags.toArray()[0]);
				M.addAll(returnedTags);
				bitsTransmittedByTags +=96;
								
			}else if(returnedTags.size()>1){
				
				//System.out.println(query+"\tcollision");
				bitsTransmittedByTags += (96 * returnedTags.size());
				Q.add(query+"0");
				Q.add(query+"1");
				
			}else{
				
				//System.out.println(query+"\tno response");
				
			}
			
		}
		
		return M;
	}
	
	//QT RECURSIVE WITHOUT QUEUE
	public Set<String> QT(String Q, Set<String> M ){
		//if(Q.length()==96) return M;
		
		List<String> returnedTags = getTagsFromPrefix(Q);
		
		if(returnedTags.size()==1){
			//System.out.println(Q+"\t"+returnedTags.toArray()[0]);
			M.addAll(returnedTags);
			bitsTransmittedByTags +=96;
		}else if(returnedTags.size()>1){
			//System.out.println(Q+"\tcollision");
			bitsTransmittedByTags += (96 * returnedTags.size());
			QT(Q+"0", M);
			QT(Q+"1", M);
		}else{
			//System.out.println(Q+"\tno response");
		}
		
		return M;
	}

}
