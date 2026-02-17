// Imagine you’re decoding a secret message that outlines the hierarchical structure 
// of a covert spy network. The message is a string composed of numbers and parentheses. 
// Here’s how the code works:

// - The string always starts with an agent’s identification number, this is the 
//   leader of the network.
// - After the leader’s ID, there can be zero, one, or two segments enclosed in 
//   parentheses. Each segment represents the complete structure of one subordinate 
//   network.
// - If two subordinate networks are present, the one enclosed in the first (leftmost) 
//   pair of parentheses represents the left branch, and the second (rightmost) 
//   represents the right branch.

// Your mission is to reconstruct the entire spy network hierarchy based on this 
// coded message.

// Example 1:
// Input: 4(2(3)(1))(6(5))
// Output: [4, 2, 6, 3, 1, 5]

// Spy network:
//         4
//        / \
//       2   6
//      / \  /
//     3   1 5

// Explanation:
// Agent 4 is the leader.
// Agent 2 (with its own subordinates 3 and 1) is the left branch.
// Agent 6 (with subordinate 5) is the right branch.

// Example 2:
// Input: 4(2(3)(-1))(6(5)(7))
// Output: [4, 2, 6, 3, -1, 5, 7]

// Spy network:
//          4
//        /   \
//       2     6
//      / \   / \
//     3   -1 5   7

// Explanation:
// Agent 4 leads the network.
// Agent 2 with subordinates 3 and 1 forms the left branch.
// Agent 6 with subordinates 5 and 7 forms the right branch.

















import java.util.*;

class Node{
    int data;
    Node left;
    Node right;
    Node(int d){
        data=d;
        left=null;
        right=null;
    }
}

class Solution{
    static Node solve(String s,int left,int right){
        String a="";
        if(s.charAt(left)=='-'){
            a+='-';
            left++;
        }
        while(left<=right && Character.isDigit(s.charAt(left))){
            a+=s.charAt(left);
            left++;
        }
        Node root=new Node(Integer.valueOf(a));
        int open=0;
        int close=0;
        int st=left;
        while(st<=right && !Character.isDigit(s.charAt(st))) {
            if(s.charAt(st)=='-') break;
            st++;
        }
        boolean leftS=false;
        for(int i=left;i<=right;i++){
            if(s.charAt(i)=='('){
                open++;
            }else if(s.charAt(i)==')'){
                close++;
            }
            if(open==close && open>0 && !leftS){
                // System.out.println("left st: "+st);
                root.left = solve(s,st,i);
                st=i+1;
                leftS=true;
                open=0;
                close=0;
            }else if(open==close && open>0 && leftS){
                while(st<=right && !Character.isDigit(s.charAt(st))){
                    if(s.charAt(st)=='-') break;
                    st++;
                }
                // System.out.println("st  "+st);
                root.right=solve(s,st,i);
                break;
            }
        }
        return root;
    }
    static void level(Node root){
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
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String s=sc.next();
        Node ans=solve(s,0,s.length()-1);
        level(ans);
    }
}