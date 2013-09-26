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
import java.util.Random;

import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageView;
import com.sibext.owlet.view.renderer.ConclusionRenderer;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;

public abstract class TransformView {
	protected Context context;
	protected Random rand;
	protected int w = 100;
	protected int h = 100;
	protected boolean colorFlag;
	protected int[] colors;
	protected BaseTaskImageView[] example;
	protected BaseTaskImageView[] test;

	
	abstract BaseTaskImageView getExampleImage();
	abstract BaseTaskImageView getExampleAnswerImage();
	abstract BaseTaskImageView getTestImage();
	abstract BaseTaskImageView getTestAnswerImage();
	abstract ArrayList<BaseTaskImageView> getVariants();
	
	public TransformView(Context context,ConclusionRenderer conRenderer) {
		this.context = context;
		rand = new Random();
		colorFlag = conRenderer.getColorFlag();
		colors = conRenderer.getColors();
	}
	
}
