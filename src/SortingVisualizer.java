import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

class SortingVisualizer {
	
	private static int[] arr;
	private static Algorithm al;
	
	SortingVisualizer() {
		initArray(10);
	}
	
	SortingVisualizer(int length) {
		initArray(length);
	}

	private static void initArray(int length) {
		arr = new int[length];
		for(int i = 0; i < length; ++i) {
			arr[i] = i+1;
		}
		for(int j = 0; j < length; ++j) {
			int r = (int)(Math.random() * length);
			int temp = arr[j];
			arr[j] = arr[r];
			arr[r] = temp;
		}
	}

	void visArray() {
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

	void inputAlgorithm(Scanner scan) {
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
		SortingVisualizer.al = al;
	}

	void sortSteps(Scanner scan) {
		System.out.println("\nPress enter for each step of sorting:");
		int at = 0;
		int qhi = arr.length-1;
		int qlo = 0;
		Stack<Integer> qstack = new Stack<Integer>();
		qstack.push(qlo);
		qstack.push(qhi);
		while(!inOrder()) {
			String str = scan.nextLine();
			if(str.equalsIgnoreCase("q")) break;
			else {
				System.out.println("Steps: " + (at+1));
				switch(al) {
				case SELECTION:
					selectionSort(at);
					break;
				case INSERTION:
					insertionSort(at+1);
					break;
				case QUICK:
					quickSort(qstack);
					break;
				case MERGE:
					break;
				default:
					throw new IllegalArgumentException("ERROR: Invalid algorithm!");
				}
			}
			visArray();
			++at;
		}
	}
	
	private static boolean inOrder() {
		for(int i = 0; i < arr.length-1; ++i) if(arr[i] >= arr[i+1]) return false;
		return true;
	}
	
	private static void selectionSort(int at) {
		int min = at;
		for (int i = at+1; i < arr.length; i++) if (arr[i] < arr[min]) min = i; 
		int temp = arr[min]; 
		arr[min] = arr[at]; 
		arr[at] = temp; 
	}
	
	private static void insertionSort(int at) {
		int i = at-1;
		int min = arr[at];
		while(i >= 0 && arr[i] > min) {
			arr[i+1] = arr[i];
			--i;
		}
		arr[i+1] = min;
	}
	
	/*
	 * Referenced: https://www.geeksforgeeks.org/iterative-quick-sort/
	 */
	private static void quickSort(Stack<Integer> qstack) {
		int qhi = qstack.pop();
        int qlo = qstack.pop();
        int part = quickSortPartition(qhi, qlo); 
        if (part - 1 > qlo) { 
            qstack.push(qlo);
            qstack.push(part-1); 
        } 
        if (part + 1 < qhi) { 
            qstack.push(part+1); 
            qstack.push(qhi);
        }
	}
	
	private static int quickSortPartition(int hi, int lo) {
		int piv = arr[hi];
		int s = lo-1;
		for(int i = lo; i < hi; ++i) {
			if(arr[i] < piv) {
				++s;
				int temp = arr[s];
				arr[s] = arr[i];
				arr[i] = temp;
			}
		}
		int temp = arr[s+1];
		arr[s+1] = arr[hi];
		arr[hi] = temp;
		return s+1;
	}
}