import java.util.*;

public class Paging {

    static ArrayList<Processes> processList = new ArrayList<Processes>();

    public Paging(ArrayList<Processes> processList){
        this.processList = processList;
    }


    public  void initalize(int jobType, int references) {
        switch (jobType) {
            case (1): {
                for (int i = 0; i < 1; i++) {
                    processList.add(new Processes(i, 1, 0, 0, references));
                }
                break;
            }
            case (2): {
                for (int i = 0; i < 4; i++) {
                    processList.add(new Processes(i, 1, 0, 0, references));
                }
                break;
            }
            case (3): {
                for (int i = 0; i < 4; i++) {
                    processList.add(new Processes(i, 0, 0, 0, references));

                }
                break;
            }
            default: {
                processList.add(new Processes(0, .75, .25, 0, references));
                processList.add(new Processes(1, .75, 0, .25, references));
                processList.add(new Processes(2, .75, .125, .125, references));
                processList.add(new Processes(3, .5, .125, .125, references));
                break;
            }
        }
    }

    public  void Paging(Frames[] frameList, Scanner random, int machineSize, int pageSize, int processSize, int jobMix, int references, String algorithm) {
       // System.out.println("in here");
        Queue<Frames> frameQueue = new LinkedList<Frames>(); //lru
        Stack<Frames> frameStack = new Stack<Frames>(); //lifo
        int time = 1;
        while (((processList.size()) * references) >= time) {

            for (int i = 0; i < processList.size(); i++) {

                for (int j = 0; j < 3 && processList.get(i).references > 0; j++) {
                    if (processList.get(i).r != 0) {
                        processList.get(i).word = (processList.get(i).nextWord + processSize) % processSize;
                    } else {
                        processList.get(i).word = (111 * (i + 1)) % processSize;
                        processList.get(i).nextWord = processList.get(i).word;
                        processList.get(i).r = 1;
                    }
                    int page_number = processList.get(i).word / pageSize;
                    Frames this_frame = new Frames(page_number, processList.get(i).processNum);
                    int frameNum = contains(this_frame, frameList, processList);
                    if (frameNum < 0) {
                        int evict = addFrame(this_frame,processList,frameList,random,frameQueue,frameStack,algorithm,time);
                    }

                    else {
                        if (algorithm.equals("lru") || algorithm.equals("fifo")) {
                            Iterator<Frames> iter = frameQueue.iterator();
                            while (iter.hasNext()) {
                                Frames frame_2 = iter.next();
                                if (this_frame.processType == frame_2.processType && this_frame.frameType == frame_2.frameType) {
                                    iter.remove();
                                }
                            }
                            frameQueue.add(this_frame);
                        }
                    }
                    int nextint = random.nextInt();
                    double y = nextint / (Integer.MAX_VALUE + 1d);
                    processList.get(i).GenerateNextWord(random,y,processSize);
                    time++;

                }

            }
        }
        //System.out.println("out of here");
        results(processList);
    }

    public int contains(Frames current_frame, Frames[] frame_list, ArrayList<Processes> processList) {
        for (int i = 0; i < frame_list.length; i++) {
            if (frame_list[i] == null) continue;

            if (frame_list[i].processType == current_frame.processType && frame_list[i].frameType == current_frame.frameType) {
                current_frame.load = frame_list[i].load;
                return i;
            }
        }
        processList.get(current_frame.processType).page_fault++;
        return -1;

    }

    public int addFrame(Frames this_frame, ArrayList<Processes> processList, Frames[] frameList, Scanner random, Queue<Frames> frameQueue, Stack<Frames> frameStack, String algorithm, int time) {
        for (int k = frameList.length - 1; k >= 0; k--) {
            if (frameList[k] == null) {
                frameList[k] = this_frame;
                if (algorithm.equals("lru") || algorithm.equals("fifo")) {
                    frameQueue.add(this_frame);
                }
                this_frame.load = time;
                return k;
            }
        }
        switch (algorithm) {
            case ("lru"): {
                Frames frameEvicted = frameQueue.poll();
                for (int k = 0; k < frameList.length; k++) {
                    if (frameList[k].processType == frameEvicted.processType && frameList[k].frameType == frameEvicted.frameType) {
                        frameList[k] = this_frame;
                        this_frame.load = time;
                        frameQueue.add(this_frame);
                        processList.get(frameEvicted.processType).residency += (time - frameEvicted.load);
                        processList.get(frameEvicted.processType).evict++;
                        return k;
                    }
                }
                return -1;

            }
            case ("fifo"): {
                Frames frameEvicted = frameQueue.poll();
                for (int k = 0; k < frameList.length; k++) {
                    if (frameList[k].processType == frameEvicted.processType && frameList[k].frameType == frameEvicted.frameType) {
                        frameList[k] = this_frame;
                        this_frame.load = time;
                        frameQueue.add(this_frame);
                        processList.get(frameEvicted.processType).residency += (time - frameEvicted.load);
                        processList.get(frameEvicted.processType).evict++;
                        return k;
                    }
                }
                return -1;

            }
            case ("random"): {
                int eviction = random.nextInt() % frameList.length;
                Frames frameEvicted = frameList[eviction];
                processList.get(frameEvicted.processType).evict++;
                processList.get(frameEvicted.processType).residency += (time - frameEvicted.load);
                frameList[eviction] = this_frame;
                this_frame.load = time;
                return eviction;
            }
            default:{
                return -1;
            }
        }

    }

    public void results(ArrayList<Processes> processList){
        int faults = 0;
        float evicts = 0;
        float residency = 0;
        for(int i = 0; i<processList.size(); i++){
            evicts += processList.get(i).evict;
            residency += processList.get(i).residency;
            faults += processList.get(i).page_fault;
            if(processList.get(i).evict == 0){
                System.out.println("Process " + processList.get(i).processNum + " had " + processList.get(i).page_fault +
                        " faults");
            }
            else{
                System.out.println("Process " + processList.get(i).processNum + " had " + processList.get(i).page_fault + " faults and a "
                        +  processList.get(i).residency /  processList.get(i).evict + " average residency ");
            }
        }
        if(evicts == 0){
            System.out.println("The total number of faults is " + faults + " average residency is undefined");
        }
        else{
            System.out.println("The total number of faults is " + faults + " and the overall average residency is " + residency / evicts);
        }


    }

}

