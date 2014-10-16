package AlgorithmsOnePercolation;

import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int N;
	private boolean [] siteGrids;
	private WeightedQuickUnionUF wquf;
	// Create NN grid with all sites blocked 
	public Percolation(int n){
		if(n <= 0){
			throw new IllegalArgumentException();
		}
		N = n;
		siteGrids = new boolean [N*N + 2];
		for(int i = 0; i<N*N; i++)
			 siteGrids[i] = false;
		
		
		// open the virtual top and bottom; 
		
		// virtual top N*N + 1
		siteGrids[N*N + 1] = true;
		// virtual bottom N*N + 2 
		siteGrids[N*N + 2] = true;
		
		wquf = new WeightedQuickUnionUF(N*N + 2);
		for(int i = 0; i< N; i++){
			// connect the virtual top to the first row 
			wquf.union(N*N+1,  0 + i);
			// connect the virtual bottom to the last row
			wquf.union(N*N+2, (N - 1)*N + i);
				
		}
	}

	// Open site (i, j) if it is not open yet.
	public void open(int i, int j){
		i = i - 1;
    	j = j - 1;
    	
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}
		int thisSite = i*N + j; 
		//open; connect it to all of its adjacent open sites.
		if(i>0)
			// the one on the previous row 
			wquf.union(thisSite, (i - 1)*N + j);
	    if(i<N - 1)
			// the one on the next row
			wquf.union(thisSite, (i - 1)*N + j);
	    if(j < N - 1) 
	    	// the one to the right
	    	wquf.union(thisSite, i*N + j + 1);
		if(j > 0)
			// the one to the left
			wquf.union(thisSite, i*N + j - 1);
	}
	
	// Is site (i,j) open?
    public boolean isOpen(int i, int j){
    	i = i - 1;
    	j = j - 1;
    	
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}
		return siteGrids[i*N + j];
    }    
    
    // Is site (i,j) full?
    public boolean isFull(int i, int j){
    	i = i - 1;
    	j = j - 1;
    	
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}
    // Is this grid connected to the virtual top ? 	
    	return wquf.connected(N*N + 1, i*N + j);
    }
    
    // Does the system percolates? 
    public boolean percolates(){
    	// If the virtual top is connected to the virtual bottom
    	return wquf.connected(N*N + 1, N*N + 2);
    }
    
    // test client
    public static void main(String [] args){
    	
    	
    }
	
}
