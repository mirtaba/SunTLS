package ir.arcinc.tls.Commons;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tahae on 4/18/2016.
 */
public class UnreliableChannel extends Observable{

    private static Random randomizer = new Random();

    private double lossRate;
    private double errorRate;

    public UnreliableChannel(double lossRate, double errorRate) {
        this.lossRate = lossRate;
        this.errorRate = errorRate;
    }

    public void send(byte[] data){
        byte[] corruptdata = data.clone();
        if (randomizer.nextDouble() < lossRate)
            return;
        for (int j=0; j < corruptdata.length; j++)
            if (randomizer.nextDouble() < errorRate)
                corruptdata[j] += randomizer.nextDouble();
        this.setChanged();
        this.notifyObservers(corruptdata);
    }
}