// 1️⃣ Single-Slot 3-Stage Pipeline

// You are given N tasks.
// Each task must go through exactly 3 stages in strict order:

// Stage-1 → Stage-2 → Stage-3

// Constraints

// Exactly 3 threads (one per stage)

// No buffer / queue / list

// Only ONE shared handoff object

// A task must complete Stage-1 before Stage-2 starts

// A task must complete Stage-2 before Stage-3 starts

// Tasks must be processed one at a time

// Sample Input
// 3
// TaskA
// TaskB
// TaskC

// Sample Output
// STAGE1 processed TaskA
// STAGE2 processed TaskA
// STAGE3 processed TaskA
// STAGE1 processed TaskB
// STAGE2 processed TaskB
// STAGE3 processed TaskB
// STAGE1 processed TaskC
// STAGE2 processed TaskC
// STAGE3 processed TaskC


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

class Task{
    String name;
    Task(){};
    Task(String n){
        name=n;
    }
}

class handOff{
    Task t;
    boolean a=false;
    boolean b=false;
    boolean c=false;
}

class Stage1 implements Callable<Void> {
    handOff handoff;
    static List<Task> lt;
    Stage1(){};
    Stage1(handOff h,List<Task> l){
        handoff=h;
        lt=l;
    }
    public Void call() throws Exception {
        for(Task task:lt){
            synchronized (handoff){
                handoff.t=task;
                handoff.a=true;
                System.out.println("STAGE1 processed "+task.name);
                handoff.notifyAll();
            }
        }
        return null;
    }
}
class Stage2 implements Callable<Void> {
    handOff handoff;
    static List<Task> lt;
    Stage2(){};
    Stage2(handOff h,List<Task> l){
        handoff=h;
        lt=l;
    }
    public Void call() throws Exception {
        for(Task task:lt){
            synchronized (handoff){
                while(handoff.a){
                    handoff.wait();
                }
                handoff.t=task;
                handoff.b=true;
                System.out.println("STAGE2 processed "+task.name);
                handoff.notifyAll();
            }
        }
        return null;
    }
}
class Stage3 implements Callable<Void> {
    handOff handoff;
    static List<Task> lt;
    Stage3(){};
    Stage3(handOff h,List<Task> l){
        handoff=h;
        lt=l;
    }
    public Void call() throws Exception {
        for(Task task:lt){
            synchronized (handoff){
                while(handoff.a && handoff.b){
                    handoff.wait();
                }
                handoff.t=task;
                handoff.c=true;
                System.out.println("STAGE3 processed "+task.name);
                handoff.notifyAll();
            }
        }
        return null;
    }
}

class m3{
    public static void main(String[] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        List<Task> arr=new ArrayList<>();
        for(int i=0;i<n;i++){
            arr.add(new Task(sc.next()));
        }
        Stage1.lt=arr;
        handOff handoff=new handOff();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Stage1(handoff,arr));
        executor.submit(new Stage2(handoff,arr));
        executor.submit(new Stage3(handoff,arr));

    }
}

