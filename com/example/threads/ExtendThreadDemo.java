package com.example.threads;

public class ExtendThreadDemo extends Thread {
  private final String fileName;

  public ExtendThreadDemo() { this.fileName = null; } // for simpleDemo
  public ExtendThreadDemo(String fileName) { this.fileName = fileName; } // for fileDownloadUsingThreads

  @Override
  public void run() {
    if (fileName == null) {
      System.out.println("Thread (extends Thread) running: " + Thread.currentThread().getName());
    } else {
      System.out.println("Downloading " + fileName + " in thread " + Thread.currentThread().getName());
      try { Thread.sleep(500); } catch (InterruptedException e) {}
      System.out.println("Downloaded " + fileName);
    }
  }

  public static void simpleDemo() {
    ExtendThreadDemo t1 = new ExtendThreadDemo();
    ExtendThreadDemo t2 = new ExtendThreadDemo();
    t1.start();
    t2.start();
  }

  public static void fileDownloadUsingThreads() {
    String[] files = {"File1", "File2", "File3", "File4"};
    for (String file : files) {
      new ExtendThreadDemo(file).start();
    }
  }

  public static void main(String[] args) {
    System.out.println("===== Simple Thread Demo =====");
    simpleDemo();

    System.out.println("\n===== File Download Demo =====");
    fileDownloadUsingThreads();
  }
}
