package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

/**
 * Created by tahae on 4/18/2016.
 */
public class Sender extends AbstractSender {

    public Sender(UnreliableChannel channel) {
        super(channel);
    }

    /**
     *
     * @param data data received from application to be sent to another application
     * Use send(byte[]) to send a data to channel.
     * Use setTimeOut(long) to set timer timeOut
     * Use stopTimer() to prevent timer to interrupt.
     */

    @Override
    public void receiveFromApplication(byte[] data) {
        send(data);
    }

    @Override
    public void receive(byte[] data) {

    }
    
    @Override
    public void timeOut() {

    }
}
