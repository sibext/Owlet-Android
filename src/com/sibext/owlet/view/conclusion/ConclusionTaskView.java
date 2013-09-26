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
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;

public abstract class ConclusionTaskView extends BaseTaskImageView{

	public static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;
	
	public ConclusionTaskView(Context context, Renderer renderer) {
		super(context, renderer);
	}

}
