// You are given a binary classification dataset and a single test sample.

// Instead of building full decision trees, this problem uses a simplified and 
// interpretable approach:
//     Each feature independently acts as a binary decision stump 
//     (a depth-1 decision tree).

// A decision stump uses:
//     - Only one feature
//     - Only one split threshold
//     - Exactly two partitions

// 🧠 What a Decision Stump Means Here
// -----------------------------------
// For a given feature f, the decision stump:
//     - Selects a single threshold value
//     - Splits the training data into two partitions:
//         - Partition A: feature value ≤ threshold
//         - Partition B: feature value > threshold
//     - Uses Gini impurity to choose the best threshold
//     - Predicts a class (0 or 1) for the test sample based on the majority class 
//     of the matching partition

// ⚠️ Even though only one feature is used, the split is binary by definition, 
// which is why two partitions always exist.

// 🧩 Task Description
// -------------------
// For each feature independently:
//     - Treat the feature as a decision stump
//     - Try all possible thresholds derived from training data
//     - Compute the best threshold using Gini impurity
//     - Predict the class of the test sample using this stump
//     - Treat this prediction as one vote

// All feature-level predictions are then combined using Random Forest–style
// majority voting to produce the final predicted class.

// 🧵 Multithreading Requirement
// -----------------------------
// Each feature’s decision stump evaluation must run in parallel
// Use one thread per feature
// Implement concurrency using:
//     - Callable
//     - ExecutorService

// 📥 Input Format
// ---------------
// N M
// N lines: M feature values each (training data)
// N class labels (0 or 1)
// M feature values (test sample)

// 📤 Output Format
// ----------------
// Feature Predictions: [p1, p2, ..., pM]
// Final Predicted Class: X


// Where:
// ------
// pi is the prediction (0 or 1) from feature i
// X is the majority-voted final class
// In case of a tie, choose the smaller class label (0)

// 📘 Sample Input
// ---------------
// 4 3
// 2 3 1
// 4 1 2
// 6 5 3
// 8 7 4
// 0 0 1 1
// 5 4 2

// 📙 Sample Output
// ----------------
// Feature Predictions: [1, 1, 0]                                                                                                                        
// Final Predicted Class: 1 






















import java.util.*;
import java.util.concurrent.*;

/**
 * STUDENT TASK:
 * Implement the logic inside FeatureStumpTask.call()
 * Each feature must act as a binary decision stump (depth-1 tree)
 */
public class RFFeatureStump {

    /* ==========================
       FEATURE STUMP TASK
       ========================== */
    static class FeatureStumpTask implements Callable<Integer> {

        private final double[][] X;   // training features
        private final int[] y;        // class labels (0 or 1)
        private final int featureIdx; // index of feature handled by this task
        private final double testVal; // test sample value for this feature

        FeatureStumpTask(double[][] X, int[] y,
                          int featureIdx, double testVal) {
            this.X = X;
            this.y = y;
            this.featureIdx = featureIdx;
            this.testVal = testVal;
        }

        @Override
        public Integer call() {
            // Implement your code here.
            double ans=Double.MAX_VALUE;
            double thresh=0;
            for(int i=0;i<X.length;i++){
                double t=X[i][featureIdx];
                double Gl=0;
                double Gr=0;
                double countGl=0;
                double countGr=0;
                double countGRl=0;
                double countGRr=0;
                
                for(int j=0;j<y.length;j++){
                    if(X[j][featureIdx]<=t){
                        if(y[j]==0){
                            countGl++;
                        }else{
                            countGr++;
                        }
                    }else{
                        if(y[j]==0){
                            countGRl++;
                        }else{
                            countGRr++;
                        }
                    }
                }
                double leftSize=countGl+countGr;
                double rightSize=countGRl+countGRr;
                if(leftSize==0 || rightSize==0) continue;
                Gl=1-((countGl/leftSize)*(countGl/leftSize)+(countGr/leftSize)*(countGr/leftSize));
                Gr=1-((countGRl/rightSize)*(countGRl/rightSize)+(countGRr/rightSize)*(countGRr/rightSize));
                double giniSplit=((leftSize)/(y.length))*Gl+((rightSize)/(y.length))*Gr;
                if(ans>giniSplit){
                    thresh=t;
                    ans=giniSplit;
                }
            }
            System.out.println(thresh);
            double l0 = 0, l1 = 0, r0 = 0, r1 = 0;
            for (int i = 0; i < y.length; i++) {
                if (X[i][featureIdx] <= thresh) {
                    if (y[i] == 0) l0++;
                    else l1++;
                } else {
                    if (y[i] == 0) r0++;
                    else r1++;
                }
            }

            if (testVal <= thresh) {
                return (l1 > l0) ? 1 : 0;
            } else {
                return (r1 > r0) ? 1 : 0;
            }
        }
    }

    /* ==========================
       MAIN METHOD (DO NOT MODIFY)
       ========================== */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Read number of samples and features
        int N = sc.nextInt();
        int M = sc.nextInt();

        // Read training data
        double[][] X = new double[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                X[i][j] = sc.nextDouble();
            }
        }

        // Read class labels
        int[] y = new int[N];
        for (int i = 0; i < N; i++) {
            y[i] = sc.nextInt();
        }

        // Read test sample
        double[] testSample = new double[M];
        for (int i = 0; i < M; i++) {
            testSample[i] = sc.nextDouble();
        }

        // One thread per feature
        ExecutorService executor = Executors.newFixedThreadPool(M);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int f = 0; f < M; f++) {
            futures.add(
                executor.submit(
                    new FeatureStumpTask(X, y, f, testSample[f])
                )
            );
        }

        // Collect feature predictions
        List<Integer> featurePredictions = new ArrayList<>();
        for (Future<Integer> f : futures) {
            featurePredictions.add(f.get());
        }

        executor.shutdown();

        // Majority voting
        int count0 = 0, count1 = 0;
        for (int p : featurePredictions) {
            if (p == 0) count0++;
            else count1++;
        }

        int finalPrediction = (count1 > count0) ? 1 : 0;

        // Output
        System.out.println("Feature Predictions: " + featurePredictions);
        System.out.println("Final Predicted Class: " + finalPrediction);

        sc.close();
    }
}
