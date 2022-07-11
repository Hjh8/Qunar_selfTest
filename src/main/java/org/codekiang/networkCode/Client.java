package org.codekiang.networkCode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class Client {

    private static SocketChannel socketChannel;

    private static Selector selector;

    public static void startServer() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        try {
            startServer();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String str = scanner.nextLine();
                socketChannel.write(StandardCharsets.UTF_8.encode(str));

                if (selector.select() > 0) {
                    // 获取当前选择器中所有注册的 “选择键(已就绪的监听事件)”
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    if (it.hasNext()) {
                        SelectionKey selectionKey = it.next();
                        if (selectionKey.isReadable()) {
                            readOperator(socketChannel);
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readOperator(SocketChannel socketChannel) throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        String msg = "";
        if (socketChannel.read(readBuffer) > 0) {
            readBuffer.flip();
            msg += StandardCharsets.UTF_8.decode(readBuffer);
            System.out.println(msg);
        }
    }
}
