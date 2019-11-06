import java.util.InputMismatchException;
import java.util.Scanner;

enum Algorithm {
	SELECTION, INSERTION, QUICK, MERGE;
}

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to sorting visualizer!\n\nPress enter to sort step by step, or enter q to quit.");
		int length = inputLength(scan);
		SortingVisualizer sv = new SortingVisualizer(length);
		sv.visArray();
		sv.inputAlgorithm(scan);
		sv.sortSteps(scan);
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
}