package careerchangeover.com.valuesvisualizer.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import careerchangeover.com.valuesvisualizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartTwoPrepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartTwoPrepFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;
    Button gotitButton;

    public PartTwoPrepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartTwoPrepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartTwoPrepFragment newInstance(String param1, String param2) {
        PartTwoPrepFragment fragment = new PartTwoPrepFragment();
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

        rootView =  inflater.inflate(R.layout.fragment_part_two_prep, container, false);
        gotitButton = (Button)rootView.findViewById(R.id.gotitButton);
        gotitButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
       System.out.println("Hello");

        SurveyFragment nextFrag= new SurveyFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_survey, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}