package careerchangeover.com.valuesvisualizer.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import careerchangeover.com.valuesvisualizer.Survey.BuildSurvey;
import careerchangeover.com.valuesvisualizer.Survey.CheckQuestion;
import careerchangeover.com.valuesvisualizer.Survey.ExpandingAndCollapsingQuestions;
import careerchangeover.com.valuesvisualizer.Survey.SetSurveyStatement;
import careerchangeover.com.valuesvisualizer.Survey.SurveyAdapter;
import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.Survey.SurveyDataProgressBar;
import careerchangeover.com.valuesvisualizer.SurveyData;

import static careerchangeover.com.valuesvisualizer.Survey.DataFactory.makeQandA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyFragment extends Fragment implements View.OnClickListener{
    View rootView;
    RecyclerView recyclerView;
    ScrollView scrollView;
    String column;
    LinearLayoutManager layoutManager;
    TextView tvSurveyStatement;
    SurveyAdapter adapter;
    List<CheckQuestion> questionsListChecker;
    List<String> questionsList = new ArrayList<>();
    List<String> answersList = new ArrayList<>();
    List<String> dimensionsList = new ArrayList<>();
    List<SurveyData> surveyData = new ArrayList<SurveyData>();
    Button nextButton;
    BuildSurvey survey = new BuildSurvey();
    ExpandingAndCollapsingQuestions expandingAndCollapsingQuestions = new ExpandingAndCollapsingQuestions();
    SetSurveyStatement surveyStatement = new SetSurveyStatement();

    public SurveyFragment() {
        // Required empty public constructor
    }

    public static SurveyFragment newInstance(String param1, String param2) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //Resources
        answersList = Arrays.asList(getResources().getStringArray(R.array.answers));
        questionsList = Arrays.asList(getResources().getStringArray(R.array.questions));
        dimensionsList = Arrays.asList(getResources().getStringArray(R.array.dimensions));

    //Survey
        survey.setSurveyData(questionsList, dimensionsList);
        surveyData = survey.getSurveyData();

    //Survey Business Logic
        questionsListChecker = makeQandA(questionsList, answersList);
        expandingAndCollapsingQuestions.setAdapter(getContext(), questionsListChecker, surveyData);
        adapter = expandingAndCollapsingQuestions.getAdapter();
        expandingAndCollapsingQuestions.setQuestionsList(questionsList);
        expandingAndCollapsingQuestions.setSurveyData(surveyData);
        expandingAndCollapsingQuestions.onClick(rootView);
        expandingAndCollapsingQuestions.onGroupExpand();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        column = getActivity().getIntent().getStringExtra("column_name");

    //RecyclerView
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(11);
        recyclerView.setLayoutManager(layoutManager);

    //ScrollView
        scrollView = (ScrollView)rootView.findViewById(R.id.scroll_view);
        scrollView.smoothScrollBy(-10, -10);

    //NextButton
        nextButton = (Button)rootView.findViewById(R.id.nextButton);

    //ProgressBar
        SurveyDataProgressBar surveyDataProgressBar;
        surveyDataProgressBar = (SurveyDataProgressBar)(ProgressBar)rootView.findViewById(R.id.progressBar1);
        surveyDataProgressBar.setMax(100);
        expandingAndCollapsingQuestions.setListener(surveyDataProgressBar);

    //TextView Survey Statement
        tvSurveyStatement = rootView.findViewById(R.id.surveyStatementTextView);
        tvSurveyStatement.setAllCaps(true);
        tvSurveyStatement.bringToFront();
        surveyStatement.setQuestionnaire("personal");
        tvSurveyStatement.setText(surveyStatement.getSurveyStatement());

    //Next Button
        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

    //Random
        adapter.toggleGroup(0);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
       System.out.println("Hello from onClick  ");
    }

//    public void updateProgressBarAndNextButton(List<SurveyData> surveyData){
//        int i = 1;
//
//        for(SurveyData question: surveyData) {
//            if (question.getHasBeenAnswered()){
//                progressBar.setProgress((i) * 10);
//                i++;
//            }
//        }
//
//        if (i >= 11){
//            nextButton.setBackgroundResource(R.drawable.rounded_button_purple_);
//            nextButton.setOnClickListener(this);
//        }
//    }
}