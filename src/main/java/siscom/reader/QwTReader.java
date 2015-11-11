package siscom.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class QwTReader extends TreeBasedReader{

	public QwTReader(List<String> tags) {
		super(tags);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> findTags() {
		// TODO Auto-generated method stub
		Stack<String> S = new Stack<String>();
		
		/*S.push("1");
		S.push("0");
		QwT(S, M);*/
		QwT("0", 1);
		QwT("1", 1);
		return M;
		
	}
	//ITERATIVE
	private Set<String> QwT(Stack<String> S, Set<String> M){
		int ws = 1;
		int k = 96;
		int t = 0;
		while(!S.isEmpty()){
		String query = S.pop();		
		List<String> returnedWs = getWsFromPrefix(query, ws);
		steps++;
		bitsTransmittedByReader+= query.length()+(Math.log(ws)/Math.log(2));
			if(returnedWs.size() == 1){
				t = 1;
				if(query.length() + returnedWs.get(0).length() < k){
					bitsTransmittedByTags += ws;
					S.push(query+returnedWs.get(0));
				}else if(query.length() + returnedWs.get(0).length() == k){
					M.add(query+returnedWs.get(0));
					bitsTransmittedByTags += ws;
				}
			}else if (returnedWs.size() > 1){
				/*boolean check = true;
				String firstW = returnedWs.get(0);
				for(String window: returnedWs){
					if(!window.equals(firstW)){
						check = false;
					}
				}
				if (check){
					t = 1;
					bitsTransmittedByTags +=(ws * returnedWs.size());
					S.push(query+firstW);
				}else{*/
					t = 2;
					bitsTransmittedByTags +=(ws * returnedWs.size());
					S.push(query + "1");
					S.push(query + "0");	
				//}				
			} else{
				t = 0;
			}
			
			if(!S.isEmpty()){
				String nextQuery = S.peek();
				ws = calculateWs(query, nextQuery, t, ws, k);	
				
			}		
		}
		
		
		return M;
	}
	
	//RECURSIVE
	private Set<String> QwT(String query, int ws){
		int k = 96;
		int t = 0;
		int crc = 1;
		List<String> returnedWs = getWsFromPrefix(query, ws);
		steps++;
		bitsTransmittedByReader+= query.length()+(Math.log(ws)/Math.log(2));
		//if(lastQuery != null){
			//newWs = calculateWs(lastQuery, query, t, ws, k);
		//}
		if(returnedWs.size() == 1){
			t = 1;
			if(query.length() + returnedWs.get(0).length() < k){
				bitsTransmittedByTags += ws + crc;
				String newQuery = query+returnedWs.get(0);
				int newWs = calculateWs(query, newQuery, t, ws, k);
				QwT(newQuery, newWs);
			}else if(query.length() + returnedWs.get(0).length() == k){
				M.add(query+returnedWs.get(0));
				bitsTransmittedByTags += ws + crc;
			}
			
		}else if (returnedWs.size() > 1){
			t = 2;
			bitsTransmittedByTags +=((ws + crc)  * returnedWs.size());
			String newQuery = query+"0";
			int newWs = calculateWs(query, newQuery, t, ws, k);
			QwT(query+"0", newWs);
			newQuery = query+"1";
			newWs = calculateWs(query, newQuery, t, ws, k);
			QwT(query+"1", newWs);
			
			
		}else{
			t = 0;
		}
		
		
		
		return M;
		
	}
	
	
	private int calculateWs(String query, String nextQuery, int t, int ws, int k){
		double r = 0;
		if(query.length() < nextQuery.length()){
			if (t == 1){
				r = k * (1 - Math.pow(Math.E, ((-0.5)*nextQuery.length())));
			}else{
				r = ws;
			}
			
		}else{
			r = 1;
		}
		
		int result = (int) Math.ceil(r);
		
		//System.out.println(result);
		return result;
		
	}
	
	
	private List<String> getWsFromPrefix(String prefix, int ws){
		
		List<String> returnedTags = new ArrayList<String>(1000);
		
		for (String tag : tags) {
			if(tag.startsWith(prefix)){
				String r = tag.replaceFirst(prefix, "");
				if((prefix.length() + ws) < tag.length()){
					r = r.substring(0, ws);
				}				
				returnedTags.add(r);
			}
		}
		
		return returnedTags;

	}
	
	

}
