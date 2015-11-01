package main.java;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class generateNumbers {
	public static void main(String[] args) throws FileNotFoundException {
		long startTime = System.currentTimeMillis();

		// cria pastas
		makeFolders(10);
		//gera tags e salva em arqvuvos
		for (int i = 1; i <= 10; i++) {
			Set<String> set= conjunto(i*100*1000);
			saveFiles(set,i*100);
		}
		System.out.print("Done in: ");
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime/1000 + "seconds");
	}
	//gera randomicamente um booleano
	public static boolean getRandomBoolean() {
	       return Math.random() < 0.5;
	}
	//gera um array de inteiros bit a bit
	public static int[] get96BitsRfid(){
		int tag[] = new int [96];
		for (int i = 0; i < tag.length; i++) {
			int bit = (getRandomBoolean()) ? 1 : 0;
			tag[i] = bit;
		}
		return tag;
	}
	//transforma o array de inteiros numa string
	public static String arrayToString(int[] array){
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i = 0; i < array.length; i++) {
			stringBuilder.append(array[i]);
		}
		String tagString = stringBuilder.toString();
		return tagString;
	}
	//gera um Set de tamanho size de strings de 0s e 1s de tamanaho 96
	public static Set<String> conjunto(int size){
		Set<String> set = new HashSet<String>();
		while (set.size() < size) {
			set.add(arrayToString(get96BitsRfid()));
		}
		return set;
	}
	public static void makeFolders(int a){
		for (int i = 1; i <= a; i++) {
			String path = Integer.toString((i*100));
			boolean success = (new File(path)).mkdirs();	
			if(!success){
				System.out.println("Could not create Folder"+path);
			}
		}
	}
	//salva strings dos sets em arquivos (PASTAS PREVIAMENTE CRIADAS)
	public static void saveFiles(Set<String> set, int size) throws FileNotFoundException{
		
			for (int i = 1; i <= 1000; i++) {
				String formatted = String.format("%04d", i);
				PrintWriter out = new PrintWriter(size+"/"+formatted+"-"+size+".txt");
				for (int j = 0; j < size; j++) {
					if(!set.isEmpty()){
						out.println(set.iterator().next().toString());
						set.remove(set.iterator().next());
					}
				}
				out.close();
				}
				System.out.println("Files added to folder: "+ size);
			}
		
		
	
}