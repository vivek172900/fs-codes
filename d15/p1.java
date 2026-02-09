// VishnuVardan is working with Decision Trees for AI-based predictions.
// To analyze alternative outcomes, Kishore has planned to flip the decision 
// tree horizontally to simulate a reverse processing approach.

// Rules for Flipping the Decision Tree:
// 	- The original root node becomes the new rightmost node.
// 	- The original left child becomes the new root node.
// 	- The original right child becomes the new left child.
// This transformation is applied level by level recursively.

// Note:
// ------
// - Each node in the given tree has either 0 or 2 children.
// - Every right node in the tree has a left sibling sharing the same parent.
// - Every right node has no further children (i.e., they are leaf nodes).

// Your task is to help VishnuVardan flip the Decision Tree while following 
// the given transformation rules.

// Input Format:
// -------------
// Space separated integers, nodes of the tree.

// Output Format:
// --------------
// Print the list of nodes of flipped tree as described below.


// Sample Input-1:
// ---------------
// 4 2 3 5 1

// Sample Output-1:
// ----------------
// 5 1 2 3 4


// Sample Input-2:
// ---------------
// 4 2 5

// Sample Output-2:
// ----------------
// 2 5 4
















import java.util.*;

class Node{
    Node right;
    Node left;
    int data;
    Node(){};
    Node(int val){
        right=null;
        left=null;
        data=val;
    }
}

class Solution{
    static Node build(int[] arr){
        Queue<Node> q=new LinkedList<>();
        Node root=new Node(arr[0]);
        int i=1;
        q.add(root);
        while(i<arr.length){
            Node n=q.poll();
            if(i<arr.length && arr[i]!=-1){
                n.left=new Node(arr[i]);
                q.add(n.left);
            }
            i++;
            if(i<arr.length && arr[i]!=-1){
                n.right=new Node(arr[i]);
                q.add(n.right);
            }
            i++;
        }
        return root;
    }
    static void print(Node root){
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node n=q.poll();
            System.out.print(n.data+" ");
            if(n.left!=null){
                q.add(n.left);
            }
            if(n.right!=null){
                q.add(n.right);
            }
        }
    }
    static Node solve(Node root){
        if(root==null || root.left==null) return root;
        Node newRoot=solve(root.left);
        
        root.left.left=root.right;
        root.left.right=root;
        root.left=null;
        root.right=null;
        return newRoot;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        int[] arr=new int[s.length];
        for(int i=0;i<arr.length;i++) arr[i]=Integer.valueOf(s[i]);
        Node root=null;
        root=build(arr);
        Node ans=solve(root);
        print(ans);
    }
}