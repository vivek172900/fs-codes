// Write a program that takes an IP address and subnet mask (in CIDR notation, 
// e.g., 192.168.1.1/24) as input and calculates the network and broadcast addresses.

// Input Format:
// ---------------
// A String, IPAddress
// An integer, CIDR

// Output Format:
// ---------------
// Space separated IP addresses, network IP and broadcast IP.


// Sample Input-1:
// -----------------
// 192.168.1.10
// 24

// Sample Output-1:
// ------------------
// 192.168.1.0 192.168.1.255


// Sample Input-2:
// -----------------
// 192.0.2.1
// 24

// Sample Output-2:
// ------------------
// 192.0.2.0 192.0.2.255













import java.util.*;


class Solution{
    static String toIp(long num){
        return ((num>>24)&255)+"."+((num>>16)&255)+"."+((num>>8)&255)+"."+(num&255);
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String[] s=sc.nextLine().split("\\.");
        int cidr=sc.nextInt();
        int m=32-cidr;
        int ip=0;
        for(int i=0;i<4;i++){
            ip=((ip<<8)|Integer.parseInt(s[i]));
        }
        long mask=(0xFFFFFFFFL<<(32-cidr));
        // System.out.println(mask);
        long network= ip&mask;
        long broadcast=network | (~mask&0xFFFFFFFFL);
        System.out.println(toIp(network));
        System.out.println(toIp(broadcast));
    }
}