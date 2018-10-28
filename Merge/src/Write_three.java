
import java.io.*;

public class Write_three {
	
    //private OutputStream ost = null;	
    //private DataOnputStream dost = null;
    private int BUFFER_SIZE = 40;
    private String fileName = null;
    private int inBufferOffset = 0;
    private byte[] bufferBlock = new byte[BUFFER_SIZE];
    private OutputStream ost=null;
    private DataOutputStream dost=null;

    private int fileOffset = 0;

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
    public void create(String fileName,int bufferSize){

        this.BUFFER_SIZE = bufferSize*4;
        this.bufferBlock = new byte[BUFFER_SIZE];


        File newFile=new File(fileName);

        try {
            this.ost=new FileOutputStream(newFile);
            this.dost=new DataOutputStream(this.ost);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void write(int intToWrite){
        if(inBufferOffset < BUFFER_SIZE){
            byte[] byteToWrite = intToByteArray(intToWrite);
            for(int idx=0; idx<4;idx++){
                this.bufferBlock[inBufferOffset+idx]=byteToWrite[idx];
            }
            this.inBufferOffset += 4;
        }
        else{

            this.inBufferOffset=0;

            try {
                this.dost.write(this.bufferBlock,0,BUFFER_SIZE);
                this.fileOffset += BUFFER_SIZE;

            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] byteToWrite = intToByteArray(intToWrite);
            for(int idx=0; idx<4;idx++){
                this.bufferBlock[inBufferOffset+idx]=byteToWrite[idx];
            }
            this.inBufferOffset += 4;

        }

    }
    public void close(){
        if(inBufferOffset>0){

            try {
                this.dost.write(this.bufferBlock,0,inBufferOffset);
                this.fileOffset += BUFFER_SIZE;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.ost.close();
            this.dost.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
