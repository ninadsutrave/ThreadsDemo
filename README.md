# Java Threads and ExecutorService Demo

This project demonstrates **different ways of creating and running threads in Java**, including **manual threads**, **Runnable**, **ExecutorService**, and **Callable tasks**. It also includes a practical use case of **downloading multiple files in parallel**.

---

## 1. Creating Threads in Java

### Way 1: Extending `Thread`
- Define a class `ExtendThreadDemo`
- Extend the `Thread` class and override the `run()` method.
- Create an instance of `ExtendThreadDemo`
- Call `start()` to execute the thread.
- Threads terminate automatically after `run()` finishes, thus cannot be reused.
- **Usage:** Simple, one-off threads.

### Way 2: Implementing `Runnable`
- Define a class `RunnableDemo`
- Implement the `Runnable` interface and override the `run()` method.
- Create an instance of `RunnableDemo`
- Pass the instance tot a `Thread` constructor and call `start()`.
- Threads terminate automatically after `run()` finishes, thus cannot be reused.
- **Usage:** Separates task logic from thread management.

### Way 3: Using ExecutorService
- Define a class `ExecutorDemo`
- Create a new Fixed Thread Pool using `Executors`
- Submit tasks to a thread pool instead of creating threads manually.
- **Runnable:** For tasks that do not return a result.
- **Callable:** For tasks that return a value or throw exceptions.
- `ExecutorService` manages thread creation and reuse, and termination.
- `ExecutorService` must be shutdown after use.

---

## 3. Different Ways of Passing Tasks to ExecutorService

1. **Lambda Expression**
```java
executor.submit(() -> System.out.println("Lambda Runnable running"));
```

2. **Pass Runnable Instance**
```java
executor.submit(new RunnableDemo());
```

3. **Pass Callable Instance**
```java
Callable<Integer> task = () -> 2 + 3;
Future<Integer> future = executor.submit(task);
System.out.println("Callable result: " + future.get());
```

4. **Anonymous Inner Class**
```java
executor.submit(new Runnable() {
    @Override
    public void run() {
        System.out.println("Anonymous Runnable running");
    }
});
```

## Comparison Table

| Feature           | Runnable                                                                 | Callable                                                                    |
|------------------|-------------------------------------------------------------------------|----------------------------------------------------------------------------|
| **Interface**     | `@FunctionalInterface public interface Runnable { void run(); }`        | `@FunctionalInterface public interface Callable<V> { V call() throws Exception; }` |
| **Method**        | `void run()` – does not return any value.                               | `V call()` – returns a value of type `V`.                                   |
| **Return Value**  | None                                                                    | Returns a result. Useful when the task produces output.                    |
| **Exceptions**    | Cannot throw checked exceptions. Must handle them internally.          | Can throw checked exceptions, allowing the caller to handle them via `Future.get()`. |
| **ExecutorService Usage** | `submit(Runnable)` – task is submitted for execution, no result expected. | `submit(Callable<V>)` – task is submitted, returns a `Future<V>` for retrieving the result. |
| **Typical Use Case** | Fire-and-forget tasks such as logging, background monitoring, updating shared state. | Tasks where the result matters, like calculations, downloading files, or fetching data from APIs. |
| **Thread Reuse**  | Runnable tasks can be submitted to a thread pool; each task runs independently. | Callable tasks can be submitted to a thread pool; `Future<V>` allows retrieving results asynchronously. |
| **Error Handling** | Exceptions must be caught inside `run()`, otherwise they terminate the thread silently. | Exceptions propagate to `Future.get()`, making it easier to handle errors in calling code. |
| **Flexibility**   | Simpler and lightweight; suitable for quick background tasks.           | More powerful; supports result retrieval and exception handling.           |

---

## Key Points

1. **Runnable** is suitable when you just want the task to run concurrently, without caring about the outcome.
2. **Callable** is preferred when you need the task to return a result or may throw checked exceptions.
3. Using **Callable + Future** allows asynchronous computation and retrieval of results once the task is complete.
4. Both can be submitted to **ExecutorService**, making thread management easy and efficient.

---
