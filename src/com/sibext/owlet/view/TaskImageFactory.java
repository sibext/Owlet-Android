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

package com.sibext.owlet.view;

import java.lang.reflect.Constructor;


import android.content.Context;

import com.sibext.owlet.helper.Log;
import com.sibext.owlet.view.conclusion.TransformView;
import com.sibext.owlet.view.renderer.ConclusionRenderer;
import com.sibext.owlet.view.renderer.Renderer;

public class TaskImageFactory {
	public static final String TAG = "TaskImageFactory";

	public static BaseTaskImageView getImageView(Class<?> clazz, Context context, Renderer renderer) {
		try {
			Constructor<?> c = clazz.getConstructor(Context.class, Renderer.class);
			Object obj = c.newInstance(context, renderer);
			return (BaseTaskImageView)obj;
		} catch (Exception e) {
			Log.e(TAG, "Not found required constructor for task image view", e);
		}
		return null;
	}
	
	public static TransformView getTransformeView(Class<?> clazz, Context context,ConclusionRenderer renderer) {
		try {
			Constructor<?> c = clazz.getConstructor(Context.class,ConclusionRenderer.class);
			Object obj = c.newInstance(context,renderer);
			return (TransformView)obj;
		} catch (Exception e) {
			for(int i=0; i < clazz.getConstructors().length;i++){
				Log.e(TAG,clazz.getConstructors()[i]+"\n");
			}
			Log.e(TAG, "Not found required constructor for task image view, class:"+clazz+clazz.getConstructors(), e);
		}
		return null;
	}
}
