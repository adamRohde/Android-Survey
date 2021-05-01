package careerchangeover.com.valuesvisualizer.Survey;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import careerchangeover.com.valuesvisualizer.SurveyData;

public class CollapseQuestion extends Activity implements View.OnClickListener {

    private SurveyAdapter adapter;
    private List<String> questionsList = new ArrayList<>();
    private List<SurveyData> surveyData = new ArrayList<SurveyData>();

    public CollapseQuestion() {
    }

    public SurveyAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Context context, List<CheckQuestion> questionsListChecker, List<SurveyData> surveyData) {
        adapter = new SurveyAdapter(context, questionsListChecker, surveyData);
        this.adapter = adapter;
    }

    public void setQuestionsList(List<String> questionsList) {
        this.questionsList = questionsList;
    }

    public void setSurveyData(List<SurveyData> surveyData) {
        this.surveyData = surveyData;
    }

    public void onClick(View v) {
        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                int index = 0;
                for (String question : questionsList) {
                    if (question.equals(group.getTitle())) {
                        surveyData.get(index).setHasBeenAnswered(true);
                        if ((index+1) == questionsList.size()){
                            surveyData.get(index).setIsCurrentlyExpanded(false);
                            adapter.toggleGroup(index);
                        }else{
                            if (!surveyData.get(index + 1).getHasBeenAnswered() && !surveyData.get(index + 1).getIsCurrentlyExpanded()) {
                                adapter.toggleGroup(index, questionsList);
                                adapter.toggleGroup(index + 1);
                            }
                        }
                        if (surveyData.get(index).getHasBeenAnswered() && surveyData.get(index).getIsCurrentlyExpanded()) {
                            surveyData.get(index).setIsCurrentlyExpanded(false);
                            adapter.toggleGroup(index);
                        }
                    }
                    index++;
                }
            }
        });
    }
}
