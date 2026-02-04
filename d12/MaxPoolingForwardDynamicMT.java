// You are given a 2-dimensional integer matrix representing a feature map obtained 
// from a convolutional layer of a neural network. Your task is to compute the Max 
// Pooling – Forward Pass on this matrix using Java multithreading.

// Max Pooling reduces the spatial dimensions of the input matrix by sliding a fixed-size
// window over it and selecting the maximum value from each window.

// The computation must be performed in parallel, where multiple threads cooperate to 
// compute different parts of the output matrix.

// Pooling Rules
// -------------
// 	Pooling window size is K × K
// 	Stride is S
// 	No padding is applied
// 	Windows move from left to right and top to bottom

// The output matrix dimensions are:
// 	Output Rows  = (H − K) / S + 1
// 	Output Cols  = (W − K) / S + 1
// where H × W is the size of the input matrix.

// Multithreading Requirement
// --------------------------
// 	The forward pooling computation must be parallelized
// 	Each thread should compute one or more output rows
// 	The final output must be deterministic and ordered

// Input Format
// ------------
// H W
// H lines each containing W space-separated integers
// K
// S

// Input Description
// -----------------
// H	  Number of rows in the input matrix
// W	  Number of columns in the input matrix
// K	  Pooling window size
// S	  Stride

// Output Format
// -------------
// Output matrix with each row printed on a new line
// Each row contains space-separated integers

// Sample Input
// ------------
// 4 4
// 1 3 2 4
// 5 6 1 2
// 0 2 4 1
// 3 1 0 2
// 2
// 2

// Sample Output
// -------------
// 6 4
// 3 4

// Explanation 
// --------------
// A 2 × 2 window slides over the matrix with stride 2
// From each window, the maximum value is selected
// These maximum values form the output matrix

// Constraints
// --------------
// 1 ≤ H, W ≤ 1000
// 1 ≤ K ≤ min(H, W)
// 1 ≤ S ≤ K
// Input values are valid integers
// (H − K) and (W − K) are divisible by S
















import java.util.concurrent.*;
import java.util.Scanner;
import java.util.*;

class MaxPoolForward implements Callable<int[]> {
    private final int[][] input;
    private final int rowIndex;
    private final int oc;
    private final int stride;
    private final int poolSize;
    MaxPoolForward(int[][] input,int rowIndex,int poolSize,int stride,int oc){
        this.input=input;
        this.oc=oc;
        this.poolSize=poolSize;
        this.stride=stride;
        this.rowIndex=rowIndex;
    }
    public int[] call(){
        int[] outputRow=new int[oc];
        int startRow=rowIndex*stride;
        for(int j=0;j<oc;j++){
            int startCol=j*stride;
            int max=Integer.MIN_VALUE;
            for(int r=0;r<poolSize;r++){
                for(int c=0;c<poolSize;c++){
                    max=Math.max(max,input[startRow+r][startCol+c]);
                }
            }
            outputRow[j]=max;
        }
        return outputRow;
    }
}

public class MaxPoolingForwardDynamicMT {
    static int[][] maxPoolForward(int[][] input,int k,int s) throws Exception{
        int H=input.length;
        int W=input[0].length;
        int or=(H-k)/s+1;
        int oc=(W-k)/s+1;
        int[][] output =new int[or][oc];
        
        ExecutorService executor= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<int[]>> futures=new ArrayList<>();
        for(int i=0;i<or;i++){
            futures.add(executor.submit(new MaxPoolForward(input,i,k,s,oc)));
        }
        
        for(int i=0;i<or;i++){
            output[i]=futures.get(i).get();
        }
        executor.shutdown();
        return output;
        
    }

    /* ==================================
       Implement Your Code using Callable
       ================================== */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Read matrix dimensions
        int H = sc.nextInt();
        int W = sc.nextInt();

        int[][] input = new int[H][W];

        // Read matrix
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                input[i][j] = sc.nextInt();
            }
        }

        // Read pooling parameters
        int poolSize = sc.nextInt();
        int stride = sc.nextInt();

        int[][] output =
                maxPoolForward(input, poolSize, stride);

        // Print output
        for (int[] row : output) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}