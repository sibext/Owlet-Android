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

package com.sibext.owlet.model.statistic;

import java.util.HashMap;

public class UsersModel {
	private HashMap<String, TaskModel> tasks;

	public UsersModel() {
		tasks = new HashMap<String, TaskModel>();
	}

	public TaskModel getTask(String key) {
		return tasks.get(key);
	}

	public void addTask(String key, int low_correct, int low_attempt,
			int med_correct, int med_attempt, int hight_correct,
			int hight_attempt) {
		TaskModel task = new TaskModel(low_correct, low_attempt, med_correct,
				med_attempt, hight_correct, hight_attempt);
		tasks.put(key, task);
	}
	public void addTask(String key, TaskModel task){
		tasks.put(key, task);
	}
}
