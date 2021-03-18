package parallelbubblesort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBubbleSort {


    List<Integer> v = new ArrayList<>();
    private static int NUM_ELEMENTS;
    private static int NUM_THREADS;

    public ParallelBubbleSort() {

        System.out.println("Enter the number of elements to be sorted [1, 50,000]");
        NUM_ELEMENTS = new Scanner(System.in).nextInt();

        System.out.println("Enter the number of threads.");
        NUM_THREADS = new Scanner(System.in).nextInt();

        for (int i = 0; i < NUM_ELEMENTS + 1; i++) {
            v.add((int) (Math.random() * 500 + 1));
        }

        this.bubbleSortParallel();

    }

    public void bubbleSortParallel() {

        System.out.println("Bubble Sort with " + NUM_THREADS + " threads");

        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < v.size(); i++) {
            if (i % 2 != 0) service.execute(this::oddPhase);
            if (i % 2 == 0) service.execute(this::evenPhase);
        }

        service.shutdown();
        while(!service.isTerminated()){}

    }

    private void oddPhase() {
        for (int j = 0; j < v.size() / 2; j++) {
            int x = 2 * j + 1;
            int y = 2 * j + 2;
            if (v.get(x) > v.get(y)) {
                Collections.swap(v, x, y);
            }
        }
    }

    private void evenPhase() {
        for (int j = 0; j < v.size() / 2; j++) {
            int x = 2 * j;
            int y = 2 * j + 1;
            if (v.get(x) > v.get(y)) {
                Collections.swap(v, x, y);
            }
        }
    }

}
