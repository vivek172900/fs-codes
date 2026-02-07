// You are given N customer orders that must be processed sequentially.
// Design a Java multithreading application using the Callable interface only, 
// with the following strict constraints:
//     - There is exactly ONE Producer task
//     - There is exactly ONE Consumer task
//     - The number of orders (N) and the list of orders are global shared objects
//     - The Producer and Consumer must not receive the orders directly
//     - The Producer and Consumer must communicate only through a shared handoff object
//     - There is NO buffer, queue, or collection used for intermediate storage

// Each order must be processed in the following strict order:
// Producer processes one order → Consumer processes the same order → then move to the next order

// The Producer must wait until the Consumer finishes processing the current order.
// The Consumer must wait until the Producer produces the next order.

// Order Information: Each order contains the following details:

// ----------------------------------------
// Field			Description
// ----------------------------------------
// orderId			Unique order identifier
// customerName	Name of the customer
// productName		Product ordered
// quantity		Number of items
// pricePerUnit	Cost per item
// totalAmount		quantity × price
// ----------------------------------------

// Input Format:
// -------------
// NUMBER_OF_ORDERS, N
// Next N lines: orderId customerName productName quantity pricePerUnit


// Sample Input:
// -------------
// 3
// 1001 Alice Laptop 1 75000
// 1002 Bob Phone 2 20000
// 1003 Charlie Tablet 1 30000

// Sample Output:
// --------------
// PRODUCED Order[ID=1001, Customer=Alice, Product=Laptop, Qty=1, Total=75000.0]
// CONSUMED Order[ID=1001, Total=75000.0]
// PRODUCED Order[ID=1002, Customer=Bob, Product=Phone, Qty=2, Total=40000.0]
// CONSUMED Order[ID=1002, Total=40000.0]
// PRODUCED Order[ID=1003, Customer=Charlie, Product=Tablet, Qty=1, Total=30000.0]
// CONSUMED Order[ID=1003, Total=30000.0]















import java.util.*;
import java.util.concurrent.*;

class Order{
    int orderId;
    String customerName;
    String productName;
    int quantity;
    int pricePerUnit;
    Order(){};
    Order(int oid,int cn,int pn,int q,double pp){
        orderId=oid;
        customerId=cn;
        productName=pn;
        quantity=q;
        pricePerUnit=pp;
    }
    double getTotalAmount(){
        return (double)quantity*pricePerUnit;
    }
}

class OrderConsumer implements Callable<Void> {
    Order o;
    OrderConsumer(){};
    OrderConsumer(Order o){
        this.o=o;
    }
    public String call() throws Exception{
        System.out.println("CONSUMED Order[ID="+o.orderId+", Total="+o.getTotalAmount()+"]");
        // return a;
    }
}

class OrderProducer implements Callable<List<Void>> {
    List<Order> ors=new ArrayList<>();
    OrderProducer(List<Order> orss){
        ors=orss;
    }
    public List<String> call() throws Exception{
        List<String> ans=new ArrayList<>();
        for(Order o:ors){
            System.out.println("PRODUCED Order[ID="+o.orderId+", Customer="+o.customerName+", Product="+o.productName+", Qty="+o.quantity+", Total="+o.getTotalAmount()+"]");
            new OrderConsumer(o);
        }
        return ans;
    }
}


class ProducerConsumer{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        List<order> orders=new ArrayList<>();
        for(int i=0;i<n;i++){
            orders.add(new Order(
                sc.nextInt(),
                sc.next(),
                sc.next(),
                sc.nextInt(),
                sc.nextDouble()
            ));
        }
        ExecutorService executor=Executors.new FixedThreadPool(2);
        long start =System.currentTimeMillis();
        Future<List<String>> pexecutor.submit(new OrderProducer(buffer,orders));
    }
}