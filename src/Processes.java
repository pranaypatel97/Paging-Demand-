import java.util.ArrayList;
import java.util.Scanner;

public class Processes {
    public float evict;
    public float residency;
    public int page_fault;
    public int nextWord;
    int word;
    public int r;
    public int processSie;
    public int references;
    public int processNum;
    public double A;
    public double B;
    public double C;
    public String algorithm;


    public Processes(int processNum, double a, double b, double c, int references) {
        this.processNum = processNum;
        this.A = a;
        this.B = b;
        this.C = c;
        this.references = references;
        this.r = 0;
        this.word = 0;
    }
    public void GenerateNextWord(Scanner random, double y, int processSize){
        if(this.A>y){
            this.nextWord = (this.nextWord + 1) % processSize;


        }
        else if(this.A + this.B>y){
            this.nextWord = (this.nextWord-5) % processSize;

        }
        else if(this.A + this.B + this.C>y){
            this.nextWord = (this.nextWord + 4) % processSize;

        }
        else{
            int randint = random.nextInt();
            this.nextWord = randint % processSize;
        }
        this.references--;
    }

   /* public void results(){
        int faults = 0;
        float evicts = 0;
        float residency = 0;
        int i = 0;
        for( i = 0; i<processList.size(); i++){
            evicts += processList.get(i).evict;
            residency += processList.get(i).residency;
            faults += processList.get(i).page_fault;
            if(processList.get(i).evict == 0){
                System.out.println("Process " + processList.get(i).processNum + " had " + processList.get(i).page_fault +
                        " faults");
            }
            else{
                System.out.println("Process " + processList.get(i).processNum + " had " + processList.get(i).page_fault + " faults and "
                        +  processList.get(i).residency /  processList.get(i).evict + " the average residency is ");
            }
        }
        if(evicts == 0){
            System.out.println("The total number of faults is " + faults + " average residency is undefined");
        }
        else{
            System.out.println("The total number of faults is " + faults + " and the overall average residency is " + residency / evicts);
        }


    }
  */
}