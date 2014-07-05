package com.gammadevs.pingpong.model;

/**
 * Created by Anton on 7/5/2014.
 */
public class PingPongController {

    private int xStart = 100;
    private int xEnd = 500;
    private int fieldHeight = 500;
    private int fieldWidth = 400;
    private int score1 = 0;
    private int score2 = 0;
    private Ball ball = new Ball(10, 10, 20, 8, 45);
    private Player player1 = new Player(20, 100, 5, (xEnd - xStart - 100) / 2);
    private Player player2 = new Player(20, 100, 5, (xEnd - xStart - 100) / 2);

    public synchronized void setPlayerPosition(int x) {
        player1.setLeft(x - 100 - player1.getWidth() / 2);
        player2.setLeft(x - 100 - player2.getWidth() / 2);
    }

    public synchronized void updateState() {
        ball.updatePosition();
        if (isPlayer1PushBall() || isPlayer2PushBall()) {
            ball.recalculateVelocityAndSetAngle(360 - ball.getAngle());
        }
        if (isPlayer1Score()) {
            score1++;
            goal();
        }
        if (isPlayer2Score()) {
            score2++;
            goal();
        }
        if (isBorderPushBall()) {
            if (ball.getAngle() < 180) {
                ball.recalculateVelocityAndSetAngle(180 - ball.getAngle());
            } else {
                ball.recalculateVelocityAndSetAngle(270 * 2 - ball.getAngle());
            }
        }
    }

    private boolean isPlayer1Score() {
        return ball.getY() <= 0;
    }

    private boolean isPlayer2Score() {
        return ball.getY() >= fieldHeight - ball.getSize();
    }

    private boolean isPlayer1PushBall() {
        return ball.getY() >= fieldHeight - player1.getHeight() - player1.getPadding() - ball.getSize()
                && ball.getY() <= fieldHeight - player1.getHeight() - player1.getPadding()  - ball.getSize() + ball.getSpeed()
                && ball.getX() < player1.getLeft() + player1.getWidth()
                && ball.getX() + ball.getSize() > player1.getLeft();
    }

    private boolean isPlayer2PushBall() {
        return ball.getY() <= player2.getHeight() + player2.getPadding()
                && ball.getY() >= player2.getHeight() + player2.getPadding() - ball.getSpeed()
                && ball.getX() < player2.getLeft() + player2.getWidth()
                && ball.getX() + ball.getSize() > player2.getLeft();
    }

    private boolean isBorderPushBall() {
        return ball.getX() <= 0 || ball.getX() >= fieldWidth - ball.getSize();
    }

    public synchronized String getState() {
        return player1.getLeft()
                + "," + player2.getLeft()
                + "," + ball.getY()
                + "," + ball.getX()
                + "," + score1
                + "," + score2;
    }

    private void goal() {
        ball.setX(30);
        ball.setY(30);
        ball.recalculateVelocityAndSetAngle(45);
    }

}
