
package AlgorithmsOnePercolation;

import java.lang.IllegalArgumentException;
import java.util.Random;

public class PercolationStats {
	
	
	private int N; // Number of grids
	private int T; // Number of tests
	private double[] pEstimates;
	private double meanEstimate;
	private double stdEstimate;
	private double confidenceLo;
	private double confidenceHi;

	// Perform T independent experiments on a NN grid
	public PercolationStats(int n, int t){
		if((n<=0)||(t<=0)){
			throw new IllegalArgumentException("Negative grid or test number.");
		}
		N = n;
		T = t;
		pEstimates = new double [N];
		meanEstimate = 0.0;
		stdEstimate = 0.0;
		
		for(int i = 0; i < T; i++){
			
			Percolation sampleExperiment = new Percolation(N);
			Random rowGenerator = new Random();
			Random columnGenerator = new Random();
			int counter = 0;
			while(!sampleExperiment.percolates()){
				int rowNumber = 1 + rowGenerator.nextInt(N);
				int columnNumber = 1 + columnGenerator.nextInt(N);
				// Make sure that this site is not yet open. 
				while(sampleExperiment.isOpen(rowNumber, columnNumber)){
					rowNumber = 1 + rowGenerator.nextInt(N);
					columnNumber = 1 + columnGenerator.nextInt(N);
				}
				sampleExperiment.open(rowNumber,
						              columnNumber);
				counter++;
			}
			
			pEstimates[i] = counter/(N*N);
			meanEstimate += pEstimates[i];
		}

			meanEstimate = meanEstimate/T;
			
			for(int i = 0; i<T; i++){
				stdEstimate += Math.pow((pEstimates[i] - meanEstimate),2);
			}
			
			
			if(T == 1)
				stdEstimate = Double.NaN; // Important error check
			else
				stdEstimate = stdEstimate/(T - 1);
			
			stdEstimate = Math.pow(stdEstimate, 0.5);
			
			
			confidenceLo = meanEstimate - 1.96*stdEstimate/Math.pow(T, 0.5);
			confidenceHi = meanEstimate + 1.96*stdEstimate/Math.pow(T, 0.5);
			
			
	}
	
	// Sample mean of the percolation threshold
	public double mean(){
		return meanEstimate;
	}
	
	// Sample std of percolation threshold
	public double stddev(){
		return stdEstimate;
	}
	
	// 
	public double confidenceLo(){
		return confidenceLo;
	}
	
	
	public double confidenceHi(){
		return confidenceHi;
	}
	
	public static void main(String[] args){
		
		
	}
}
