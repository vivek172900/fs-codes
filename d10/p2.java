// Given the preorder and postorder traversals of a binary tree, construct 
// the original binary tree and print its level order traversal.

// Input Format:
// ---------------
// Space separated integers, pre order data
// Space separated integers, post order data

// Output Format:
// -----------------
// Print the level-order data of the tree.


// Sample Input:
// ----------------
// 1 2 4 5 3 6 7
// 4 5 2 6 7 3 1

// Sample Output:
// ----------------
// [[1], [2, 3], [4, 5, 6, 7]]

// Explanation:
// --------------
//         1
//        / \
//       2   3
//      / \  / \
//     4   5 6  7


// Sample Input:
// ----------------
// 1 2 3
// 2 3 1

// Sample Output:
// ----------------
// [[1], [2, 3]]

// Explanation:
// --------------
//     1
//    / \
//   2  3
















import java.util.*;

class Node{
    int data;
    Node left;
    Node right;
    Node(){
        left=null;
        right=null;
    }
    Node(int val){
        left=null;
        right=null;
        data=val;
    }
}


class Solution{
    static int[] pre;
    static int[] post;
    static int k=0;
    static int j=0;
    static Node build(){
        if(j>=pre.length || k>=post.length) return null;
        Node root=new Node(pre[j++]);
        if(root.data==post[k]){
            k++;
            return root;
        }
        root.left=build();
        if(root.data==post[k]){
            k++;
            return root;
        }
        root.right=build();
        k++;
        return root;
    }
    static void level(Node root){
        Queue<Node> q=new LinkedList<>();
        // ArrayList<ArrayList<Integer>> ans=new ArrayList<>();
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
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s1=sc.nextLine().split(" ");
        String[] s2=sc.nextLine().split(" ");
        pre=new int[s1.length];
        post=new int[s2.length];
        for(int i=0;i<s1.length;i++){
            pre[i]=Integer.valueOf(s1[i]);
        }
        for(int i=0;i<s1.length;i++){
            post[i]=Integer.valueOf(s2[i]);
        }
        Node root=build();
        level(root);
    }
}