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

import java.util.ArrayList;
import java.util.List;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.activity.adapter.LanguageAdapter;
import com.sibext.owlet.activity.adapter.LanguageAdapter.LanguageItem;
import com.sibext.owlet.helper.Log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SettingsActivity extends BaseOwletActivity implements OnSharedPreferenceChangeListener {
	private static final String TAG = "SettingsActivity";

	private TableLayout tableView;
	private Button backButton;
	private SharedPreferences sharedPreferences;
	private int id;
	private View soundLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLanguageFromPref();
		super.onCreate(savedInstanceState);
		id = getIntent().getIntExtra(MainActivity.ID, 0);
		setContentView(R.layout.settings);
		backButton = (Button) findViewById(R.id.settings_back_button);
		tableView = (TableLayout) findViewById(R.id.settings_content);

		sharedPreferences = getSharedPreferences(OwletApplication.SETTINGS_NAME, MODE_PRIVATE);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		soundLine = getSoundLineView();
		tableView.addView(getSoundOffView());
		tableView.addView(soundLine);
		tableView.addView(getLanguageView());
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), ScreenOfTaskActivity.class);
		intent.putExtra(MainActivity.ID, id);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(OwletApplication.LANGUAGE_NAME)) {
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			intent.putExtra(MainActivity.ID, id);
			startActivity(intent);
			finish();
		} else {
			return;
		}
	}

	@Override
	protected int getMenuId() {
		return R.menu.settings;
	}

	@Override
	protected void onMainMenuSelected(int id) {
		switch (id) {
		case R.id.menu_exit:
			onExit();
			break;
		case R.id.menu_logout:
			onExit();
			onAuthorizationActivity();
			break;
		case R.id.menu_about:
			onAboutActivity();
			break;
		case R.id.menu_back:
			onBackPressed();
			break;
		case R.id.menu_statistic:
			onStatisticsActivity();
			break;
		}
	}
	
	private View getSoundOffView() {
		final String valueKey = getString(R.string.volume_key); 
		TableRow sound = (TableRow)View.inflate(this, R.layout.settings_sound_off_item, null);
		CheckBox checkBox = (CheckBox)sound.findViewById(R.id.settings_sound_off_item_checkbox);
		checkBox.setChecked(sharedPreferences.getBoolean(valueKey, true));
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sharedPreferences.edit().putBoolean(valueKey, isChecked).commit();
				soundLine.findViewById(R.id.settings_sound_line).setEnabled(isChecked);
			}
		});
		return sound;
	}

	private View getSoundLineView() {
		TableRow languageLine = (TableRow)View.inflate(this, R.layout.settings_sound_line_item, null);
		SeekBar level = (SeekBar)languageLine.findViewById(R.id.settings_sound_line);
		//
		boolean soundLineEnableFlag = sharedPreferences.getBoolean(getString(R.string.volume_key), true);
		//
		level.setEnabled(soundLineEnableFlag);
		float currentLevel = level.getMax()*sharedPreferences.getFloat(getString(R.string.volume_level_key), 1);
		level.setProgress((int)currentLevel);
		level.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float level = (float)progress/seekBar.getMax();
				sharedPreferences.edit().putFloat(getString(R.string.volume_level_key), level).commit();
			}
		});
		return languageLine;
	}

	private View getLanguageView() {
		final String valueKey = getString(R.string.language_key);
		TableRow language = (TableRow)View.inflate(this, R.layout.settings_language_item, null);
		Spinner spinner = (Spinner)language.findViewById(R.id.settings_language_item_spinner);
		final String[] langs = getResources().getStringArray(R.array.values_lang);
		final String[] entries = getResources().getStringArray(R.array.entries_lang);
		List<LanguageItem> items = new ArrayList<LanguageItem>();
		int index = 0;
		for(String lang : langs) {
			items.add(new LanguageItem(lang, entries[index]));
			index += 1;
		}
		spinner.setAdapter(new LanguageAdapter(this, items));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.d(TAG, "Selected " + entries[position]);
				sharedPreferences.edit().putString(valueKey, langs[position]).commit();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
			
		});
		String currentCode = sharedPreferences.getString(getString(R.string.language_key), getString(R.string.pref_lang_default));
		int position = 0;
		for (int pos = 0; pos < langs.length; ++pos) {
			if (langs[pos].equals(currentCode)) {
				position = pos;
				break;
			}
		}
		spinner.setSelection(position);
		return language;
	}

}