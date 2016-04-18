package ir.arcinc.tls.Commons;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tahae on 4/18/2016.
 */
public abstract class AbstractSender implements Observer{
    private UnreliableChannel channel;
    private Timer timer = new Timer();

    private long timeOut = 0;

    public AbstractSender(UnreliableChannel channel) {
        this.channel = channel;
        timer.schedule(new TimeOut(), timeOut);
    }

    public abstract void receiveFromApplication(byte[] data);
    public abstract void receive(byte[] data);
    public abstract void timeOut();

    protected void setTimeOut(long milliseconds){
        this.timeOut = milliseconds;
        timer.schedule(new TimeOut(), timeOut);
    }

    protected void stopTimer(){
        timer.cancel();
    }

    protected final void send(byte[] data){
        channel.send(data);
    }

    @Override
    public final void update(Observable o, Object arg) {
        this.receive((byte[]) arg);
    }

    private class TimeOut extends TimerTask{
        @Override
        public void run() {
            timeOut();
        }
    }
}
