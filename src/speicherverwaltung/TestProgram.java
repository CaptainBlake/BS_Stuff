package speicherverwaltung;

import speicherverwaltung.memoryManager.MemoryManager;
import speicherverwaltung.memoryManager.WorstFit;
import speicherverwaltung.processManager.Process;
import speicherverwaltung.processManager.ProcessManager;

public class TestProgram {

	private static final int physicalMemorySize = 1024;

	private static ProcessManager pm;
	private static MemoryManager mmu;

	/**
	 * Test test-program does not reflect the real interaction between processes and the memory Manager. However, it can
	 * be used to test different memory management strategies.
	 * 
	 * For simplicity, in this test-program every process receives its own continuous block of memory
	 */
	public static void main(String[] args) {
		mmu = new WorstFit(physicalMemorySize);
		pm = new ProcessManager(mmu);

		Process p1 = createProcess("p1", 130);
		Process p2 = createProcess("p2", 80);
		Process p3 = createProcess("p3", 250);
		Process p4 = createProcess("p4", 250);
		Process p5 = createProcess("p5", 60);

		killProcess(p2);
		killProcess(p4);

		Process p6 = createProcess("p6", 30);
		Process p7 = createProcess("p7", 70);
	}

	/**
	 * This function just calls "process.kill()" and produces some nice output
	 */
	private static void killProcess(Process process) {
		if (null != process) {
			String name = process.getName();
			boolean result = process.kill();
			if (true == result) {
				System.out.println("New Memory Partitioning after killing process: " + name);
				mmu.printAllProcesses();
			}
		} else {
			System.out.println("could not kill process");
		}
		System.out.println();
	}

	/**
	 * This function just calls "pm.createProcess(name, size)" and produces some nice output
	 */
	private static Process createProcess(String name, int size) {
		Process result;
		result = pm.createProcess(name, size);
		if (null != result) {
			System.out.println("New Memory Partitioning after starting process: " + name + " with size:" + size);
			mmu.printAllProcesses();
		}
		System.out.println();
		return result;
	}
}
