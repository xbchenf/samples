package com.xbchen.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket=new ServerSocket(1010);
        System.out.println("服务端启动.....");
        int i=1;
        while(true){
            Socket socket=serverSocket.accept();
            System.out.println("接收到第"+i+++"个请求");
            executorService.submit(() -> {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    System.out.println("服务端接收："+in.readUTF());

                    DataOutputStream out=new DataOutputStream(socket.getOutputStream());
                    out.writeUTF("server is ok");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
