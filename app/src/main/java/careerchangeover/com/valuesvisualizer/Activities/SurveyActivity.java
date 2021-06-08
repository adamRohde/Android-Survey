package careerchangeover.com.valuesvisualizer.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import careerchangeover.com.valuesvisualizer.Fragments.SurveyFragment;
import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.Survey.SurveyAdapter;
import careerchangeover.com.valuesvisualizer.Survey.SurveyDataProgressBar;

public class SurveyActivity extends AppCompatActivity {
    SurveyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container_survey);

        if (fragment == null) {
            fragment = new SurveyFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container_survey, fragment);
            transaction.commit();
        }

       Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_values);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}