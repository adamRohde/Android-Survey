package careerchangeover.com.valuesvisualizer.Survey;

import android.widget.TextView;

import careerchangeover.com.valuesvisualizer.R;

public class SetSurveyStatement {
    String questionnaire;

    public void SetSurveyStatement() {}

    public void setQuestionnaire(String questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getSurveyStatement() {
        String theSurveyStatement = "";

        if (questionnaire=="personal"){
            theSurveyStatement = "IN MY LIFE, IT IS IMPORTANT FOR ME TO:";
        }else if (questionnaire=="employer"){
            theSurveyStatement = "TO MY BEST KNOWLEDGE, AT THIS COMPANY, I\\'M EXPECTED TO:";
        }
        return theSurveyStatement;
    }

}
