package careerchangeover.com.valuesvisualizer.Survey;

import java.util.ArrayList;
import java.util.List;

import careerchangeover.com.valuesvisualizer.SurveyData;

public class BuildSurvey {

    private List<SurveyData> surveyData = new ArrayList<SurveyData>();

    public BuildSurvey() {

    }

    public void setSurveyData(List<String> questions, List<String> dimensions) {
        int i = 0;
        for (String question: questions){
            surveyData.add(new SurveyData());
            surveyData.get(i).setQuestion(question);
            surveyData.get(i).setQuestionID(i);
            surveyData.get(i).setHasBeenAnswered(false);
            surveyData.get(i).setDimension(dimensions.get(i));
            i++;
        }

        this.surveyData = surveyData;
    }

    public List<SurveyData> getSurveyData() {
        return surveyData;
    }

}
