package careerchangeover.com.valuesvisualizer.Survey;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class SurveyDataProgressBar extends ProgressBar implements ExpandingAndCollapsingQuestions.IntValueStoreListener {

    public SurveyDataProgressBar(Context context) {
        super(context);
    }

    public SurveyDataProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onValueChanged(int newValue) {
        setProgress(newValue);
    }
}


