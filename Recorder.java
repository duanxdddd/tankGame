package com.hspedu.tankGame01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * To record number of tanks destroyed locally
 */
public class Recorder {
    private static int numEnemyDestroyed = 0;
    private static BufferedWriter bw = null;
    private static String recordFile = "src/myRecord.txt";

    public static void storeRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(numEnemyDestroyed + "\r\n");
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
