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

import java.lang.reflect.Constructor;


import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sibext.owlet.R;
import com.sibext.owlet.helper.Log;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.tasks.Task;
import com.sibext.owlet.tasks.TaskParamsContainer;

public class CompareTask extends Task{
	private static final String TAG = "CompareTaskSwitch";
	
	private AbstractCompareTask task;

	public CompareTask(Context context, TaskParamsContainer container) {
		super(COMPARE_TASK_TYPE, COMPARE_TASK_LEGTH_DEFAULT,
				R.string.task_types_message_compare, context, container);
		int taskChoice = rand.nextInt(getCompareTaskList().length);
		task = getCompareTask(getCompareTaskList()[taskChoice], context, container);
	}

	@Override
	protected Class<?>[] getCollectionOfView() {
		return task.getCollectionOfView();
	}

	@Override
	public ViewGroup taskBuildView(Context context, ViewGroup layout,
			RelativeLayout root) {
		return task.taskBuildView(context, layout, root);
	}

	@Override
	public int getEveryFailCountLimit() {		
		return task.getEveryFailCountLimit();
	}

	@Override
	public void playTaskMessage(MediaPlayerSingleton sound) {
		task.playTaskMessage(sound);
	}
	
	private AbstractCompareTask getCompareTask(Class<?> clazz,Context context,TaskParamsContainer container){
		try {
			Constructor<?> c = clazz.getConstructor(Context.class,TaskParamsContainer.class);
			Object obj = c.newInstance(context,container);
			return (AbstractCompareTask)obj;
		} catch (Exception e) {
			Log.e(TAG, "Not found required constructor for task image view", e);
		}
		return null;
	}
	
	private Class<?>[] getCompareTaskList(){
		return new Class<?>[]{
				CubeCompareTask.class,
				PictureCompareTask.class,
		};	
	}	
	
	@Override
	public int getImageGroupId() {
		return task.getImageGroupId();
	}
	
	@Override
	public void setImageGroupId(int id) {
		task.setImageGroupId(id);
	}
	
	@Override
	public void setFailCount(int failCount) {
		task.setFailCount(getFailCount());
	}
}
