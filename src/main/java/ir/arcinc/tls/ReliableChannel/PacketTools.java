package ir.arcinc.tls.ReliableChannel;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class PacketTools {

    public static byte[] byteToByteArray(byte myByte){
        return ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN).put(myByte).array();
    }

    public static  byte[] shortToByteArray(short myShort){
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(myShort).array();
    }

    public static  byte[] intToByteArray(int myInteger){
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(myInteger).array();
    }

    public static  byte[] longToByteArray(long myLong){
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(myLong).array();
    }

    public static int byteArrayToInt(byte [] byteBarray){
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static byte[] concatByteArrays(byte[] first, byte[] second) {

        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static byte[] Fletcher32(byte[] data){
        int C0 = 0;
        int C1 = 0;

        for(int i = 0 ; i <data.length ; i++){
            C0 = ( (C0 + data[i]) % 65535);
            C1 = ((C1 + C0) % 65535);
        }
        return PacketTools.intToByteArray(((C1 << 16)| (C0 & 0xffff)));
    }

    public static boolean isValidFletcher32(byte[] hash , byte[] data){

        byte[] dataHash = Fletcher32(data);
        boolean answer = true;
        for(int i = 0 ; i < hash.length; i++){
            if(hash[i] != dataHash[i]){
                answer = false;
            }
        }

        return answer;
    }

}
