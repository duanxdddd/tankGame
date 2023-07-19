package com.hspedu.tankGame01;

import java.io.*;
import java.util.Vector;

/**
 * To record number of tanks destroyed locally
 */
public class Recorder {
    private static int numEnemyDestroyed = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src/myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<TankInfo> tankInfos = new Vector<>();
    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    // read history tank record from disk
    public static Vector<TankInfo> getTankInfoRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            numEnemyDestroyed = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                TankInfo tankinfo = new TankInfo(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                tankInfos.add(tankinfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return tankInfos;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void storeRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(numEnemyDestroyed + "\r\n");
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isAlive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static int getNumEnemyDestroyed() {
        return numEnemyDestroyed;
    }

    public static void setNumEnemyDestroyed(int numEnemyDestroyed) {
        Recorder.numEnemyDestroyed = numEnemyDestroyed;
    }

    // add numEnemyDestroyed when hero takes down an enemy
    public static void addNumEnemyDestroyed() {
        Recorder.numEnemyDestroyed++;
    }
}
