package careerchangeover.com.valuesvisualizer.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import careerchangeover.com.valuesvisualizer.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the about fragment layout
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
