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

package com.sibext.owlet;


import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sibext.owlet.R;
import com.sibext.owlet.tasks.Task;

public class OwletApplication extends Application {
	public static final String SETTINGS_NAME = "Settings";
	private static final int CORRECT_ANSWER_LIM_VALUE = 5;
	private static final int INCORRECT_ANSWER_LIM_VALUE = 5;
	private static final float DEFAULT_VOLUME_LEVEL = 1;
	public static final String CORRECT_ANSWER_LIM_NAME = "correct";
	public static final String INCORRECT_ANSWER_LIM_NAME = "incorrect";
	public static String VOLUME_NAME;
	public static String LANGUAGE_NAME;
	public static String VOLUME_LEVEL_NAME;
	
	public static String COMPLEX_POSTFIX = "_complex";
	public static String SYSTEM_TASK_COMPLEXITY = Task.SYSTEMATISATION_TASK_TYPE+COMPLEX_POSTFIX;
	public static String COMPARE_TASK_COMPLEXITY = Task.COMPARE_TASK_TYPE+COMPLEX_POSTFIX;
	public static String MAGIC_TASK_COMPLEXITY = Task.MAGICSUARE_TASK_TYPE+COMPLEX_POSTFIX;
	public static String CONCLUSION_TASK_COMPLEXITY = Task.CONCLUSION_TASK_TYPE+COMPLEX_POSTFIX;
	public static int VERSION;

	@Override
	public void onCreate() {
		VERSION = android.os.Build.VERSION.SDK_INT;
		VOLUME_NAME = getResources().getString(R.string.volume_key);
		LANGUAGE_NAME = getResources().getString(R.string.language_key);
		VOLUME_LEVEL_NAME = getResources().getString(R.string.volume_level_key);
		saveIntInSettings(CORRECT_ANSWER_LIM_NAME, CORRECT_ANSWER_LIM_VALUE);
		saveIntInSettings(INCORRECT_ANSWER_LIM_NAME, INCORRECT_ANSWER_LIM_VALUE);
		saveFloatInSettings(VOLUME_LEVEL_NAME, DEFAULT_VOLUME_LEVEL);
		saveBoolInSettings(VOLUME_NAME, true);
		//
		saveIntInSettings(SYSTEM_TASK_COMPLEXITY, Task.COMPLEXITY_LOW);
		saveIntInSettings(COMPARE_TASK_COMPLEXITY, Task.COMPLEXITY_LOW);
		saveIntInSettings(MAGIC_TASK_COMPLEXITY, Task.COMPLEXITY_LOW);
		saveIntInSettings(CONCLUSION_TASK_COMPLEXITY, Task.COMPLEXITY_LOW);
		super.onCreate();
	}

	public SharedPreferences getSettings() {
		return getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
	}

	public void saveIntInSettings(String key, int value) {
		SharedPreferences pref = getSettings();
		if (pref.contains(key)) {
			return;
		} else {
			Editor editor = pref.edit();
			editor.putInt(key, value);
			editor.commit();
		}
	}

	public void saveStringInSettings(String key, String value) {
		SharedPreferences pref = getSettings();
		if (pref.contains(key)) {
			return;
		} else {
			Editor editor = pref.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}
	public void saveBoolInSettings(String key, boolean value) {
		SharedPreferences pref = getSettings();
		if (pref.contains(key)) {
			return;
		} else {
			Editor editor = pref.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
	}
	
	public void saveFloatInSettings(String key, float value) {
		SharedPreferences pref = getSettings();
		if (pref.contains(key)) {
			return;
		} else {
			Editor editor = pref.edit();
			editor.putFloat(key, value);
			editor.commit();
		}
	}

	public void editBoolInSettings(String key, boolean value) {
		SharedPreferences pref = getSettings();
		Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

}
