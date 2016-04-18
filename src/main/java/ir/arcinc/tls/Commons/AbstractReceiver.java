package ir.arcinc.tls.Commons;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by tahae on 4/18/2016.
 */
public abstract class AbstractReceiver extends Observable implements Observer{
    private UnreliableChannel channel;

    public AbstractReceiver(UnreliableChannel channel) {
        this.channel = channel;
    }

    public abstract void receive(byte[] data);

    protected final void send(byte[] data){
        channel.send(data);
    }

    @Override
    public final void update(Observable o, Object arg) {
        this.receive((byte[]) arg);
    }

    public final void sendToApplication(byte[] data){
        this.setChanged();
        this.notifyObservers(data);
    }
}
