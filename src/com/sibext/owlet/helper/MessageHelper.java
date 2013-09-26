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

package com.sibext.owlet.helper;

import java.util.Timer;
import java.util.TimerTask;

import com.sibext.owlet.R;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class MessageHelper {
	
	private static Toast currentToast = null;
	
	private static final String CORRECT_ANSWER_SOUND_NAME = "correct_answer_sound";
	private static final String INCORRECT_ANSWER_SOUND_NAME = "incorrect_answer_sound";	
	private static final String DEF_TYPE_SOUND = "raw";
	private static final String CORRECT_ANSWER_IMAGE_NAME = "dialog_yes_symbol";
	private static final String INCORRECT_ANSWER_IMAGE_NAME = "dialog_no_symbol";
	private static final String DEF_TYPE_IMAGE = "drawable";
	
	public static void showToast(Context context, int msg) {
		if (null == currentToast) {
			currentToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			currentToast.cancel();
		}
		currentToast.setText(msg);		
		currentToast.show();
	}
	
	public static void showAlertDialog(Context context, final boolean result) {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.result_answer_dialog);
		dialog.setCancelable(false);
		TextView textView = (TextView) dialog.findViewById(R.id.result_answer_dialog_title);
		ImageView imageView = (ImageView) dialog.findViewById(R.id.result_answer_dialog_image_view);
		
		int soundResId = 0;
		int imageResId = 0;
		if (result) {
			soundResId = context.getResources().getIdentifier(CORRECT_ANSWER_SOUND_NAME, DEF_TYPE_SOUND, context.getPackageName());
			imageResId = context.getResources().getIdentifier(CORRECT_ANSWER_IMAGE_NAME, DEF_TYPE_IMAGE, context.getPackageName());
			textView.setText(context.getString(R.string.dialog_title_correct_answer));
		}
		else {
			soundResId = context.getResources().getIdentifier(INCORRECT_ANSWER_SOUND_NAME, DEF_TYPE_SOUND, context.getPackageName());
			imageResId = context.getResources().getIdentifier(INCORRECT_ANSWER_IMAGE_NAME, DEF_TYPE_IMAGE, context.getPackageName());
			textView.setText(context.getString(R.string.dialog_title_incorrect_answer));
		}
		imageView.setImageResource(imageResId);
		final MediaPlayer mediaPlayer = MediaPlayer.create(context,soundResId);
		dialog.show();
		mediaPlayer.start();
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dialog.cancel();
				mediaPlayer.stop();
				t.cancel();
			}
		}, 2000);
	}
}
