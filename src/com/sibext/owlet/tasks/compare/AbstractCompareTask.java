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

package com.sibext.owlet.tasks.compare;

import java.util.HashMap;


import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.activity.ScreenOfTaskActivity;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.tasks.Task;
import com.sibext.owlet.tasks.TaskParamsContainer;
import com.sibext.owlet.view.BaseTaskImageView;
public abstract class AbstractCompareTask extends Task {
	protected final int CORRECT_ANSWER_ID = 1;
	protected final int INCORRECT_ANSWER_ID = 0;
	protected final int CORREC_ANSWER_IMAGE_NAME = R.drawable.selected_correct;
	protected final int INCORREC_ANSWER_IMAGE_NAME = R.drawable.selected_incorrect;
	protected final int FAIL_COUNT_LIMIT = 2;
	
	protected final int CUBE_EDGES_COUNT = 3;
	protected final int CUBE_EXCESS_IMAGES_COUNT = 1;

	protected int imageCount;
	protected int[] marginLeft;
	protected int[] marginTop;
	protected HashMap<Integer, Integer> hashColors;

	public AbstractCompareTask(Context context,TaskParamsContainer container) {
		super(COMPARE_TASK_TYPE, COMPARE_TASK_LEGTH_DEFAULT,
				R.string.task_types_message_compare, context, container);
		imageCount = 5;
		settingOfParameters(
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_LEFT_INDEX] + 20,
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_TOP_INDEX] + heightBoard + 7);
		hashColors = new HashMap<Integer, Integer>();
		switch (container.getComplexity()) {
		case Task.COMPLEXITY_HIGH:
			colorFlag = false;
			break;
		case Task.COMPLEXITY_MEDIUM:
			colorFlag = rand.nextBoolean();
			break;
		default:
			colorFlag = true;
			break;
		}
	}

	private void settingOfParameters(int marginLeftAnswer,
			int marginTopAnswer) {
		int boundTopSize = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);		
		int boundLeftSize = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);		
		heightTaskImage = (int) ((heightBoard - (int)(boundTopSize*2.5)) / 3);
		widthTaskImage = heightTaskImage;
		marginTopTaskImage = boundTopSize;
		marginLeftTaskImage = boundLeftSize;
		//
		marginLeft = new int[imageCount];
		marginTop = new int[imageCount];
		int cLeft = (int) (widthBoard / 2) - (int) (widthTaskImage / 2);
		int cTop = (int) (heightBoard / 2) - (int) (heightTaskImage / 2);
		int delta = 20;
		marginLeft[0] = cLeft;
		marginTop[0] = cTop;
		marginLeft[1] = cLeft - widthTaskImage - delta;
		marginTop[1] = cTop - heightTaskImage;
		marginLeft[2] = cLeft + widthTaskImage + delta;
		marginTop[2] = cTop - heightTaskImage;
		marginLeft[3] = cLeft - widthTaskImage - delta;
		marginTop[3] = cTop + heightTaskImage;
		marginLeft[4] = cLeft + widthTaskImage + delta;
		marginTop[4] = cTop + heightTaskImage;
		colors = selectColors(length);
	}

	public int getEveryFailCountLimit() {
		return FAIL_COUNT_LIMIT;
	}

	public abstract ViewGroup taskBuildView(final Context context,
			final ViewGroup layout, final RelativeLayout root);
	
	@Override
	protected abstract Class<?>[] getCollectionOfView();
	
	@Override
	public void playTaskMessage(MediaPlayerSingleton sound) {
		sound.play(R.raw.task_compare_message);
	}
	
	protected OnTouchListener getCompareListener(final ViewGroup layout,final int id){
		return new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				Resources res = context.getResources();
				BaseTaskImageView img = (BaseTaskImageView) view;
				if (img.getEnabledFlag()) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						final ImageView frame = new ImageView(context);
						FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
								widthTaskImage, heightTaskImage);
						params.leftMargin = marginLeft[id];
						params.topMargin = marginTop[id];
						params.gravity = Gravity.TOP | Gravity.LEFT;
						frame.setLayoutParams(params);
						if (img.getCorrectAnswer() == CORRECT_ANSWER_ID) {
							textView.setText(R.string.dialog_text_correct_answer);
							frame.setImageDrawable(res.getDrawable(CORREC_ANSWER_IMAGE_NAME));
							AlphaAnimation anim = new AlphaAnimation(1, 0);
							anim.setDuration(1000);
							anim.setAnimationListener(new AnimationListener() {
								public void onAnimationStart(
										Animation animation) {
									for (int i = 0; i < answers.size(); i++) {
										answers.get(i).setEnabledFlag(false);
									}
								}

								public void onAnimationRepeat(
										Animation animation) {
								}

								public void onAnimationEnd(
										Animation animation) {
									layout.removeView(frame);
									observer.onTaskEnd(true);
									for (int i = 0; i < answers.size(); i++) {
										answers.get(i).setEnabledFlag(true);
									}
								}
							});
							
							if(OwletApplication.VERSION<=Build.VERSION_CODES.GINGERBREAD_MR1){
								layout.addView(frame);
								frame.startAnimation(anim);
							} else {
								observer.onTaskEnd(true);
							}
						} else {
							frame.setImageDrawable(res.getDrawable(INCORREC_ANSWER_IMAGE_NAME));
							textView.setText(R.string.dialog_title_incorrect_answer);
							//
							AlphaAnimation anim = new AlphaAnimation(1, 0);
							anim.setDuration(1000);
							anim.setFillAfter(true);
							anim.setAnimationListener(new AnimationListener() {
								public void onAnimationStart(
										Animation animation) {
									for (int i = 0; i < answers.size(); i++) {
										answers.get(i).setEnabledFlag(false);
									}
								}

								public void onAnimationRepeat(
										Animation animation) {
								}

								public void onAnimationEnd(
										Animation animation) {
									for (int i = 0; i < answers.size(); i++) {
										answers.get(i).setEnabledFlag(true);
									}
									failCount++;
									if (failCount >= FAIL_COUNT_LIMIT) {
										observer.onTaskEnd(false);
									} else {
										sound.play(R.raw.dialog_title_incorrect_answer);
									}
									
								}
							});
							if(OwletApplication.VERSION<=Build.VERSION_CODES.GINGERBREAD_MR1){
								layout.addView(frame);
								frame.startAnimation(anim);
							} else {
								failCount++;
								if(failCount>FAIL_COUNT_LIMIT){
									observer.onTaskEnd(false);
								} else {
									sound.play(R.raw.dialog_title_incorrect_answer);
								}
							}
						}
					}
				}
				return false;

			}
		};
	}
}
