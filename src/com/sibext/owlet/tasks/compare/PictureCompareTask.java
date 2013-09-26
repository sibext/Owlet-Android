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

import com.sibext.owlet.tasks.TaskParamsContainer;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;
import com.sibext.owlet.view.resourses.ButterflyView;
import com.sibext.owlet.view.resourses.FigureView;
import com.sibext.owlet.view.resourses.HouseView;
import com.sibext.owlet.view.resourses.LadybugView;
import com.sibext.owlet.view.resourses.SnowmanView;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class PictureCompareTask extends AbstractCompareTask{

	public PictureCompareTask(Context context, TaskParamsContainer container) {
		super(context, container);
	}

	@Override
	public ViewGroup taskBuildView(Context context, ViewGroup layout,
			RelativeLayout root) {
		Renderer ren = new Renderer();
		ren.setId(0);
		generateImageGroupId();
		BaseTaskImageView test = taskImageViewFactory(context,getImageGroupId(),ren);
		int[] images = new int[2];
		images = selectImages(2, test.getRenderedLimit());
		int excess_idx = rand.nextInt(imageCount);
		
		for (int i = 0; i < imageCount; i++) {
			BaseTaskImageView img = null;
			Renderer renderer = new Renderer();
			final int id = i;
			int answerId = INCORRECT_ANSWER_ID;
			int picId = images[0];
			if(i==excess_idx){
				answerId = CORRECT_ANSWER_ID;
				picId = images[1];
			} 
			renderer.setId(picId);
			img = taskImageViewFactory(context,getImageGroupId(),renderer);
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
		
		return layout;
	}

	@Override
	protected Class<?>[] getCollectionOfView() {
		switch (complexity) {
		case COMPLEXITY_LOW:	
			Class<?>[] collectionOfView = {
					ButterflyView.class,
					FigureView.class,
					SnowmanView.class,
					LadybugView.class,
			};
			return collectionOfView;
		case COMPLEXITY_MEDIUM:
			Class<?>[] collectionOfView1 = {
					SnowmanView.class,
					LadybugView.class,
			};
			return collectionOfView1;
		case COMPLEXITY_HIGH:	
			Class<?>[] collectionOfView2 = {
					SnowmanView.class,
					LadybugView.class,
					HouseView.class,
			};
			return collectionOfView2;
		 default: 
			Class<?>[] collectionOfView3 = {
					SnowmanView.class,
					LadybugView.class,
					HouseView.class,
			};
			return collectionOfView3;
		}
	}


}
