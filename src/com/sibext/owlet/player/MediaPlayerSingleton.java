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

package com.sibext.owlet.player;

import java.util.Locale;
import java.util.Random;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.activity.MainActivity;
import com.sibext.owlet.helper.Log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.MediaPlayer;

public final class MediaPlayerSingleton {
	private static final String TAG = "MediaPlayerSingleton";
	private static MediaPlayerSingleton instance = null;
	private MediaPlayer mp;
	private Context context;
	private Random random;
	private SharedPreferences pref;

	private static int[] resoursesForCorrectAnswer = new int[]{
		R.raw.dialog_title_correct_answer,
		R.raw.dialog_title_correct_answer2,
		R.raw.dialog_title_correct_answer3,
		R.raw.dialog_title_correct_answer4,
		R.raw.dialog_title_correct_answer5,
		R.raw.dialog_title_good_work,
		R.raw.its_correct_answer,
		R.raw.ugu_1,
	};
	
	private static int[] resoursesForCorrectAnswerEn = new int[]{
		R.raw.dialog_title_correct_answer,
		R.raw.dialog_title_correct_answer2,
		R.raw.dialog_title_correct_answer3,
		R.raw.dialog_title_correct_answer4,
		R.raw.dialog_title_correct_answer5,
		R.raw.dialog_title_correct_answer6,
		R.raw.dialog_title_correct_answer7,
		R.raw.dialog_title_correct_answer8,
		R.raw.dialog_title_correct_answer9,
		R.raw.dialog_title_correct_answer10,
		R.raw.dialog_title_correct_answer11,
		R.raw.its_correct_answer,
		R.raw.ugu_1,
	};
	
	private static int[] resoursesForIncorrectAnswer = new int[]{
		R.raw.its_incorrect_answer,
		R.raw.its_incorrect_answer2,
		R.raw.its_incorrect_answer3,
		R.raw.ugu_2,
	};
	
	private static int[] resoursesForIncorrectAnswerEn = new int[]{
		R.raw.its_incorrect_answer,
		R.raw.its_incorrect_answer2,
		R.raw.its_incorrect_answer3,
		R.raw.its_incorrect_answer4,
		R.raw.ugu_2,
	};

	private MediaPlayerSingleton(Context context) {
		this.context = context;
		random = new Random();
		OwletApplication app = (OwletApplication) context.getApplicationContext();
		pref = app.getSettings();

	}

	public static MediaPlayerSingleton getInstance(Context context) {
		if (instance == null){
			synchronized (MediaPlayerSingleton.class) {
				if (instance == null){
					instance = new MediaPlayerSingleton(context);
				}				
			}
		}
		return instance;
	}

	public synchronized void play(int res) {
		boolean volumeOn = pref.getBoolean(OwletApplication.VOLUME_NAME, true);
		if (volumeOn) {
			if (mp != null) {
				if (mp.isPlaying()) {
					mp.stop();
				}
				mp.release();
			}			
			mp = MediaPlayer.create(context, res);
			float volLevel = pref.getFloat(OwletApplication.VOLUME_LEVEL_NAME, 1);
			Log.d(TAG,"at play: current volLevel is "+volLevel);
			mp.setVolume(volLevel, volLevel);
			mp.start();
		} else {
			return;
		}
	}
	
	public synchronized void playSomeCorrectAnswerSound(){
		if(getCurrentLang().equals(MainActivity.RU)){
			int choice = random.nextInt(resoursesForCorrectAnswer.length);
			play(resoursesForCorrectAnswer[choice]);
		} else {
			int choice = random.nextInt(resoursesForCorrectAnswerEn.length);
			play(resoursesForCorrectAnswerEn[choice]);
		}
	}

	public synchronized void playSomeIncorrectAnswerSound(){
		if(getCurrentLang().equals(MainActivity.RU)){
			int choice = random.nextInt(resoursesForIncorrectAnswer.length);
			play(resoursesForIncorrectAnswer[choice]);
		} else {
			int choice = random.nextInt(resoursesForIncorrectAnswerEn.length);
			play(resoursesForIncorrectAnswerEn[choice]);
		}
	}

	public synchronized void playSomeFunSound(){
		final Resources res = context.getResources();
		final TypedArray sounds = res.obtainTypedArray(R.array.resources_for_fun);
		int[] array = res.getIntArray(R.array.resources_for_fun);
		int choice = random.nextInt(array.length);
		play(sounds.getResourceId(choice, 0));
	}
	
	private String getCurrentLang(){
		Configuration configuration = context.getResources().getConfiguration();
		Locale locale = configuration.locale;
		String lang = locale.getLanguage();
		if(lang.equalsIgnoreCase(MainActivity.RU)){
			return MainActivity.RU;
		}
		return MainActivity.EN;
	}
}
