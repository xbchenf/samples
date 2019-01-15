package com.xbchen.lock;

/**
 * @author xx
 * @date xxx
 * @description xx
 */
public class SynchronizedTest {
    private static volatile int t=0;
    private static synchronized void method(){
        System.out.println("t:"+t+";name:"+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    t++;
                    method();

                }
            }).start();
        }
    }
}
