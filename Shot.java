package com.hspedu.tankGame01;

public class Shot implements Runnable {
    int x, y;
    int direct = 0;
    int speed = 2;
    boolean isAlive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            // out of bound
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isAlive)) {
                System.out.println("bullet terminated!");
                isAlive = false;
                break;
            }
            System.out.println("bullet at: "+x + "," + y);
        }
    }
}
