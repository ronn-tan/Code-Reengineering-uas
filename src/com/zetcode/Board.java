package com.zetcode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int RAND_POS = B_WIDTH / DOT_SIZE;
    private final int DELAY = 140;

    private Snake snake;
    private Dot apple;
    private boolean inGame = true;

    private Timer timer;
    private Image ballImage;
    private Image appleImage;
    private Image headImage;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ballImage = iid.getImage();
        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        appleImage = iia.getImage();
        ImageIcon iih = new ImageIcon("src/resources/head.png");
        headImage = iih.getImage();
    }

    private void initGame() {
        snake = new Snake();
        loadImages();
        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(appleImage, apple.getX(), apple.getY(), this);
            for (int i = 0; i < snake.getBody().size(); i++) {
                Dot dot = snake.getBody().get(i);
                if (i == 0) {
                    g.drawImage(headImage, dot.getX(), dot.getY(), this);
                } else {
                    g.drawImage(ballImage, dot.getX(), dot.getY(), this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if (snake.getHead().getX() == apple.getX() && snake.getHead().getY() == apple.getY()) {
            snake.grow();
            locateApple();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        int appleX = r * DOT_SIZE;
        r = (int) (Math.random() * RAND_POS);
        int appleY = r * DOT_SIZE;
        apple = new Dot(appleX, appleY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            if (snake.checkCollision(B_WIDTH, B_HEIGHT)) {
                inGame = false;
            } else {
                snake.move(DOT_SIZE);
            }
        }
        if (!inGame) {
            timer.stop();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            Snake.Direction currentDir = snake.getDirection();

            if ((key == KeyEvent.VK_LEFT) && (currentDir != Snake.Direction.RIGHT)) {
                snake.setDirection(Snake.Direction.LEFT);
            }
            if ((key == KeyEvent.VK_RIGHT) && (currentDir != Snake.Direction.LEFT)) {
                snake.setDirection(Snake.Direction.RIGHT);
            }
            if ((key == KeyEvent.VK_UP) && (currentDir != Snake.Direction.DOWN)) {
                snake.setDirection(Snake.Direction.UP);
            }
            if ((key == KeyEvent.VK_DOWN) && (currentDir != Snake.Direction.UP)) {
                snake.setDirection(Snake.Direction.DOWN);
            }
        }
    }
}