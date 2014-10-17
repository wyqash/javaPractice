//package AlgorithmsOnePercolation;
//import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

public class Percolation {
	
    private int N;
    private int gridSize;
    private boolean [] siteGrids;
    private WeightedQuickUnionUF wquf;
    //    private boolean percolationJudge;
    // Create NN grid with all sites blocked
    private int two2one(int i, int j){
	return i*N + j;
    }
    private void checkInput(int i, int j){
	if(i<1||i>N)
	    throw new IndexOutOfBoundsException("Grid i outside boundary.");
	if(j<1||j>N)
	    throw new IndexOutOfBoundsException("Grid i outside boundary.");
    }


	
    public Percolation(int n){
	if(n < 1){
	    throw new IllegalArgumentException("Non-positive grid size.");
	}
	N = n;
	//	percolationJudge = false;

	gridSize = N*N;
	//	siteGrids = new boolean [gridSize + 1];
	siteGrids = new boolean [gridSize + 2];
	for(int i = 0; i<gridSize; i++)
	    siteGrids[i] = false;
		
		
	// open the virtual top and bottom; 
		
	// virtual top N*N 
	siteGrids[gridSize] = true;
	// virtual bottom N*N + 1
	siteGrids[gridSize + 1] = true;
	// Connect the virtual top&bottom
	wquf = new WeightedQuickUnionUF(gridSize + 2);
	// wquf = new WeightedQuickUnionUF(gridSize + 1);
    }

    // Open site (i, j) if it is not open yet.
    public void open(int i, int j){
	// Use the boundary check in "isOpen()"
	if(isOpen(i,j)) 
	    return;

	int thisSite = two2one(i - 1, j - 1); 

	siteGrids[thisSite] = true;

	if(i==1){
	    wquf.union(gridSize,  thisSite);
	    if(N==1)
	    	wquf.union(gridSize+1,  thisSite);
	}else if(i==N){
	    // if(!percolationJudge){
	    // 	if(wquf.connected(gridSize, thisSite))
		// percolationJudge = true;
		wquf.union(gridSize + 1, thisSite);
	    // }
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
     	checkInput(i, j);
	i--;j--;
	return siteGrids[two2one(i, j)];
    }    
    
    // Is site (i,j) full?
    public boolean isFull(int i, int j){
     	checkInput(i, j);
	i--;j--;

	// Is this grid connected to the virtual top ? 	
    	return wquf.connected( 
			      gridSize, two2one(i, j));
    }
    
    // Does the system percolates? 
    public boolean percolates(){
    	// If the virtual top is connected to the virtual bottom
	// if(percolationJudge)
	//return percolationJudge;
	// else
	return wquf.connected(gridSize, gridSize + 1);
    }
    
    // test client
    public static void main(String [] args){
    	
    	Percolation p = new Percolation(1);
    	p.open(1, 1);
        System.out.println("Percolate ?: " + p.percolates());
    	
    }
	
}
