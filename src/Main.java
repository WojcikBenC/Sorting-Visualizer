enum Algorithm {
	SELECTION, INSERTION, QUICK, MERGE;
}

public class Main {
	public static void main(String[] args) {
		SortingVisualizer sv = new SortingVisualizer();
		sv.inputLength();
		sv.inputReversed();
		sv.initArray();
		sv.visArray();
		sv.inputAlgorithm();
		sv.sortSteps();
	}
}