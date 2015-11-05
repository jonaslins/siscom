package siscom;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;









import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import siscom.reader.EomLeeReader;
import siscom.reader.LowerBoundReader;
import siscom.reader.QTReader;
import siscom.reader.TreeBasedReader;

public class Main {
	
	static final int TAG_ID_LENGTH = 96;  
	
	/*TODO 
	 * 
	 * 1. Contagem dos bits trocados leitor<->tags
	 * 		1. as queries feitas pelo leitor são os bits trocados "leitor->tag"
	 * 		2. as respostas das tags são os bits trocados "tag->leitor"
	 * */	

	public static void main(String[] args) throws Exception {
		
		printDFSA();
        
	}
	
	public static void printDFSA() throws Exception{
		XYSeriesCollection dataset1 = new XYSeriesCollection();
		
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		
		XYSeriesCollection dataset3 = new XYSeriesCollection();
		
		EomLeeReader.runGUI(dataset1, dataset2, dataset3);
		LowerBoundReader.runGUI(dataset1, dataset2, dataset3);
		
		
		GUI gui = new GUI();

		
		JFreeChart chart1 = ChartFactory.createXYLineChart(
        		"Performance of the DFSA's",	// chart title
        		"No of tags, n",			// x axis label
        		"No of Slots",	// y axis label
				dataset1,					// data
				PlotOrientation.VERTICAL,
				true,                     	// include legend
				true,                     	// tooltips
				false                    	 // urls
				);
        
        JFreeChart chart2 = ChartFactory.createXYLineChart(
        		"Performance of the DFSA's",	// chart title
        		"No of tags, n",			// x axis label
        		"No of Empty Slots",	// y axis label
				dataset2,					// data
				PlotOrientation.VERTICAL,
				true,                     	// include legend
				true,                     	// tooltips
				false                    	 // urls
				);
        
        JFreeChart chart3 = ChartFactory.createXYLineChart(
        		"Performance of the DFSA's",	// chart title
        		"No of tags, n",			// x axis label
        		"No of Collision Slots",	// y axis label
				dataset3,					// data
				PlotOrientation.VERTICAL,
				true,                     	// include legend
				true,                     	// tooltips
				false                    	 // urls
				);
        
        gui.createChartAndAddLineChart(chart1, 100, 200);
        gui.createChartAndAddLineChart(chart2, 100, 100);
        gui.createChartAndAddLineChart(chart3, 100, 200);
	}
	
	public static void printQT() throws Exception{
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries serie = new XYSeries("QT");	
		
		for(int N = 100; N<=1000; N+=100){			
			
			File folder = new File(Main.class.getClass().getResource("/"+N).toURI());
			File[] files = folder.listFiles(); 
			
			if(files.length>0){
				
				double bitsTransmittedPerTagSum = 0;
				for (int i = 0; i < files.length; i++) {
					List<String> tags = getTagsFromfile(files[i]);
					TreeBasedReader reader = new QTReader(tags);
					Set<String> foundTags = reader.findTags();
					bitsTransmittedPerTagSum += (reader.bitsTransmittedByTags/((double)N));
				}
				
				double mean = (bitsTransmittedPerTagSum/(double)files.length);
				serie.add(N, mean);
				System.out.println("N: "+N+" Bits p/ tag: "+mean);
				
			}
			
		}		
				
		System.out.println();
//		for (String string : foundTags) {
//			System.out.println(string);
//		}					
		
        GUI gui = new GUI();
        
        dataset.addSeries(serie); // add curve to chart
        
        JFreeChart chart = ChartFactory.createXYLineChart(
        		"Performance of the QT",	// chart title
        		"Nº of tags, n",			// x axis label
        		"Transmitted bits per tag",	// y axis label
				dataset,					// data
				PlotOrientation.VERTICAL,
				true,                     	// include legend
				true,                     	// tooltips
				false                    	 // urls
				);
        
        gui.createChartAndAddLineChart(chart, 200, 200);
	}
	
	public static List<String> getTagsFromfile(File file) throws IOException{
		Path path = Paths.get(file.getParent(), file.getName());
		List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
		List<String> tags = new ArrayList<String>(lines);
		return tags;
	}
		
}
