package com.xxoo.slidingmenu.demo.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;

import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.example.R;
import com.xxoo.slidingmenu.demo.Util;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import java.net.URLEncoder;

/**
 * This activity is an example of a responsive Android UI.
 * On phones, the SlidingMenu will be enabled only in portrait mode.
 * In landscape mode, it will present itself as a dual pane layout.
 * On tablets, it will will do the same general thing. In portrait
 * mode, it will enable the SlidingMenu, and in landscape mode, it
 * will be a dual pane layout.
 * 
 * @author jeremy
 *
 */
public class ResponsiveUIActivity extends SlidingFragmentActivity {

	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);

		setContentView(R.layout.responsive_content_frame);

		// 小屏幕 check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			// 大屏幕 add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
//		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new BirdGridFragment(0);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new BirdMenuFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
        //偏离屏幕右侧边的距离
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		// show the explanation dialog
//		if (savedInstanceState == null)
//			new AlertDialog.Builder(this)
//			.setTitle(R.string.what_is_this)
//			.setMessage(R.string.responsive_explanation)
//			.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                break;
            case R.id.github:
      			Util.goToGitHub(this);
      			return true;
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about)
                        .setMessage(Html.fromHtml(getString(R.string.about_msg)))
                        .show();
                break;
            case R.id.licenses:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.licenses)
                        .setMessage(Html.fromHtml(getString(R.string.apache_license)))
                        .show();
                break;
            case R.id.contact:
                final Intent email = new Intent(android.content.Intent.ACTION_SENDTO);
                String uriText = "mailto:jfeinstein10@gmail.com" +
                        "?subject=" + URLEncoder.encode("SlidingMenu Demos Feedback");
                email.setData(Uri.parse(uriText));
                try {
                    startActivity(email);
                } catch (Exception e) {
                    Toast.makeText(this, R.string.no_email, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}	

	public void onBirdPressed(int pos) {
		Intent intent = BirdActivity.newInstance(this, pos);
		startActivity(intent);
	}
    @Override
   	public boolean onCreateOptionsMenu(Menu menu) {


        menu.add("Save")
            .setIcon(R.drawable.ic_compose)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Search")
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add("Refresh")
            .setIcon(R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
   		getSupportMenuInflater().inflate(R.menu.example_list, menu);
   		return true;
   	}
}
