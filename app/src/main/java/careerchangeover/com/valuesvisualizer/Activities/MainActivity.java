package careerchangeover.com.valuesvisualizer.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import careerchangeover.com.valuesvisualizer.MyDbHandler;
import careerchangeover.com.valuesvisualizer.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDbHandler dbHandler;
    SharedPreferences mPref;
    String seeTutorialPref = "openTutorial";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean goToTutorial = mPref.getBoolean(seeTutorialPref,true);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (goToTutorial) {
            Intent openTutorial = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(openTutorial);
        }

        findViewById(R.id.takeSelfSurveyButton).setOnClickListener(this);
        findViewById(R.id.takeEmployerSurveyButton).setOnClickListener(this);
        findViewById(R.id.viewResultsButton).setOnClickListener(this);
        findViewById(R.id.crashButton).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewTutorial:
                Intent goToTutorial = new Intent(MainActivity.this,TutorialActivity.class);
                goToTutorial.putExtra("allow",true);
                startActivity(goToTutorial);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeSelfSurveyButton:
                Intent toSelfEval = new Intent(MainActivity.this, SurveyActivity.class);
                toSelfEval.putExtra("column_name", dbHandler.COLUMN_SELF_EVAL);
                startActivity(toSelfEval);
                break;
            case R.id.takeEmployerSurveyButton:
                Intent toEmployerEval = new Intent(MainActivity.this,SurveyActivity.class);
                toEmployerEval.putExtra("column_name", dbHandler.COLUMN_EMPLOYER_EVAL);
                startActivity(toEmployerEval);
                break;
            case R.id.viewResultsButton:
                Intent toResults = new Intent(MainActivity.this, ResultsActivity.class);
                startActivity(toResults);
                break;
            case R.id.crashButton:
                throw new RuntimeException("Test Crash");
        }
    }
}
