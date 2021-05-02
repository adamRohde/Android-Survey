package careerchangeover.com.valuesvisualizer.Survey;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

public class CheckQuestion extends SingleCheckExpandableGroup {

    private int iconResId;

    public CheckQuestion(String title, List items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    protected CheckQuestion(Parcel in) {
        super(in);
        iconResId = in.readInt();
    }
}
