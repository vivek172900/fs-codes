// You are developing a multi-threaded backend system for an e-commerce platform.

// The platform receives customer orders from multiple sources (mobile app, website, partner APIs).
// These orders must be queued, processed, and dispatched concurrently.

// To ensure scalability:
// 	- Order creation and order processing run in parallel
// 	- Orders are exchanged through a shared buffer with limited capacity

// Order Information: Each order contains the following details:

// --------------------------------------------------
// Field						Description
// --------------------------------------------------
// orderId					Unique order identifier
// customerName	Name of the customer
// productName		Product ordered
// quantity					Number of items
// pricePerUnit			Cost per item
// totalAmount			quantity × price
// --------------------------------------------------

// Input Format:
// -----------------
// BUFFER_CAPACITY
// NUMBER_OF_ORDERS, N
// Next N lines:
// orderId customerName productName quantity pricePerUnit


// Sample Input:
// -----------------
// 3
// 4
// 1001 Alice Laptop 1 75000
// 1002 Bob Phone 2 20000
// 1003 Charlie Tablet 1 30000
// 1004 Diana Headphones 2 5000

// Sample Output:
// -------------------
// PRODUCED Order[ID=1001, Customer=Alice, Product=Laptop, Qty=1, Total=75000.0]
// PRODUCED Order[ID=1002, Customer=Bob, Product=Phone, Qty=2, Total=40000.0]
// PRODUCED Order[ID=1003, Customer=Charlie, Product=Tablet, Qty=1, Total=30000.0]
// PRODUCED Order[ID=1004, Customer=Diana, Product=Headphones, Qty=2, Total=10000.0]
// CONSUMED Order[ID=1001, Total=75000.0]
// CONSUMED Order[ID=1002, Total=40000.0]
// CONSUMED Order[ID=1003, Total=30000.0]
// CONSUMED Order[ID=1004, Total=10000.0]





















import java.util.*;
import java.util.concurrent.*;

// Sample Input:
// -----------------
// 3
// 4
// 1001 Alice Laptop 1 75000
// 1002 Bob Phone 2 20000
// 1003 Charlie Tablet 1 30000
// 1004 Diana Headphones 2 5000

// Sample Output:
// -------------------
// PRODUCED Order[ID=1001, Customer=Alice, Product=Laptop, Qty=1, Total=75000.0]
// PRODUCED Order[ID=1002, Customer=Bob, Product=Phone, Qty=2, Total=40000.0]
// PRODUCED Order[ID=1003, Customer=Charlie, Product=Tablet, Qty=1, Total=30000.0]
// PRODUCED Order[ID=1004, Customer=Diana, Product=Headphones, Qty=2, Total=10000.0]
// CONSUMED Order[ID=1001, Total=75000.0]
// CONSUMED Order[ID=1002, Total=40000.0]
// CONSUMED Order[ID=1003, Total=30000.0]
// CONSUMED Order[ID=1004, Total=10000.0]

class OrderBuffer{
    private final BlockingQueue<Order> queue;
    OrderBuffer(int cap){
        queue=new ArrayBlockingQueue<>(cap);
    }
    void produce(Order order) throws InterruptedException{
        queue.put(order);
    }
    Order consume() throws InterruptedException {
        return queue.take();
    }
}

class Order{
    // orderId customerName productName quantity pricePerUnit
    int orderId;
    String customerName;
    String productName;
    int quantity;
    double pricePerUnit;
    Order(){};
    Order(int oid,String cn,String pn,int q,double pp){
        orderId=oid;
        customerName=cn;
        productName=pn;
        quantity=q;
        pricePerUnit=pp;
    }
    double getTotalAmount(){
        return (double)quantity*pricePerUnit;
    }
}

class OrderProducer implements Callable<List<String>>{
    // int count;
    OrderBuffer buffer;
    List<Order> ors=new ArrayList<>();
    
    OrderProducer(OrderBuffer b,List<Order> orss){
        buffer=b;
        ors=orss;
    }
    public List<String> call() throws Exception{
        List<String> ans=new ArrayList<>();
        for(int i=0;i<ors.size();i++){
            buffer.produce(ors.get(i));
            double a=((double)ors.get(i).quantity)*ors.get(i).pricePerUnit;
            ans.add("PRODUCED Order[ID="+ors.get(i).orderId+", Customer="+ors.get(i).customerName+", Product="+ors.get(i).productName+", Qty="+ors.get(i).quantity+", Total="+a+"]");
        }
        return ans;
    }
}

class OrderConsumer implements Callable<List<String>> {
    OrderBuffer buffer;
    int n;
    OrderConsumer(OrderBuffer b,int n1){
        n=n1;
        buffer=b;
    }
    public List<String> call() throws Exception{
        List<String> logs=new ArrayList<>();
        for(int i=0;i<n;i++){
            Order o=buffer.consume();
            logs.add("CONSUMED Order[ID="+o.orderId+", Total="+o.getTotalAmount()+"]");
        }
        return logs;
    }
}

public class ProducerConsumerCallableDemo {
    

    /* ==========================
       IMPLEMENT YOUR CODE HERE
       ========================== */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        int bufferCapacity = sc.nextInt();
        int n = sc.nextInt();

        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            orders.add(new Order(
                    sc.nextInt(),
                    sc.next(),
                    sc.next(),
                    sc.nextInt(),
                    sc.nextDouble()
            ));
        }

        OrderBuffer buffer = new OrderBuffer(bufferCapacity);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        Future<List<String>> producerFuture =
                executor.submit(new OrderProducer(buffer, orders));

        Future<List<String>> consumerFuture =
                executor.submit(new OrderConsumer(buffer, n));

        /* ---- PHASE 1: PRINT PRODUCERS ---- */
        for (String log : producerFuture.get()) {
            System.out.println(log);
        }

        /* ---- PHASE 2: PRINT CONSUMERS ---- */
        for (String log : consumerFuture.get()) {
            System.out.println(log);
        }

        executor.shutdown();
        sc.close();
    }
}
