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

import java.util.Random;

import com.sibext.owlet.tasks.TaskParamsContainer;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.compare.CubeView;
import com.sibext.owlet.view.compare.View1;
import com.sibext.owlet.view.compare.View2;
import com.sibext.owlet.view.compare.View3;
import com.sibext.owlet.view.renderer.CombinatedRenderer;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class CubeCompareTask extends AbstractCompareTask{

	public CubeCompareTask(Context context, TaskParamsContainer container) {
		super(context, container);
	}

	@Override
	public ViewGroup taskBuildView(Context context, ViewGroup layout,
			RelativeLayout root) {
		CombinatedRenderer empty = new CombinatedRenderer(
				getImageGroupClass(), 
				CUBE_EDGES_COUNT+CUBE_EXCESS_IMAGES_COUNT);
		BaseTaskImageView test = new CubeView(context, empty);
		int[] images = new int[4];
		images = selectImages(4, test.getRenderedLimit());
		for(int i=0; i<images.length; i++){
			hashColors.put(images[i], colors[i]);
		}
		//
		Random excess = new Random();
		int excess_idx = excess.nextInt(imageCount);
		for (int i = 0; i < imageCount; i++) {
			final int id = i;
			boolean excessFlag = false;
			int answerId = INCORRECT_ANSWER_ID;
			if (i == excess_idx) {
				excessFlag = true;
				answerId = CORRECT_ANSWER_ID;
			}
			CombinatedRenderer renderer = new CombinatedRenderer(
					getImageGroupClass(),
					CUBE_EDGES_COUNT + CUBE_EXCESS_IMAGES_COUNT);
			renderer.setMultId(images);
			renderer.setExcessFlag(excessFlag);
			renderer.setColorFlag(colorFlag);
			renderer.setMultColors(hashColors);
			BaseTaskImageView img = new CubeView(context, renderer);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					widthTaskImage, heightTaskImage);
			params.leftMargin = marginLeft[i];
			params.topMargin = marginTop[i];
			params.gravity = Gravity.TOP | Gravity.LEFT;
			img.setLayoutParams(params);
			img.setCorrectAnswer(answerId);
			img.setOnTouchListener(getCompareListener(layout, id));	
			layout.addView(img);
			answers.add(img);
		}
		//

		return layout;

	}
	
	@Override
	protected Class<?>[] getCollectionOfView() {
		switch (complexity) {
		case COMPLEXITY_LOW:	
			Class<?>[] collectionOfView = {
					com.sibext.owlet.view.resourses.FruitView.class,
					com.sibext.owlet.view.resourses.FigureView.class,
					com.sibext.owlet.view.resourses.StarView.class,
					com.sibext.owlet.view.resourses.RedView.class,
			};
			return collectionOfView;
		case COMPLEXITY_MEDIUM:
			Class<?>[] collectionOfView1 = {
					com.sibext.owlet.view.resourses.ButterflyView.class,
					com.sibext.owlet.view.resourses.FigureView.class,
					com.sibext.owlet.view.resourses.SeaWorldView.class,
					com.sibext.owlet.view.resourses.MashroomsView.class,
			};
			return collectionOfView1;
		case COMPLEXITY_HIGH:	
			Class<?>[] collectionOfView2 = {
					View1.class,
					View2.class,
					View3.class,
					com.sibext.owlet.view.systematisation.View1.class,
					com.sibext.owlet.view.systematisation.View3.class,
					com.sibext.owlet.view.systematisation.View5.class,
			};
			return collectionOfView2;
			//для отладки по дефолту ставим complexity=3, чтобы проверить все наборы в таске
		 default: 
			Class<?>[] collectionOfView3 = {
				//ButterflyView.class,
				View1.class,
				View2.class,
				View3.class,
				com.sibext.owlet.view.systematisation.View1.class,
				com.sibext.owlet.view.systematisation.View3.class,
				com.sibext.owlet.view.resourses.ButterflyView.class,
				com.sibext.owlet.view.resourses.SeaWorldView.class,
				com.sibext.owlet.view.resourses.FruitView.class,
				com.sibext.owlet.view.resourses.FigureView.class,
				com.sibext.owlet.view.resourses.StarView.class,
				com.sibext.owlet.view.resourses.MashroomsView.class,
				com.sibext.owlet.view.resourses.RedView.class,
			};
			return collectionOfView3;
		}

	}



	
}
