package echo.client;

import echo.Utils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    int counter = 0;
    public static void main(String[] args) throws InterruptedException {
        EchoClient client = new EchoClient();
        float time = System.currentTimeMillis();
        Runnable task = () -> {
            try {
                client.start("localhost",8080);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(task);
            t.start();
//            System.out.println("starting thread");
        }
        Thread.currentThread().join();
        System.err.println(System.currentTimeMillis() - time);
        System.out.println(client.counter);
    }

    public void start(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        EchoClientHandler handler = new EchoClientHandler(String.valueOf(counter++));
        try {
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(Utils.channelInitializerWithSocketChannel(handler))
                    .remoteAddress(new InetSocketAddress(host, port))
                    .connect()
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            group.shutdownGracefully().sync();
        }
//        System.err.println("closing threed");
    }
}
