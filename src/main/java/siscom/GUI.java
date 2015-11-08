package siscom;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class GUI extends JFrame {
		
    public GUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.height, screenSize.height);
        
    }
    
    public void createChartAndAddLineChart(JFreeChart chart, double xTickUnit, double yTickUnit){
    	if(getContentPane().getComponentCount()==1){
    		setLayout(new GridLayout(2,1));
    	}else if(getContentPane().getComponentCount()==2){
    		setLayout(new GridLayout(2,2));
    	}
    	setChart(chart, xTickUnit, yTickUnit);
    	final ChartPanel chartPanel = new ChartPanel(chart);
    	getContentPane().add(chartPanel);
    	setVisible(true);
    }
    
	private static JFreeChart setChart(JFreeChart chart,	double xTickUnit, double yTickUnit) {



		chart.setBackgroundPaint(Color.white);
		
		//        final StandardLegend legend = (StandardLegend) chart.getLegend();
		//      legend.setDisplaySeriesShapes(true);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		//    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//		renderer.setSeriesLinesVisible(1, false); //disable series line
//		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);
		
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setTickUnit(new NumberTickUnit(xTickUnit));
		xAxis.setLowerBound(100);
		
		// change the auto tick unit selection to integer units only...
		final NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setTickUnit(new NumberTickUnit(yTickUnit));
		yAxis.setAutoRangeIncludesZero(true);

		//yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.
		
		return chart;

	}
}
