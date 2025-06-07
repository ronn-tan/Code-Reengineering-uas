package com.zetcode;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<Dot> body;
    private Direction direction;
    private boolean growing = false;
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Snake() {
        body = new ArrayList<>();
        direction = Direction.RIGHT;
        body.add(new Dot(50, 50));
        body.add(new Dot(40, 50));
        body.add(new Dot(30, 50));
    }

    public List<Dot> getBody() {
        return body;
    }
    
    public Dot getHead() {
        return body.get(0);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move(int dotSize) {
        Dot head = getHead();
        int newX = head.getX();
        int newY = head.getY();

        switch (direction) {
            case UP:    newY -= dotSize; break;
            case DOWN:  newY += dotSize; break;
            case LEFT:  newX -= dotSize; break;
            case RIGHT: newX += dotSize; break;
        }

        body.add(0, new Dot(newX, newY));

        if (growing) {
            growing = false;
        } else {
            body.remove(body.size() - 1);
        }
    }

    public void grow() {
        this.growing = true;
    }

    public boolean checkCollision(int boardWidth, int boardHeight) {
        Dot head = getHead();
        
        if (head.getX() < 0 || head.getX() >= boardWidth || head.getY() < 0 || head.getY() >= boardHeight) {
            return true;
        }
        for (int i = 1; i < body.size(); i++) {
            if (head.getX() == body.get(i).getX() && head.getY() == body.get(i).getY()) {
                return true;
            }
        }
        return false;
    }
}