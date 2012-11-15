package ru.devpad.chapter2.linearlayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class DevPadActivity extends Activity {
    private int position = 0;
    private int[] stek = new int[16];

    public static final String POSITION = "pos";
    public static final String STEK = "stek";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            stek = savedInstanceState.getIntArray(STEK);
            position = savedInstanceState.getInt(POSITION);
        } else {
            stek[position] = R.layout.main;
        }
        setLayout(stek[position]);
    }

    public void onClick(View view) {
        int layout = getResources().getIdentifier(getResources().getResourceEntryName(view.getId()), "layout", getPackageName());
        if (layout > 0) {
            nextLayout(layout);
            Button b = (Button) view;
            setTitle(b.getText().toString());
        }
    }

    public boolean onSearchRequested() {
        return false;
    }

    private void onExit() {
        finish();
        moveTaskToBack(true);
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            prevLayout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setLayout(int layout) {
        stek[position] = layout;
        setContentView(layout);
    }

    private void nextLayout(int layout) {
        position++;
        setLayout(layout);
    }

    private void prevLayout() {
        position--;
        if (position < 0) onExit();
        else {
            int layout = stek[position];
            try {
                int id = getResources().getIdentifier(getResources().getResourceEntryName(stek[position]), "id", getPackageName());
                if (id != 0) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup vg = (ViewGroup) inflater.inflate(stek[position - 1], null);
                    Button b = (Button) vg.findViewById(id);
                    if (b != null) {
                        setTitle(b.getText().toString());
                    } else setTitle(R.string.app_name);
                } else setTitle(R.string.app_name);
            } catch (ArrayIndexOutOfBoundsException ae) {
                setTitle(R.string.app_name);
            }
            setLayout(layout);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSITION, position);
        outState.putIntArray(STEK, stek);
        super.onSaveInstanceState(outState);
    }
}
