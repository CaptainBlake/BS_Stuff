package speicherverwaltung.memoryManager;

import speicherverwaltung.processManager.Process;

public class WorstFit extends MemoryManager {

	private int[] memory = new int[size]; //Memory als array
	private int[] gapTable = new int [2];    // [Lückenanfang] [Lückengröße]
	/*
	 * Constructor baut den Speicher, hier: int array [cell] in der größe size [1024] mit dem Eintrag "-1" -> frei
	 * kommt ein prozess, wird die ID in die cell geschrieben
	 * Aktuallisierungen werden iterativ über dem gesammten Speicher gemacht. (soll ja nicht schnell sein)
	 */
	public WorstFit(int size) {
		super(size);

		for(int i : memory){
			memory[i] = -1;
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
