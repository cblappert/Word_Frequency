package writeupExperiment;

public class Experiments {

	public static void main(String[] args) {
		// Tests for Question 9.
		String[] dataStructures = {"-b", "-a", "-m", "-h"};
		String[] sortingMethods = {"-is", "-hs", "-os"};
		String[] input = {"datastructure", "sorting method", "hamlet.txt"};
		for(String structure : dataStructures) {
			for(String sort : sortingMethods) {
				input[0] = structure;
				input[1] = sort;
				System.out.println("Run: " + structure + sort + " " + 
						WordCount.getAverageRuntime(input));
			}
		}
		// Other tests

	}

}
