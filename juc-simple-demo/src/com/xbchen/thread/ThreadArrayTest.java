package com.xbchen.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 队列--数组（1-1）
 */
public class ThreadArrayTest {
    private static BlockingQueue<Person>[] taskQueues;
    private static int queue_num = 5;
    private static WorkThrad[] workThrads;

    public static void main(String[] args) {
        ThreadArrayTest t=new ThreadArrayTest();
        for(int i=0;i<100;i++){
            Person p=new Person();
            p.setId(i);
            p.setName("abc-"+i);
            t.putThradTask(p);
        }
    }
    /**
     * 初始化线程数组
     * 初始化队列数组
     * 将每一个队列丢入对应的线程中，启动所有线程
     */
    public ThreadArrayTest(){
        taskQueues=new BlockingQueue[queue_num];
        workThrads=new WorkThrad[queue_num];
        for(int i=0;i<queue_num;i++){
            taskQueues[i]=new LinkedBlockingQueue<>();
            workThrads[i]=new WorkThrad(taskQueues[i]);
            workThrads[i].setName("process thread-"+i);
            workThrads[i].start();//开启线程
        }
    }

    public void putThradTask(Person person){
        try{
            int index = Math.abs(person.getName().hashCode())%queue_num;//通过hash算法均匀的分摊到各个队列,abs方法避免出现负数
            System.out.println("队列数："+taskQueues.length+";当前队列queue_"+index);
            System.out.println("当前队列queue_"+index+"大小："+taskQueues[index].size());
            taskQueues[index].put(person);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Person{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
class WorkThrad extends Thread{
    private BlockingQueue<Person> taskQueue;
    WorkThrad(BlockingQueue<Person> taskQueue){
        this.taskQueue=taskQueue;
    }
    @Override
    public void run() {
       try {
           while (true){
               System.out.println("开始等待任务。。。。。。。");
               Person person=taskQueue.take();//一致阻塞，直到队列中有任务进来被读取到
               System.out.println("当前处理线程:"+Thread.currentThread().getName()+";name:"+person.getName());
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}