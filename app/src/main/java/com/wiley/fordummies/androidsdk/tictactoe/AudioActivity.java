package com.wiley.fordummies.androidsdk.tictactoe;

import androidx.fragment.app.Fragment;

/**
 * Activity for handling audio.
 *
 * Created by adamcchampion on 2017/08/12.
 */

public class AudioActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AudioFragment();
    }
}
