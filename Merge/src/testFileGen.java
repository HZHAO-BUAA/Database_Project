import java.util.Random;
public class testFileGen {
    private Write_three filewriter3=new Write_three();
    private Write_four filewriter4=new Write_four();
    private Random randomSeed=new Random();

    public void generateFile(String Filename, Integer N){
        filewriter3.create(Filename,10);
        filewriter4.create(Filename,10,N);
        Integer counter=0;

        while(counter<N){
            filewriter3.write(randomSeed.nextInt(2147483646));
            counter+=1;
        }

        filewriter3.close();
    }
}
