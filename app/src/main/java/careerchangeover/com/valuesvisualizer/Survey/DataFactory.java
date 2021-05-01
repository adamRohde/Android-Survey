package careerchangeover.com.valuesvisualizer.Survey;

import java.util.ArrayList;
import java.util.List;

import careerchangeover.com.valuesvisualizer.R;

public class DataFactory {

    public static List<CheckQuestion> makeQandA(List<String> questions, List<String> answers) {
        int i = 0;

        List<CheckQuestion> questionList;
        questionList = new ArrayList<>();

        List<Answers> answersList;
        answersList = new ArrayList<>();

        for (String answer : answers) {
            answersList.add(new Answers(answer));
        }

        for (String question : questions){
            questionList.add(new CheckQuestion(question, answersList, R.drawable.ic_checked_foreground));
        }
        return questionList;
    }
}

