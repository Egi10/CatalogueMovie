package id.egifcb.cataloguemovie.ui.activity.setting;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import id.egifcb.cataloguemovie.R;
import id.egifcb.cataloguemovie.broadcast.AlarmReceiver;
import id.egifcb.cataloguemovie.service.scheduler.SchedulerService;

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        private FirebaseJobDispatcher firebaseJobDispatcher;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getActivity()));

            findPreference(getString(R.string.daily_reminder)).setOnPreferenceChangeListener(this);
            findPreference(getString(R.string.one_time)).setOnPreferenceChangeListener(this);
            findPreference(getString(R.string.key_change_langguage)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.langguage);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String key = preference.getKey();
            boolean aktif = (boolean) o;

            if (key.equals(getString(R.string.daily_reminder))) {
                if (aktif) {
                    Log.d("daily_reminder", "In Setting");
                    String time = "07:00";
                    String title = getResources().getString(R.string.title_notive_repeat);
                    String message = getResources().getString(R.string.message_notife_repeat);

                    alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING, time, title, message);
                } else {
                    Log.d("daily_reminder", "Not Setting");
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
                }
                return true;
            }

            if (key.equals(getString(R.string.one_time))) {
                if (aktif) {
                    startDispatcher();
                } else {
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_ONE_TIME);
                }
                return true;
            }

            return false;
        }

        public void startDispatcher() {
            String DISPATCHIEW_TAG = "mydispatcher";
            Job myJob = firebaseJobDispatcher.newJobBuilder()
                    .setService(SchedulerService.class)
                    .setTag(DISPATCHIEW_TAG)
                    .setRecurring(true)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setTrigger(Trigger.executionWindow(0, 60))
                    .setReplaceCurrent(true)
                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .build();
            firebaseJobDispatcher.mustSchedule(myJob);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home :
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
