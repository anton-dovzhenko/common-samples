package com.gammadevs.pingpong.servlets;

import com.gammadevs.pingpong.Constants;
import com.gammadevs.pingpong.model.PingPongController;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet(name="PingPongAsyncServlet", urlPatterns={"/pingpongasync"}, asyncSupported = true)
public class PingPongAsyncServlet extends HttpServlet {

    private static final Map<String, PingPongController> games = new HashMap<String, PingPongController>();
    private static final Map<String, AsyncContext> contexts = new HashMap<String, AsyncContext>();
    private static final AtomicLong ids = new AtomicLong(0);

    @Override
    public void init() {
        Thread thread = new Thread(new Runnable() {
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
                            for (String id : contexts.keySet()) {
                                AsyncContext context = contexts.get(id);
                                ServletResponse response = context.getResponse();
                                PingPongController game = games.get(id);
                                game.updateState();
                                response.getWriter().print(game.getState());
                                response.getWriter().flush();
                                context.complete();
                            }
                            contexts.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setName("ping_pong_async");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String left = request.getParameter("x");
        if (id == null || left == null) {
            PingPongController game = new PingPongController();
            games.put(ids.incrementAndGet() + "", game);
            response.getWriter().print(ids.get());
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            AsyncContext context = request.startAsync();
            PingPongController game = games.get(id);
            synchronized (contexts) {
                contexts.put(id, context);
                game.setPlayerPosition(Integer.parseInt(left));
            }
        }
    }

}
