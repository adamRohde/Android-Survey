package careerchangeover.com.valuesvisualizer.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import careerchangeover.com.valuesvisualizer.Survey.CheckQuestion;
import careerchangeover.com.valuesvisualizer.Survey.SurveyAdapter;
import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.SurveyData;

import static careerchangeover.com.valuesvisualizer.Survey.DataFactory.makeQandA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyFragment extends Fragment implements View.OnClickListener{
    // MyDbHandler myDbHandler;
    //Value[] dbQuestionsArray = new Value[10];

    View rootView;
    RecyclerView recyclerView;
    ScrollView scrollView;
    TextView surveyStatement;
    String column;
    LinearLayoutManager layoutManager;

    SurveyAdapter adapter;
    List<CheckQuestion> questionsListChecker;

    List<String> questionsList = new ArrayList<>();
    List<String> answersList = new ArrayList<>();
    List<String> dimensionsList = new ArrayList<>();
    List<SurveyData> surveyData = new ArrayList<SurveyData>();

    int openGroup = 0;
    boolean childClicked = false;
    Button nextButton;
    ProgressBar progressBar;
    String questionnaire = null;

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
        if (getArguments() != null) {

        }
        answersList = Arrays.asList(getResources().getStringArray(R.array.answers));
        questionsList = Arrays.asList(getResources().getStringArray(R.array.questions));
        dimensionsList = Arrays.asList(getResources().getStringArray(R.array.dimensions));
        buildSurveyData(questionsList, dimensionsList);
        questionnaire = "personal";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        column = getActivity().getIntent().getStringExtra("column_name");

        System.out.println("Hello from lifecycle onCreateView");

        //myDbHandler = new MyDbHandler(getActivity());
        //dbQuestionsArray = myDbHandler.getValuesArray();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(11);
        recyclerView.setLayoutManager(layoutManager);
        scrollView = (ScrollView)rootView.findViewById(R.id.scroll_view);
        scrollView.smoothScrollBy(-10, -10);
        nextButton = (Button)rootView.findViewById(R.id.nextButton);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        progressBar.setMax(100);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Survey: Prospective Part I");
        questionsListChecker = makeQandA(questionsList, answersList);
        adapter = new SurveyAdapter(getContext(), questionsListChecker, surveyData);

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean clicked, CheckedExpandableGroup group, int childIndex) {
                int index = 0;
                for (String question : questionsList) {
                    if (question.equals(group.getTitle())) {
                        surveyData.get(index).setHasBeenAnswered(true);
                        if ((index+1) != questionsList.size()){
                            if (!surveyData.get(index + 1).getHasBeenAnswered() && !surveyData.get(index + 1).getIsCurrentlyExpanded()) {
                                adapter.toggleGroup(index, questionsList);
                                adapter.toggleGroup(index + 1);
                                updateProgressBarAndNextButton(surveyData);
                            }
                        }else{
                            surveyData.get(index).setIsCurrentlyExpanded(false);
                            adapter.toggleGroup(index);
                            updateProgressBarAndNextButton(surveyData);
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

        adapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
            @Override
            public void onGroupExpanded(ExpandableGroup group) {
                int index = 0;
                for (String question : questionsList) {
                    if (question.equals(group.getTitle())) {
                        System.out.println("Hello from SurveyFragment ExpandListener line144 index=" + index);
                        surveyData.get(index).setIsCurrentlyExpanded(true);
                        openGroup = index;
                    }
                    index++;
                }
            }

            @Override
            public void onGroupCollapsed(ExpandableGroup group) {
                int index = 0;
                for (String question : questionsList) {
                    if (question.equals(group.getTitle())) {
                        surveyData.get(index).setIsCurrentlyExpanded(false);
                    }
                    index++;
                }
                childClicked = false;
            }
        });

        setSurveyStatement();
        adapter.toggleGroup(0);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void buildSurveyData(List<String> questions, List<String> dimensions){
        int i = 0;
        for (String question: questions){
            surveyData.add(new SurveyData());
            surveyData.get(i).setQuestion(question);
            surveyData.get(i).setQuestionID(i);
            surveyData.get(i).setHasBeenAnswered(false);
            surveyData.get(i).setDimension(dimensions.get(i));
            i++;
        }
    }

    public void setSurveyStatement() {
        surveyStatement = rootView.findViewById(R.id.surveyStatementTextView);
        surveyStatement.setAllCaps(true);
        surveyStatement.bringToFront();

/*        if (column.startsWith("personal")) {
            surveyStatement.setText(getResources().getString(R.string.personal_survey_statement));
        } else if (column.startsWith("employer")) {
            surveyStatement.setText(getResources().getString(R.string.employer_survey_statement));
        }*/

        if (questionnaire=="personal"){
            surveyStatement.setText(getResources().getString(R.string.personal_survey_statement));
        }else if (questionnaire=="employer"){
            progressBar.setProgress(0);
            surveyStatement.setText(getResources().getString(R.string.employer_survey_statement));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextButton) {

            questionnaire = "employer";

            for (int i = 0; i >= 9; i++){
                surveyData.get(i).setHasBeenAnswered(false);
            }

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();

            PartTwoPrepFragment nextFrag= new PartTwoPrepFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_survey, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void updateProgressBarAndNextButton(List<SurveyData> surveyData){
        int i = 1;

        for(SurveyData question: surveyData) {
            if (question.getHasBeenAnswered()){
                progressBar.setProgress((i) * 10);
                i++;
            }
        }

        if (i >= 11){
            nextButton.setBackgroundResource(R.drawable.rounded_button_purple_);
            nextButton.setOnClickListener(this);
        }
    }
}