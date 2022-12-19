package u2;

public class Semaphore {
    private int counter;

    public Semaphore() {
        counter = 1;
    }

    public Semaphore(int initial_counter) {
        counter = initial_counter;
    }

    public synchronized void p() throws InterruptedException {
        while (counter == 0){wait();}
        counter--;
    }

    public synchronized void v() {
        counter++;
        notify();
    }

    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack(10);
        int thread_count = 10;
        int iterations = 5;
        Thread [] consumers = new Thread[thread_count];
        Thread [] producers = new Thread[thread_count];

        for (int i=0; i < thread_count; i++) {
            producers[i] = new Producer(stack, iterations);
            consumers[i] = new Consumer(stack, iterations);
        }

        for (int i=0; i < thread_count; i++) {
            producers[i].join();
            consumers[i].join();
        }

        Consumer.printSum();
    }
}

class Stack {
    private int [] stack;
    private int free = 0;
    private int n;
    private Semaphore mutex;
    private Semaphore empty;
    private Semaphore full;

    public Stack(int n) {
        stack = new int[n];
        this.n = n;
        mutex = new Semaphore(1);
        empty = new Semaphore(n);
        full = new Semaphore(0);
    }

    public void push(int item) throws InterruptedException {
        empty.p();
        mutex.p();
        stack[free++] = item;
        mutex.v();
        full.v();
    }

    public int pop() throws InterruptedException {
        full.p();
        mutex.p();
        int item = stack[--free];
        mutex.v();
        empty.v();
        return item;
    }
}

class Producer extends Thread {
    private Stack stack;
    private int iterations;

    public Producer(Stack stack, int iterations) {
        this.stack = stack;
        this.iterations = iterations;
        this.start();
    }

    @Override
    public void run() {
        for (int i=0; i < iterations; i++) {
            try {
                stack.push(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private Stack stack;
    private static int sum = 0;
    private int iterations;

    public Consumer(Stack stack, int iterations) {
        this.stack = stack;
        this.iterations = iterations;
        this.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                sum += stack.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void printSum() {
        System.out.println(sum);
    }
}

