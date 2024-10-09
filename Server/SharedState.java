package Server;

// import java.net.*;
// import java.io.*;

public class SharedState {
    String threadName;
    Account account; // shared object for each client
    private volatile boolean accessing = false; // volatile can help ensure that updates to the variable are visible to all threads
    int threadsWaiting = 0;

    SharedState(Account account) {
        this.account = account;
    }

    public synchronized void acquireLock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " is attemting to acquire a lock!");
     
        threadsWaiting++;   
        while (accessing) {
            wait(1000); // wait for 1 second before rechecking
            System.out.println(thread.getName() + " waiting to get a lock as someone else is accessing...");
            wait();
        }
        threadsWaiting--;
        accessing = true;
        System.out.println(thread.getName() + " got a lock");
    }

    public synchronized void releaseLock() {
        accessing = false;
        notifyAll();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " released a lock");
    }
}
