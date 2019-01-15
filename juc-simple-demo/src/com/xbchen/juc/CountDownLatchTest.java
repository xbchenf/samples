package com.xbchen.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @description  CountDownLatch
 * 闭锁可以延迟线程的进度直到其到达终止状态，闭锁可以用来确保某些活动直到其他活动都完成才继续执行
 * 线程计数器， 主线程在等待所有其它的子线程完成后再往下执行
 *
 * 线程池中实现，等待所有子线程结束
executorService.shutdown();
    while (!executorService.isTerminated()) {
    try {
    executorService.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
    e.printStackTrace();
    }
    }
 */
public class CountDownLatchTest {

    public static void main(String []args){
        int threadCount = 10;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for(int i=0; i< threadCount; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");
                    latch.countDown();//计数器减1
                }
            }).start();
        }

        try {
            latch.await();//等待计数器为0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("10个线程已经执行完毕！开始计算排名");
    }
}
