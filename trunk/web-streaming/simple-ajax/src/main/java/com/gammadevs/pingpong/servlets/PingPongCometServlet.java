package com.gammadevs.pingpong.servlets;

import com.gammadevs.pingpong.Constants;
import com.gammadevs.pingpong.model.PingPongController;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet(name="PingPongCometServlet", urlPatterns={"/pingpongcomet"})
public class PingPongCometServlet extends HttpServlet implements CometProcessor {

    private static final Map<String, PingPongController> games = new HashMap<String, PingPongController>();
    private static final Map<String, HttpServletResponse> responses = new HashMap<String, HttpServletResponse>();
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
                        synchronized (responses) {
                            for (String id : responses.keySet()) {
                                PingPongController game = games.get(id);
                                HttpServletResponse response = responses.get(id);
                                game.updateState();
                                response.getWriter().print(game.getState());
                                response.getWriter().flush();
                                response.getWriter().close();
                            }
                            responses.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setName("ping_pong_comet");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void event(CometEvent cometEvent) throws IOException, ServletException {
        switch(cometEvent.getEventType()) {
            case BEGIN:
                begin(cometEvent.getHttpServletRequest(), cometEvent.getHttpServletResponse()
                        , cometEvent);
                break;
            case ERROR:
            case END:
                end(cometEvent);
                break;
            case READ:
                read();
                break;
        }

    }

    private void begin(HttpServletRequest request, HttpServletResponse response
            , CometEvent cometEvent) throws IOException {
        String id = request.getParameter("id");
        String left = request.getParameter("x");
        if (id == null || left == null) {
            PingPongController game = new PingPongController();
            games.put(ids.incrementAndGet() + "", game);
            response.getWriter().print(ids.get());
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            PingPongController game = games.get(id);
            synchronized (responses) {
                responses.put(id, cometEvent.getHttpServletResponse());
                game.setPlayerPosition(Integer.parseInt(left));
            }
        }
    }

    private void end(CometEvent cometEvent) throws IOException {
        synchronized (responses) {
            String id = cometEvent.getHttpServletRequest().getParameter("id");
            if (id != null) {
                responses.remove(id);
            }
            cometEvent.close();
        }
    }

    private void read() {

    }

}
