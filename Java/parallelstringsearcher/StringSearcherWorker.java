package parallelstringsearcher;

import java.util.concurrent.Callable;

public class StringSearcherWorker implements Callable<StringSearcherWorker> {

    private final String PROBE;
    private final int FILE_ID;

    public StringSearcherWorker(int FILE_ID, String PROBE) {
        this.PROBE = PROBE;
        this.FILE_ID = FILE_ID;
    }

    @Override
    public StringSearcherWorker call() {

        StringSearcher.search("files/file" + FILE_ID, PROBE);
        return new StringSearcherWorker(FILE_ID, PROBE);

    }

}
