package writeupExperiment;

public class Experiments {

	public static void main(String[] args) {
		String[] dataStructures = {"-b", "-a", "-m", "-h"};
		String[] sortingMethods = {"-is", "-hs", "-os"};
		String[] input = {"datastructure", "sorting method", "hamlet.txt"};
		WordCount.getAverageRuntime(input);

	}

}
