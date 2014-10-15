
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

package AlgorithmsOnePercolation;

public class Percolation {
	
	private int N;
	
	// Create NN grid with all sites blocked 
	public Percolation(int n){
		if(n <= 0){
			throw new IllegalArgumentException();
		}
		N = n;
	}

	// Open site (i, j) if it is not open yet.
	public void open(int i, int j){
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}

		
		
	}
	
	// Is site (i,j) open?
    public boolean isOpen(int i, int j){
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}
		
    }    
    // Is site (i,j) full?
    public boolean isFull(int i, int j){
		if(i<=0||i>N||j<=0||j>N){
			throw new IndexOutOfBoundsException();
		}
    	
    	
    }
    
    // Does the system percolates? 
    public boolean percolates(int i, int j){
    	
    	
    }
    
    // test client
    public static void main(String [] args)；
	
}
