package com.xbchen.juc;

import java.util.concurrent.Exchanger;

/**
 * exchanger 交换器，多线程直接交换数据
 * 使用场景：比如需要将生产和消防分隔开时； 一个线程读取数据，将结果丢个另一个线程，另一个线程将读取的结果进行写入
 */
public class ExchangerTest {

    public static void main(String []args) {
        final Exchanger <Integer>exchanger = new Exchanger<Integer>();
        for(int i = 0 ; i < 2 ; i++) {
            final Integer num = i;
            new Thread() {
                public void run() {
                    System.out.println("我是线程：Thread_" + this.getName() + "我的数据是：" + num);
                    try {
                        Integer exchangeNum = exchanger.exchange(num);
                        Thread.sleep(1000);
                        System.out.println("我是线程：Thread_" + this.getName() + "我原先的数据为：" + num + " , 交换后的数据为：" + exchangeNum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
