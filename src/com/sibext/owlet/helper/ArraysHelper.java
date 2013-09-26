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

package com.sibext.owlet.helper;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.os.Build;

public class ArraysHelper {

	/**
	 * Copies elements from original into a new array, from indexes start (inclusive) to end (exclusive). 
	 * The original order of elements is preserved. If end is greater than original.length, 
	 * the result is padded with the value null.
	 * @param orig the original array
	 * @param start the start index, inclusive
	 * @param end the end index, exclusive
	 * @return the new array
	 */
	@SuppressLint("NewApi")
	public static String[] copyOfRange(String[] orig, int start, int end) {
		int curVersion = android.os.Build.VERSION.SDK_INT;
		if (curVersion < Build.VERSION_CODES.GINGERBREAD) {
			String[] res = new String[end - start];

			for (int i = 0; i < res.length; i++) {
				res[i] = orig[i + start];
			}
			return res;
		} else {
			return Arrays.copyOfRange(orig, start, end);
		}
	}
}
