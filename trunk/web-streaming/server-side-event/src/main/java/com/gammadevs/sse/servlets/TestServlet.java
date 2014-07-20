package com.gammadevs.sse.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    private final static AtomicLong count = new AtomicLong(0);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Thread.currentThread().setName(Thread.currentThread().getName() + "-ts" + count.incrementAndGet());
        //content type must be set to text/event-stream
        response.setContentType("text/event-stream");
        //encoding must be set to UTF-8
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        Date date = new Date();
        writer.write("retry: 5000\n");
        writer.write("event: date\n");
        writer.write("data: " + date + "\n\n");


        writer.write("retry: 5000\n");
        writer.write("event: time\n");
        writer.write("data: " + date.getTime() + "\n\n");
        writer.flush();
        writer.close();
    }

}
