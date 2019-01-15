package com.xbchen.thread;

/**
 * @author xx
 * @date xxx
 * @description xx
 */
public class ThreadTest  {
    public static void main(String[] args) {
        new Test().start();
    }
}

class Test extends Thread{
    @Override
    public void run() {
        System.out.println("123");
    }
}
