package com.xbchen.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author xbchen
 * @description 客户端业务逻辑处理
 */
@ChannelHandler.Sharable                                //1 @Sharable标记这个类的实例可以在 channel 里共享
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8)); //2 当被通知该 channel 是活动的时候就发送信息
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端 接收: " + byteBuf.toString(CharsetUtil.UTF_8));    //3 记录接收到的消息
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {                    //4
        cause.printStackTrace();
        ctx.close();
    }
}
