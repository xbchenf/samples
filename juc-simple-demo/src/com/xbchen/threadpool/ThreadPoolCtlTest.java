package com.xbchen.threadpool;

/**
 * 线程池中的AtomicInteger ctl 解析
 *
 * 线程池的数量和状态放在：AtomicInteger中
 * 状态：Running，Shutdown，Stop，Tidying，Terminate五种状态。
 */
public class ThreadPoolCtlTest {

    private String toBinaryString(int i){

        final char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
        };

        int shift = 1;
        char[] buf = new char[32];
        for (int j=0;j<buf.length;j++){
            buf[j] = '0';
        }
        int charPos = 32;
        int mask = 1;
        do {
            buf[--charPos] = digits[i & mask];
            i >>>= shift;
        } while (i != 0);

        return new String(buf);
    }

    public void testNumber(){
        final int COUNT_BITS = Integer.SIZE - 3;
        final int CAPACITY   = (1 << COUNT_BITS) - 1;
        // runState is stored in the high-order bits
        final int RUNNING    = -1 << COUNT_BITS;
        final int SHUTDOWN   =  0 << COUNT_BITS;
        final int STOP       =  1 << COUNT_BITS;
        final int TIDYING    =  2 << COUNT_BITS;
        final int TERMINATED =  3 << COUNT_BITS;

        System.out.println(CAPACITY);
        System.out.println("1转换成32位bit:"+toBinaryString(1));
        System.out.println("1左移动29位   :"+toBinaryString(1 << COUNT_BITS));

        //int值的 低29位，表示线程数量 最大值：536870911=2^29-1  最小值为：0
        System.out.println("CAPACITY     :"+toBinaryString(CAPACITY));//536870911转成32位

        //线程状态 （int值的高3位表示线程状态）
        System.out.println("-1转换成32位bit:"+toBinaryString(-1));
        System.out.println("RUNNING       :"+toBinaryString(RUNNING));//-1左移29位表示running状态

        System.out.println("SHUTDOWN     :"+toBinaryString(SHUTDOWN));//0左移29位表示SHUTDOWN状态
        System.out.println("STOP         :"+toBinaryString(STOP));//1左移29位表示STOP状态

        System.out.println("2转换成32位bit:"+toBinaryString(2));
        System.out.println("TIDYING      :"+toBinaryString(TIDYING));//2左移29位表示STOP状态

        System.out.println("3转换成32位bit:"+toBinaryString(3));
        System.out.println("TERMINATED   :"+toBinaryString(TERMINATED));//3左移29位表示TERMINATED状态

    }

    public static void main(String[] args) {
            new ThreadPoolCtlTest().testNumber();
    }
}
