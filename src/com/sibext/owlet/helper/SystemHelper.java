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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;

public class SystemHelper {

	public static String getVersion(Context context) {
		try {
			ComponentName componentName = new ComponentName(context, Activity.class);
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(componentName.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	public static boolean isLandscapeOrientation(Context context){
		return (context.getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE);
	}

	public static boolean isPortretOrientation(Context context){
		return (context.getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_PORTRAIT);
	}
}
