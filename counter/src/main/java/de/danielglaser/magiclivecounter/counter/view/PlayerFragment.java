package de.danielglaser.magiclivecounter.counter.view;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import de.danielglaser.magiclivecounter.counter.R;
import de.danielglaser.magiclivecounter.counter.model.Player;
import de.danielglaser.magiclivecounter.counter.model.Settings;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Player player;
    private TextView playerLiveText;
    private TextView poisonTextView;
    private TextView energyTextView;
    private TextView commanderDamageTextView;

    private CounterActivity activity;
    private ConstraintLayout playerLayout;

    //private OnFragmentInteractionListener mListener;

    public PlayerFragment() {
        // Required empty public constructor
        player = new Player();
        Settings.getInstance().addPlayer(player);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance(String param1, String param2) {
        PlayerFragment fragment = new PlayerFragment();
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
        View v = inflater.inflate(R.layout.fragment_player, container, false);

        playerLayout = v.findViewById(R.id.playerLayout);

        playerLiveText = v.findViewById(R.id.playerLiveText);
        updateLive();

        Button playerPlusButton = v.findViewById(R.id.playerPlusButton);
        final Button playerMinusButton = v.findViewById(R.id.playerMinusButton);

        playerPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.addLive(1);
                updateLive();
            }
        });

        playerPlusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.addLive(10);
                updateLive();
                return true;
            }
        });

        playerMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.subLive(1);
                updateLive();
            }
        });

        playerMinusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.subLive(10);
                updateLive();
                return true;
            }
        });

        final TextView nameTextView = v.findViewById(R.id.nameTextView);

        final View view = inflater.inflate(R.layout.dialog_edit_name, null, false);

        final EditText nameEditText = view.findViewById(R.id.nameEditText);

        nameTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Name");
                builder.setView(view)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = nameEditText.getText().toString();
                                player.setName(name);
                                if (name.isEmpty()) {
                                    Toast.makeText(getContext(), R.string.NameNotNull, Toast.LENGTH_SHORT).show();
                                } else {
                                    name = name.trim();
                                    nameTextView.setText(name);
                                }

                                // fullscreen again
                                activity.delayedHide(getResources().getInteger(R.integer.hide_delay_millis));

                                // to not get a IllegalStateException
                                ((ViewGroup) view.getParent()).removeAllViews();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // cancel the dialog

                                // fullscreen again
                                activity.delayedHide(getResources().getInteger(R.integer.hide_delay_millis));

                                // to not get a IllegalStateException
                                ((ViewGroup) view.getParent()).removeAllViews();
                            }
                        })
                        .setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                // cancel the dialog

                                // fullscreen again
                                activity.delayedHide(getResources().getInteger(R.integer.hide_delay_millis));

                                // to not get a IllegalStateException
                                ((ViewGroup) view.getParent()).removeAllViews();
                            }
                        })
                        .create()
                        .show();
                return true;
            }
        });

        poisonTextView = v.findViewById(R.id.poisonTextView);

        poisonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.addPoison(1);
                updatePoison();
            }
        });

        poisonTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.subPoison(1);
                updatePoison();
                return true;
            }
        });

        energyTextView = v.findViewById(R.id.energyTextView);

        energyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.addEnergy(1);
                updateEnergy();
            }
        });

        energyTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.subEnergy(1);
                updateEnergy();
                return true;
            }
        });

        commanderDamageTextView = v.findViewById(R.id.commanderDamageTextView);

        commanderDamageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.addCommanderDamage(1);
                updateCommanderDamage();
            }
        });

        commanderDamageTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                player.subCommanderDamage(1);
                updateCommanderDamage();
                return true;
            }
        });

        return v;
    }

    private void updateLive() {
        playerLiveText.setText("" + player.getLive());
    }

    private void updatePoison() {
        poisonTextView.setText("" + player.getPoison());
    }

    private void updateEnergy() {
        energyTextView.setText("" + player.getEnergy());
    }

    private void updateCommanderDamage() {
        commanderDamageTextView.setText("" + player.getCommanderDamage());
    }

    public void resetPoints() {
        player.resetPoints();
        updateLive();
        updatePoison();
        updateEnergy();
        updateCommanderDamage();
    }

    public void setBackgroundColor(int color) {
        playerLayout.setBackgroundColor(color);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
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
