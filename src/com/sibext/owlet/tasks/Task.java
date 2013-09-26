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

package com.sibext.owlet.tasks;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.activity.ScreenOfTaskActivity;
import com.sibext.owlet.helper.Log;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageFactory;
import com.sibext.owlet.view.TaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

abstract public class Task {
	public static final String TAG = "Task";
	public final static int SYSTEMATISATION_TASK_NAME = R.string.systematisation;
	public final static int COMPARE_TASK_NAME = R.string.compare;
	public final static int MAGICSUARE_TASK_NAME = R.string.magic_square;
	public final static int CONCLUSION_TASK_NAME = R.string.conclusion;
	
	public final static int SYSTEMATISATION_TASK_TYPE = 1;
	public final static int COMPARE_TASK_TYPE = 2;
	public final static int MAGICSUARE_TASK_TYPE = 3;
	public final static int CONCLUSION_TASK_TYPE = 4;
	
	public final static int MAGICSUARE_TASK_LEGTH_DEFAULT = 4;
	public final static int SYSTEMATISATION_TASK_LEGTH_DEFAULT = 4;
	public final static int CONCLUSION_TASK_LEGTH_DEFAULT = 7;
	public final static int COMPARE_TASK_LEGTH_DEFAULT = 4;
	
	public final static int COMPLEXITY_LOW = 0;
	public final static int COMPLEXITY_MEDIUM = 1;
	public final static int COMPLEXITY_HIGH = 2;
	protected final int DISTANCE_BETWEEN_IMAGES = 5;

	private final int UNDEFINED_ID = -1;

	protected int widthTaskImage;
	protected int heightTaskImage;
	protected int marginTopTaskImage;
	protected int marginLeftTaskImage;

	protected int widthAnswerImage;
	protected int heightAnswerImage;
	protected int marginLeftAnswerImage;
	protected int marginTopAnswerImage;

	protected int type;
	protected int complexity = COMPLEXITY_LOW;
	protected int length;
	protected final int infoTextId;
	protected final int infoAudioId;
	
	protected Context context;
	protected int widthScreen;
	protected int heightScreen;	
	protected int widthBoard;
	protected TaskObservable observer;
	protected int heightBoard;
	protected int failCount;
	protected TextView textView;
	protected MediaPlayerSingleton sound;
	private int imageGroupId;
	protected ArrayList<BaseTaskImageView> answers;
	protected ArrayList<TaskImageView> emptyCells;
	protected int[] location;
	protected int[] acceptColors;
	protected boolean colorFlag;
	protected int[] colors;
	protected Random rand;
	
	public Task(int type, int complexity, int length, int infoTextId) {
		this.type = type;
		this.complexity = complexity;
		this.length = length;
		this.infoTextId = infoTextId;
		this.infoAudioId = R.raw.task_compare_message;
		this.rand = new Random();
		answers = new ArrayList<BaseTaskImageView>();
	}

	public Task(int type, int length, int taskMessageId, 
			Context context,TaskParamsContainer container) {
		this(type, container.getComplexity(), length, taskMessageId);
		this.context = context;
		this.complexity = getComplexityFromPref();
		acceptColors = new int[]{
				context.getResources().getColor(R.color.task_image_red),	
				context.getResources().getColor(R.color.task_image_brown),	
				context.getResources().getColor(R.color.task_image_dark_blue),	
				context.getResources().getColor(R.color.task_image_lilac),	
				context.getResources().getColor(R.color.task_image_yellow),	
				context.getResources().getColor(R.color.task_image_gray),	
				context.getResources().getColor(R.color.task_image_purple),	
				context.getResources().getColor(R.color.task_image_white),	
			};
		this.widthBoard = container.getBoardParams()[ScreenOfTaskActivity.WIDTH_INDEX];
		this.heightBoard = container.getBoardParams()[ScreenOfTaskActivity.HEIGTH_INDEX];
		this.widthScreen = container.getWidthScreen();
		this.heightScreen = container.getHeightScreen() - ScreenOfTaskActivity.statusBarHeigth;
		this.observer = new TaskObservable();
		this.failCount=0;
		this.textView = container.getTextView();
		this.observer.addObserver(container.getObserver());
		this.sound = container.getSound();

	}
	
	public class TaskObservable extends Observable {
		public void onTaskEnd(boolean result){
			setChanged();
			notifyObservers(result);
		}
	}
	public int getType() {
		return type;
	}

	public int getComplexity() {
		return complexity;
	}
	
	public int getFailCount() {
		return failCount;
	}
	
	public int getLiength() {
		return length;
	}

	public int getInfoTextId() {
		return infoTextId;
	}

	public int getInfoAudioId() {
		return infoAudioId;
	}
	
	public void setFailCount(int failCount){
		this.failCount = failCount;
	}
	
	public void setType(int t) {
		type = t;
	}

	public void setComplexity(int c) {
		this.complexity = c;
	}

	public void setLiength(int l) {
		length = l;
	}
	
	public void removeAnswerViews(){
		for(int i=0; i < answers.size(); i++){
			answers.get(i).setVisibility(View.GONE);
		}
	}
	
	private int getComplexityFromPref(){
		OwletApplication app = (OwletApplication)context.getApplicationContext();
		SharedPreferences pref = app.getSettings();
		return pref.getInt(type+OwletApplication.COMPLEX_POSTFIX, COMPLEXITY_LOW);
	}
	
	abstract protected Class<?>[] getCollectionOfView();
		
	abstract public ViewGroup taskBuildView(final Context context,
			final ViewGroup layout, final RelativeLayout root);
	
	abstract public int getEveryFailCountLimit();	
		
	protected BaseTaskImageView taskImageViewFactory(Context context, int groupId, Renderer renderer) {
		try {
			Class<?>[] views = getCollectionOfView();
			return TaskImageFactory.getImageView(views[groupId], context, renderer);
		} catch (Exception e) {
			Log.e(TAG, "Not found required constructor for task image view", e);
		}
		return null;
	}
	
	protected int getRandomId() {
		return rand.nextInt(getCollectionOfView().length);
	}

	protected int generateImageGroupId() {
		if(imageGroupId == UNDEFINED_ID){
			imageGroupId = getRandomId();
		}
		return imageGroupId;
	}

	/**
	 * Chooses random group only one times
	 * 
	 * @return the selected class of TaskImageView
	 */
	protected Class<?> getImageGroupClass() {
		imageGroupId = generateImageGroupId();
		return getCollectionOfView()[imageGroupId];
	}

	protected boolean getRandomColor() {
		return rand.nextBoolean();
	}
	public void setImageGroupId(int id){
		this.imageGroupId = id%getCollectionOfView().length;
	}
	public int getImageGroupId(){
		return this.imageGroupId;
	}
	public abstract void playTaskMessage(MediaPlayerSingleton sound);

	/**
	 *    Возвращает OnTouchListener который при клике перемещает элемент на позицию елемента массива emptyCells
	 * с индексом 0. При совпадении вариантов ответа этот элемент перерисовывается в перемещаемый элемент
	 * и выталкивается из emptyCells, а перемещаемый элемент возвращается на исходное место, иначе 
	 * перемещаемый элемент просто возврвщатся обратно и массив emptyCells остается без изменений. 
	 *    После возврата элемента на место, элементы на которые установлен данный OnTouchListener перемешиваются.
	 *    При опустошении массива emptyCells вызывается метод "onTaskEnd(boolean)", с параметром, соответствующим
	 * результату выполнения текущего задания.
	 * @param emptyCells
	 * @return
	 */
	protected OnTouchListener getMovebleListener(final ArrayList<TaskImageView> emptyCells){
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (emptyCells.size() != 0) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						final BaseTaskImageView img = (BaseTaskImageView) v;
						if(img.getEnabledFlag()){
						final TaskImageView goal = emptyCells.get(0);
						int delta = (int) ((widthAnswerImage - widthTaskImage) / 2);
						float toX = goal.getXcoordinate() - img.getLeftMargin() - delta;
						float toY = goal.getYcoordinate() - img.getTopMargin() - delta - ScreenOfTaskActivity.statusBarHeigth;
						TranslateAnimation animation = new TranslateAnimation(0, toX, 0, toY);
						animation.setDuration(700);	
						TranslateAnimation backAnim = new TranslateAnimation(0, -toX, 0, -toY);
						backAnim.setStartOffset(700);
						backAnim.setDuration(200);
						backAnim.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
							}
							@Override
							public void onAnimationRepeat(Animation animation) {
							}
							@Override
							public void onAnimationEnd(Animation animation) {
								for(int i=0;i<answers.size();i++){
									answers.get(i).setEnabledFlag(true);
								}
								shakeAnswer();
							}
						});
						//
						TranslateAnimation backAnimfast = new TranslateAnimation(0, -toX, 0, -toY);
						backAnimfast.setStartOffset(700);
						backAnimfast.setDuration(50);
						backAnimfast.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
								
							}
							@Override
							public void onAnimationRepeat(Animation animation) {
							}
							@Override
							public void onAnimationEnd(Animation animation) {
								for(int i=0;i<answers.size();i++){
									answers.get(i).setEnabledFlag(true);
								}
								setVisible(img, true);
								shakeAnswer();
							}
						});
						if (img.getVariantAnswer() == emptyCells.get(0)
								.getCorrectAnswer()) {
							final Bitmap bm = img.getDrawingCache();
							animation.setAnimationListener(new AnimationListener() {
								public void onAnimationStart(Animation animation) {
									for(int i=0;i<answers.size();i++){
										answers.get(i).setEnabledFlag(false);
									}
									emptyCells.remove(0);
								}
								public void onAnimationRepeat(Animation animation) {
								}
								public void onAnimationEnd(Animation animation) {
									goal.setupBackground(context, 0);
									goal.setImageBitmap(bm);
									textView.setText(context.getString(R.string.dialog_title_good_work));
									if (emptyCells.size() == 0){
										observer.onTaskEnd(true);
									} else {
										sound.play(R.raw.dialog_title_correct_answer);
									}
									setQuestionImageOnHead(emptyCells);
									setVisible(img, false);
								}
							});	
							AnimationSet set = new AnimationSet(context, null);
							set.addAnimation(animation);
							set.addAnimation(backAnimfast);
							if(OwletApplication.VERSION<=Build.VERSION_CODES.GINGERBREAD_MR1){
								img.startAnimation(set);
							} else {
								if(emptyCells.size()==1){
									observer.onTaskEnd(true);
								} else {
									img.startAnimation(set);
								}
							}
						} else {	
							animation.setAnimationListener(new AnimationListener() {
								public void onAnimationStart(Animation animation) {
									for(int i=0;i<answers.size();i++){
										answers.get(i).setEnabledFlag(false);
									}
								}
								public void onAnimationRepeat(Animation animation) {
								}
								public void onAnimationEnd(Animation animation) {	
									textView.setText(context.getString(R.string.dialog_title_incorrect_answer));
									if(failCount>getEveryFailCountLimit()){
										observer.onTaskEnd(false);
									} else {
										Random random = new Random();
										boolean say  = random.nextBoolean();
										if(say) sound.play(R.raw.dialog_title_incorrect_answer);
									}
								}
							});
							AnimationSet set = new AnimationSet(context, null);
							set.addAnimation(animation);
							set.addAnimation(backAnim);
							if(OwletApplication.VERSION<=Build.VERSION_CODES.GINGERBREAD_MR1){
								failCount++;
								img.startAnimation(set);
							} else {
								failCount++;
								if(failCount>getEveryFailCountLimit()){
									observer.onTaskEnd(false);
								} else {
									img.startAnimation(set);
								}
							}
						}
						}
						
					}
				}
				return false;
			}
		};
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setVisible(BaseTaskImageView img, boolean isFull){
		if(OwletApplication.VERSION>=Build.VERSION_CODES.HONEYCOMB){
			if(isFull) {
				img.setAlpha((float)1);
			} else {
				img.setAlpha((float)0);
			}
		} else {
			if(isFull) {
				img.setAlpha(255);
			} else {
				img.setAlpha(0);
			}
		}

	}

	private void shakeAnswer(){
		int answersCount = answers.size();
		int[] position = new int[answersCount];
		position = selectImages(answersCount, answersCount);
		for (int i = 0; i < answersCount; i++) {
			TaskImageView img = answers.get(i);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)img.getLayoutParams();
			params.leftMargin = position[i] * (widthAnswerImage + 2*DISTANCE_BETWEEN_IMAGES) + marginLeftAnswerImage;
			params.topMargin = marginTopAnswerImage;
			img.setLeftMargin(params.leftMargin);
			img.setTopMargin(params.topMargin);
			img.setLayoutParams(params);
		}
	}
	
	protected int[] selectImages(int length, int max_value) {
		ArrayList<Integer> allImages = new ArrayList<Integer>();
		for(int i=0;i<max_value;i++){
			allImages.add(i);			
		}
		Random generator = new Random();
		int[] images = new int[length];
		int n;
		
		for(int i = 0; i < length; i++) {
			n = generator.nextInt(allImages.size());
			images[i] = allImages.get(n);
			allImages.remove(n);
		}
		return images;
	}
	
	protected int[] selectColors(int length){
		ArrayList<Integer> allImages = new ArrayList<Integer>();
		for(int i=0;i<acceptColors.length;i++){
			allImages.add(acceptColors[i]);
		}
		Random generator = new Random();
		int[] images = new int[length];
		int n;
		
		for(int i = 0; i < length; i++) {
			n = generator.nextInt(allImages.size());
			images[i] = allImages.get(n);
			allImages.remove(n);
		}
		return images;
	}
	
	protected void setQuestionImageOnHead(ArrayList<TaskImageView> emptyCells){
		if(emptyCells.size() == 0){
			return;
		}
		int resId = R.drawable.question; 
		emptyCells.get(0).setupBackground(context, resId);
	}
	

}
