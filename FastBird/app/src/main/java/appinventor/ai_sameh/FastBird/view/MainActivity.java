package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;

import appinventor.ai_sameh.FastBird.R;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle b = new Bundle();
        b.putString("key", "Settings");
        mTabHost.addTab(mTabHost.newTabSpec("Settings").setIndicator("Settings"),
                SettingsFragment.class, b);
        //
        b = new Bundle();
        b.putString("key", "Contacts");
        mTabHost.addTab(mTabHost.newTabSpec("contacts")
                .setIndicator("Contacts"), Fragment2.class, b);
        b = new Bundle();
        b.putString("key", "Custom");
        mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
                Fragment3.class, b);
        // setContentView(mTabHost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}