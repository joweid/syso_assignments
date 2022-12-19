public class SumThread extends Thread {
    static int sum = 0;
    int limit = 20;
    static int index = 0;

    @Override
    public void run() {
        synchronized (this){
            while (index < limit){
                sum += index;
                index += 2;
            }
            printSum();
            sum = 0;
            index = 0;
        }

    }

    void printSum(){
        System.out.printf("The sum of %s: %d\n", Thread.currentThread().getName(), sum);
    }

    public static void main(String[] args) throws InterruptedException {
        final int number_of_threads = 4;
        Thread[] threads = new Thread[number_of_threads];

        for (int i=0; i < number_of_threads; i++){
            threads[i] = new SumThread();
        }

        for (int i=0; i < number_of_threads; i++){
            threads[i].start();
        }

        for (int i=0; i < number_of_threads; i++) {
            threads[i].join();
        }
    }
}
