package parallelfibonacci;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fibonacci {

    private static int NTH_TERM;
    private static int NUM_THREADS;

    public static long fib() {

        System.out.println("Enter n-th term whose Fibonacci you wish you calculate [10, 50]");
        NTH_TERM = new Scanner(System.in).nextInt();

        System.out.println("Enter the number of threads [0, 4]");
        NUM_THREADS = new Scanner(System.in).nextInt();

        System.out.println("Fibonacci with " + NUM_THREADS + " threads");

        List<FibonacciWorker> fibonacciWorkers = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            fibonacciWorkers.add(new FibonacciWorker(NTH_TERM - (NUM_THREADS - 1) - i));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(fibonacciWorkers.get(i));
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) break;
        }

        switch (NUM_THREADS) {
            case 1:
                return fibonacciWorkers.get(0).answer;
            case 2:
                return fibonacciWorkers.get(0).answer + fibonacciWorkers.get(1).answer;
            case 3:
                return fibonacciWorkers.get(0).answer + 2*fibonacciWorkers.get(1).answer + fibonacciWorkers.get(2).answer;
            case 4:
                return fibonacciWorkers.get(0).answer + 3*fibonacciWorkers.get(1).answer + 3*fibonacciWorkers.get(2).answer + fibonacciWorkers.get(3).answer;
            default:
                return -1;
        }

    }

}
