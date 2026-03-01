package com.jayhello.network;

import java.io.*;
import java.net.*;

/**
 * 简单的Socket客户端示例
 */
public class SimpleSocketClient {
    
    private String host;
    private int port;
    
    public SimpleSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public String sendMessage(String message) throws IOException {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(message);
            return in.readLine();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Socket客户端示例（需要先启动服务器）");
        // 示例用法（需要服务器运行）
        // SimpleSocketClient client = new SimpleSocketClient("localhost", 8080);
        // String response = client.sendMessage("Hello Server");
        // System.out.println("收到响应: " + response);
    }
}
