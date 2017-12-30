package ir.arcinc.tls.ReliableChannel;

import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;

public class SenderPacket {

    private byte[] packet;
    private int squNum = -1;

    public SenderPacket(byte[] data , int squNum){

        this.squNum = squNum;
        byte[] payload = PacketTools.concatByteArrays(PacketTools.intToByteArray(squNum) , data);
        packet = PacketTools.concatByteArrays(hash(payload) , payload);

    }

    protected byte[] hash(byte[] data){
        /**
        byte[] tmp = PacketTools.Fletcher32("abcde".getBytes());
        for(int j = 0 ; j < tmp.length; j++){
            System.out.print(tmp[j] + " ");
        }
        System.out.println();
        /**/
        return PacketTools.Fletcher32(data);
    }

    public byte[] getPacket() {
        return packet;
    }

    public int getSquNum() {
        return squNum;
    }
}
