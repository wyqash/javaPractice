// package AlgorithmsOnePercolation;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class Percolation {
	
    private int N;
    private boolean [] siteGrids;
    private WeightedQuickUnionUF wquf;
    // Create NN grid with all sites blocked
    private int two2one(int i, int j){
	return i*N + j;
    }
	
	
    public Percolation(int n){
	if(n < 1){
	    throw new IllegalArgumentException("Non-positive grid size.");
	}
	N = n;
	siteGrids = new boolean [N*N + 2];
	for(int i = 0; i<N*N; i++)
	    siteGrids[i] = false;
		
		
	// open the virtual top and bottom; 
		
	// virtual top N*N 
	siteGrids[N*N] = true;
	// virtual bottom N*N + 1
	siteGrids[N*N + 1] = true;
	// Connect the virtual top&bottom
	wquf = new WeightedQuickUnionUF(N*N + 2);
	// for(int i = 0; i< N; i++){
	//     // connect the virtual top to the first row 
	//     wquf.union(N*N,  two2one(0, i));
	//     // connect the virtual bottom to the last row
	//     wquf.union(N*N + 1, two2one(N - 1, i));
	// }
    }

    // Open site (i, j) if it is not open yet.
    public void open(int i, int j){
	// Use the boundary check in "isOpen()"
	if(isOpen(i,j)) 
	    return;

	int thisSite = two2one(i - 1, j - 1); 

	siteGrids[thisSite] = true;

	if(i==1){
	    wquf.union(N*N,  thisSite);
	}else if(i==N){
		if(wquf.connected(N*N, thisSite))
		wquf.union(N*N + 1, thisSite);
	}

	//open; connect it to all of its adjacent open sites.
	if(i>1)
	    // the one on the previous row
	    if(isOpen(i - 1, j))
		wquf.union(thisSite,two2one(i - 2, j - 1));
		
	if(i < N )
	    // the one on the next row
	    if(isOpen(i+1, j))
		wquf.union(thisSite, two2one(i, j - 1));
	if(j < N ) 
	    // the one to the right
	    if(isOpen(i, j+1))
		wquf.union(thisSite, two2one(i - 1, j ));
	if(j > 1)
	    // the one to the left
	    if(isOpen(i, j-1))
		wquf.union(thisSite, two2one(i - 1, j - 2));
    }

    // Is site (i,j) open?
    public boolean isOpen(int i, int j){
	if(i<1||i>N||j<1||j>N){
	    throw new IndexOutOfBoundsException("Grid outside boundary.");
	}
     	i--;j--;
	return siteGrids[two2one(i, j)];
    }    
    
    // Is site (i,j) full?
    public boolean isFull(int i, int j){
    	
	if(i<1||i>N||j<1||j>N){
	    throw new IndexOutOfBoundsException("Grid outside boundary.");
	}
	i--;j--;

	// Is this grid connected to the virtual top ? 	
    	return wquf.connected( 
			      N*N, two2one(i, j));
    }
    
    // Does the system percolates? 
    public boolean percolates(){
    	// If the virtual top is connected to the virtual bottom
    	return wquf.connected(N*N, N*N + 1);
    }
    
    // test client
    public static void main(String [] args){
    	
    	
    }
	
}
