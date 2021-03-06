package siscom.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import siscom.GUI;


public class LowerBoundReader {
	
	static int qtdTags;
	static int tags;
	static int executionSlots;
	static int emptySlots;
	static int collisionSlots;
	static double totalEmptySlots;
	static double totalCollisionSlots;
	static double totalSlots;

	
	public static void runGUI(XYSeriesCollection dataset1, XYSeriesCollection dataset2,XYSeriesCollection dataset3){
		
		XYSeries serie1 = new XYSeries("LowerBound");
		
		XYSeries serie2 = new XYSeries("LowerBound");
		
		XYSeries serie3 = new XYSeries("LowerBound");

		for(qtdTags = 100; qtdTags <=1000; qtdTags+=100){
			totalSlots = 0;
			totalEmptySlots = 0;
			totalCollisionSlots = 0;
			int exec = 1;
			System.out.println("Number of Tags: " + qtdTags);
			
			while(exec <=1000){
				//System.out.println("Loop #" + exec);
				executionSlots = 0;
				emptySlots = 0;
				collisionSlots = 0;
				tags = qtdTags;
				
				findTags();
				
				exec++;
				totalSlots = totalSlots + executionSlots;
				totalEmptySlots = totalEmptySlots + emptySlots;
				totalCollisionSlots = totalCollisionSlots + collisionSlots;
			}
			totalSlots = totalSlots/1000;
			System.out.println("Average Slots: " + totalSlots);
			serie1.add(qtdTags, totalSlots);
			
			totalEmptySlots = totalEmptySlots/1000;
			serie2.add(qtdTags, totalEmptySlots);
			
			totalCollisionSlots = totalCollisionSlots/1000;
			serie3.add(qtdTags, totalCollisionSlots);
			
		}
		
		
        dataset1.addSeries(serie1); // add curve to chart
        dataset2.addSeries(serie2);
        dataset3.addSeries(serie3);
        
        
	}
	
	
	public static void findTags() {
		int frameSize = 64;
		List<List<Integer>> collision = initColision(frameSize);		
		
		while(tags != 0){
			frameSize = lowerBounds(collision,frameSize);
			collision = initColision(frameSize);
			
		}
	}

	public static List<List<Integer>> initColision(int frameSize) {
		List<List<Integer>> collision = new ArrayList<List<Integer>>();

		for(int j = 0; j< frameSize; j++){
			collision.add(new ArrayList<Integer>());
		}

		for(int i = 0; i < tags; i++){
			int random = (int) (Math.random() * frameSize);
			collision.get(random).add(i);
		}
		
		return collision;

	}
	
	
	public static int lowerBounds(List<List<Integer>> collision, int frameSize){
		int Lc = 0,Lv = 0,Ls = 0;
		
		for(int i = 0; i < frameSize; i++){
			
			if(collision.get(i).size() > 1){
				Lc++;		
			}else if(collision.get(i).size() == 1){
				Ls++;
				tags--;
			}else{
				Lv++;
			}	
		}
		
		executionSlots = executionSlots + Lc + Lv + Ls;
		
		emptySlots = emptySlots + Lv;
		
		collisionSlots = collisionSlots + Lc;
		
		int newLowerBounds = Lc * 2;
		
		return newLowerBounds;
		
	}
	
	
}
