package com.gammadevs.pingpomg.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name="PingPongServlet", urlPatterns={"/pingpong"}, asyncSupported=true)
public class PingPongServlet extends HttpServlet {
}
