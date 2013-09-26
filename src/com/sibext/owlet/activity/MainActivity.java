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

import java.util.Locale;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.database.DatabaseProvider;
import com.sibext.owlet.helper.MessageHelper;
import com.sibext.owlet.helper.SystemHelper;
import com.sibext.owlet.model.TableUsers;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends BaseOwletActivity implements OnClickListener{

	private EditText nameField, ageField;
	private String nameFieldValue, ageFieldValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	OwletApplication app = (OwletApplication)getApplication();
    	SharedPreferences pref = app.getSettings();
    	if(pref.contains(OwletApplication.LANGUAGE_NAME)){
    		String lang = pref.getString(OwletApplication.LANGUAGE_NAME, RU);
    		Locale locale = new Locale(lang);
    		Locale.setDefault(locale);
    		Configuration configuration = new Configuration();
    		configuration.locale = locale;
    		getBaseContext().getResources().updateConfiguration(configuration, null);
    	} else {
    		Configuration configuration = getResources().getConfiguration();
    		Locale locale = configuration.locale;
    		String lang = locale.getLanguage();
    		if(lang.equalsIgnoreCase(RU)){
    			lang = RU;
    		} else {
    			lang = EN;
    		}
    		Editor editor = pref.edit();
			editor.putString(OwletApplication.LANGUAGE_NAME, lang);
			editor.commit();
    	}
    	
        super.onCreate(savedInstanceState);
        if (!autoLogin()) {
        	setContentView(R.layout.task_main);
            nameField = (EditText) findViewById(R.id.main_nameField);
    		ageField = (EditText) findViewById(R.id.main_ageField);
    		Button authButton = (Button) findViewById(R.id.main_enter_button);
    		authButton.setOnClickListener(this);
    		
    		ImageView field = (ImageView)findViewById(R.id.field);
			field.post(new Runnable() {
				@Override
				public void run() {
	    			ImageView field = (ImageView)findViewById(R.id.field);
	    			ImageView leftField = (ImageView)findViewById(R.id.field1);
	    			ImageView rightField = (ImageView)findViewById(R.id.field2);
					int w = field.getWidth();
					int wScreen = getScreenWidth();
					// padding - величина отступа для подгонки картинок сбоку к центральной
					int padding = (int)((3*w-wScreen)/2)-1;
					leftField.setPadding(0, 0,padding, 0);
					rightField.setPadding(padding, 0, 0, 0);
				}
			});
			// Clouds and sun size changing
			/**
			 * Массив коэффициентов который определяют ширину каждого облака, относительно ширины экрана.
			 */
    		double[] alpha = new double[4];
    		alpha[0]=0.3;
    		alpha[1]=0.27;
    		alpha[2]=0.22;
    		alpha[3]=0.27;
    		/**
    		 * Пропорции размеров облаков. При изменении ширины высота увеличивается пропорционально. 
    		 */
    		double[] beta = new double[4];
    		beta[0]=0.5;
    		beta[1]=0.35;
    		beta[2]=0.45;
    		beta[3]=0.5;
    		int w = getScreenWidth();
    		if(SystemHelper.isPortretOrientation(this)){
        		ImageView sun = (ImageView)findViewById(R.id.sun);
    			sun.setLayoutParams(new LinearLayout.LayoutParams((int)(w*0.15),(int)(w*0.15)));
    			ImageView[] clouds = new ImageView[4];
    			clouds[0] = (ImageView)findViewById(R.id.cloud1);
    			clouds[1] = (ImageView)findViewById(R.id.cloud2);
    			clouds[2] = (ImageView)findViewById(R.id.cloud3);
    			clouds[3] = (ImageView)findViewById(R.id.cloud4);
    			for(int i=0;i<4;i++){
    				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)clouds[i].getLayoutParams();
    				params.width= (int)(w*alpha[i]);
    				params.height = (int)((int)(w*alpha[i])*beta[i]);
    				//clouds[i].setLayoutParams(params);
    			}
    		} else if(SystemHelper.isLandscapeOrientation(this)){
    			ImageView sun = (ImageView)findViewById(R.id.sun);
    			RelativeLayout.LayoutParams sunPar =  new RelativeLayout.LayoutParams((int)(w*0.12),(int)(w*0.12));    			
    			sunPar.topMargin = (int)(getScreenHeight()*0.05);
    			sunPar.leftMargin = (int)(getScreenWidth()*0.8);
    			sun.setLayoutParams(sunPar);
    			final ImageView cloud = (ImageView)findViewById(R.id.cloud1);
    			cloud.post(new Runnable() {
					@Override
					public void run() {
		    			ImageView leftCloud = (ImageView)findViewById(R.id.cloud2);
		    			ImageView rightCloud = (ImageView)findViewById(R.id.cloud3);
						int w = cloud.getWidth();
						int wScreen = getScreenWidth();
						// padding - величина отступа для подгонки картинок сбоку к центральной
						int padding = (int)((3*w-wScreen)/2)-1;
						leftCloud.setPadding(0, 0,padding, 0);
						rightCloud.setPadding(padding, 0, 0, 0);
					}
				});
    			
    		}
    		
		}
    }

	private boolean autoLogin() {
		String selection = TableUsers.TABLE_FIELD_LOGIN + " = 1";
		String fields[] = new String[] {
				TableUsers.TABLE_NAME, TableUsers.TABLE_FIELD_ID
			};
		boolean logon = false;
		Cursor finding = getContentResolver().query(DatabaseProvider.CONTENT_URI, 
				fields, selection, null, null);
		if (finding.getCount() == 1) {
			finding.moveToFirst();
			int columnIndexIdField = finding.getColumnIndex(TableUsers.TABLE_FIELD_ID);
			int id = finding.getInt(columnIndexIdField);
			startScreenOfTask(id);
			logon = true;
		}
		finding.close();
		return logon;
	}

	private void startScreenOfTask(int id) {
		Intent screenOfTask = new Intent(getApplicationContext(), ScreenOfTaskActivity.class);
		screenOfTask.putExtra(ID, id);
		startActivity(screenOfTask);
		finish();
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_enter_button:{
			nameFieldValue = nameField.getText().toString().trim();
			ageFieldValue = ageField.getText().toString().trim();
			if ( nameFieldValue.length() != 0 
					&& ageFieldValue.length() != 0 ){
				int age = Integer.parseInt(ageFieldValue);
				if(age>90){
					MessageHelper.showToast(getBaseContext(), R.string.screen_authorization_error_age);
					return;
				}
				//
				String selection = TableUsers.TABLE_FIELD_NAME +
						" = '"  + nameFieldValue +
						"' AND " + TableUsers.TABLE_FIELD_AGE + 
						" = " + ageFieldValue;
				String fields[] = new String[] {
						TableUsers.TABLE_NAME, TableUsers.TABLE_FIELD_ID
					};
				
				Cursor finding = getContentResolver().query(DatabaseProvider.CONTENT_URI, fields, selection, null, null);
				if ( finding.getCount() == 0 ) {
					// Add new user
					ContentValues values = new ContentValues();
	                values.put(TableUsers.TABLE_FIELD_NAME, nameFieldValue);
	                values.put(TableUsers.TABLE_FIELD_AGE, ageFieldValue);
	                values.put(TableUsers.TABLE_FIELD_LOGIN, 1);
	                values.put(DatabaseProvider.KEY_OF_TABLE_NAME, TableUsers.TABLE_NAME);
	                getContentResolver().insert(DatabaseProvider.CONTENT_URI, values);
				}
				else {
					ContentValues values = new ContentValues();
					values.put(TableUsers.TABLE_FIELD_LOGIN, 1);
					values.put(DatabaseProvider.KEY_OF_TABLE_NAME, TableUsers.TABLE_NAME);
					StringBuilder where = new StringBuilder();
					where.append(TableUsers.TABLE_FIELD_NAME);
					where.append(" = '");
					where.append(nameFieldValue);
					where.append("' AND ");
					where.append(TableUsers.TABLE_FIELD_AGE);
					where.append(" = ");
					where.append(ageFieldValue);
					getContentResolver().update(DatabaseProvider.CONTENT_URI, values, where.toString(), null);
				}				
				finding.close();
				finding = getContentResolver().query(
						DatabaseProvider.CONTENT_URI, fields, selection, null, null);
				finding.moveToFirst();				
				int idColumnIndex = finding.getColumnIndex(TableUsers.TABLE_FIELD_ID);
				int id = finding.getInt(idColumnIndex);
				finding.close();
				startScreenOfTask(id);
			}
			else{
				MessageHelper.showToast(getBaseContext(), R.string.screen_authorization_error_msg);
			}
			break;
		}
		}
	}

	@Override
	protected void onMainMenuSelected(int id) {
		switch (id) {
		case R.id.menu_exit:
			finish();
			break;
		}
	}

	@Override
	protected int getMenuId() {
		return R.menu.login;
	}

}
