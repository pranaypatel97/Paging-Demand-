import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pager {
    static ArrayList<Processes> processList = new ArrayList<Processes>();

    public static void main(String args[]) throws IOException {
        int machine_size = Integer.parseInt(args[0]);
        int page_size = Integer.parseInt(args[1]);
        int process_size = Integer.parseInt(args[2]);
        int job_mix = Integer.parseInt(args[3]);
        int references = Integer.parseInt(args[4]);
        String algorithm = args[5];
        int debug = Integer.parseInt(args[6]);
        Scanner rand;
        rand = new Scanner(new File("random-numbers.txt"));
        Frames[] frameList = null;
        frameList = new Frames[machine_size / page_size];
        System.out.println("");
        System.out.println("The machine size is " + machine_size);
        System.out.println("The page size is " + page_size);
        System.out.println("The process size is " + process_size);
        System.out.println("The job mix number is " + job_mix);
        System.out.println("The number of references per process is " + references);
        System.out.println("The replacement algorithm is " + algorithm);
        System.out.println("The level of debugging output is " + debug);
        Paging page = new Paging(processList);
        page.initalize(job_mix, references);
        page.Paging(frameList, rand, machine_size, page_size, process_size, job_mix, references, algorithm);

    }

}
