package com.xbchen.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    ExecutorService executorService1=Executors.newFixedThreadPool(10);
    ExecutorService executorService2=Executors.newCachedThreadPool();
    ExecutorService executorService3=Executors.newSingleThreadExecutor();
    ExecutorService executorService4=Executors.newScheduledThreadPool(10);

    public static void mian(String[] args){

    }


}
