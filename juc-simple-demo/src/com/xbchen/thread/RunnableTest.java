package com.xbchen.thread;

/**
 * @author xx
 * @date xxx
 * @description xx
 */
public class RunnableTest {
    public static void main(String[] args) {
        new Thread(new Test1()).start();
    }
}
class Test1 implements Runnable{
    @Override
    public void run() {
        System.out.println("123");
    }
}