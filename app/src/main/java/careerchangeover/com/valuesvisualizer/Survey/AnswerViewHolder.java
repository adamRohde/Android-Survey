package careerchangeover.com.valuesvisualizer.Survey;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import careerchangeover.com.valuesvisualizer.R;

public class AnswerViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public AnswerViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView =
                (CheckedTextView) itemView.findViewById(R.id.list_answer_check);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setArtistName(String artistName) {
        childCheckedTextView.setText(artistName);
    }
}

