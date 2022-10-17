package echo.server;

import echo.Utils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new EchoServer().start(port);
    }

    public void start(int port) throws InterruptedException {
        var echoServerHandler = new EchoServerHandler();
        ServerBootstrap server = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup(1000);
        try {
            server.group(group)
                    .channel(NioServerSocketChannel.class) // the concrete type of Channel to be initialized
                    .childHandler(Utils.channelInitializerWithSocketChannel(echoServerHandler)) // add channel handlers with ChannelInitializer
                    .bind(port).sync()
                    .channel()
                    .closeFuture().sync()
            ;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
