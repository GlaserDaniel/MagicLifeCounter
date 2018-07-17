package de.danielglaser.magiclivecounter.counter.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import de.danielglaser.magiclivecounter.counter.R;
import de.danielglaser.magiclivecounter.counter.view.CounterActivity;

public class Settings {

    // Innere private Klasse, die erst beim Zugriff durch die umgebende Klasse initialisiert wird
    private static final class InstanceHolder {
        // Die Initialisierung von Klassenvariablen geschieht nur einmal
        // und wird vom ClassLoader implizit synchronisiert
        static final Settings INSTANCE = new Settings();
    }

    // Eine nicht synchronisierte Zugriffsmethode auf Klassenebene.
    public static Settings getInstance () {
        return InstanceHolder.INSTANCE;
    }

    private Context context;

    // Verhindere die Erzeugung des Objektes über andere Methoden
    private Settings () {
        // first set context
        context = CounterActivity.getAppContext();

        // than load the settings
        load();
    }

    private int startLive;
    private ArrayList<Player> players = new ArrayList<>();
    private int amountOfPlayers;

    public int getStartLive() {
        return startLive;
    }

    public void setStartLive(int startLive) {
        this.startLive = startLive;
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public void setAmountOfPlayers(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void resetPoints() {
        for (Player player: players) {
            player.resetPoints();
        }
    }

    private void load() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        setStartLive(sharedPref.getInt(context.getString(R.string.START_LIVE_KEY), context.getResources().getInteger(R.integer.default_start_live)));
        setAmountOfPlayers(sharedPref.getInt(context.getString(R.string.AMOUNT_OF_PLAYERS_KEY), context.getResources().getInteger(R.integer.default_amount_of_players)));
    }

    public void save() {
        //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.START_LIVE_KEY), startLive);
        editor.putInt(context.getString(R.string.AMOUNT_OF_PLAYERS_KEY), amountOfPlayers);
        editor.apply();
    }
}
