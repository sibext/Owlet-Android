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

package com.sibext.owlet.model;

import android.net.Uri;

public class TableStatistic extends Base {
	public static String TABLE_NAME = "Statistic";
	public static String TABLE_FIELD_ID = "_id";
	public static String TABLE_FIELD_TASK_TYPE = "taskType";
	public static String TABLE_FIELD_ATTEMPTS_COUNT = "attemptsCount";
	public static String TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS = "countOfCorrectAnswers";
	public static String TABLE_FIELD_TASK_COMPLEX = "complex";
	public static final Uri CONTENT_URI = Uri.parse("content://" + Base.URI + "/" + TableStatistic.TABLE_NAME);
}
