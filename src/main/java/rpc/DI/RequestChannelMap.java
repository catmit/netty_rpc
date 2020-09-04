package rpc.DI;

import java.nio.channels.Channel;
import java.util.concurrent.ConcurrentHashMap;

public class RequestChannelMap {
    private static final ConcurrentHashMap<Integer, Channel> channelsHolder = new ConcurrentHashMap<>();

    public static void addChannel(int requestId, Channel channel){
        channelsHolder.put(requestId, channel);
    }

    public static Channel getChannel(int requestId){
        return channelsHolder.get(requestId);
    }
    public static void removeChannel(int requestId){
        channelsHolder.remove(requestId);
    }
}
