package main;

import parallelbubblesort.ParallelBubbleSort;
import parallelfibonacci.Fibonacci;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static parallelstringsearcher.StringSearcher.*;

public class Main {

    private static final int RUNS = 1;
    private double totalTime = 0.0;

    private enum Algorithm {
        STRING_SEARCHER, FIBONACCI, BUBBLE_SORT
    }

    private Main() {

        Algorithm[] algorithms = new Algorithm[]{Algorithm.STRING_SEARCHER, Algorithm.FIBONACCI, Algorithm.BUBBLE_SORT};

        System.out.println("Choose an algorithm");
        for (int i = 0; i < algorithms.length; i++) {
            System.out.println("("+i+")" + " " + algorithms[i]);
        }

        Algorithm algorithm = algorithms[new Scanner(System.in).nextInt()];

        for (int i = 0; i < RUNS; i++) {
            algoChooser(algorithm);
        }

        System.out.println(algorithm + " time " + totalTime / RUNS);

    }

    private void algoChooser(Algorithm algorithm) {

        switch (algorithm) {

            case STRING_SEARCHER: {

                for (int j = 0; j < FILES; j++) {
                    randomTextFile("files/file" + j, STRINGS_IN_FILE);
                }

                String PROBE = getProbe();

                long startTime = System.nanoTime();

                parallelSearch(PROBE);

                long duration = System.nanoTime() - startTime;
                duration = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
                totalTime += duration;

                break;
            }
            case FIBONACCI: {

                long startTime = System.nanoTime();

                Fibonacci.fib();

                long duration = System.nanoTime() - startTime;
                duration = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
                totalTime += duration;

                break;
            }
            case BUBBLE_SORT: {

                long startTime = System.nanoTime();

                new ParallelBubbleSort();

                long duration = System.nanoTime() - startTime;
                duration = TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS);
                totalTime += duration;

                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + algorithm);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
