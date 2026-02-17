// You are a gardener designing a beautiful floral pathway in a vast botanical garden. 
// The garden is currently overgrown with plants, trees, and bushes arranged in a 
// complex branching structure, much like a tree. Your task is to carefully prune 
// and rearrange the plants to form a single-file walking path that visitors can 
// follow effortlessly.

// To accomplish this, you must flatten the garden’s layout into a linear sequence 
// while following these rules:
//     1. The garden path should maintain the same PlantNode structure, where the 
//        right branch connects to the next plant in the sequence, and the left branch 
//        is always trimmed (set to null).
       
//     2. The plants in the final garden path should follow the same arrangement 
//        as a pre-order traversal of the original garden layout. 

// Example 1:
// Input:
// 1 2 5 3 4 -1 6

// Output:
// 1 2 3 4 5 6

// Explanation:
// input structure:
//        1
//       / \
//      2   5
//     / \    \
//    3   4    6
   
// output structure:
// 	1
// 	 \
// 	  2
// 	   \
// 		3
// 		 \
// 		  4
// 		   \
// 			5
// 			 \
// 			  6
			  













import java.util.*;

class Node{
    int data;
    Node left;
    Node right;
    Node(){};
    Node(int d){
        data=d;
        left=null;
        right=null;
    }
}

class Solution{
    static Node build(int[] arr){
        Queue<Node> q=new LinkedList<>();
        Node root=new Node(arr[0]);
        q.add(root);
        int i=1;
        while(!q.isEmpty()){
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
    static Node print(Node root){
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node n=q.poll();
            System.out.print(n.data+" ");
            if(n.left!=null){
                // n.left=new Node(arr[i]);
                q.add(n.left);
            }
            // i++;
            if(n.right!=null){
                // n.right=new Node(arr[i]);
                q.add(n.right);
            }
            // i++;
        }
        return root;
    }
    static void build2(Node root){
        if(root==null) return ;
        build2(root.left);
        build2(root.right);
        
        Node rightsub=root.right;
        Node leftsub=root.left;
        
        root.left=null;
        root.right=leftsub;
        Node temp=root;
        while(temp.right!=null){
            temp=temp.right;
        }
        temp.right=rightsub;
        // return root2;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        int[] arr=new int[s.length];
        for(int i=0;i<s.length;i++) arr[i]=Integer.valueOf(s[i]);
        Node root=null;
        root=build(arr);
        build2(root);
        print(root);
    }
}