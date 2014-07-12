package com.gammadevs.pingpong.servlets;

import com.gammadevs.pingpong.Constants;
import com.gammadevs.pingpong.model.PingPongController;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;
import org.atmosphere.websocket.WebSocketStreamingHandlerAdapter;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Anton on 7/12/2014.
 */
@Singleton
@WebSocketHandlerService(path = "/atmosphere/pingpong/websocket", broadcaster = SimpleBroadcaster.class,
        atmosphereConfig = {"org.atmosphere.websocket.WebSocketProtocol=org.atmosphere.websocket.protocol.StreamingHttpProtocol"})
public class PingPongAtmosphereWebSocket extends WebSocketStreamingHandlerAdapter {

    private final Map<String, PingPongController> games = new HashMap<String, PingPongController>();
    private final Map<String, WebSocket> contexts = new HashMap<String, WebSocket>();
    private final AtomicLong clientCount = new AtomicLong(0);
    private final Thread gameTread = new GameThread();

    private class GameThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(Constants.PUSH_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    synchronized (contexts) {
                        synchronized (games) {
                            for (String id : contexts.keySet()) {
                                WebSocket context = contexts.get(id);
                                PingPongController game = games.get(id);
                                game.updateState();
                                context.write(game.getState());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
        if (!gameTread.isAlive()) {
            gameTread.start();
        }
        String id = webSocket.resource().uuid();
        synchronized (contexts) {
            synchronized (games) {
                contexts.put(id, webSocket);
                games.put(id, new PingPongController());
            }
        }
        webSocket.resource().addEventListener(new WebSocketEventListenerAdapter() {
            @Override
            public void onDisconnect(AtmosphereResourceEvent event) {
                synchronized (contexts) {
                    synchronized (games) {
                        contexts.remove(event.getResource().uuid());
                        games.remove(event.getResource().uuid());
                    }
                }
            }
        });
    }

    @Override
    public void onTextStream(WebSocket webSocket, Reader reader) {
        try {
            char[] buffer = new char[4096];
            int charsRead = reader.read(buffer);
            String left = new String(buffer, 0, charsRead);
            String id = webSocket.resource().uuid();
            synchronized (games) {
                games.get(id).setPlayerPosition(Integer.parseInt(left));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
