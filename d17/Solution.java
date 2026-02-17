import java.util.*;

class Shared{
    int i;
    int n;

    Shared(int n){
        this.n = n;
        i = 1;
    }

    public synchronized void increment(){
        i++;
        notifyAll();
    }

    public synchronized int getVal(){
        return i;
    }

    public int getFinal(){
        return n;
    }
}

class Thread1 implements Runnable{
    Shared sh;

    Thread1(Shared sh){
        this.sh = sh;
    }

    public void run(){
        while (true){
            synchronized (sh){
                if (sh.getVal() > sh.getFinal()) break;
                int n = sh.getVal();
                if (n%3==0 && n%5!=0){
                    System.out.print("Hi ");
                    sh.increment();
                }
                else{
                    try{
                        sh.wait();
                    }
                    catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Thread2 implements Runnable{
    Shared sh;

    Thread2(Shared sh){
        this.sh = sh;
    }

    public void run(){
        while (true){
            synchronized (sh){
                if (sh.getVal() > sh.getFinal()) break;
                int n = sh.getVal();
                if (n%3!=0 && n%5==0){
                    System.out.print("Hello ");
                    sh.increment();
                }
                else{
                    try{
                        sh.wait();
                    }
                    catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Thread3 implements Runnable{
    Shared sh;

    Thread3(Shared sh){
        this.sh = sh;
    }

    public void run(){
        while (true){
            synchronized (sh){
                if (sh.getVal() > sh.getFinal()) break;
                int n = sh.getVal();
                if (n%3==0 && n%5==0){
                    System.out.print("HiHello ");
                    sh.increment();
                }
                else{
                    try{
                        sh.wait();
                    }
                    catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

class Thread4 implements Runnable{
    Shared sh;

    Thread4(Shared sh){
        this.sh = sh;
    }

    public void run(){
        while (true){
            synchronized (sh){
                if (sh.getVal() > sh.getFinal()) break;
                int n = sh.getVal();
                if (n%3!=0 && n%5!=0){
                    System.out.print(n + " ");
                    sh.increment();
                }
                else{
                    try{
                        sh.wait();
                    } 
                    catch (InterruptedException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

public class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        Shared sh = new Shared(n);

        Thread t1 = new Thread(new Thread1(sh));
        Thread t2 = new Thread(new Thread2(sh));
        Thread t3 = new Thread(new Thread3(sh));
        Thread t4 = new Thread(new Thread4(sh));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}