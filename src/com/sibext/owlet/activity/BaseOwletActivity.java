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

package com.sibext.owlet.activity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.sibext.android.activity.CrashCatcherActivity;
import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.activity.adapter.PopupMenuAdapter;
import com.sibext.owlet.activity.adapter.PopupMenuAdapter.PopupMenuItem;
import com.sibext.owlet.database.DatabaseProvider;
import com.sibext.owlet.factory.WindowFactory;
import com.sibext.owlet.factory.window.IListPopupWindow;
import com.sibext.owlet.helper.Log;
import com.sibext.owlet.model.TableUsers;

/**
 * The base activity with menu and metrics
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public abstract class BaseOwletActivity extends CrashCatcherActivity {
	private static final String TAG = "BaseOwletActivity";
	private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
	public static final String RU = "ru";
	public static final String EN = "en";
	public static final String ID = "id";

	protected int id;
	protected static boolean exitFlag = false;

	private IListPopupWindow popupWindow;
	private DisplayMetrics metrics;
	private boolean popupWindowAlreadyShown;

	protected abstract int getMenuId();
	protected abstract void onMainMenuSelected(int id);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPopUpMenu();
		initLegacyMenu();
		metrics = new DisplayMetrics();
	}

	protected OnClickListener menuOncClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (OwletApplication.VERSION < Build.VERSION_CODES.HONEYCOMB) {
				openOptionsMenu();
			} else {
				if (!popupWindowAlreadyShown) {
					popupWindowAlreadyShown = true;
					popupWindow.setAnchorView(v);
					popupWindow.show();
				} else {
					popupWindow.dismiss();
					popupWindowAlreadyShown = false;
				}
			}
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onMainMenuSelected(item.getItemId());
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(getMenuId(), menu);
		return true;
	}

	protected void onAboutActivity(){
		Intent intent = new Intent(self(), AboutActivity.class);
		startActivity(intent);
	}

	protected void onAuthorizationActivity() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_are_you_sure_switch_account)
				.setTitle(R.string.dialog_are_you_sure_title)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								exit();
								Intent intent = new Intent(self(), MainActivity.class);
								startActivity(intent);
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		builder.create().show();
	}

	protected void onStatisticsActivity() {
		Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
		intent.putExtra(ID, id);
		startActivity(intent);
		finish();
	}

	protected void onSettingsActivity(){
		Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
		intent.putExtra(ID, id);
		startActivity(intent);
		finish();
	}

	protected void onExit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_are_you_sure_quit)
				.setTitle(R.string.dialog_are_you_sure_title)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								exit();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		builder.create().show();
	}

	private void exit() {
		ContentValues values = new ContentValues();
		values.put(TableUsers.TABLE_FIELD_LOGIN, 0);
		values.put(DatabaseProvider.KEY_OF_TABLE_NAME, TableUsers.TABLE_NAME);
		StringBuilder where = new StringBuilder();
		where.append(TableUsers.TABLE_FIELD_ID);
		where.append(" = ");
		where.append(id);
		getContentResolver().update(DatabaseProvider.CONTENT_URI, values, where.toString(), null);
		exitFlag = true;
		finish();
	}

	private void initLegacyMenu() {
		getLayoutInflater().setFactory(new Factory() {
			@Override
			public View onCreateView(String name, Context context, AttributeSet attrs) {
				if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {
					try { 
						LayoutInflater f = LayoutInflater.from(context);//getLayoutInflater();
						final View view = f.createView(name, null, attrs);
						//MenuItem v = (MenuItem)(f.createView(name, null, attrs));
						
						view.post(new Runnable() {
							public void run() {
								view.setBackgroundColor(Color.WHITE);
								setTextColor(view);
								Log.d(TAG, "at initLegacyMenu:run() was set background as WHITE");
							}
						});
						return view;
					} catch (InflateException e) {
						Log.e(TAG, "Can't create the icon menu view", e);
					} catch (ClassNotFoundException e) {
						Log.e(TAG, "Can't create the icon menu view", e);
					}
				}
				return null;
			}
		});
	}
	
	private void setTextColor(final View view) {
        try {
            final Method setTextColor = view.getClass()
                    .getMethod("setTextColor", int.class);
            setTextColor.invoke(view, Color.BLACK);
        } catch (Exception e) {
            Log.e(TAG, 
                   "Error while setting the text color: "
                                        + e.getMessage(), e);
        }
    }
	private void  initPopUpMenu() {
		popupWindow = WindowFactory.getPopupWindow(getBaseContext());
		popupWindow.setBackgroundDrawable(R.drawable.menu_item_background_normal);
		final List<PopupMenuItem> menuItems = new ArrayList<PopupMenuItem>();
		XmlResourceParser parser = getBaseContext().getResources().getXml(getMenuId());
		try {
			while (parser.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (parser.getEventType() == XmlResourceParser.START_TAG) {
					final String name = parser.getName();
					if ("item".equals(name)) {
						int id = parser.getAttributeResourceValue(ANDROID_NAMESPACE, "id", 0);
						int icon = parser.getAttributeResourceValue(ANDROID_NAMESPACE, "icon", 0);
						int title = parser.getAttributeResourceValue(ANDROID_NAMESPACE, "title", 0);
						menuItems.add(new PopupMenuItem(id, icon, title));
						Log.d(TAG, "Item =>"+ Integer.toHexString(id) + ", " + Integer.toHexString(icon) + ", " + Integer.toHexString(title));
					}
				}
				parser.next();
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, "Can't read menu", e);
		} catch (IOException e) {
			Log.e(TAG, "Can't read menu", e);
		}
		parser.close();
		popupWindow.setAdapter(new PopupMenuAdapter(self(), menuItems));
		popupWindow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onMainMenuSelected(menuItems.get(position).resId);
				popupWindow.dismiss();
			}
			
		});
	}

	protected int getScreenWidth(){
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	protected int getScreenHeight(){
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	protected void setLanguageFromPref(){
		OwletApplication app= (OwletApplication)getApplicationContext();
		SharedPreferences pref = app.getSettings(); 				
		String langKey = getResources().getString(R.string.language_key);
		String lang = pref.getString(langKey, "ru");
		Log.d(TAG,"at setLanguageFromPref: Current language is "+lang);
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration configuration = new Configuration();
		configuration.locale = locale;
		getBaseContext().getResources().updateConfiguration(configuration, null);
	}
	
	protected BaseOwletActivity self() {
		return this;
	}
}
