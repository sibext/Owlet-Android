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
import java.util.Random;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sibext.owlet.R;
import com.sibext.owlet.activity.ScreenOfTaskActivity;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageView;
import com.sibext.owlet.view.conclusion.ConclusionViewFactory;
import com.sibext.owlet.view.conclusion.View1;
import com.sibext.owlet.view.conclusion.View2;
import com.sibext.owlet.view.conclusion.View3;
import com.sibext.owlet.view.conclusion.View4;
import com.sibext.owlet.view.conclusion.View5;
import com.sibext.owlet.view.conclusion.ViewTrans1;
import com.sibext.owlet.view.conclusion.ViewTrans2;
import com.sibext.owlet.view.conclusion.ViewTransFlag;
import com.sibext.owlet.view.renderer.ConclusionRenderer;

/**
 * Умозаключение
 * 
 * @author Mike Osipov <osipov@sibext.com>
 */

public class ConclusionTask extends Task {

	private final static int FAIL_COUNT_LIMIT = 2;
	
	public static int EXAMPLE_ID = 0;
	public static int ARROW1_ID = 1;
	public static int EXAMPLE_ANSWER_ID = 2;
	public static int TEST_ID = 3;
	public static int ARROW2_ID = 4;
	public static int QUESTION_ID = 5;
	public static int TEST_ANSWER_ID = 3;
	public static int ANSWER_CODE = 111;
	
	private ConclusionViewFactory factory;
	private SparseArray<BaseTaskImageView> elements;
	
	private SparseArray<Class<?>> getCollectionOfTransformView(){
		SparseArray<Class<?>> set = new SparseArray<Class<?>>();
		set.put(0,ViewTrans1.class);
		set.put(1,ViewTrans2.class);
		set.put(2, ViewTransFlag.class);
		return set;
	}
	
	@Override
	protected Class<?>[] getCollectionOfView() {
		switch (complexity) {
		case COMPLEXITY_LOW:	
			Class<?>[] collectionOfView = {
					View1.class,
					View5.class,
					getCollectionOfTransformView().get(0),
			};
			return collectionOfView;
		case COMPLEXITY_MEDIUM:
			Class<?>[] collectionOfView1 = {
					View4.class,
					getCollectionOfTransformView().get(0),
					getCollectionOfTransformView().get(1),
					getCollectionOfTransformView().get(2),
			};
			return collectionOfView1;
		case COMPLEXITY_HIGH:	
			Class<?>[] collectionOfView2 = {
					View5.class,
					View2.class,
					View3.class,	
					getCollectionOfTransformView().get(0),
					getCollectionOfTransformView().get(1),
					getCollectionOfTransformView().get(2),
			};
			return collectionOfView2;
			//для отладки по дефолту ставим complexity=3, чтобы проверить все наборы в таске
		 default: 
			Class<?>[] collectionOfView3 = {
				View1.class,
				View2.class,
				View3.class,
				View4.class,
				View5.class
			};
			return collectionOfView3;
		}
	}
	
	private int defineModeByType(Class<?> type){
		if(getCollectionOfTransformView().indexOfValue(type)>=0){
			return ConclusionViewFactory.TRANSFORM_VIEW_MODE;
		}
		return ConclusionViewFactory.SIMPLE_VIEW_MODE;
	}
		
	public ConclusionTask(Context context,TaskParamsContainer container) {
		super(CONCLUSION_TASK_TYPE, CONCLUSION_TASK_LEGTH_DEFAULT,
				R.string.task_types_message_conclusion, context, container);
		elements = new SparseArray<BaseTaskImageView>();
		settingOfParameters(
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_LEFT_INDEX] + 20,
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_TOP_INDEX] + heightBoard + 7);
		colors = selectColors(ConclusionViewFactory.COLORS_SIZE);
		switch (complexity) {
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

	private void settingOfParameters(int marginLeftAnswer, int marginTopAnswer) {
		int bound_size = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);				
		heightTaskImage = (int) ((heightBoard - bound_size*2) / 2.5)+DISTANCE_BETWEEN_IMAGES;
		widthTaskImage = heightTaskImage;

		heightAnswerImage = (int) ((heightBoard - 80) / 2.5);
		widthAnswerImage = heightAnswerImage;
		marginLeftAnswerImage = marginLeftAnswer;
		marginTopTaskImage = 0;// + (heightBoard - 80 - heightTaskImage*2);
		marginLeftTaskImage = 0;//(int)((20+widthBoard - 40  - (3*widthTaskImage))/2-DISTANCE_BETWEEN_IMAGES);
		marginTopAnswerImage = marginTopAnswer;
	}

	public int getEveryFailCountLimit(){
		return FAIL_COUNT_LIMIT;
	}
	
	public ViewGroup taskBuildView(final Context context,
			ViewGroup layout, final RelativeLayout root) {
		emptyCells = new ArrayList<TaskImageView>();
		
		ConclusionRenderer renderer = new ConclusionRenderer();
		renderer.setType(getImageGroupClass());
		renderer.setColorFlag(colorFlag);
		renderer.setColors(colors);
		int mode = defineModeByType(getImageGroupClass());
		//
		factory = new ConclusionViewFactory(context, renderer, mode);
		BaseTaskImageView example = factory.getExample();
		BaseTaskImageView exampleAnswer = factory.getExampleAnswer();
		BaseTaskImageView test = factory.getTest();
		BaseTaskImageView testAnswer = factory.getTestAnswer();
		testAnswer.setVariantAnswer(ANSWER_CODE);
		//
		int[] idx = new int[]{
			EXAMPLE_ID,
			EXAMPLE_ANSWER_ID,
			TEST_ID,
		};
		elements.put(EXAMPLE_ID, example);
		elements.put(EXAMPLE_ANSWER_ID, exampleAnswer);
		elements.put(TEST_ID, test);

		layout.addView(getArrow(ARROW1_ID));
		layout.addView(getArrow(ARROW2_ID));
		layout.addView(getQuestion());
		
		for(int i=0; i<idx.length;i++){
			BaseTaskImageView img = elements.get(idx[i]);
			img.setLayoutParams(calcEveryPointOfBeginningDrawing(idx[i]));
			layout.addView(img);
		}
		//variants of answer
		ArrayList<BaseTaskImageView> variants = factory.getVariants();
		variants.add(testAnswer);
		int[] varPositions = selectImages(variants.size(), variants.size());
		
		for(int i=0 ; i<variants.size(); i++){
			BaseTaskImageView img = variants.get(varPositions[i]);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					widthAnswerImage,
					heightAnswerImage);
			params.leftMargin = i * (widthAnswerImage + DISTANCE_BETWEEN_IMAGES+10) + marginLeftAnswerImage;
			params.topMargin = marginTopAnswerImage;
			img.setLeftMargin(params.leftMargin);
			img.setTopMargin(params.topMargin);
			img.setLayoutParams(params);
	
			root.addView(img);	
			img.setOnTouchListener(getMovebleListener(emptyCells));
			answers.add(img);
		}
		return layout;
	}

	private FrameLayout.LayoutParams calcEveryPointOfBeginningDrawing(int s) {
		FrameLayout.LayoutParams params;
		int w = widthTaskImage - DISTANCE_BETWEEN_IMAGES;
		int h = heightTaskImage - DISTANCE_BETWEEN_IMAGES;
		int lm = 0;
		int tm = 0;
		
		if ((s == 0) || (s == 3)) {
			lm = (int) (0.5*this.widthBoard)-(int)(1.5*w)-DISTANCE_BETWEEN_IMAGES;
		}
		else if ((s == 1) || (s == 4)) {
			lm = (int) (0.5*this.widthBoard-(int)(0.5*w));
		} else {
			lm = (int) (0.5*this.widthBoard+(int)(0.5*w)+DISTANCE_BETWEEN_IMAGES);
		}
			
		
		if ((s == 3) || (s == 4) || (s == 5)) {
			tm = (int) (0.5*this.heightBoard)+(int)(h*0.1);
		} else {
			tm = (int) (0.5*this.heightBoard)-(int)(h*1.1);
		}
		
		params = new FrameLayout.LayoutParams(w, h, Gravity.TOP);
		params.leftMargin = lm + marginLeftTaskImage;
		params.topMargin = tm + marginTopTaskImage ;
		return params;
	}
		
	private int[] arrayConclusionTask() {
		
		int[] randArray= new int[4];
		int[] arrayForImageOnBoard = {0, 1, 2, 3, 4, 5};
		int[] arrayCollective= new int[10];
		
		//create random array (example: {6, 7, 8, 9})
		Random random = new Random();
		int j = 6+Math.abs( random.nextInt() ) % 4;
		for (int i = 0; i < 4; i++) {
			randArray[i]=(i+j)%4+6;
		}
		
		//randArray + arrayForImageOnBoard = arrayCollective
		System.arraycopy(arrayForImageOnBoard, 0, arrayCollective, 0, 6);
		System.arraycopy(randArray, 0, arrayCollective, 6, 4);
		//for (int i = 0; i < 10; i++) 
		//Log.d("llolog", ""+arrayCollective[i]);
		return arrayCollective;
		}

	@Override
	public void playTaskMessage(MediaPlayerSingleton sound) {
		sound.play(R.raw.task_conclusion_message);
	}
	
	private ImageView getArrow(int arrowId){
		ImageView res = new ImageView(context);
		Resources resources = context.getResources();	
		Drawable drawable = resources.getDrawable(R.drawable.arrow);
		res.setImageDrawable(drawable);
		/*Canvas canvas = new Canvas();
		Bitmap b = Bitmap.createBitmap(widthTaskImage, heightTaskImage, Config.ARGB_8888);
		canvas.setBitmap(b);
		Paint paint = new Paint();
		TaskImageHelper.Arrow.drawArrow(canvas, widthTaskImage, heightTaskImage,paint);
		res.setImageBitmap(b);*/
		res.setLayoutParams(calcEveryPointOfBeginningDrawing(arrowId));
		res.setBackgroundColor(0);
		return res;
	}
	private TaskImageView getQuestion(){
		Resources res = context.getResources();	
		Drawable drawable = res.getDrawable(R.drawable.question);
		final TaskImageView img = new TaskImageView(context);
		FrameLayout.LayoutParams params = calcEveryPointOfBeginningDrawing(QUESTION_ID);
		img.setLayoutParams(params);
		img.setImageDrawable(drawable);  
		img.setCorrectAnswer(ANSWER_CODE);
		img.setBackgroundResource(0);
		location = new int[2];
		img.post(new Runnable() {
			public void run() {
				img.getLocationOnScreen(location);
				img.setCoordinate(location[0], location[1]);
				emptyCells.add(img);
			}
		});
		return img;
	}
}
