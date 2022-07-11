package org.codekiang.networkCode;

import com.google.common.base.Strings;
import org.codekiang.networkCode.AnalysisUtils.AnalysisData;
import org.codekiang.networkCode.AnalysisUtils.HttpAnalysis;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Server {

    private static ServerSocketChannel serverSocketChannel;

    public static Selector startServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }


    public static void main(String[] args) {
        try {
            Selector selector = startServer();
            monitorEvents(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void monitorEvents(Selector selector) throws IOException {
        // 监听事件
        while (true) {
            int readChannels = selector.select();
            if (readChannels == 0){
                continue;
            }
            // 获取当前选择器中所有注册的 "选择键(已就绪的监听事件)"
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                // 判断具体事件
                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel.accept(), selector);
                } else if (selectionKey.isReadable()) {
                    readOperator((SocketChannel) selectionKey.channel());
                }
                it.remove();
            }
        }
    }

    private static void acceptOperator(SocketChannel socketChannel, Selector selector) throws IOException {
        socketChannel.configureBlocking(false);
        // 将该通道注册到选择器上
        socketChannel.register(selector, SelectionKey.OP_READ);
    }


    public static void readOperator(SocketChannel socketChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        String url = "";
        if (socketChannel.read(buf) > 0) {
            buf.flip();
            url += StandardCharsets.UTF_8.decode(buf);
            System.out.println(url);
            sentMsgToClient(socketChannel, parseUrl(url));
        }
    }

    public static AnalysisData parseUrl(String url){
        if (Strings.isNullOrEmpty(url)){
            return new AnalysisData();
        }
        String context = HttpAnalysis.getContextByUrl(url);
        return HttpAnalysis.getAnalysisData(context);
    }

    public static void sentMsgToClient(SocketChannel targetChannel, AnalysisData data) throws IOException {
        targetChannel.write(StandardCharsets.UTF_8.encode(data.toString()));
    }
}
