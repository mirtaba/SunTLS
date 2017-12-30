package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractReceiver;
import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tahae on 4/18/2016.
 * Modified for Network final project by Amirhossein on 12/28/2017 (Almost Christmas :D)
 */
public class Receiver extends AbstractReceiver {

    Set<byte[]> recivedHashs;
    HashMap<Integer , ReceiverPacket> outOfOrderReceived;
    int nextInOrderPacket = 0;

    public Receiver(UnreliableChannel channel, AbstractSender sender) {
        super(channel,sender);
        recivedHashs = new HashSet<>();
        outOfOrderReceived = new HashMap<>();
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
        ReceiverPacket packet = new ReceiverPacket(data);
        //System.out.println("Received:" + packet.getSquNum());
        if(packet.isValid()){
            if(!recivedHashs.contains(packet.getHash())){
                recivedHashs.add(packet.getHash());
                if(packet.getSquNum() == nextInOrderPacket){
                    nextInOrderPacket++;
                    sendToApplication(packet.getData());
                    while(outOfOrderReceived.containsKey(nextInOrderPacket)){
                        sendToApplication(outOfOrderReceived.get(nextInOrderPacket).getData());
                        outOfOrderReceived.remove(nextInOrderPacket);
                        nextInOrderPacket++;
                    }
                }
                else {
                    outOfOrderReceived.put(packet.getSquNum(),packet);
                }

            }
        }
        //System.out.println("nextInOrderPacket:" + nextInOrderPacket);
        //System.out.println("Ack:");
        SenderPacket ackPacket = new SenderPacket("Ack".getBytes(),nextInOrderPacket);
        send(ackPacket.getPacket());
    }
}
