package parallelstringsearcher;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StringSearcher {

    public static int FILES = 1000;
    public static int STRINGS_IN_FILE = 1000;
    public static int NUM_THREADS = 4;

    public static void parallelSearch(String probe) {

        System.out.println("Enter the number of files [1,1,000]");
        FILES = new Scanner(System.in).nextInt();

        System.out.println("Enter the number of files per file [1. 1,000]");
        STRINGS_IN_FILE = new Scanner(System.in).nextInt();

        System.out.println("Enter the number of threads [0, 4]");
        NUM_THREADS = new Scanner(System.in).nextInt();


        System.out.println("String Searcher with " + NUM_THREADS + " threads");

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        Collection<StringSearcherWorker> stringSearcherWorkers = new HashSet<>();
        for (int i = 0; i < FILES; i++) {
            stringSearcherWorkers.add(new StringSearcherWorker(i, probe));
        }

        try {
            executorService.invokeAll(stringSearcherWorkers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

    }

    static String search(String filename, String probe) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)))) {
            if (bufferedReader.lines().anyMatch(s -> s.equals(probe))) {
                return " Found in " + filename;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getProbe() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("files/file" + (int) (Math.random() * FILES))))) {
            return (String) bufferedReader.lines().toArray()[(int) (Math.random() * STRINGS_IN_FILE)];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static void randomTextFile(String filename, long numChars) {

        try (FileWriter fileWriter = new FileWriter(new File(filename))) {
            for (int i = 0; i < numChars; i++) {
                fileWriter.write(randomString());
                fileWriter.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String randomString() {

        StringBuilder stringBuilder = new StringBuilder();

        int stringLen = (int) (Math.random() * 15 + 5);

        for (int i = 0; i < stringLen; i++) {
            stringBuilder.append(randomPrintableChar());
        }

        return stringBuilder.toString();

    }

    private static char randomPrintableChar() {
        return (char) ((int) (Math.random() * 95 + 32));
    }

}
