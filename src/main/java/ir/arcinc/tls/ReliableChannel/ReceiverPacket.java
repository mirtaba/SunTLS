package ir.arcinc.tls.ReliableChannel;

import java.util.Arrays;

public class ReceiverPacket {

    private byte[] payload;
    private byte[] data;
    private byte[] hash = new byte[4];
    private int squNum = -1;

    public ReceiverPacket(byte[] payload) {
        this.payload = payload;
        System.arraycopy(payload,0,this.hash,0,4);
        byte[] squNumByteArray = new byte[4];
        System.arraycopy(payload,4,squNumByteArray,0,4);
        this. squNum = PacketTools.byteArrayToInt(squNumByteArray);
        this. data = new byte[payload.length - 8];
        System.arraycopy(payload,8,this.data,0,payload.length - 8);
    }

    public boolean isValid(){
        byte[] hashData = new byte[payload.length - 4];
        System.arraycopy(payload,4,hashData,0,payload.length - 4);
        return PacketTools.isValidFletcher32(this.hash,hashData);
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getHash() {
        return hash;
    }

    public int getSquNum() {
        return squNum;
    }
}
