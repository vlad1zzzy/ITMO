import java.util.Arrays;
import java.util.stream.Collectors;

public class Answer {
    double[] vector;
    double value;
    int numberOfIterations;

    public Answer(double[] vector, double value, int numberOfIterations) {
        this.vector = vector;
        this.value = value;
        this.numberOfIterations = numberOfIterations;
    }

    public void print() {
        System.out.println(Arrays.stream(vector).mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", numberOfIterations + ") ANSWER : f( ", " ) = ")) + value);
    }

    public void printAns() {
        System.out.println("Iterations: " + this.numberOfIterations + "  , f = " + this.value + ", xk = " + Arrays.stream(vector).mapToObj(String::valueOf)
                .collect(Collectors.joining(", ")));
    }
}
