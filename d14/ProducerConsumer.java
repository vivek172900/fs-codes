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

// ---------------- ORDER CLASS ----------------
class Order {
    int orderId;
    String customerName;
    String productName;
    int quantity;
    double pricePerUnit;

    Order(int orderId, String customerName, String productName,
          int quantity, double pricePerUnit) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    double getTotalAmount() {
        return quantity * pricePerUnit;
    }
}

// ---------------- HANDOFF (NO BUFFER) ----------------
class OrderHandoff {
    Order order;
    boolean available = false;
}

// ---------------- PRODUCER ----------------
class OrderProducer implements Callable<Void> {

    // GLOBAL shared orders
    static List<Order> orders;

    OrderHandoff handoff;

    OrderProducer(OrderHandoff handoff) {
        this.handoff = handoff;
    }

    @Override
    public Void call() throws Exception {
        for (Order o : orders) {
            synchronized (handoff) {
                while (handoff.available) {
                    handoff.wait();
                }

                handoff.order = o;
                handoff.available = true;

                System.out.println(
                    "PRODUCED Order[ID=" + o.orderId +
                    ", Customer=" + o.customerName +
                    ", Product=" + o.productName +
                    ", Qty=" + o.quantity +
                    ", Total=" + o.getTotalAmount() + "]"
                );

                handoff.notifyAll();
            }
        }
        return null;
    }
}

// ---------------- CONSUMER ----------------
class OrderConsumer implements Callable<Void> {

    OrderHandoff handoff;
    int totalOrders;

    OrderConsumer(OrderHandoff handoff, int totalOrders) {
        this.handoff = handoff;
        this.totalOrders = totalOrders;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 0; i < totalOrders; i++) {
            synchronized (handoff) {
                while (!handoff.available) {
                    handoff.wait();
                }

                Order o = handoff.order;

                System.out.println(
                    "CONSUMED Order[ID=" + o.orderId +
                    ", Total=" + o.getTotalAmount() + "]"
                );

                handoff.available = false;
                handoff.notifyAll();
            }
        }
        return null;
    }
}

// ---------------- MAIN CLASS ----------------
public class ProducerConsumer {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
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

        // GLOBAL shared object
        OrderProducer.orders = orders;

        OrderHandoff handoff = new OrderHandoff();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new OrderProducer(handoff));
        executor.submit(new OrderConsumer(handoff, n));

        executor.shutdown();
    }
}
