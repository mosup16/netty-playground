package echo;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class Utils {
    public static ChannelInitializer<SocketChannel> channelInitializerWithSocketChannel(ChannelInboundHandlerAdapter handler) {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(handler);
            }
        };
    }
}
