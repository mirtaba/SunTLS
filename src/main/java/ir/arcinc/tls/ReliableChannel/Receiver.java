package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractReceiver;
import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

import java.net.URLEncoder;

/**
 * Created by tahae on 4/18/2016.
 */
public class Receiver extends AbstractReceiver {

    public Receiver(UnreliableChannel channel, AbstractSender sender) {
        super(channel,sender);
    }

    /**
     *
     * @param data data received from channel;
     *
     *  You can use method send(byte[]) to send a data to channel
     *  Or use sendToApplication(byte[]) to send data to application
     */

    @Override
    public void receive(byte[] data) {
        sendToApplication(data);
    }
}
