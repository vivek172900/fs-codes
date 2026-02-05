import java.util.*;

class Node{
    int data;
    Node left;
    Node right;
    Node(){}
    Node(int val){
        left=null;
        right=null;
        data=val;
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
            i++;
        }
        return root;
    }
    static Node solve(Node root,int a,int b){
        if(root==null) return null;
        if(root.data==a) {
            return root;
        }
        if(root.data==b){
            return root;
        }
        Node left=solve(root.left,a,b);
        Node right=solve(root.right,a,b);
        if(left!=null && right!=null) return root;
        return left==null?right:left;
    }
    static int e1=0;
    static int e2=0;
    static void edges(Node root,int a,int h){
        if(root==null) return ;
        if(root.data==a){
            if(e1==0) e1=h;
            else e2=h;
            return ;
        }
        edges(root.left,a,h+1);
        edges(root.right,a,h+1);
        
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split(" ");
        int[] arr=new int[s.length];
        for(int i=0;i<arr.length;i++) arr[i]=Integer.valueOf(s[i]);
        int a=sc.nextInt();
        int b=sc.nextInt();
        Node root=null;
        root=build(arr);
        Node ans=solve(root,a,b);
        // if(ans!=null) System.out.println(ans.data);
        edges(ans,a,0);
        edges(ans,b,0);
        System.out.println((e1+e2));
    }
}