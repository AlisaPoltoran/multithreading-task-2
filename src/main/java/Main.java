import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callableTask = () -> {
            int counter = 0;
            for (int i = 0; i < 5; i++) {
                System.out.println("Всем привет, я поток " + Thread.currentThread().getName());
                counter++;
            }
            return counter;
        };

        List<Callable<Integer>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Future<Integer>> futures = executor.invokeAll(callableTasks);
        for (Future<Integer> future : futures) {
            System.out.println(future.get());
        }

//        int result = executor.invokeAny(callableTasks);
//        System.out.println("Результат одной задачи: " + result);

        executor.shutdown();

        try {
            if (!executor.awaitTermination(800, TimeUnit.MICROSECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

    }
}
