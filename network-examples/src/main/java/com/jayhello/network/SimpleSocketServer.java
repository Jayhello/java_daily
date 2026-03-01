package com.jayhello.network;

import java.io.*;
import java.net.*;

/**
 * 简单的Socket服务器示例
 * 
 * 注意: 这是一个简单的示例，为了演示基本概念。
 * 实际生产环境中应该使用线程池来处理并发客户端连接。
 */
public class SimpleSocketServer {
    
    private int port;
    
    public SimpleSocketServer(int port) {
        this.port = port;
    }
    
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器启动，监听端口: " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端连接: " + clientSocket.getInetAddress());
                
                // 处理客户端请求
                handleClient(clientSocket);
            }
        }
    }
    
    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            String inputLine = in.readLine();
            System.out.println("收到消息: " + inputLine);
            
            // 回复客户端
            out.println("服务器收到: " + inputLine);
            
        } catch (IOException e) {
            System.err.println("处理客户端请求时出错: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SimpleSocketServer server = new SimpleSocketServer(8080);
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("服务器启动失败: " + e.getMessage());
        }
    }
}
