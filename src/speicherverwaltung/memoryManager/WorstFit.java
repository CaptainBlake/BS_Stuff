package speicherverwaltung.memoryManager;

import speicherverwaltung.processManager.Process;

public class WorstFit extends MemoryManager {

	private int[] memory; //Memory als array
	private int[][] gapTable = new int [6][2];    // [Lückenanfang] [Lückengröße]
	private String[] names = new String[10];
	/*
	 * Constructor baut den Speicher, hier: int array [cell] in der größe size [1024] mit dem Eintrag "-1" -> frei
	 * kommt ein prozess, wird die ID in die cell geschrieben
	 * Aktuallisierungen werden iterativ über dem gesammten Speicher gemacht. (soll ja nicht schnell sein)
	 */
	public WorstFit(int size) {
		super(size);
		memory = new int[size];
		//setzt felder frei
		System.out.println("setting up...");
		for(int i = 0; i<size; ++i){
			memory[i] = -1;
			//System.out.print(i + ": " +memory[i] + " |");
		}
		setGapTable();
		printGapTable();
		System.out.println("Done!");
	}

	private void printGapTable() {
		for(int i = 0; i < gapTable.length; i++){
			System.out.println("gapStart "+ gapTable[i][0] + " GapSize: " + gapTable[i][1]);
		}
	}

	private void setGapTable(){
		int c = 0;
		int gapStart = 0;
		int gapEnd = 0;
		int gapIndex = 0;
		boolean toggle = false;
		int size = getMemorySize();
		while(c < size){
			//System.out.print(c + ": " + memory[c] + " |");
			if(memory[c] == -1 && c < size - 1){
				//System.out.println("found gap");
				if(!toggle){
					gapStart = c;
					gapEnd = gapStart;
					toggle = true;
				}else{
					//System.out.println("same gap");
					gapEnd++;
				}
			}else{
				if (toggle){
					System.out.println("gap closed");
					gapTable[gapIndex][0] = gapStart;
					gapTable[gapIndex][1] = (c != size - 1) ? (gapEnd - gapStart) : (gapEnd + 1 - gapStart);
					gapIndex++;
					if(gapIndex >= gapTable.length){
						System.err.println("GapTable overflow!");
						return;
					}
					toggle = false;
				}
			}
			c++;
		}
	}
	//reserviert den speicher
	@Override
	public boolean allocateMemory(Process process) {
		int pid = process.getId();
		int psize = process.getSize();
		int gapStart = getBiggestGap();
		names[pid] = process.getName();

		if (gapStart == -1)
			return false;

		for (int i = 0; i < psize; ++i) {
			memory[gapStart + i] = pid;
		}
		
		setGapTable();
		printGapTable();
		return true;
	}

	private int getBiggestGap() {
		int max = 0;
		int index = -1;
		for (int i = 0; i < gapTable.length; i++) {
			if (gapTable[0][1] == 0) {
				return -1;
			} else if (gapTable[i][1] > max) {
				max = gapTable[i][1];
                index = gapTable[i][0];
			}
		}
		return index;
	}


	//gibt den speicher eines Prozesses wieder frei
	@Override
	public void freeMemory(Process process) {
		// TODO Auto-generated method stub

		int pid = process.getId();
		int psize = process.getSize();
		int gapStart = -1;
		names[pid] = null;
		
		for(int i = 0; i < memory.length; ++i)
		{
			if(memory[i] == pid)
			{
				gapStart = i;
				break;
			}
		}

		for (int i = 0; i < psize; ++i) {
			memory[gapStart + i] = -1;
		}
		
		setGapTable();
		printGapTable();

	}
	/**
	 * This function prints out all reserved memory in the following format:
	 *
	 * Id 	Name 	Size 	start 	EndPos
	 * 0	p1		100		0		99
	 * 1	p2		500		100		599
	 * .....
	 */
	@Override
	public void printAllProcesses() {
        int[][] table = new int[10][4]; // [ID][Size][start][End]
        for(int i=0;i<table.length;i++){
            for(int k=0;k<table[i].length;k++){
                table[i][k] = -1;
            }
        }
        int index = -1;
        for(int i=0;i<memory.length;i++){
            if(memory[i] != -1){
                index++;
                table[index][0] = memory[i];
                table[index][2] = i;
                int size = 1;
                while(memory[i] == table[index][0]){
                    i++;
                    size++;
                }
                table[index][1] = i-table[index][2];
                table[index][3] = i;
            }
        }
        System.out.println("\n***Processtable***");
        System.out.println("[ID]\t[NAME]\t[Size]\t[Start]\t[End]");
        for(int i=0;i<table.length;i++){
            System.out.println(table[i][0] + "\t" + (names[i] == null ? " " : names[i]) + "\t" + table[i][1] + "\t" + table[i][2] + "\t" + table[i][3]);
        }
    }
}
