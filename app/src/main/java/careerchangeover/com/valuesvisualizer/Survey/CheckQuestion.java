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

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(iconResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckQuestion> CREATOR = new Creator<CheckQuestion>() {
        @Override
        public CheckQuestion createFromParcel(Parcel in) {
            return new CheckQuestion(in);
        }

        @Override
        public CheckQuestion[] newArray(int size) {
            return new CheckQuestion[size];
        }
    };
}
