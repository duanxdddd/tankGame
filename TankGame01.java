package com.hspedu.tankGame01;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame01 tankGame = new TankGame01();
    }
    public TankGame01() {
        System.out.println("Please select 1: new game, 2: resume...");
        String key = scanner.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1300,800);
        this.setVisible(true);
        this.addKeyListener(mp);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.storeRecord();
                System.exit(0);
            }
        });
    }
}
