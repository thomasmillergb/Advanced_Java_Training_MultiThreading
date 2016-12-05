package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    //Run Thread
//    public static void main(String[] args) {
//        // write your code here
//        Runnable runnable = () -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i);
//            }
//        };
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(runnable);
//    }

    //Callable
    public static void main(String[] args) {
        List<Match> matches =new ArrayList<Match>(){};

        List<Callable<Match>> callables = new ArrayList<Callable<Match>>(){};
        IntStream
                .range(0, 100).forEach(f -> callables.add(() -> getIntegerHistory(f)));
        ExecutorService executor = Executors.newWorkStealingPool();
        try {
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(matches:: add);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        matches.forEach(System.out::println);



    }

    private static Match getIntegerHistory(Integer i) throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
        System.out.println(i + ": " + Thread.currentThread());
        return new Match("match_:" + i);
    }
}
class Match{
    private String name_;

    public Match(String name) {
        name_ = name;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    @Override
    public String toString() {
        return name_;
    }
}
