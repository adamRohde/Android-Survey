package careerchangeover.com.valuesvisualizer.Survey;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.SurveyData;

public class SurveyAdapter extends CheckableChildRecyclerViewAdapter<QuestionViewHolder, AnswerViewHolder> {
    int openGroup;
    public List<CheckQuestion> QuestionGroups;
    public List<SurveyData> surveyData;
    public int index;
    public View view = null;
    public List<String> questionArray = new ArrayList<>();

    public SurveyAdapter(Context context, List<CheckQuestion> groups, List<SurveyData> data) {
        super(groups);
        QuestionGroups = groups;
        surveyData = data;
    }

    @Override
    public AnswerViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_answers_recycler, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindCheckChildViewHolder(AnswerViewHolder holder, int position,
                                           CheckedExpandableGroup group, int childIndex) {
        final Answers answers = (Answers) group.getItems().get(childIndex);
        holder.setAnswerName(answers.getName());
    }

    @Override
    public QuestionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_questions_recycler, parent, false);
        return new QuestionViewHolder(view);
    }

    public boolean toggleGroup(int flatPos, List<String> Questions) {
        index++;
        openGroup = flatPos + 1;
        questionArray = Questions;
        System.out.println("Hello from toggleGroup = " + index);
        return super.toggleGroup(flatPos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindGroupViewHolder(QuestionViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group, openGroup, surveyData);
    }
}
