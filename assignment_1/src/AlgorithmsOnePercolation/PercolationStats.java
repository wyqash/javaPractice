package AlgorithmsOnePercolation;

import java.lang.IllegalArgumentException;
import java.util.Random;

import edu.princeton.cs.introcs.In;

public class PercolationStats {
	
	private int N; // Number of grids
	private int T; // Number of tests

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
		double [] pEstimates = new double [N];
		meanEstimate = 0.0;
		stdEstimate = 0.0;
		
		for(int i = 0; i < T; i++){
			
			Percolation sampleExperiment = new Percolation(N);
			Random rowGenerator = new Random();
			Random columnGenerator = new Random();
			int counter = 0;
			//			System.out.println("sample percolates? :                         = " + sampleExperiment.percolates());

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
			pEstimates[i] = (double) counter/(double)(N*N);

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
	    int N = Integer.parseInt(args[0]);
	    int T = Integer.parseInt(args[1]);
	    System.out.println("N is: " + N + ", T is: " + T);
	    
	    PercolationStats testSample = new PercolationStats(N, T);
	    System.out.println("Mean:                         = " + testSample.mean());
	    System.out.println("std:                          = " + testSample.stddev());
	    System.out.println("95% confidence interval is:   = " + testSample.confidenceLo() + ", " + testSample.confidenceHi()); 
	}
}
