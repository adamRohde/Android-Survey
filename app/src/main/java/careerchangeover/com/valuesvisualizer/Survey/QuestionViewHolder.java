package careerchangeover.com.valuesvisualizer.Survey;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import careerchangeover.com.valuesvisualizer.R.style;

import java.util.List;

import careerchangeover.com.valuesvisualizer.R;
import careerchangeover.com.valuesvisualizer.SurveyData;

public class QuestionViewHolder extends GroupViewHolder {
    private final TextView tvQuestion;
    private final ImageView icon;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        tvQuestion = (TextView) itemView.findViewById(R.id.list_item_question);
        icon = (ImageView) itemView.findViewById(R.id.list_item_question_icon);
    }

    public void setGenreTitle(ExpandableGroup question, int openGroup, List<SurveyData> surveyData) {
        int iconNum = 0;

        iconNum = getCurrentlyRenderingQuestionID(question, surveyData);

        TextDrawable drawable_dark = TextDrawable.builder()
                .beginConfig()
                .fontSize(130)
                .textColor(Color.rgb(12,146,154))
                .useFont(Typeface.DEFAULT_BOLD)
                .withBorder(0)
                .endConfig()
                .buildRound(String.valueOf(iconNum + 1), Color.WHITE); // radius in px

/*        TextDrawable drawable_light = TextDrawable.builder()
                .beginConfig()
                .textColor(R.color.appbar_color)
                .useFont(Typeface.DEFAULT_BOLD)
                .withBorder(0)
                .endConfig()
                .buildRound(String.valueOf(iconNum + 1), Color.WHITE);*/ // radius in px

        if (surveyData.get(iconNum).getHasBeenAnswered() ){
            tvQuestion.setText(question.getTitle());
            icon.setBackground(null);
            icon.setImageResource(R.mipmap.ic_checked_round);
            tvQuestion.setTextColor(Color.BLACK);
            System.out.println("Hello from iconNum 2 - " + iconNum);
        }else{
            tvQuestion.setText(question.getTitle());
            tvQuestion.setTextColor(Color.LTGRAY);
            if ( 0 == getCurrentlyRenderingQuestionID(question, surveyData) || openGroup == iconNum ){
                //Currently being answered
                icon.setBackground(null);
                icon.setImageDrawable(drawable_dark);
                tvQuestion.setTextColor(Color.BLACK);
               // icon.setBackgroundResource(R.drawable.ic_border_dark);
            }else{
                //Not yet answered condition (on deck)
                icon.setBackground(null);
                icon.setImageDrawable(drawable_dark);
                tvQuestion.setTextColor(Color.LTGRAY);
                //icon.setBackgroundResource(R.drawable.ic_border_light);
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
