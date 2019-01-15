package com.xbchen.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务端
 *
 * @author -琴兽-
 */
public class Client {
    // 通道管理器
    private Selector selector;

    /**
     * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
     *
     * @param port
     *            绑定的端口号
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        // 获得一个Socket通道
        SocketChannel channel = SocketChannel.open();
        // 设置通道为非阻塞
        channel.configureBlocking(false);
        // 选择器
        this.selector = Selector.open();

        //配置连接IP、端口
        channel.connect(new InetSocketAddress("192.168.1.174",8000));

        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     *
     * @throws IOException
     */
    public void listen() throws IOException {
        // 轮询访问selector
        while (true) {
            // 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            selector.select();
            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator<?> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();

                handler(key);
            }
        }
    }
    /**
     * 处理请求
     *
     * @param key
     * @throws IOException
     */
    public void handler(SelectionKey key) throws IOException {

        // 客户端连接事件事件发生
        if (key.isConnectable()) {
            handlerConnect(key);
            // 获得了可读的事件
        } else if (key.isReadable()) {
            handelerRead(key);
        }
    }

    /**
     * 处理连接请求
     *
     * @param key
     * @throws IOException
     */
    public void handlerConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 如果正在连接，则完成连接
        if(channel.isConnectionPending()){
            channel.finishConnect();
        }
        // 设置成非阻塞
        channel.configureBlocking(false);

        //在这里可以给服务端发送信息哦
        channel.write(ByteBuffer.wrap(new String("abc123").getBytes()));

        //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    /**
     * 处理读的事件
     *
     * @param key
     * @throws IOException
     */
    public void handelerRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到信息："+msg);

        //回写数据
        //ByteBuffer outBuffer = ByteBuffer.wrap("客户端12341234".getBytes());
       // channel.write(outBuffer);// 将消息回送给客户端
    }

    /**
     * 启动服务端测试
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Client server = new Client();
        server.initServer(8000);
        server.listen();
    }

}
