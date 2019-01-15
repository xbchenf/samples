package com.xbchen.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author xbchen
 * @description 服务器
 */
public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }
    public static void main(String[] args) throws Exception {
        int port = 1080;//1
        new Server(port).start();                //2
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(); //3
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)                                //4
                    .channel(NioServerSocketChannel.class)        //5 指定使用 NIO 的传输 Channel
                    .localAddress(new InetSocketAddress(port))    //6
                    .childHandler(new ChannelInitializer<SocketChannel>() { //7 初始化通道；添加 ServerHandler 到 Channel 的 ChannelPipeline
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });

            ChannelFuture f = b.bind().sync();            //8.绑定的服务器;sync 等待服务器关闭
            System.out.println(Server.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();            //9.关闭 channel 和 块，直到它被关闭
        } finally {
            group.shutdownGracefully().sync();            //10.关机的 EventLoopGroup，释放所有资源。
        }
    }

}