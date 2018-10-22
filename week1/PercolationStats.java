import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
/**
 *
 * @author dan
 */
public class PercolationStats {
	double[] percThresholds;	
	
	public PercolationStats(int n, int trials)  {
		
		percThresholds = new double[trials];
		
		for (int trial=0; trial<trials; trial++) {
			Percolation p = new Percolation(n);

			while (!p.percolates()) {
				int row = StdRandom.uniform(1, n+1);
				int col = StdRandom.uniform(1, n+1);
				if (!p.isOpen(row, col)) {
					p.open(row, col);
				}
			}

//			System.out.println("opened: " + p.numberOfOpenSites() + "; ratio : " + (float) p.numberOfOpenSites()/(n*n));
			percThresholds[trial] = (double) p.numberOfOpenSites()/(n*n);
		}	

			
	}  // perform trials independent experiments on an n-by-n grid
	
	public double mean() {
		
		return StdStats.mean(percThresholds);
	}                     // sample mean of percolation threshold
	  
	public double stddev() {
		return StdStats.stddev(percThresholds);
	}                  // sample standard deviation of percolation threshold
	  
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(percThresholds.length);
	  
	}                // low  endpoint of 95% confidence interval
	  
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(percThresholds.length);
	} 

	public static void main(String[] args) {
		
//		Stopwatch stopWatch = new Stopwatch();
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//		System.out.println("time taken: " + stopWatch.elapsedTime());
		
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
	System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}	
	
}

