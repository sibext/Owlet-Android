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
import com.sibext.owlet.view.magic_square.View1;
import com.sibext.owlet.view.magic_square.View2;
import com.sibext.owlet.view.magic_square.View3;
import com.sibext.owlet.view.magic_square.View4;
import com.sibext.owlet.view.renderer.Renderer;
import com.sibext.owlet.view.resourses.ButterflyView;
import com.sibext.owlet.view.resourses.FigureView;
import com.sibext.owlet.view.resourses.FruitView;
import com.sibext.owlet.view.resourses.MashroomsView;
import com.sibext.owlet.view.resourses.RedView;
import com.sibext.owlet.view.resourses.SeaWorldView;
import com.sibext.owlet.view.resourses.StarView;

public class MagicSquareTask extends Task {

	private final static int FAIL_COUNT_LIMIT = 1;
	private int[] templateArray;
	private LinkedList<Integer> indexesOfEmptyCells;
	
	@Override
	protected Class<?>[] getCollectionOfView() {
		switch (complexity) {
		case COMPLEXITY_LOW:	
			Class<?>[] collectionOfView = {
					FigureView.class,
					SeaWorldView.class,
					ButterflyView.class,
					MashroomsView.class,
					FruitView.class,
					StarView.class,
					RedView.class
			};
			return collectionOfView;
		case COMPLEXITY_MEDIUM:
			Class<?>[] collectionOfView1 = {
					View3.class,
					View4.class,
					com.sibext.owlet.view.resourses.ButterflyView.class,
			};
			return collectionOfView1;
		case COMPLEXITY_HIGH:	
			Class<?>[] collectionOfView2 = {
					View1.class,
					View2.class,
			};
			return collectionOfView2;
			//для отладки по дефолту ставим complexity=3, чтобы проверить все наборы в таске
		 default: 
			Class<?>[] collectionOfView3 = {
					View1.class,
					View2.class,
					View3.class,
					View4.class,
					com.sibext.owlet.view.systematisation.View1.class,
					com.sibext.owlet.view.systematisation.View2.class,
					com.sibext.owlet.view.systematisation.View3.class,
					com.sibext.owlet.view.systematisation.PeoplesSetView.class,
					com.sibext.owlet.view.systematisation.View5.class,
					FigureView.class,
					SeaWorldView.class,
					ButterflyView.class,
					MashroomsView.class,
					FruitView.class,
					StarView.class,
					RedView.class
			};
			return collectionOfView3;
		}
	}
	
	public MagicSquareTask(Context context, TaskParamsContainer container) {
		super(MAGICSUARE_TASK_TYPE, MAGICSUARE_TASK_LEGTH_DEFAULT,
				R.string.task_types_message_magicsquare, context, container);
		templateArray = templateMagicSquareTask();
		settingOfParameters(
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_LEFT_INDEX] + 20,
				container.getBoardParams()[ScreenOfTaskActivity.MARGIN_TOP_INDEX] + heightBoard + 7);
		//
		rand = new Random();
		int empty1 = rand.nextInt(9);
		indexesOfEmptyCells = new LinkedList<Integer>();		
		switch (complexity) {
		case Task.COMPLEXITY_MEDIUM:{
			colorFlag = rand.nextBoolean();
			int empty2 = 0;
			do{
				empty2 = rand.nextInt(9); 
			} while(empty1==empty2);
			indexesOfEmptyCells.add(empty1);
			indexesOfEmptyCells.add(empty2);
			break;
		}
		case Task.COMPLEXITY_HIGH:{
			colorFlag = false;
			int empty2 = 0;
			do{
				empty2 = rand.nextInt(9); 
			} while(empty1==empty2);
			int empty3 = 0;
			do{
				empty3 = rand.nextInt(9); 
			} while(empty1==empty3||empty1==empty3);
			indexesOfEmptyCells.add(empty1);
			indexesOfEmptyCells.add(empty2);
			indexesOfEmptyCells.add(empty3);
			break;
		}
		default://Task.COMPLEXITY_LOW
			colorFlag = true;
			indexesOfEmptyCells.add(empty1);
			break;
		}		
		colors = selectColors(length);
	}

	private void settingOfParameters(int marginLeftAnswer, int marginTopAnswer) {
		int bound_size = context.getResources().getDimensionPixelSize(R.dimen.task_board_margin_up_down);
		heightTaskImage = (int) ((heightBoard - bound_size*1.5) / 3) - 2*DISTANCE_BETWEEN_IMAGES;
		widthTaskImage = heightTaskImage;

		widthAnswerImage = widthTaskImage;
		heightAnswerImage = widthAnswerImage;
		marginLeftAnswerImage = marginLeftAnswer;
		marginTopTaskImage = (int)(40+(heightBoard - 80  - 3*heightTaskImage)/2);
		marginLeftTaskImage = (int)(20+(widthBoard - 40  - 3*widthTaskImage)/2);
		marginTopAnswerImage = marginTopAnswer;
	}

	public int getEveryFailCountLimit(){
		return FAIL_COUNT_LIMIT;
	}
	
	public ViewGroup taskBuildView(final Context context,
			ViewGroup layout, final RelativeLayout root) {
		int figureCount = templateArray.length;	
		emptyCells = new ArrayList<TaskImageView>();
		//choose random group
		generateImageGroupId();

		for (int i = 0; i < figureCount; i++) {
			if (indexesOfEmptyCells.contains(i)) {
				//draw glassy square
				final TaskImageView img = new TaskImageView(context);
				 FrameLayout.LayoutParams params = calcEveryPointOfBeginningDrawing(i);
				img.setLayoutParams(params);
				img.setupBackground(context, R.drawable.glassy_square);
				img.setCorrectAnswer(templateArray[i]);				
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
			} else {
				//draw image square
				Renderer renderer = new Renderer();
				renderer.setId(templateArray[i]);
				renderer.setColorFlag(colorFlag);
				renderer.setColor(colors[templateArray[i]]);
				TaskImageView img = (TaskImageView)taskImageViewFactory(context,getImageGroupId(),renderer);	
				FrameLayout.LayoutParams params = calcEveryPointOfBeginningDrawing(i);
				img.setLayoutParams(params);
			
				layout.addView(img);
			}
		
		}
		for (int i = 0; i < 3; i++) {
			//draw answer image
			Renderer renderer = new Renderer();
			renderer.setId(templateArray[i]);
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[templateArray[i]]);
			BaseTaskImageView img = (BaseTaskImageView)taskImageViewFactory(context, getImageGroupId(), renderer);					
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					widthAnswerImage,
					heightAnswerImage);
			params.leftMargin = i * (widthAnswerImage+DISTANCE_BETWEEN_IMAGES) + marginLeftAnswerImage;
			params.topMargin = marginTopAnswerImage;
			img.setLeftMargin(params.leftMargin);
			img.setTopMargin(params.topMargin);
			img.setLayoutParams(params);
			img.setVariantAnswer(templateArray[i]);			
			root.addView(img);
			
			img.setOnTouchListener(getMovebleListener(emptyCells));
			answers.add(img);
		}
		return layout;
	}
	
	private FrameLayout.LayoutParams calcEveryPointOfBeginningDrawing(int s) {
		FrameLayout.LayoutParams params;
		int w = widthTaskImage-DISTANCE_BETWEEN_IMAGES;
		int h = heightTaskImage-DISTANCE_BETWEEN_IMAGES;
		int lm = 0;
		int tm = 0;
		
		if ((s == 1) || (s == 4) || (s == 7)) {
			tm = heightTaskImage;
		}
			else if ((s == 2) || (s == 5) || (s == 8)) {
				tm = 2 * heightTaskImage;
			}
		
		if ((s == 3) || (s == 4) || (s == 5)) {
			lm = widthTaskImage;
		}
			else if ((s == 0) || (s == 1) || (s == 2)) {
				lm = 2 * widthTaskImage;
			}
		
		params = new FrameLayout.LayoutParams(w, h, Gravity.TOP);
		params.leftMargin = lm + marginLeftTaskImage;
		params.topMargin = tm + marginTopTaskImage;
		return params;
	}
		
	private int[] templateMagicSquareTask() {
		
		int[] randArray= new int[3];
		int[] reiterRandArray= new int[7];
		int[] arrayForSquare1= new int[9];
		int[] arrayForSquare2= new int[9];
		
		//create random array (example: {1, 2, 3})  (really!!! {0,1,2})
		Random random = new Random();
		int j = 1+Math.abs( random.nextInt() ) % 2;
		for (int i = 0; i < 3; i++) {
			randArray[i]=(i+j)%3;
		}		
		
		//copy from {1, 2, 3} to {1, 2, 3, 1, 2, 3, 1} (intermediate array)
		for (int i=0; i<2; i++) {
			System.arraycopy(randArray, 0, reiterRandArray, 3*i, 3);
			}
		System.arraycopy(randArray, 0, reiterRandArray, 6, 1);
	
		Random random1 = new Random();
		int a = Math.abs( random1.nextInt() ) % 1;
		if (a==0) {
			//copy from {1, 2, 3, 1, 2, 3, 1} to {1, 2, 3, 2, 3, 1, 3, 1, 2}
			for (int i=0; i<3; i++) {
				System.arraycopy(reiterRandArray, i, arrayForSquare1, 3*i, 3);
			}
			return arrayForSquare1;
			}
		else
			//copy from {1, 2, 3, 1, 2, 3, 1} to {1, 2, 3, 3, 1, 2, 2, 3, 1}
			for (int i=0; i<3; i++) {
				System.arraycopy(reiterRandArray, i*2, arrayForSquare2, 3*i, 3);
			}
			return arrayForSquare2;
		}

	@Override
	public void playTaskMessage(MediaPlayerSingleton sound) {
		sound.play(R.raw.task_magicsquare_message);
	}

}
