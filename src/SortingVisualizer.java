import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

class SortingVisualizer {

	private static int length;
	private static int[] arr;
	private static Algorithm al;
	private static boolean reversed;

	private static final Scanner scan = new Scanner(System.in);

	SortingVisualizer() {
		System.out.println("Welcome to sorting visualizer!\n\nPress enter to sort step by step, or enter q to quit.");
	}

	void initArray() {
		arr = new int[length];
		if(!reversed) {
			System.out.println("test");
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
		else {
			for(int i = 0; i < length; ++i) {
				arr[i] = length-i;
			}
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

	void inputLength() {
		System.out.println("\nPlease enter an array length: ");
		while(length < 2) {
			try {
				length = scan.nextInt();
				if(length < 2) System.out.println("Please enter an integer greater than 1: ");
			}catch(InputMismatchException e) {
				System.out.println("Please enter an integer greater than 1: ");
			}
			scan.nextLine();
		}
	}

	void inputAlgorithm() {
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

	void inputReversed() {
		System.out.println("Please choose if you want the array random or reversed: ");
		boolean chosen = false;
		String s = "";
		while(!chosen) {
			try {
				s = scan.nextLine();
				if(s.equalsIgnoreCase("random")) {
					chosen = true;
					reversed = false;
				}
				else if(s.equalsIgnoreCase("reversed")) {
					chosen = true;
					reversed = true;
				}
				else System.out.println("Please enter either [random, reversed]: ");
			} catch(InputMismatchException e) {
				System.out.println("Please enter either [random, reversed]: ");
			}
		}
	}

	void sortSteps() {
		System.out.println("\nPress enter for each step of sorting:");
		int at = 0;
		Stack<Integer> qstack = new Stack<Integer>();
		Stack<Integer> mstack = new Stack<Integer>();
		qstack.add(0);
		qstack.add(arr.length-1);
		mstack = split(mstack, 0, arr.length-1);
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
					mergeSort(mstack);
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
		for(int i = 1; i < arr.length; ++i) if(arr[i-1] >= arr[i]) return false;
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

	private static void mergeSort(Stack<Integer> mstack) {
		int left = mstack.pop();
		System.out.println("Left: " + left);
		int right = mstack.pop();
		System.out.println("Right: " + right);
		if(right > left) {
			int mid = (left+right)/2;
			merge(left, mid, right);
		}
	}
	
	private static Stack<Integer> split(Stack<Integer> mstack, int left, int right) {
		if(right > left) {
			int mid = (left+right)/2;
			mstack.push(right);
			mstack.push(left);
			mstack = split(mstack, left, mid);
			mstack = split(mstack, mid+1, right);
		}
		return mstack;
	}

	private static void merge(int left, int mid, int right) {
		int n1 = mid - left + 1;
		int n2 = right - mid;
		int L[] = new int [n1]; 
		int R[] = new int [n2]; 
		for (int i=0; i<n1; ++i) L[i] = arr[left + i]; 
		for (int j=0; j<n2; ++j) R[j] = arr[mid + 1+ j]; 
		int i = 0, j = 0; 
		int k = left; 
		while (i < n1 && j < n2) { 
			if (L[i] <= R[j]) { 
				arr[k] = L[i]; 
				i++; 
			} else { 
				arr[k] = R[j]; 
				j++; 
			} 
			k++; 
		} 
		while (i < n1) { 
			arr[k] = L[i]; 
			i++; 
			k++; 
		} 
		while (j < n2) { 
			arr[k] = R[j]; 
			j++; 
			k++; 
		}
	}
}