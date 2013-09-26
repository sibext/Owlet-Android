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

public class Log {
	private static final boolean ENABLED = false;

	public static void e(String tag, String msg, Throwable tr) {
			android.util.Log.e(wrapTag(tag), msg, tr);
	}

	public static void e(String tag, String msg) {
		if (isEnable(tag))
			android.util.Log.e(wrapTag(tag), msg);
	}

	public static void w(String tag, String msg) {
		if (isEnable(tag))
			android.util.Log.w(wrapTag(tag), msg);
	}

	public static void i(String tag, String msg) {
		if (isEnable(tag))
			android.util.Log.i(wrapTag(tag), msg);
	}

	public static void d(String tag, String msg) {
		if (isEnable(tag))
			android.util.Log.d(wrapTag(tag), msg);
	}

	/**
	 * Wrapper for performance debug logging call
	 * 
	 * @param tag
	 *            the classic Android tag
	 * @param msg
	 *            the log message
	 */
	public static void d(final String tag, final StringBuilder msg) {
		d(tag, msg.toString());
	}

	private static String wrapTag(String tag) {
		return tag;
	}

	private static boolean isEnable(String tag) {
		return ENABLED;
	}
}
