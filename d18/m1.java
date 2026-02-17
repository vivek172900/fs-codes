// A smart factory has a central alarm system that coordinates three independent 
// subsystems:
//     - Thread A – Alarm Controller -> Activates a warning beep (prints 0).
//     - Thread B – Even Sensor Unit -> Reports only even-numbered machine IDs.
//     - Thread C – Odd Sensor Unit -> Reports only odd-numbered machine IDs.

// The system must produce an alternating sequence:
// 0 1 0 2 0 3 0 4 0 5 ...


// The total length of the sequence must be 2n, where: n is the total number of 
// machine IDs (from 1 to n). Each machine report must always be preceded by a 
// warning beep (0).

// Implement a class FactoryAlarmSystem that ensures proper synchronization between 
// three threads so that: The alarm thread always prints 0, Then either the odd or 
// even thread prints the next correct number. Continue this pattern until all 
// numbers from 1 to n are printed

// only

// 🧾 Input Format
// ---------------
// A single integer: N

// 🖨 Output Format
// ----------------
// A continuous string representing the alarm and machine sequence.

// Sample Input-1:
// ---------------
// 2

// Sample Output-1:
// ----------------
// 0102

// Sample Input-2:
// ---------------
// 5

// Sample Output-2:
// ----------------
// 0102030405














import java.util.*;

class shared{
    int n;
    int prev;
    boolean zero;
    shared(int n){
        prev=0;
        this.n=n;
        zero=true;
    }
    public synchronized int finalVal(){
        return n;
    }
    public synchronized int getVal(){
        return prev;
    }
    public synchronized void m1(){
        if(zero){
            if(prev==n) {
                prev++;
                return ;
            }
            System.out.print(0);
            // if(prev==n) break;
            prev++;
            zero=false;
            notifyAll();
        }
    }
    public synchronized void m2(){
        if(!zero && prev%2==0){
            zero=true;
            System.out.print(prev);
            notifyAll();
        }
    }
    public synchronized void m3(){
        if(!zero && prev%2==1){
            zero=true;
            System.out.print(prev);
            notifyAll();
        }
    }
}

class Thread1 implements Runnable{
    shared sh;
    Thread1(shared sh){
        this.sh=sh;
    }
    public void run(){
        while(true){
            synchronized(sh){
                if(sh.getVal()>sh.finalVal()) break;
                sh.m1();
            }
        }
    }
}
class Thread2 implements Runnable{
    shared sh;
    Thread2(shared sh){
        this.sh=sh;
    }
    public void run(){
        while(true){
            synchronized(sh){
                if(sh.getVal()>sh.finalVal()) break;
                sh.m2();
            }
        }
    }
}
class Thread3 implements Runnable{
    shared sh;
    Thread3(shared sh){
        this.sh=sh;
    }
    public void run(){
        while(true){
            synchronized(sh){
                if(sh.getVal()>sh.finalVal()) break;
                sh.m3();
            }
        }
    }
}

class Solution{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        shared sh=new shared(n);
        Thread t1=new Thread(new Thread1(sh));
        Thread t2=new Thread(new Thread2(sh));
        Thread t3=new Thread(new Thread3(sh));
        t1.start();
        t2.start();
        t3.start();
    }
}