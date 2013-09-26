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

public class TaskModel {
	public int low_correct;
	public int low_attempt;
	public int med_correct;
	public int med_attempt;
	public int hight_correct;
	public int hight_attempt;
	
	public TaskModel(int low_correct, int low_attempt,
			int med_correct, int med_attempt, int hight_correct,
			int hight_attempt) {
		this.low_correct = low_correct;
		this.low_attempt = low_attempt;
		this.med_correct = med_correct;
		this.med_attempt = med_attempt;
		this.hight_correct = hight_correct;
		this.hight_attempt = hight_attempt;
	}
	
	public TaskModel(){	
		this.low_correct = 0;
		this.low_attempt = 0;
		this.med_correct = 0;
		this.med_attempt = 0;
		this.hight_correct = 0;
		this.hight_attempt = 0;
	}
}
