package com.example.threads;

public class RunnableDemo implements Runnable {
  private final String fileName;

  public RunnableDemo() { this.fileName = null; } // simpleDemo
  public RunnableDemo(String fileName) { this.fileName = fileName; } // fileDownloadUsingThreads

  @Override
  public void run() {
    if (fileName == null) {
      System.out.println("Thread (Runnable) running: " + Thread.currentThread().getName());
    } else {
      System.out.println("Downloading " + fileName + " in thread " + Thread.currentThread().getName());
      try { Thread.sleep(500); } catch (InterruptedException e) {}
      System.out.println("Downloaded " + fileName);
    }
  }

  public static void simpleDemo() {
    Thread t1 = new Thread(new RunnableDemo());
    Thread t2 = new Thread(new RunnableDemo());
    t1.start();
    t2.start();
  }

  public static void fileDownloadUsingThreads() {
    String[] files = {"File1", "File2", "File3", "File4"};
    for (String file : files) {
      new Thread(new RunnableDemo(file)).start();
    }
  }

  public static void main(String[] args) {
    System.out.println("===== Simple Runnable Demo =====");
    simpleDemo();

    System.out.println("\n===== File Download Demo =====");
    fileDownloadUsingThreads();
  }
}

