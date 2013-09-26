/**
 * This file is part of Owlet.
 * 
 * Owlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * Owlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with Owlet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sibext.owlet.activity;

import com.sibext.owlet.R;
import com.sibext.owlet.helper.SystemHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseOwletActivity {
	private TextView title;
	private TextView contentView;
	private TextView bottomView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		title = (TextView)findViewById(R.id.about_title);
		contentView = (TextView)findViewById(R.id.about_content);
		bottomView = (TextView)findViewById(R.id.about_bottom);
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		ImageView off = (ImageView)findViewById(R.id.about_close);
		off.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		title.setText(title.getText() + " (" +SystemHelper.getVersion(this) + ")");
		Spanned sp = Html.fromHtml( getString(R.string.about_text));
		contentView.setText(sp);
		sp = Html.fromHtml( getString(R.string.about_copyright));
		bottomView.setText(sp);
		bottomView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(
						Intent.ACTION_VIEW, 
						Uri.parse("http://www.sibext.com"));
				startActivity(browserIntent);
			}
		});
	}

	@Override
	protected void onMainMenuSelected(int id) {
		switch (id) {
		case R.id.menu_exit: {
			onExit();
			break;
		}
		case R.id.menu_logout: {
			onExit();
			onAuthorizationActivity();
			break;
		}
		case R.id.menu_settings: {
			onSettingsActivity();
			break;
		}
		case R.id.menu_back: {
			onBackPressed();
			break;
		}
		case R.id.menu_statistic: {
			onStatisticsActivity();
			break;
		}
		}
	}

	@Override
	protected int getMenuId() {
		return R.menu.about;
	}
	
}
