package com.xbchen.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * FutureTask+callable
 * Futuretask是runnable,future的子类
 * callable封装成futureTask
 * futuretask封装成thread
 */
public class CallableTest {
    public static void main(String[] args)throws Exception {
        FutureTask<Integer> f=new FutureTask<Integer>(new Test3());
        new Thread(f).start();
        System.out.println(f.get());
    }
}
class Test3 implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println(123);
        return 1;
    }
}