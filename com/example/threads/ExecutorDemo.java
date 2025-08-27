package com.example.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {

  public static void simpleDemo() {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    try {
      // executor.submit(task)
      // task: can be a Runnable or a Callable object

      // submit a Runnable using lambda
      executor.submit(() -> System.out.println("Executor thread running: " + Thread.currentThread().getName()));
      executor.submit(() -> System.out.println("Another Executor thread: " + Thread.currentThread().getName()));

      // submit a Runnable instance
      executor.submit(new RunnableDemo());

      // submit a Callable instance
      callableExample(executor);
    } finally {
      executor.shutdown();
      try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
  }

  private static void callableExample(ExecutorService executor) {
    try {
      Callable<String> callableTask = () -> {
        System.out.println("Callable running in: " + Thread.currentThread().getName());
        Thread.sleep(500);
        return "Callable Result";
      };
      Future<String> future = executor.submit(callableTask);
      String result = future.get(); // retrieve the result
      System.out.println(result);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Callable was interrupted: " + e.getMessage());
    } catch (ExecutionException e) {
      System.err.println("Exception in Callable task: " + e.getCause());
    }
  }

  public static void fileDownloadUsingThreads() {
    String[] files = {"File1", "File2", "File3", "File4"};
    ExecutorService executor = Executors.newFixedThreadPool(4);
    try {
      for (String file : files) {
        executor.submit(() -> {
          System.out.println("Downloading " + file + " in thread " + Thread.currentThread().getName());
          try { Thread.sleep(500); } catch (InterruptedException e) {}
          System.out.println("Downloaded " + file);
        });
      }
    } finally {
      executor.shutdown();
      try { executor.awaitTermination(2, TimeUnit.SECONDS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
  }

  public static void main(String[] args) {
    System.out.println("===== ExecutorService Simple Demo =====");
    simpleDemo();

    System.out.println("\n===== File Download Demo =====");
    fileDownloadUsingThreads();
  }
}
