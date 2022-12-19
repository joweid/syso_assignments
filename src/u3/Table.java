package u3;

public class Table {
    // true -> Gabel besetzt; false -> Gabel frei
    boolean [] forks;

    public Table (int numberOfPhilosophers) {
        forks = new boolean[numberOfPhilosophers];
        for (int i=0; i < forks.length; i++) {
            forks[i] = false;
        }
    }

    public synchronized void takeFork (int philNum) throws InterruptedException {
        int posLeftFork = philNum;
        int posRightFork = (philNum + 1) % this.forks.length;

        while (this.forks[posLeftFork] && this.forks[posRightFork]) {
            wait();
        }
        this.forks[posLeftFork] = true;
        this.forks[posRightFork] = true;

    }

    public synchronized void putFork (int philNum) {
        int posLeftFork = philNum;
        int posRightFork = (philNum + 1) % this.forks.length;

        this.forks[posLeftFork] = false;
        this.forks[posRightFork] = false;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException {
        int numberOfPhilosophers = 5;
        Table table = new Table(numberOfPhilosophers);
        Philosopher [] philosophers = new Philosopher[numberOfPhilosophers];
        philosophers[0] = new Philosopher(0, table);
        philosophers[1] = new Philosopher(1, table);
        philosophers[2] = new Philosopher(2, table);
        philosophers[3] = new Philosopher(3, table);
        philosophers[4] = new Philosopher(4, table);

        for (int i=0; i < numberOfPhilosophers; i++) {
            philosophers[i].start();
        }

        for (int i=0; i < numberOfPhilosophers; i++) {
            philosophers[i].join();
        }
    }
}

class Philosopher extends Thread{
    private int position;
    private Table table;

    public Philosopher (int position, Table table) {
        this.position = position;
        this.table = table;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                table.takeFork(position);
                eat();
                table.putFork(position);
            }
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void think () throws InterruptedException {
        System.out.printf("Philosoph an Position %s denkt gerade...\n", position);
        sleep(5000);
    }

    public void eat () throws InterruptedException {
        System.out.printf("Philosoph an Position %s beginnt zu essen...\n", position);
        sleep(5000);
        System.out.printf("Philosoph an Position %s ist fertig mit Essen...\n", position);
    }
}
