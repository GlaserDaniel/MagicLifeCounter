package de.danielglaser.magiclivecounter.counter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import de.danielglaser.magiclivecounter.counter.R;
import de.danielglaser.magiclivecounter.counter.helperClasses.Utilities;
import de.danielglaser.magiclivecounter.counter.model.Settings;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CounterActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private static Context appContext;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    //private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            //mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    public static Context getAppContext() {
        return appContext;
    }

    private int actualPlayersView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = this;

        setContentView(R.layout.activity_counter);

        mVisible = true;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.mainLayout);

        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(getResources().getInteger(R.integer.hide_delay_millis));
    }

    @Override
    protected void onResume() {
        super.onResume();

        delayedHide(getResources().getInteger(R.integer.hide_delay_millis));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Settings.getInstance().save();
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainLayout, fragment)
                .commit();
        delayedHide(getResources().getInteger(R.integer.hide_delay_millis));

        if (fragment instanceof TwoPlayerFragment) {
            // for 2 player portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            // for 3 player and more landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void loadFittingFragment(int amountOfPlayers) {
        // wenn bereits in der gewählten Ansicht
        if (amountOfPlayers == actualPlayersView) {
            // mache nichts
            return;
        }

        // Andernfalls gehe in die gewählte Ansicht
        actualPlayersView = amountOfPlayers;
        Settings.getInstance().setAmountOfPlayers(amountOfPlayers);
        switch (amountOfPlayers) {
            case 2:
                loadFragment(new TwoPlayerFragment());
                break;
            case 3:
                loadFragment(new ThreePlayerFragment());
                break;
            case 4:
                loadFragment(new FourPlayerFragment());
                break;
            case 5:
                loadFragment(new FivePlayerFragment());
                break;
            case 6:
                loadFragment(new SixPlayerFragment());
                break;
            default:
                Settings.getInstance().setAmountOfPlayers(2);
        }
    }

    public void initMenuUI(final Fragment fragment, View view) {
        final Button menuButton = view.findViewById(R.id.menuButton);
        final ConstraintLayout menuLayout = view.findViewById(R.id.menuLayout);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = menuLayout.getVisibility();
                switch (visibility) {
                    case View.GONE:
                    case View.INVISIBLE:
                        menuLayout.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        menuLayout.setVisibility(View.INVISIBLE);
                        delayedHide(getResources().getInteger(R.integer.hide_delay_millis));
                        break;
                }
            }
        });

        final Spinner startLiveSpinner = view.findViewById(R.id.startLiveSpinner);

        startLiveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object startLiveObject = startLiveSpinner.getItemAtPosition(position);
                if (startLiveObject instanceof String) {
                    String startLiveString = (String) startLiveObject;
                    int startLiveInteger = Integer.parseInt(startLiveString);
                    Log.d("Test", "Live: " + startLiveInteger);
                    Settings.getInstance().setStartLive(startLiveInteger);
                    delayedHide(getResources().getInteger(R.integer.hide_delay_millis));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] startLiveArray = getResources().getStringArray(R.array.amountOfStartLive);

        for (int i = 0; i < startLiveArray.length; i++) {
            if (Integer.parseInt(startLiveArray[i]) == Settings.getInstance().getStartLive()) {
                startLiveSpinner.setSelection(i);
                break;
            }
        }

        Button restartButton = view.findViewById(R.id.restartButton);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // reset Points
                //Settings.getInstance().resetPoints();

                FragmentManager fragmentManager = fragment.getChildFragmentManager();

                List<Fragment> fragments = fragmentManager.getFragments();

                for (Fragment fragment : fragments) {
                    if (fragment instanceof PlayerFragment) {
                        ((PlayerFragment) fragment).resetPoints();
                    }
                }

                // with player may start the game?
                int amountOfPlayers = fragments.size();
                int playerWhoBegins = Utilities.getRandomNumberInRange(1, amountOfPlayers);

                Toast.makeText(CounterActivity.this, "Player " + playerWhoBegins + " begins", Toast.LENGTH_LONG).show();
            }
        });

        final Spinner playerSelectorSpinner = view.findViewById(R.id.playerSelectorSpinner);

        playerSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object object = playerSelectorSpinner.getItemAtPosition(position);
                if (object instanceof String) {
                    String string = (String) object;
                    int playerSelectInt = Integer.parseInt(string);
                    if (playerSelectInt != Settings.getInstance().getAmountOfPlayers()) {
                        loadFittingFragment(Integer.parseInt(string));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] amountOfPlayersArray = getResources().getStringArray(R.array.amountOfPlayers);

        for (int j = 0; j < amountOfPlayersArray.length; j++) {
            if (Integer.parseInt(amountOfPlayersArray[j]) == Settings.getInstance().getAmountOfPlayers()) {
                playerSelectorSpinner.setSelection(j);
                break;
            }
        }

        menuLayout.setVisibility(View.VISIBLE);
        menuLayout.setVisibility(View.INVISIBLE);

        loadFittingFragment(Settings.getInstance().getAmountOfPlayers());
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    public void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
