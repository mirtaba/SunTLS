package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

import java.util.HashMap;

/**
 * Created by tahae on 4/18/2016.
 * Modified for Network final project by Amirhossein on 12/28/2017 (Almost Christmas :D)
 */
public class Sender extends AbstractSender {

    int sqnNum = 0;
    HashMap<Integer ,SenderPacket> packetsToSend;
    int maxSuccessfulAckNumber = 0;

    public Sender(UnreliableChannel channel) {
        super(channel);
        packetsToSend = new HashMap<>();
    }

    /**
     *
     * @param data data received from application to be sent to another application
     * Use send(byte[]) to send a data to channel.
     */



    @Override
    public void receiveFromApplication(byte[] data){
        SenderPacket packet = new SenderPacket(data, sqnNum++ );
        packetsToSend.put(packet.getSquNum(),packet);
        //System.out.println("Sent:" + packet.getSquNum());
        send(packet.getPacket());
    }

    @Override
    public void receive(byte[] data){
        ReceiverPacket ackPacket = new ReceiverPacket(data);

        if(ackPacket.isValid()){
            if(ackPacket.getSquNum()> maxSuccessfulAckNumber){
                for(int i = maxSuccessfulAckNumber ; i < ackPacket.getSquNum(); i++){
                    packetsToSend.remove(i);
                }
                maxSuccessfulAckNumber = ackPacket.getSquNum();
            }
            if(packetsToSend.containsKey(maxSuccessfulAckNumber)) {
                //System.out.println("Resent:" + maxSuccessfulAckNumber);
                send(packetsToSend.get(maxSuccessfulAckNumber).getPacket());
            }
        }
        //System.out.println("maxSuccessfulAckNumber:" + maxSuccessfulAckNumber);
    }
    
    @Override
    public void timeOut() {

        if(packetsToSend.containsKey(maxSuccessfulAckNumber)) {
            //System.out.println("Resent:" + maxSuccessfulAckNumber);
            send(packetsToSend.get(maxSuccessfulAckNumber).getPacket());
        }
    }
}
