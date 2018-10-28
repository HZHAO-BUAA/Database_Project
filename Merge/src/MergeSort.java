import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.lang.Math.ceil;
import static java.lang.Math.min;


public class MergeSort {

    private Queue<Integer> bufferQueue = new PriorityQueue<Integer>();

    private Queue<Read_three> streamReference=new LinkedList<Read_three>();

    private Comparator<Integer> comp=new Comparator<Integer>() {
        @Override
        public int compare(Integer integer, Integer t1) {
            return integer-t1;
        }
    };

    private Comparator<Element> compe=new Comparator<Element>() {
        @Override
        public int compare(Element e1, Element e2) {
            return e1.getValue()-e2.getValue();
        }
    };

    private int totalStream=0;
    private int M=0;
    private long N=0;
    private int d=0;
    private int mergeTimes=0;

    public void MergeSort(String fileToSort,String fileSorted,int M,int d,int method){
        this.bufferQueue=new PriorityQueue<Integer>(M,comp);
        File toBeSort=new File(fileToSort);
        this.M=M;
        this.N=toBeSort.length()/4;
        this.d=d;
        this.totalStream=(int)ceil(((double)N)/( (double)M));

        Read_three filereader= new Read_three();


        filereader.open(fileToSort,M);
        //for each stream
        for(int ids=0;ids<this.totalStream;ids++){
            Write_three fileWriter=new Write_three();
            fileWriter.create("Stream_"+String.valueOf(ids)+".bin",M);
            //sort each stream and save them to Stream_ids.bin
            for(int idx=0;idx<min(this.M,(N-M*ids));idx++){
                bufferQueue.add(filereader.read_next());

            }
            for(int idx=0;idx<min(this.M,(N-M*ids));idx++){
                fileWriter.write(bufferQueue.poll());
            }
            fileWriter.close();
            Read_three ReaderForStream=new Read_three();
            ReaderForStream.open("Stream_"+String.valueOf(ids)+".bin",M);
            this.streamReference.add(ReaderForStream);

        }
        //merge streams
        //total irritation to merge
        int remeaningStream=streamReference.size();
        //not the last merge
        //merge all streams, d stream at a time
        while(remeaningStream>1){
            mergeTimes+=1;
            Queue<Element> mergeQueue=new PriorityQueue<Element>(d,compe);
            Read_three[] streamRefInMerge=new Read_three[d];
            Write_three writer_merged=new Write_three();

            writer_merged.create("Stream_merge_"+String.valueOf(mergeTimes)+".bin",M);
            //initialization of queue
            for(int idd=0;idd<min(d,remeaningStream);idd++){
                streamRefInMerge[idd]=streamReference.poll();
                Element ele=new Element();
                ele.setValue(streamRefInMerge[idd].read_next());

                ele.setIndex(idd);
                mergeQueue.add(ele);
            }
            while(!mergeQueue.isEmpty()){
                Element minToWrite=mergeQueue.poll();
                writer_merged.write(minToWrite.getValue());
                int nextvalue=streamRefInMerge[minToWrite.getIndex()].read_next();
                if(!streamRefInMerge[minToWrite.getIndex()].end_of_stream()){
                    Element ele=new Element();
                    ele.setValue(nextvalue);
                    ele.setIndex(minToWrite.getIndex());
                    mergeQueue.add(ele);
                }
            }
            writer_merged.close();
            //show final result
            Read_three newStreamReader=new Read_three();
            newStreamReader.open("Stream_merge_"+String.valueOf(mergeTimes)+".bin",M);
            this.streamReference.add(newStreamReader);

            remeaningStream=streamReference.size();

        }

        System.out.println("\n\nfinal result stored in Stream_merge_"+String.valueOf(mergeTimes)+".bin, first 100 elements (close this display during formal test):");
        //the following results doesn't appear in formal test
        Read_three newStreamReader=new Read_three();
        newStreamReader.open("Stream_merge_"+String.valueOf(mergeTimes)+".bin",M);
        int tempInt=0;
        for(int idx=0;idx<100;idx++){
            tempInt=newStreamReader.read_next();
            System.out.print(tempInt+", ");
        }

    }

}
