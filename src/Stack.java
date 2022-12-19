public class Stack {
    private int stack;
    private boolean empty;

    public Stack() {
        this.empty = true;
    }

    public void push(int item) throws InterruptedException {
        synchronized (this) {
            if (!empty) {
                wait();
            }
            stack = item;
            empty = false;
            // with Deadlock
            //notify();
            // without Deadlock
            notifyAll();
        }
    }

    public void pop() throws InterruptedException {
        synchronized (this){
            if (empty) {
                wait();
            }
            empty = true;
            System.out.println(stack);
            // with Deadlock
            //notify();
            // with Deadlock
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack();
        Thread [] producers = new Thread[10];
        Thread [] consumers = new Thread[10];

        for (int i=0; i < 10; i++) {
            producers[i] = new Producer(stack);
        }

        for (int i=0; i < 10; i++) {
            consumers[i] = new Consumer(stack);
        }

        for (int i=0; i < 10; i++) {
            producers[i].start();
        }

        for (int i=0; i < 10; i++) {
            consumers[i].start();
        }

        for (int i=0; i < 10; i++) {
                producers[i].join();
        }

        for (int i=0; i < 10; i++) {
            consumers[i].join();
        }

    }
}

class Producer extends Thread {
    Stack stack;

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i=0; i < 10; i++) {
            try {
                stack.push(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i=0; i < 10; i++) {
            try {
                stack.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
