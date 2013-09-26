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

package com.sibext.owlet.view.conclusion;

import java.util.ArrayList;

import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageFactory;
import com.sibext.owlet.view.renderer.ConclusionRenderer;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.util.Log;

public class ConclusionViewFactory {
	public static final String EXAMPLE_FIELD = "EXAMPLE_ID";
	public static final String EXAMPLE_ANSWER_FIELD = "EXAMPLE_ANSWER_ID";
	public static final String TEST_FIELD = "TEST_ID";
	public static final String TEST_ANSWER_FIELD = "TEST_ANSWER_ID";
	public static final String VARIANTS_FIELD = "variants";
	
	public static final int SIMPLE_VIEW_MODE = 0;
	public static final int TRANSFORM_VIEW_MODE = 1;

	private static final String TAG = "ConclusionViewFactory";
	public static final int COLORS_SIZE = 7;

	private Context context;
	private Class<?> type;
	private Renderer renderer;
	private int[] colors;
	private boolean colorFlag;
	private int mode;
	private TransformView trans;

	public ConclusionViewFactory(Context context,
			ConclusionRenderer conRenderer, int mode) {
		this.context = context;
		this.type = conRenderer.getType();
		this.colorFlag = conRenderer.getColorFlag();
		this.colors = conRenderer.getColors();
		this.mode = mode;
		if (mode == TRANSFORM_VIEW_MODE) {
			trans = TaskImageFactory.getTransformeView(type, context, conRenderer);
		}
	}

	public BaseTaskImageView getExample(){
		switch (mode) {
		case TRANSFORM_VIEW_MODE:
			return trans.getExampleImage();
		default:
			renderer = new Renderer();
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[0]);
			int id = 0;
			try {
				id = (int)type.getField(EXAMPLE_FIELD).getInt(null);
			} catch (Exception e) {
				Log.d(TAG," problem with getting value from static field"+type,e);
			}
			renderer.setId(id);
			return TaskImageFactory.getImageView(type, context, renderer);
		}
	}

	public BaseTaskImageView getExampleAnswer() {
		switch (mode) {
		case TRANSFORM_VIEW_MODE:
			return trans.getExampleAnswerImage();
		default:
			renderer = new Renderer();
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[1]);
			int id = 0;
			try {
				id = (int)type.getField(EXAMPLE_ANSWER_FIELD).getInt(null);
			} catch (Exception e) {
				Log.d(TAG," problem with getting value from static field",e);
			}
			renderer.setId(id);
		return TaskImageFactory.getImageView(type, context, renderer);
		}
	}

	public BaseTaskImageView getTest() {
		switch (mode) {
		case TRANSFORM_VIEW_MODE:
			return trans.getTestImage();
		default:
			renderer = new Renderer();
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[2]);
			int id = 0;
			try {
				id = (int)type.getField(TEST_FIELD).getInt(null);
			} catch (Exception e) {
				Log.d(TAG," problem with getting value from static field",e);
			}
			renderer.setId(id);
			return TaskImageFactory.getImageView(type, context, renderer);
		}
	}

	public BaseTaskImageView getTestAnswer() {
		switch (mode) {
		case TRANSFORM_VIEW_MODE:
			return trans.getTestAnswerImage();
		default:
			renderer = new Renderer();
			renderer.setColorFlag(colorFlag);
			renderer.setColor(colors[3]);
			int id = 0;
			try {
				id = (int)type.getField(TEST_ANSWER_FIELD).getInt(null);
			} catch (Exception e) {
				Log.d(TAG," problem with getting value from static field",e);
			}
			renderer.setId(id);
			return TaskImageFactory.getImageView(type, context, renderer);
		}
	}

	public ArrayList<BaseTaskImageView> getVariants() {
		
		switch (mode) {
		case TRANSFORM_VIEW_MODE:
			return trans.getVariants();
		default:
			ArrayList<Integer> var = null;
			try {
				var = (ArrayList<Integer>)type.getField(VARIANTS_FIELD).get(null);
			} catch (Exception e) {
				Log.d(TAG," problem with getting value from static field"+type,e);
			}
			ArrayList<BaseTaskImageView> res = new ArrayList<BaseTaskImageView>();
			for (int i = 0; i < var.size(); i++) {
				renderer = new Renderer();
				renderer.setColorFlag(colorFlag);
				renderer.setColor(colors[(i + 4) % COLORS_SIZE]);
				renderer.setId(var.get(i));
				res.add(TaskImageFactory.getImageView(type, context, renderer));
			}
			return res;
		}
	}

}
