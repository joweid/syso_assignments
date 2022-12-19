package u3;

import java.util.concurrent.Semaphore;

public class ThreadSynchro {
    public static void main(String[] args) throws InterruptedException {
        /*Semaphore oneToTwo = new Semaphore(1);
        Semaphore oneToThree = new Semaphore(1);
        Semaphore oneToFour = new Semaphore(1);
        Semaphore twoToFive = new Semaphore(1);
        Semaphore threeToFive = new Semaphore(1);
        Semaphore fourToFive = new Semaphore(1);
        Semaphore fiveToOne = new Semaphore(1);*/

        CustomSemaphore beginner = new CustomSemaphore();
        CustomSemaphore oneToTwo = new CustomSemaphore();
        CustomSemaphore oneToThree = new CustomSemaphore();
        CustomSemaphore oneToFour = new CustomSemaphore();
        CustomSemaphore twoToFive = new CustomSemaphore();
        CustomSemaphore threeToFive = new CustomSemaphore();
        CustomSemaphore fourToFive = new CustomSemaphore();
        CustomSemaphore fiveToOne = new CustomSemaphore();

        Thread1 a1 = new Thread1();
        Thread2 a2 = new Thread2();
        Thread3 a3 = new Thread3();
        Thread4 a4 = new Thread4();
        Thread5 a5 = new Thread5();

        a1.oneToTwo = oneToTwo;
        a1.oneToThree = oneToThree;
        a1.oneToFour = oneToFour;
        a1.fiveToOne = fiveToOne;

        a2.oneToTwo = oneToTwo;
        a2.twoToFive = twoToFive;

        a3.oneToThree = oneToThree;
        a3.threeToFive = threeToFive;

        a4.oneToFour = oneToFour;
        a4.fourToFive = fourToFive;

        a5.twoToFive = twoToFive;
        a5.threeToFive = threeToFive;
        a5.fourToFive = fourToFive;
        a5.fiveToOne = fiveToOne;

        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();

        a1.join();
        a2.join();
        a3.join();
        a4.join();
        a5.join();
    }
}

class Thread1 extends Thread {
    /*public Semaphore oneToTwo;
    public Semaphore oneToThree;
    public Semaphore oneToFour;
    public Semaphore fiveToOne;

    @Override
    public void run() {
        try {
            fiveToOne.acquire();
            System.out.println("A1");
            oneToTwo.release();
            oneToThree.release();
            oneToFour.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public CustomSemaphore oneToTwo;
    public CustomSemaphore oneToThree;
    public CustomSemaphore oneToFour;
    public CustomSemaphore fiveToOne;

    @Override
    public void run() {
        try {
            fiveToOne.p();
            System.out.println("A1");
            oneToTwo.v();
            oneToThree.v();
            oneToFour.v();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread2 extends Thread {
    /*public Semaphore oneToTwo;
    public Semaphore twoToFive;
    @Override
    public void run() {
        try {
            oneToTwo.acquire();
            System.out.println("A2");
            twoToFive.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public CustomSemaphore oneToTwo;
    public CustomSemaphore twoToFive;
    @Override
    public void run() {
        try {
            oneToTwo.p();
            System.out.println("A2");
            twoToFive.v();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread3 extends Thread {
    /*public Semaphore oneToThree;
    public Semaphore threeToFive;
    @Override
    public void run() {
        try {
            oneToThree.acquire();
            System.out.println("A3");
            threeToFive.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public CustomSemaphore oneToThree;
    public CustomSemaphore threeToFive;
    @Override
    public void run() {
        try {
            oneToThree.p();
            System.out.println("A3");
            threeToFive.v();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread4 extends Thread {
    /*public Semaphore oneToFour;
    public Semaphore fourToFive;
    @Override
    public void run() {
        try {
            oneToFour.acquire();
            System.out.println("A4");
            fourToFive.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public CustomSemaphore oneToFour;
    public CustomSemaphore fourToFive;
    @Override
    public void run() {
        try {
            oneToFour.p();
            System.out.println("A4");
            fourToFive.v();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread5 extends Thread {
    /*public Semaphore twoToFive;
    public Semaphore threeToFive;
    public Semaphore fourToFive;
    public Semaphore fiveToOne;
    @Override
    public void run() {
        try {
            twoToFive.acquire();
            threeToFive.acquire();
            fourToFive.acquire();
            System.out.println("A5");
            fiveToOne.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public CustomSemaphore twoToFive;
    public CustomSemaphore threeToFive;
    public CustomSemaphore fourToFive;
    public CustomSemaphore fiveToOne;
    @Override
    public void run() {
        try {
            twoToFive.p();
            threeToFive.p();
            fourToFive.p();
            System.out.println("A5");
            fiveToOne.v();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CustomSemaphore {
    private int value;

    public CustomSemaphore() {
        value = 1;
    }

    public synchronized void p() throws InterruptedException {
        value--;
        if (value == 0) {
            wait();
        }
    }

    public synchronized void v() {
        value++;
        notify();
    }

}
