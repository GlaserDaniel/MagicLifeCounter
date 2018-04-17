package de.danielglaser.magiclivecounter.counter.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import de.danielglaser.magiclivecounter.counter.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThreePlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThreePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreePlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CounterActivity activity;

    //private OnFragmentInteractionListener mListener;

    public ThreePlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreePlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreePlayerFragment newInstance(String param1, String param2) {
        ThreePlayerFragment fragment = new ThreePlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (CounterActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three_player, container, false);

        final Button menuButton = v.findViewById(R.id.menuButton);
        final ConstraintLayout manuLayout = v.findViewById(R.id.menuLayout);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = manuLayout.getVisibility();
                switch (visibility) {
                    case View.GONE:
                    case View.INVISIBLE:
                        manuLayout.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        manuLayout.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });

        final Spinner playerSelectorSpinner = v.findViewById(R.id.playerSelectorSpinner);

        playerSelectorSpinner.setSelection(1);

        playerSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = playerSelectorSpinner.getItemAtPosition(position);
                if (object instanceof String) {
                    String string = (String) object;

                    switch (string) {
                        case "2":
                            activity.loadFragment(new TwoPlayerFragment());
                            break;
                        case "4":
                            activity.loadFragment(new FourPlayerFragment());
                            break;
                        case "5":
                            activity.loadFragment(new FivePlayerFragment());
                            break;
                        case "6":
                            activity.loadFragment(new SixPlayerFragment());
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Object o = new Object();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
