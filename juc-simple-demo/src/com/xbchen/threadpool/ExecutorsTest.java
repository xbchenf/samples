package com.xbchen.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description 线程池学习记录
 */
public class ExecutorsTest {
    //1.固定大小的线程池，一般用在需要线程数量比较大的业务场景
    private static ExecutorService executorService1=Executors.newFixedThreadPool(10);

    //2.无限大小的线程池，一般用在线程池数据比较少的业务场景，可重复利用回收
    private static ExecutorService executorService2=Executors.newCachedThreadPool();

    //3.单线程的线程池。当业务需要同步执行时使用。
    private static ExecutorService executorService3=Executors.newSingleThreadExecutor();

    //5.定时调度的线程池，用于定时任务。
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
        executorService4.shutdown();//shutdown后，不能再添加任务。
        while (!executorService4.isTerminated()){//判断任务是否全部执行完成, 多线程时，可使用线程计数器CountDonwLatch，实现类似效果
            try {
                executorService4.awaitTermination(2, TimeUnit.SECONDS);
            }catch(Exception e){

            }
        }
        System.out.println("abcdefg");

    }
}
