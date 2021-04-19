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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Eventually get database code working with app.
    // MyDbHandler myDbHandler;
    //Value[] dbQuestionsArray = new Value[10];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SurveyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyFragment newInstance(String param1, String param2) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        column = getActivity().getIntent().getStringExtra("column_name");

        //TODO: Removed temporarily so to get around problems I'm having with the db
        //myDbHandler = new MyDbHandler(getActivity());
        //dbQuestionsArray = myDbHandler.getValuesArray();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(layoutManager);

        scrollView = (ScrollView)rootView.findViewById(R.id.scroll_view);
        scrollView.smoothScrollBy(-10, -10);

        nextButton = (Button)rootView.findViewById(R.id.nextButton);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        progressBar.setMax(100);

      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("Survey: Prospective Part I");

        answersList = Arrays.asList(getResources().getStringArray(R.array.answers));
        questionsList = Arrays.asList(getResources().getStringArray(R.array.questions));
        dimensionsList = Arrays.asList(getResources().getStringArray(R.array.dimensions));

        //Builds the surveyData object onCreate.  Doesn't update it, just does initial build.
        buildSurveyData(questionsList, dimensionsList);
        questionnaire = "personal";

        questionsListChecker = makeQandA(questionsList, answersList);
        adapter = new SurveyAdapter(getContext(), questionsListChecker, surveyData);

        adapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean clicked, CheckedExpandableGroup group, int childIndex) {
                int clickedQuestion = openGroup-1;
                childClicked = true;
                if (!surveyData.get(clickedQuestion).getHasBeenAnswered()){
                    adapter.toggleGroup(openGroup, questionsList);
                }
                surveyData.get(clickedQuestion).setHasBeenAnswered(true);
                questionsAnswered(surveyData);
            }
        });

        adapter.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
            @Override
            public void onGroupExpanded(ExpandableGroup group) {
                int index = 0;
                for (String question : questionsList) {
                    index++;
                    if (question.equals(group.getTitle())) {
                        openGroup = index;
                    }
                }
            }

            @Override
            public void onGroupCollapsed(ExpandableGroup group) {
                int index = 0;
                for (String question : questionsList) {
                    index++;
                    //Determines what question is being collapsed
                    if (question.equals(group.getTitle())) {
                        if ((index < questionsList.size()) && !surveyData.get(index).getHasBeenAnswered()){
                            expandNext(index, childClicked);
                        }
                    }
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
            i++;
            surveyData.add(new SurveyData());
            surveyData.get(i-1).setQuestion(question);
            surveyData.get(i-1).setQuestionID(i-1);
            surveyData.get(i-1).setHasBeenAnswered(false);
            surveyData.get(i-1).setDimension(dimensions.get(i-1));
        }
    }

    public void expandNext(int expand, boolean clicked){
        if (clicked){
            adapter.toggleGroup(expand);
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
            System.out.println("hello");

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

    public void questionsAnswered(List<SurveyData> surveyData){
        int i = 1;
        System.out.println("questionAnswered" + surveyData.get(i).getHasBeenAnswered());

        for(SurveyData question: surveyData) {
            if (question.getHasBeenAnswered()){
                System.out.println("questionAnswered " + ((i) * 10));
                progressBar.setProgress((i) * 10);
                i++;
            }
        }

        if (i >= 11){
            nextButton.setBackgroundResource(R.drawable.rounded_button_purple_);
            nextButton.setOnClickListener(this);
        }
        System.out.println("Hello from =" + i);
    }
}