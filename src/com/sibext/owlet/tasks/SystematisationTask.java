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
import java.util.LinkedList;
import java.util.Random;


import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.sibext.owlet.R;
import com.sibext.owlet.activity.ScreenOfTaskActivity;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageView;
import com.sibext.owlet.view.renderer.Renderer;
import com.sibext.owlet.view.systematisation.PeoplesSetView;
import com.sibext.owlet.view.systematisation.View1;
import com.sibext.owlet.view.systematisation.View2;
import com.sibext.owlet.view.systematisation.View3;
import com.sibext.owlet.view.systematisation.View5;

public class SystematisationTask extends Task {
	private int FAIL_COUNT_LIMIT = 2;
	
	private Integer[] templateArray;

	@Override
	protected Class<?>[] getCollectionOfView() {
		switch (complexity) {
		case COMPLEXITY_LOW:	
			Class<?>[] collectionOfView = {
					View1.class,
					View2.class,
					View3.class,
					PeoplesSetView.class,
					View5.class
			};
			return collectionOfView;
		case COMPLEXITY_MEDIUM:
			Class<?>[] collectionOfView1 = {
					View1.class,
					View2.class,
					View3.class,
					PeoplesSetView.class,
					View5.class
			};
			return collectionOfView1;
		case COMPLEXITY_HIGH:	
			Class<?>[] collectionOfView2 = {
					View1.class,
					View2.class,
					View3.class,
					PeoplesSetView.class,
					View5.class
			};
			return collectionOfView2;
			//для отладки по дефолту ставим complexity=3, чтобы проверить все наборы в таске
		 default: 
			Class<?>[] collectionOfView3 = {
				com.sibext.owlet.view.resourses.FigureView.class,
				com.sibext.owlet.view.resourses.SeaWorldView.class,
				com.sibext.owlet.view.resourses.ButterflyView.class,
				com.sibext.owlet.view.resourses.MashroomsView.class,
				com.sibext.owlet.view.resourses.FruitView.class,
				View1.class,
				View2.class,
				View3.class,
				PeoplesSetView.class,
				View5.class
			};
			return collectionOfView3;
		}
	}

	int figureCount;
	private int imagesCount;
	int cellsCount;
	
	public SystematisationTask(Context context,TaskParamsContainer container) {
		super(SYSTEMATISATION_TASK_TYPE, SYSTEMATISATION_TASK_LEGTH_DEFAULT,
				R.string.task_types_message_systematisation, context, container);
		rand = new Random();
		switch (complexity) {
		case Task.COMPLEXITY_HIGH:
			colorFlag = false;
			length = 4;
			cellsCount = (int)(length*2.5);
			FAIL_COUNT_LIMIT = 1;
			break;
		case Task.COMPLEXITY_MEDIUM:
			colorFlag = rand.nextBoolean();
			length = 4;
			cellsCount = length*2;
			FAIL_COUNT_LIMIT = 2;
			break;
		default:
			colorFlag = true;
			length = 3;
			cellsCount = length*2;			
			FAIL_COUNT_LIMIT = 2;
			break;
		}
		colors = selectColors(length);
		figureCount = length + 1;
		templateArray = templateSystematisationTask(length);
		imagesCount = templateArray.length;
		settingOfParameters(
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_LEFT_INDEX] + 20,
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_TOP_INDEX] + heightBoard + 7);
	}
	
	private void settingOfParameters(int marginLeftAnswer, int marginTopAnswer) {
		int bound_size = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);
		heightTaskImage = (int) ((heightBoard - bound_size*2) / 4.2) + DISTANCE_BETWEEN_IMAGES;
		widthTaskImage = heightTaskImage;

		widthAnswerImage = widthTaskImage - DISTANCE_BETWEEN_IMAGES;
		heightAnswerImage = widthAnswerImage;
		marginLeftAnswerImage = marginLeftAnswer;
		marginTopTaskImage = 40 + (heightBoard - 80 - heightTaskImage*3)/2;
		marginLeftTaskImage = (int)(bound_size/2) + (widthBoard - bound_size  - (int) ((widthBoard-bound_size) / widthTaskImage) * widthTaskImage)/2;
		marginTopAnswerImage = marginTopAnswer;
	}
	
	public int getEveryFailCountLimit(){
		return FAIL_COUNT_LIMIT;
	}

	public ViewGroup taskBuildView(final Context context, ViewGroup layout, final RelativeLayout root){
		Renderer ren = new Renderer();
		ren.setId(0);
		ren.setColorFlag(true);
		generateImageGroupId();
		BaseTaskImageView test = taskImageViewFactory(context,getImageGroupId(),ren);
		int[] images = new int[imagesCount];
		images = selectImages(imagesCount, test.getRenderedLimit());		
		emptyCells = new ArrayList<TaskImageView>();	
		int bound_size = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);
		int viewWight = widthBoard-bound_size*2;
		int figInLine = (int) (viewWight / widthTaskImage);
		for (int i = 0; i < cellsCount; i++) {
			int figureIndex = i % imagesCount;			
			if (i < figureCount) {
				int img_id = templateArray[figureIndex];
				Renderer renderer = new Renderer();
				renderer.setId(images[img_id]);
				renderer.setColorFlag(colorFlag);
				renderer.setColor(colors[img_id]);
				BaseTaskImageView img = taskImageViewFactory(context,getImageGroupId(),renderer);				
				FrameLayout.LayoutParams params = calcLP(i, figInLine);				
				img.setLayoutParams(params);
				layout.addView(img);
			} else {
				final TaskImageView img = new TaskImageView(context);
				FrameLayout.LayoutParams params = calcLP(i, figInLine);
				img.setLayoutParams(params);
				img.setCorrectAnswer(templateArray[figureIndex]);
				layout.addView(img);
				location = new int[2];
				img.post(new Runnable() {					
					public void run() {
						img.getLocationOnScreen(location);
						img.setCoordinate(location[0], location[1]);
						emptyCells.add(img);
						setQuestionImageOnHead(emptyCells);
					}
				});
			}
		}
		positionAnswers(context,root,images);
		return layout;
	}
	
	private void positionAnswers(final Context context,final RelativeLayout root,final int[] images){
		
		for (int i = 0; i < imagesCount; i++) {			
			Renderer renderer = new Renderer();
			renderer.setId(images[i]);
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[i]);
			BaseTaskImageView img = taskImageViewFactory(context, getImageGroupId(), renderer);					
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					widthAnswerImage,
					heightAnswerImage);
			params.leftMargin = i * (widthAnswerImage + 2*DISTANCE_BETWEEN_IMAGES) + marginLeftAnswerImage;
			params.topMargin = marginTopAnswerImage;
			img.setLeftMargin(params.leftMargin);
			img.setTopMargin(params.topMargin);
			img.setCoordinate(params.leftMargin, params.topMargin);
			img.setLayoutParams(params);
			img.setVariantAnswer(i);
			root.addView(img);	
			img.setOnTouchListener(getMovebleListener(emptyCells));
			answers.add(img);
		}
	}
			
	private FrameLayout.LayoutParams calcLP(int i, int lim) {
		FrameLayout.LayoutParams params;
		int w = widthTaskImage - DISTANCE_BETWEEN_IMAGES;
		int h = heightTaskImage - DISTANCE_BETWEEN_IMAGES;
		int lm = 0;
		int tm = 0;
		if (i < lim) {
			lm = (i) * widthTaskImage;
			tm = 0;
		} else if (i == lim) {
			lm = (i - 1) * widthTaskImage;
			tm = heightTaskImage;
		} else if (i < 2 * lim + 1) {
			lm = (lim - (i % (lim+1))-1) * widthTaskImage;
			tm = 2 * heightTaskImage;
		} else if (i == 2 * lim + 1) {
			lm = 0;
			tm = 3 * heightTaskImage;
		} else {
			lm = (i % (lim+1)) * widthTaskImage;
			tm = 4 * heightTaskImage;
		}

		params = new FrameLayout.LayoutParams(w, h, Gravity.TOP);
		params.leftMargin = lm + marginLeftTaskImage;
		params.topMargin = tm + marginTopTaskImage;
		return params;
	}
	
	private Integer[] templateSystematisationTask(int length) {
		LinkedList<Integer> valueList = new LinkedList<Integer>();
		for (int i = 0; i < length; i++) {
			valueList.add(i);
		}
		Integer[] taskArray = new Integer[length];
		Random generator = new Random();
		int size;
		int value;
		int i = 0;
		do {
			size = valueList.size();
			value = generator.nextInt(size);
			taskArray[i] = valueList.get(value);
			valueList.remove(value);
			i++;
		} while (size != 1);
		return taskArray;
	}
	
	@Override
	public void playTaskMessage(MediaPlayerSingleton sound) {
		sound.play(R.raw.task_systematisation_message);
	}

}
