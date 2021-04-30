package careerchangeover.com.valuesvisualizer.Survey;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.amulyakhare.textdrawable.TextDrawable;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.SurveyData;

import static java.lang.StrictMath.floorMod;

public class GenreViewHolder extends GroupViewHolder {
    private final TextView genreName;
    private final ImageView icon;

    public GenreViewHolder(View itemView) {
        super(itemView);
        genreName = (TextView) itemView.findViewById(R.id.list_item_genre_name);
        icon = (ImageView) itemView.findViewById(R.id.list_item_question_icon);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)

    public void setGenreTitle(ExpandableGroup question, int openGroup, List<SurveyData> surveyData) {
        int iconNum = 0;

        iconNum = getCurrentlyRenderingQuestionID(question, surveyData);

        System.out.println("Hello from iconNum 1 - " + iconNum);

//        for (int i = 0; i < surveyData.size(); i++){
//            System.out.println("Hello from is expanded ="+ i + " " + surveyData.get(i).getIsCurrentlyExpanded());
//            System.out.println("Hello from allData - " + i);
//        }

        TextDrawable drawable_dark = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.DEFAULT_BOLD)
                .withBorder(0)
                .endConfig()
                .buildRound(String.valueOf(iconNum + 1), Color.WHITE); // radius in px

        TextDrawable drawable_light = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.LTGRAY)
                .useFont(Typeface.DEFAULT_BOLD)
                .withBorder(0)
                .endConfig()
                .buildRound(String.valueOf(iconNum + 1), Color.WHITE); // radius in px

        // && !surveyData.get(iconNum + 1).getIsCurrentlyExpanded()

        if (surveyData.get(iconNum).getHasBeenAnswered() ){
            genreName.setText(question.getTitle());
            icon.setBackground(null);
            icon.setImageResource(R.mipmap.ic_checked_round);
            genreName.setTextColor(Color.BLACK);
            System.out.println("Hello from iconNum 2 - " + iconNum);
        }else{
            genreName.setText(question.getTitle());
            genreName.setTextColor(Color.LTGRAY);
            if ( 0 == getCurrentlyRenderingQuestionID(question, surveyData) || openGroup == iconNum ){
                icon.setImageDrawable(drawable_dark);
                genreName.setTextColor(Color.BLACK);
                icon.setBackgroundResource(R.drawable.ic_border_dark);
            }else{
                icon.setImageDrawable(drawable_light);
                genreName.setTextColor(Color.LTGRAY);
                icon.setBackgroundResource(R.drawable.ic_border_light);
            }
        }
    }

    public int getCurrentlyRenderingQuestionID(ExpandableGroup question, List<SurveyData> surveyData ){
        int getRendering = 0;
        for (int i = 0; i < surveyData.size(); i++){
            if (surveyData.get(i).getIsCurrentlyExpanded()){
                System.out.println("Hello from is expanded ="+ i + " " + surveyData.get(i).getIsCurrentlyExpanded());
            }

            if (question.getTitle() == surveyData.get(i).getQuestion()){
                getRendering = i;
            }
        }
        return getRendering;
    }
}
