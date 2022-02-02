package methods.marquardt;

import java.util.List;

public class AnswerM {
    private final double[] answer;
    private final List<Double> tau;

    public List<Integer> getCholetskyNum() {
        return choletskyNum;
    }

    private  final List<Integer> choletskyNum;

    public AnswerM(double[] answer, List<Double> tau) {
        this.answer = answer;
        this.tau = tau;
        choletskyNum = List.of();
    }

    public AnswerM(double[] answer, List<Double> tau, List<Integer> choletskyNum) {
        this.answer = answer;
        this.tau = tau;
        this.choletskyNum = choletskyNum;
    }

    public double[] getAnswer() {
        return answer;
    }

    public List<Double> getTau() {
        return tau;
    }

}
