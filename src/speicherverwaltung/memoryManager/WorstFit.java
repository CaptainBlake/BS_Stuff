package speicherverwaltung.memoryManager;

import speicherverwaltung.processManager.Process;

public class WorstFit extends MemoryManager {

	private int[] memory; //Memory als array
	private int[][] gapTable = new int [10][2];    // [Lückenanfang] [Lückengröße]
	/*
	 * Constructor baut den Speicher, hier: int array [cell] in der größe size [1024] mit dem Eintrag "-1" -> frei
	 * kommt ein prozess, wird die ID in die cell geschrieben
	 * Aktuallisierungen werden iterativ über dem gesammten Speicher gemacht. (soll ja nicht schnell sein)
	 */
	public WorstFit(int size) {
		super(size);
		memory = new int[size];
		//setzt felder frei
		for(int i : memory){
			memory[i] = -1;
		}
		setGapTable();
		printGapTable();
	}

	private void printGapTable() {
		for(int[] i : gapTable){
			System.out.print();
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
			if(memory[c] == -1){
				if(!toggle){
					gapStart = c;
					gapEnd = gapStart;
					toggle = true;
				}else{
					gapEnd++;
				}
			}else{
				if (toggle){
					gapTable[gapIndex][0] = gapStart;
					gapTable[gapIndex][1] = gapEnd-gapStart;
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

		return false;
	}

	//gibt den speicher eines Prozesses wieder frei
	@Override
	public void freeMemory(Process process) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub


	}

}
