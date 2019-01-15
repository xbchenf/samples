package com.xbchen.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("客户端启动.....");
        Socket socket=new Socket("192.168.1.174",1010);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        out.writeUTF("Hello World!!!!");

        DataInputStream in=new DataInputStream(socket.getInputStream());
        System.out.println("客户端接收："+in.readUTF());
        socket.close();
    }
}
