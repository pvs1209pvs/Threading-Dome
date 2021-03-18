package parallelfibonacci;

public class FibonacciWorker implements Runnable{

    public int answer = 0;
    private int term = 0;

    public FibonacciWorker(int term) {
        this.term = term;
    }

    @Override
    public void run() {
        this.answer = this.fib(term);
    }

    public int fib(int number){

        if(number == 1 || number == 2){
            return 1;
        }

        return fib(number-1) + fib(number-2);

    }
}
