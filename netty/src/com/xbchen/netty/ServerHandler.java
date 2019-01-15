package com.xbchen.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author xbchen
 * @description 通过 ChannelHandler 来实现服务器的业务逻辑
 */
@ChannelHandler.Sharable           //1.@Sharable 标识这类的实例之间可以在 channel 里面共享
public class ServerHandler extends
        ChannelInboundHandlerAdapter {

    /**
     * channelRead() - 每个信息入站都会调用
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        String result=in.toString(CharsetUtil.UTF_8);
        System.out.println("服务器 接收: " + result);        //2
        //in.clear().writeBytes(("服务器001").getBytes());
        ctx.write(in); //将所接收的消息返回给发送者。注意，这还没有冲刷数据
    }

    /**
     * 通知处理器最后的 channelread() 是当前批处理中的最后一条消息时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);//4 冲刷所有待审消息到远程节点。关闭通道后，操作完成
    }

    /**
     * 读操作时捕获到异常时调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();                //5
        ctx.close();                            //6
    }
}