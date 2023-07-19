package com.hspedu.tankGame01;

import javax.swing.*;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame01 tankGame = new TankGame01();
    }
    public TankGame01() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100,800);
        this.setVisible(true);
        this.addKeyListener(mp);
    }
}
