// Raju is organizing a friendly competition where each participant submits a 
// secret code representing their choices on a series of questions which are 
// answered as yes or no. Each code is a number, and when you convert it to binary, 
// every digit indicates a specific answer: 0 for "no" and 1 for "yes." 

// The uniqueness of each participant's code lies in how much it differs from another's. 
// To measure this, you compare two codes digit by digit and count the number of 
// positions where their answers disagree.

// Given an array of these integer-encoded codes, help Raju to calculate the total 
// sum of these disagreement counts for every possible pair of participants.

// Example 1:
// ----------
// Input: 
// 5 13 3
// Output: 
// 6

// Explanation:
// Converting to binary (using four bits for clarity):

// 5 is 0101
// 13 is 1101
// 3 is 0011
// Now, compare each pair and count the number of positions with different digits:

// Comparing 5 (0101) and 13 (1101) gives 1 differences.
// Comparing 5 (0101) and 3 (0011) gives 2 differences.
// Comparing 13 (1101) and 3 (0011) gives 3 differences.
// Total differences = 1 + 2 + 3 = 6.














import java.util.*;


class Solution{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        // System.out.println(s[0]);
        int[] arr=new int[s.length];
        int ans=0;
        for(int i=0;i<s.length;i++) arr[i]=Integer.parseInt(s[i]);
        HashMap<Integer,Integer> map=new HashMap<>();
        int n=arr.length;
        for(int i=0;i<arr.length;i++){
            int b=arr[i];
            for(int j=0;j<32;j++){
                if(((b>>j)&1)==1){
                    map.put(j,map.getOrDefault(j,0)+1);
                }
            }
        }
        for(int i=0;i<32;i++){
            int c=n-map.getOrDefault(i,0);
            ans+=c*map.getOrDefault(i,0);
        }
        
        System.out.println(ans);
    }
}





