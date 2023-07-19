package com.hspedu.tankGame01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    // define 3 images for bombing effect
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    int enemyTankSize = 3;
    public MyPanel() {
        hero = new Hero(100, 200);
        hero.setSpeed(10);
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirect(2);
            enemyTank.setEnemyTanks(enemyTanks);
            new Thread(enemyTank).start();
            enemyTanks.add(enemyTank);
        }
        // init images
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/com/hspedu/tankGame01/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/com/hspedu/tankGame01/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/com/hspedu/tankGame01/bomb_3.gif"));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);

        // draw hero bullet
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isAlive) {
                g.setColor(Color.white);
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {
                hero.shots.remove(shot);
            }
        }

        // draw the hero tank
        if (hero != null && hero.isAlive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }

        // draw bomb effects
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Bomb bomb = bombs.get(i);
            if (bomb != null && bomb.isAlive) {
                if (bomb.life > 6) {
                    g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
                } else if (bomb.life > 3) {
                    g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
                } else {
                    g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
                }
                bomb.lifeDown();
                if (bomb.life <= 0) {
                    bombs.remove(bomb);
                }
            }
        }

        // draw the enemy tank
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isAlive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
            }
            // draw enemy's bullets
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (shot != null && shot.isAlive) {
                    g.setColor(Color.red);
                    g.draw3DRect(shot.x, shot.y, 1, 1, false);
                } else {
                    // remove from Vector shots
                    enemyTank.shots.remove(j);
                }
            }
        }
    }

    /**
     *
     * @param x x coord
     * @param y y coord
     * @param g graphics
     * @param direct direction
     * @param type tank type
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: // player tank
                g.setColor(Color.cyan);
                break;
            case 1: // enemy tank
                g.setColor(Color.yellow);
                break;
        }
        // draw based on direction
        switch (direct) {
            case 0: // up
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case 1: // right
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20,x + 60, y + 20);
                break;
            case 2: // down
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3: // left
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x, y + 20,x + 30, y + 20);
                break;
            default:
                System.out.println("\nnot implemented yet...");
                break;
        }
    }

    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isAlive = false;
                    tank.isAlive = false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    enemyTanks.remove(tank);
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isAlive = false;
                    tank.isAlive = false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    enemyTanks.remove(tank);
                    bombs.add(bomb);
                }
                break;
        }
    }

    public void hitEnemyTank() {
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isAlive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (hero.isAlive && shot!=null && shot.isAlive) {
                    hitTank(shot, hero);
                }
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (code == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        } else if (code == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (code == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (code == KeyEvent.VK_J) {
            // hero to shoot bullet
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        // every 100ms, panel repaint
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // check if hit enemy tank
            hitEnemyTank();
            // check if hit hero tank
            hitHero();

            this.repaint();
        }
    }
}
