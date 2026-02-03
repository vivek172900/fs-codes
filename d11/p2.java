// Balbir Singh is working with Binary Trees.
// The elements of the tree are given in level-order format.

// Balbir is observing the tree from the right side, meaning he 
// can only see the rightmost nodes (one node per level).

// You are given the root of a binary tree. Your task is to determine 
// the nodes visible from the right side and return them in top-to-bottom order.

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
// A list of integers representing the node values visible from the right side


// Sample Input-1:
// ---------------
// 1 2 3 5 -1 -1 5

// Sample Output-1:
// ----------------
// [1, 3, 5]



// Sample Input-2:
// ---------------
// 3 2 4 3 2

// Sample Output-2:
// ----------------
// [3, 4, 2]












import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

class Solution{
    static void solve(TreeNode root){
        Queue<TreeNode> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size=q.size();
            int a=-1;
            for(int i=0;i<size;i++){
                TreeNode node=q.poll();
                a=node.val;
                if(node.left!=null){
                    q.add(node.left);
                }
                if(node.right!=null){
                    q.add(node.right);
                }
            }
            if(a!=-1) System.out.print(a+" ");
        }
    }
    static TreeNode build(int[] arr){
        int n=arr.length;
        Queue<TreeNode> q=new LinkedList<>();
        TreeNode root=new TreeNode(arr[0]);
        q.add(root);
        int i=1;
        while(i<arr.length){
            TreeNode r=q.poll();
            if(i<arr.length && arr[i]!=-1){
                r.left=new TreeNode(arr[i]);
                q.add(r.left);
            }
            i++;
            if(i<arr.length &&  arr[i]!=-1){
                r.right=new TreeNode(arr[i]);
                q.add(r.right);
            }
            i++;
        }
        return root;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        int[] arr=new int[s.length];
        for(int i=0;i<s.length;i++) arr[i]=Integer.valueOf(s[i]);
        TreeNode root=null;
        root=build(arr);
        solve(root);
    }
}