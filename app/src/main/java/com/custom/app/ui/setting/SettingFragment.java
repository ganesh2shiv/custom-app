package com.custom.app.ui.setting;

import android.os.Bundle;
import android.view.View;

import com.core.app.util.Util;
import com.custom.app.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat implements OnPreferenceChangeListener {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_setting);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (getString(R.string.key_send_feedback).equals(preference.getKey())) {
            Util.handleUrl(getContext(), "mailto:feedback@custom.com");
            return true;
        } else {
            return super.onPreferenceTreeClick(preference);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setTitle(getString(R.string.title_settings));
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}