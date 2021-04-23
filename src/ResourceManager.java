import java.util.Scanner;
public class ResourceManager{
	private int need[][],
	allocation[][],
	maximum[][],
	available[][],
	processes,
	resources;

	private void getInput(){
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter no. of processes: ");
		processes = sc.nextInt();  
		System.out.print("Enter no. resources: ");
		resources = sc.nextInt();  
		need = new int[processes][resources];  //initializing arrays
		maximum = new int[processes][resources];
		allocation = new int[processes][resources];
		available = new int[1][resources];

		System.out.println("Enter allocation matrix");
		for(int i = 0; i < processes; i++)
			for(int j = 0; j < resources; j++)
				allocation[i][j] = sc.nextInt();  //allocation matrix

		System.out.println("Enter maximum matrix");
		for(int i = 0; i < processes; i++)
			for(int j = 0; j < resources; j++)
				maximum[i][j] = sc.nextInt();  //max matrix

		System.out.println("Enter available matrix");
		for(int j = 0; j < resources; j++)
			available[0][j] = sc.nextInt();  //available matrix

		sc.close();
	}
	
	public void start() {
		getInput();
		isSafe();
		
	}

	private int[][] synchronizedAllocate(){
		for(int i = 0; i < processes;i++) {
			//calculating need matrix
			for(int j = 0; j < resources; j++) {
				if (allocation[i][j] > maximum[i][j]) {
					System.out.println("Can't request more than you declared");
					System.exit(0);
				}
//				else if (allocation[i][j] > available[i][j]) {
//					System.out.println("Insufficient resource available");
//				}
//				else {
//					need[i][j]=max[i][j]-allocation[i][j];
//				}
				need[i][j] = maximum[i][j] - allocation[i][j];
			}
		}
		return need;
	}
	
	//check if all resources for ith process can be allocated
	private boolean check(int i) {
		
		for(int j = 0; j < resources; j++) 
			if(available[0][j] < need[i][j])
				return false;
		return true;
	}

	public void isSafe(){
		synchronizedAllocate();
		boolean done[]=new boolean[processes];
		int j = 0;
		
		//until all process allocated
		while(j < processes){  
			boolean allocated=false;
			for(int i = 0; i < processes; i++) {
				//trying to allocation
				// process hasn't done allocation yet
				if(!done[i] && check(i)){  
					for(int k = 0; k < resources; k++)
						available[0][k] = available[0][k] - need[i][k] + maximum[i][k];
					System.out.println("Allocated process : "+i);
					allocated = done[i] = true;
					j++;
				}
			}
			//if no allocation
			if(!allocated) {
				System.out.println("Insufficient resource available");
				break;  
			}
		}
		//if all processes are allocated
		if(j == processes)  
			System.out.println("\nSafely allocated");
		else
			System.out.println("All proceess can't be allocated safely");
	}

	public static void main(String[] args) {
		ResourceManager obj = new ResourceManager();
		obj.start();
	}
}