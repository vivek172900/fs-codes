// A software development company is designing a smart home automation 
// system that uses sensor networks to monitor and control different devices 
// in a house. The sensors are organized in a hierarchical structure, where each 
// sensor node has a unique ID and can have up to two child nodes (left and right).

// The company wants to analyze the left-most sensors in the system to determine
// which ones are critical for detecting environmental changes. The hierarchy of 
// the sensors is provided as a level-order input, where missing sensors are 
// represented as -1.

// Your task is to build the sensor network as a binary tree and then determine 
// the left-most sensor IDs at each level.

// Refernce Node:
// --------------
// class TreeNode {
//     Integer val;
//     TreeNode left, right;
    
//     TreeNode(Integer val) {
//         this.val = val;
//         this.left = this.right = null;
//     }
// }


// Input Format:
// -------------
// Space separated integers, elements of the tree.

// Output Format:
// --------------
// A list of integers representing the left-most sensor IDs at each level


// Sample Input-1:
// ---------------
// 1 2 3 4 -1 -1 5

// Sample Output-1:
// ----------------
// [1, 2, 4]



// Sample Input-2:
// ---------------
// 3 2 4 1 5

// Sample Output-2:
// ----------------
// [3, 2, 1]














import java.util.*;

class Node {
    int val;
    Node left, right;
    
    Node(int val) {
        this.val = val;
        this.left = this.right = null;
    }

}

class Solution{
    static Node build(int[] arr){
        Queue<Node> q=new LinkedList<>();
        Node root=new Node(arr[0]);
        q.add(root);
        int i=1;
        while(i<arr.length){
            Node node=q.poll();
            if(i<arr.length && arr[i]!=-1){
                node.left=new Node(arr[i]);
                q.add(node.left);
            }
            i++;
            if(i<arr.length && arr[i]!=-1){
                node.right=new Node(arr[i]);
                q.add(node.right);
            }
            // System.out.println(i);
            i++;
        }
        return root;
    }
    static void left(Node root){
        if(root==null) return;
        System.out.print(root.val+" ");
        if(root.left==null){
            left(root.right);
        }else{
            left(root.left);
        }
    }
    static void solve(Node root){
        System.out.print(root.val+" ");
        left(root.left);
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        int[] arr=new int[s.length];
        for(int i=0;i<s.length;i++) arr[i]=Integer.valueOf(s[i]);
        Node root=null;
        root=build(arr);
        solve(root);
    }

}