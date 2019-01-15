package com.xbchen.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description ReentrantLock 需要手动加锁，解锁
 */
public class LockTest {
    public static void main(String[] args) {
        final Outputter1 output = new Outputter1();
        new Thread() {
            public void run() {
                output.output("abc");
            };
        }.start();
        new Thread() {
            public void run() {
                output.output("123");
            };
        }.start();
    }
}
class Outputter1 {
    private Lock lock = new ReentrantLock();// 锁对象
    public void output(String name) {
        lock.lock();// 得到锁
        try {
          //  if(lock.tryLock(10, TimeUnit.SECONDS)){
                for(int i = 0; i < name.length(); i++) {
                    System.out.print(name.charAt(i));
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                }
           /* }else {
            }*/
        } catch (Exception ee) {

        }finally {
           lock.unlock();// 释放锁
        }
    }
}
