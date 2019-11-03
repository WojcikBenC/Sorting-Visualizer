import java.util.InputMismatchException;
import java.util.Scanner;

enum Algorithm {
	SELECTION, INSERTION, QUICK, MERGE;
}

class SortingVisualizer {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to sorting visualizer!\n\nPress enter to sort step by step, or enter q to quit.");
		int length = inputLength(scan);
		int[] arr = initArray(length);
		visArray(arr);
		Algorithm al = inputAlgorithm(scan);
		sortSteps(arr, scan, al);
		scan.close();
	}

	private static int[] initArray(int length) {
		int[] arr = new int[length];
		for(int i = 0; i < length; ++i) {
			arr[i] = i+1;
		}
		for(int j = 0; j < length; ++j) {
			int r = (int)(Math.random() * length);
			int temp = arr[j];
			arr[j] = arr[r];
			arr[r] = temp;
		}
		return arr;
	}

	private static void visArray(int[] arr) {
		int s = arr.length;
		for(int i = 0; i < arr.length; ++i) {
			for(int j = 0; j < arr.length; ++j) {
				if(arr[j] >= s) System.out.print("# ");
				else System.out.print("  ");
			}
			System.out.print("\n");
			--s;
		}
		System.out.print("\n");
	}

	private static int inputLength(Scanner scan) {
		System.out.println("\nPlease enter an array length: ");
		int length = 0;
		while(length < 2) {
			try {
				length = scan.nextInt();
				if(length < 2) System.out.println("Please enter an integer greater than 1: ");
			}catch(InputMismatchException e) {
				System.out.println("Please enter an integer greater than 1: ");
			}
			scan.nextLine();
		}
		return length;
	}

	private static Algorithm inputAlgorithm(Scanner scan) {
		System.out.println("Please enter a sorting algorithm [selection, insertion, quick, merge]: ");
		Algorithm al = null;
		String s = "";
		while(al == null) {
			try {
				s = scan.nextLine();
				if(s.equalsIgnoreCase("selection")) al = Algorithm.SELECTION;
				else if(s.equalsIgnoreCase("insertion")) al = Algorithm.INSERTION;
				else if(s.equalsIgnoreCase("quick")) al = Algorithm.QUICK;
				else if(s.equalsIgnoreCase("merge")) al = Algorithm.MERGE;
				else System.out.println("Please enter either [selection, insertion, quick, merge]: ");
			}catch(InputMismatchException e) {
				System.out.println("Please enter either [selection, insertion, quick, merge]: ");
			}
		}
		return al;
	}

	private static void sortSteps(int[] arr, Scanner scan, Algorithm al) {
		System.out.println("\nPress enter for each step of sorting:");
		int at = 0;
		while(!inOrder(arr)) {
			String s = scan.nextLine();
			if(s.equalsIgnoreCase("q")) break;
			else {
				switch(al) {
				case SELECTION:
					arr = selectionSort(arr, at);
					break;
				case INSERTION:
					arr = insertionSort(arr, at+1);
					break;
				case QUICK:
					break;
				case MERGE:
					break;
				default:
					throw new IllegalArgumentException("ERROR: Invalid algorithm!");
				}
				visArray(arr);
			}
			++at;
		}
	}
	
	private static boolean inOrder(int[] arr) {
		for(int i = 0; i < arr.length-1; ++i) if(arr[i] >= arr[i+1]) return false;
		return true;
	}
	
	private static int[] selectionSort(int[] arr, int at) {
		int min = at;
		for (int i = at+1; i < arr.length; i++) if (arr[i] < arr[min]) min = i; 
		int temp = arr[min]; 
		arr[min] = arr[at]; 
		arr[at] = temp; 
		return arr;
	}
	
	private static int[] insertionSort(int[] arr, int at) {
		int i = at-1;
		int min = arr[at];
		while(i >= 0 && arr[i] > min) {
			arr[i+1] = arr[i];
			--i;
		}
		arr[i+1] = min;
		return arr;
	}
}