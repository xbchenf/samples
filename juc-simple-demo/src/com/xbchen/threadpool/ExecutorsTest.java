package com.xbchen.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xx
 * @date xxx
 * @description xx
 */
public class ExecutorsTest {
    private static ExecutorService executorService1=Executors.newFixedThreadPool(10);
    private static ExecutorService executorService2=Executors.newCachedThreadPool();
    private static ExecutorService executorService3=Executors.newSingleThreadExecutor();
    private static ExecutorService executorService4=Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            executorService4.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("123456");
                }
            });
        }
        executorService4.shutdown();
        while (!executorService4.isTerminated()){
            try {
                executorService4.awaitTermination(2, TimeUnit.SECONDS);
            }catch(Exception e){

            }
        }
        System.out.println("abcdefg");

    }
}
