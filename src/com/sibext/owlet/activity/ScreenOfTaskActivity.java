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

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

import com.sibext.owlet.OwletApplication;
import com.sibext.owlet.R;
import com.sibext.owlet.database.DatabaseProvider;
import com.sibext.owlet.helper.Log;
import com.sibext.owlet.helper.SystemHelper;
import com.sibext.owlet.model.TableStatistic;
import com.sibext.owlet.model.TableUsers;
import com.sibext.owlet.player.MediaPlayerSingleton;
import com.sibext.owlet.tasks.ConclusionTask;
import com.sibext.owlet.tasks.MagicSquareTask;
import com.sibext.owlet.tasks.SystematisationTask;
import com.sibext.owlet.tasks.Task;
import com.sibext.owlet.tasks.TaskParamsContainer;
import com.sibext.owlet.tasks.compare.CompareTask;
import com.sibext.owlet.view.SovenokLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScreenOfTaskActivity extends BaseOwletActivity implements Observer {
	private static final String TAG = "ScreenOfTask";

	public static final int WIDTH_INDEX = 0;
	public static final int HEIGTH_INDEX = 1;
	public static final int MARGIN_TOP_INDEX = 2;
	public static final int MARGIN_LEFT_INDEX = 3;
	public static final int AGE_FOR_MEDIUM_LEVEL = 6;
	public static final int AGE_FOR_HIGH_LEVEL = 10;
	public static final int MAX_COUNT_FOR_FUN = 2;
	
	private static final int FAIL_NUM_FOR_COMPLEX_DOWN = 2;
	private static final int CORRECT_NUM_FOR_COMPLEX_UP = 2;
	
	private static final String CURRENT_TASK_TYPE = "taskType";
	private static final String CORRECT_ANSWER_COUNT = "correctAnswer";
	private static final String INCORRECT_ANSWER_COUNT = "incorrectAnswer";
	private static final String FAIL_COUNT = "failCount";
	private static final String IMAGE_GROUP_ID = "imageGroupId";
	private static final String COMPLEX= "complex";

	/**
	 * В статическом bondle хранится текущая конфигурация задачи. Не всегда понятно, что лежит в bondle,
	 *  который приходит в метод onCreate. Поэтому сделал свой, его контролировать легче.  
	 */
	private static Bundle bundle = new Bundle();
	private static boolean instantWasSaved = false;
	public static int statusBarHeigth;

	private Task currentTask;
	private String name;
	private String sovenokMessage;
	private String age;
	private int resultCount = 0;
	private int correctAnswerCount = 0;
	private int correctAnswerLimit;
	private int incorrectAnswerCount = 0;
	private int incorrectAnswerLimit;
	private int failCount = 0;
	private int currentTaskId = 1;
	private int currentImageGroupId = -1;
	private int currentComplex = Task.COMPLEXITY_LOW;
	private int clickOnOwletCount = 0;
	
	private volatile boolean onceLoadCreate = false;

	private OwletApplication application;
	private SharedPreferences set;
	private MediaPlayerSingleton sound;

	private TextView textView;
	private ImageView lampView;
	private SovenokLayout sovenokImg;
	
	private LinearLayout nameAndAgePlace;
	private TableLayout sovenokAndTextLayout;
	private ImageView tree_img;
	private LinearLayout task_textLayout;
	private ViewGroup task;
	private RelativeLayout task_board;
	private RelativeLayout sovenokAndTree;
	private int[] boardParams;
	private TextView text; 


	@Override
	public void update(Observable observable, Object data) {
		boolean result = (Boolean) data;
		
		//Удаление вьюх предыдущей задачи
		removeViewsFromPrevioslyTask(currentTask);

		lampView.setImageResource(R.drawable.lampon);
		if (result) {
			correctAnswerCount++;
			resultCount++;
			textView.setText(R.string.dialog_title_correct_answer);
			sovenokImg.startRandomAnimation();
			sound.playSomeCorrectAnswerSound();
			if (currentTask.getEveryFailCountLimit() < currentTask.getFailCount())	{
				sovenokMessage = getResources().getString(R.string.screen_of_task_bad_sovenok_message);
				downComplexity(currentTask.getType());
			} else if (currentTask.getFailCount()==0){
				sovenokMessage = getResources().getString(R.string.screen_of_task_excellent_sovenok_message);
				upperComplexity(currentTask.getType());
			} else {
				sovenokMessage = getResources().getString(R.string.screen_of_task_good_sovenok_message);
			}
		} else {
			incorrectAnswerCount++;
			resultCount--;
			textView.setText(R.string.dialog_title_incorrect_answer);
			sovenokImg.startRandomAnimation();
			sound.playSomeIncorrectAnswerSound();
			sovenokMessage = getResources().getString(R.string.screen_of_task_bad_sovenok_message);
			downComplexity(currentTask.getType());
		}
		Log.d(TAG,"at update: the result previously task is " + data+
				"\ncorAnswerCount = "+correctAnswerCount+"\nincorAnswerCount = "+incorrectAnswerCount);
		if((correctAnswerCount>=correctAnswerLimit)||(incorrectAnswerCount>=incorrectAnswerLimit)){
			currentTaskId++;
			resultCount = 0;
			if (currentTask.getEveryFailCountLimit() < currentTask.getFailCount())	{
				sovenokMessage = getResources().getString(R.string.screen_of_task_bad_sovenok_message);
				downComplexity(currentTask.getType());
			} else if (currentTask.getFailCount()==0)	{
				sovenokMessage = getResources().getString(R.string.screen_of_task_excellent_sovenok_message);
				upperComplexity(currentTask.getType());
			} else {
				sovenokMessage = getResources().getString(R.string.screen_of_task_good_sovenok_message);
			}
			correctAnswerCount=0;
			incorrectAnswerCount=0;
		}
		currentTaskId = currentTaskId%getTaskList().length;
		recordingResult(result,currentTask.getType(),currentTask.getComplexity());
		
		onStart();
		onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		application = (OwletApplication)getApplicationContext();
		set = application.getSettings();
		setLanguageFromPref();
		super.onCreate(savedInstanceState);
		sound = MediaPlayerSingleton.getInstance(getApplicationContext()); 
		correctAnswerLimit = set.getInt(OwletApplication.CORRECT_ANSWER_LIM_NAME, 1);
		incorrectAnswerLimit = set.getInt(OwletApplication.INCORRECT_ANSWER_LIM_NAME, 1);
		//
		id = getIntent().getIntExtra(ID, 0);
		//
		String[] projectionTableUsers = { TableUsers.TABLE_NAME,
				TableUsers.TABLE_FIELD_ID, TableUsers.TABLE_FIELD_NAME,
				TableUsers.TABLE_FIELD_AGE };
		String selection = TableUsers.TABLE_FIELD_ID + "=" + id;
		Cursor cursorTableUsers = getContentResolver().query(
				DatabaseProvider.CONTENT_URI, projectionTableUsers, selection,
				null, null);
		cursorTableUsers.moveToFirst();
		name = cursorTableUsers.getString(cursorTableUsers
				.getColumnIndex(projectionTableUsers[2]));
		age = cursorTableUsers.getString(cursorTableUsers
				.getColumnIndex(projectionTableUsers[3]));
		cursorTableUsers.close();
		//
		createBasicRecord();
		//
		setContentView(R.layout.task);
		Button menu = (Button)findViewById(R.id.screen_of_task_menu_button);
		menu.setOnClickListener(menuOncClickListener);
		Button volume = (Button)findViewById(R.id.screen_of_task_volume_button);
		boolean volOn = set.getBoolean(OwletApplication.VOLUME_NAME, true);
		if(volOn){
			volume.setBackgroundResource(R.drawable.volume_on);
		} else {
			volume.setBackgroundResource(R.drawable.volume_off);
		}
		
		volume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OwletApplication application = (OwletApplication)getApplication();
				Button vol = (Button)v;
				boolean volOn = set.getBoolean(OwletApplication.VOLUME_NAME, true);
				if(volOn){
					application.editBoolInSettings(OwletApplication.VOLUME_NAME, false);
					vol.setBackgroundResource(R.drawable.volume_off);
				} else {
					application.editBoolInSettings(OwletApplication.VOLUME_NAME, true);
					vol.setBackgroundResource(R.drawable.volume_on);
				}
			}
		});
		//
		textView = (TextView) findViewById(R.id.task_text);
		nameAndAgePlace = (LinearLayout) findViewById(R.id.task_nameAndage_place);
		sovenokAndTextLayout = (TableLayout) findViewById(R.id.sovenok_and_text);
		tree_img = (ImageView) findViewById(R.id.task_img_tree);
		lampView = (ImageView) findViewById(R.id.lamp);
		task_textLayout = (LinearLayout) findViewById(R.id.task_textLayout);
		task = (ViewGroup) findViewById(R.id.task_content);
		task_board = (RelativeLayout) findViewById(R.id.task_board);
		sovenokAndTree = (RelativeLayout)findViewById(R.id.task_img_sovenok);

		sovenokImg = new SovenokLayout(this);
		sovenokAndTree.addView(sovenokImg);
		sovenokImg.startRandomAnimation();

		boardParams = new int[4];

		boardParams = settingOfTaskScreen(
				nameAndAgePlace, sovenokAndTextLayout, sovenokImg,
				task_textLayout, task, task_board, sovenokAndTree,tree_img,lampView);
		nameAndAgePlace.setBackgroundColor(Color.argb(50, 0, 0, 0));
		text = (TextView) findViewById(R.id.task_nameAndage_text);

		
		//
		sound.play(R.raw.screen_of_task_default_sovenok_message);
		onceLoadCreate = true;
		
		Log.d(TAG,"onCreate was call, variable 'onceLoadCreate' is " + onceLoadCreate);
	}
	
	@Override
	protected void onStart() {
		Log.d(TAG,"at onStart: variable 'onceLoadCreate' is "+onceLoadCreate);
		if(onceLoadCreate){
			sovenokMessage = getResources().getString(R.string.screen_of_task_default_sovenok_message);
			onceLoadCreate = false;
		}
		Log.d(TAG,"at onStart: variable 'onceLoadCreate' is "+onceLoadCreate);
		if (instantWasSaved) {
			currentTaskId = bundle.getInt(CURRENT_TASK_TYPE);
			correctAnswerCount = bundle.getInt(CORRECT_ANSWER_COUNT);
			incorrectAnswerCount = bundle.getInt(INCORRECT_ANSWER_COUNT);
			failCount = bundle.getInt(FAIL_COUNT);
			currentImageGroupId = bundle.getInt(IMAGE_GROUP_ID);
			currentComplex = bundle.getInt(COMPLEX);
			Log.d(TAG,"at onStart(): FROM bundle was TAKE values: "
							+ currentTaskId + " " + correctAnswerCount + " "
							+ incorrectAnswerCount + " " + failCount
							+ " (currentTaskId,correctAnswerCount," 
							+ "incorrectAnswerCount,failCount)");
			instantWasSaved = false;
		} else {
			Log.d(TAG, "at onStart(): in bundle was saved nothing, currentImageGroupId = -1 for" +
					"random generating new imageGroupId");
			currentImageGroupId = -1;
		}
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		removeViewsFromPrevioslyTask(currentTask);

		String fmt = getResources().getText(R.string.item_shop).toString();
		text.setText(name + ", "+MessageFormat.format(fmt, Integer.parseInt(age)));
		
		TaskParamsContainer container = new TaskParamsContainer();
		container.setTextView(textView);
		container.setBoardParams(boardParams);
		container.setWidthScreen(getScreenWidth());
		container.setHeightScreen(getScreenHeight());
		//container.setOrientation(getScreenOrientation());
		container.setObserver(this);
		container.setSound(sound);
		container.setComplexity(currentComplex);
		
		currentTask = taskFactory(currentTaskId, this, container);
		
		currentTask.setFailCount(failCount);
		
		textView.setText(sovenokMessage);
		findViewById(R.id.task_img_sovenok).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						clickOnOwletCount += 1;
						if (clickOnOwletCount <= MAX_COUNT_FOR_FUN) {
							textView.setText(currentTask.getInfoTextId());
							currentTask.playTaskMessage(sound);
							lampView.setImageResource(R.drawable.lampoff);
						} else {
							clickOnOwletCount = 0;
							sound.playSomeFunSound();
							sovenokImg.startRandomAnimation();
						}
					}
				});
		RelativeLayout root = (RelativeLayout) findViewById(R.id.task_root);

		currentTask.setImageGroupId(currentImageGroupId);
		currentImageGroupId = currentTask.getImageGroupId();
		task = currentTask.taskBuildView(this,task, root);

		Log.d(TAG,"at onResume: curruent Complexity is "+ currentTask.getComplexity());
		
		root.post(new Runnable() {
			@Override
			public void run() {
				statusBarHeigth = getStatusBarHeigth();
			}
		});
	}


	@Override
	protected void onPause() {
		bundle.putInt(CURRENT_TASK_TYPE, currentTaskId);
		bundle.putInt(CORRECT_ANSWER_COUNT, correctAnswerCount);
		bundle.putInt(INCORRECT_ANSWER_COUNT, incorrectAnswerCount);
		bundle.putInt(FAIL_COUNT, currentTask.getFailCount());
		bundle.putInt(IMAGE_GROUP_ID, currentTask.getImageGroupId());
		bundle.putInt(COMPLEX, currentComplex);
		Log.d(TAG,"at onPause(): IN bundle was SAVED values: "
				+ currentTaskId + " " + correctAnswerCount + " "
				+ incorrectAnswerCount + " " + failCount
				+ " (currentTaskId,correctAnswerCount," 
				+ "incorrectAnswerCount,failCount)");
		instantWasSaved = true;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "In onDestroy " + exitFlag);
		if (exitFlag) {
			instantWasSaved = false;
			exitFlag = false;
			bundle.clear();
		}
		super.onDestroy();
	}

	@Override
	protected void onMainMenuSelected(int id) {
		switch (id) {
		case R.id.menu_exit: {
			onExit();
			break;
		}
		case R.id.menu_logout: {
			onAuthorizationActivity();
			break;
		}
		case R.id.menu_settings: {
			onSettingsActivity();
			break;
		}
		case R.id.menu_about: {
			onAboutActivity();
			break;
		}
		case R.id.menu_statistic: {
			onStatisticsActivity();
			break;
		}
		}
	}

	@Override
	protected int getMenuId() {
		return R.menu.screen_of_task;
	}

	private Class<?>[] getTaskList(){
		return new Class<?>[]{
			MagicSquareTask.class,
			CompareTask.class,
			ConclusionTask.class,
			SystematisationTask.class,
		};
	}

	private int[] settingOfTaskScreen(
			LinearLayout nameAndAgePlace, TableLayout sovenokAndTextLayout,
			SovenokLayout sovenokImg, LinearLayout task_textLayout,
			ViewGroup task, RelativeLayout task_board,RelativeLayout sovenokAndTree,ImageView tree_img,ImageView lamp) {
		int[] res = new int[4];
		int width = getScreenWidth();
		int heigth = getScreenHeight();
		if (SystemHelper.isPortretOrientation(this)) {
			int heigthNameandAge = (int) (heigth / 20);
			int hSovenokImg = (int) (heigth * 0.2);
			int wSovenokImg = (int) (hSovenokImg * 0.7);
			int heigthTaskArea = heigth - heigthNameandAge - hSovenokImg;
			int heigthTaskBoard = Math.min((int) (width / 1.6), heigthTaskArea
					- (int) (heigth * 0.2));

			int lampWidth = (int)(wSovenokImg/2);
			int lampHeight = (int)(lampWidth/1.5);
			int sovenokMarginTop = lampHeight;
			int marginSovAndText = heigthNameandAge + 15;
			int marginTask = marginSovAndText + (int)(hSovenokImg*0.85) + sovenokMarginTop;

			nameAndAgePlace
					.setLayoutParams(new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							heigthNameandAge));

			RelativeLayout.LayoutParams sov_params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			sov_params.topMargin = marginSovAndText;
			sovenokAndTextLayout.setLayoutParams(sov_params);
			//
			RelativeLayout.LayoutParams img_params = new RelativeLayout.LayoutParams(
					wSovenokImg, hSovenokImg);			
			img_params.topMargin = sovenokMarginTop;
			sovenokImg.setLayoutParams(img_params);
			//
			RelativeLayout.LayoutParams lampParams = new RelativeLayout.LayoutParams(
					lampWidth, lampHeight);
			lampParams.leftMargin = (int)(wSovenokImg*0.1);
			lamp.setLayoutParams(lampParams);
			//
			int textHeight = (int)(hSovenokImg*2/3);
			int textWidth = Math.min((int)(textHeight*2.5),(width - wSovenokImg-10));
			TableRow.LayoutParams tastTextParams =new TableRow.LayoutParams(textWidth,textHeight);
			//tastTextParams.leftMargin = -(int)(wSovenokImg*0.02);
			tastTextParams.topMargin = (int)(hSovenokImg*0.15);
			task_textLayout.setLayoutParams(tastTextParams);

			RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, heigthTaskArea);
			par.topMargin = marginTask;
			par.leftMargin = 3;
			par.rightMargin = 3;
			task.setLayoutParams(par);

			task_board.setLayoutParams(new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, heigthTaskBoard));
			//
			//
			res[WIDTH_INDEX] = width;
			res[HEIGTH_INDEX] = heigthTaskBoard;
			res[MARGIN_TOP_INDEX] = marginTask;
			res[MARGIN_LEFT_INDEX] = 0;
		} else if (SystemHelper.isLandscapeOrientation(this)) {
			int heigthNameandAge = (int) (heigth / 20);
			int hSovenokImg = (int) (heigth * 0.25);
			int wSovenokImg = (int) (hSovenokImg * 0.7);
			int widthSovAndText = (int) (width * 0.25) - 40;
			int marginTaskTop = (int) (heigthNameandAge * 1.1) + (int)(hSovenokImg*2/3);
			int marginTaskLeft = widthSovAndText + 20;
			int marginSovAndText = heigthNameandAge + 20;
			int lampWidth = (int)(wSovenokImg/2);
			int lampHeight = (int)(lampWidth/1.5);
			/*int widthTaskBoard = (int) (width * 0.6);
			int heigthTaskBoard = Math.min((int) (widthTaskBoard / 1.5), heigth
					- marginTaskTop - (int) (heigth * 0.05));*/
			
			int heigthTaskBoard = (int) (heigth * 0.55);
			int widthTaskBoard = (int) (heigthTaskBoard * 1.5);
			
			nameAndAgePlace
					.setLayoutParams(new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.MATCH_PARENT,
							heigthNameandAge));

			RelativeLayout.LayoutParams sov_params = new RelativeLayout.LayoutParams(
					widthSovAndText, LayoutParams.WRAP_CONTENT);
			sov_params.topMargin = marginSovAndText;
			sovenokAndTextLayout.setLayoutParams(sov_params);
			
			//
			int sovenokMarginLeft = (int)(widthSovAndText/10);
			int sovenokMarginTop = lampHeight;
			int treeWidth = wSovenokImg;
			int treeHeight = marginTaskTop+heigthTaskBoard - hSovenokImg+(int)(hSovenokImg*0.2)-50-sovenokMarginTop;
			RelativeLayout.LayoutParams tree_params = new RelativeLayout.LayoutParams(
					treeWidth, treeHeight);
			tree_params.topMargin = hSovenokImg-(int)(hSovenokImg*0.2)+ lampHeight;
			tree_params.leftMargin = sovenokMarginLeft+(int)(wSovenokImg/10);
			tree_img.setLayoutParams(tree_params);
			//
			RelativeLayout.LayoutParams lampParams = new RelativeLayout.LayoutParams(
					lampWidth, lampHeight);
			lampParams.leftMargin = sovenokMarginLeft+(int)(wSovenokImg/10);
			lamp.setLayoutParams(lampParams);
			//
			RelativeLayout.LayoutParams img_params = new RelativeLayout.LayoutParams(
					wSovenokImg, hSovenokImg);			
			img_params.leftMargin = sovenokMarginLeft;
			img_params.topMargin = sovenokMarginTop;
			sovenokImg.setLayoutParams(img_params);
			//
			int textHeight = (int)(((int)(hSovenokImg*2/3)-10)*1.2);
			int textWidth = (int)(textHeight*3.5);
			RelativeLayout.LayoutParams textPar = new RelativeLayout.LayoutParams(textWidth, textHeight);
			textPar.topMargin = (int) (heigthNameandAge * 1.5);
			textPar.leftMargin = sovenokMarginLeft+(int)(wSovenokImg)+5;
			task_textLayout.setLayoutParams(textPar);
			//
			RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(
					widthTaskBoard, RelativeLayout.LayoutParams.MATCH_PARENT);
			par.topMargin = marginTaskTop;
			par.leftMargin = marginTaskLeft;
			task.setLayoutParams(par);

			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
					widthTaskBoard, heigthTaskBoard);
			params.gravity = Gravity.RIGHT;
			task_board.setLayoutParams(params);
			res[WIDTH_INDEX] = widthTaskBoard;
			res[HEIGTH_INDEX] = heigthTaskBoard;
			res[MARGIN_TOP_INDEX] = marginTaskTop;
			res[MARGIN_LEFT_INDEX] = marginTaskLeft;
		}
		return res;
	}

	private void createBasicRecord() {
		int[] taskTypeList = new int[] {
				Task.SYSTEMATISATION_TASK_TYPE,
				Task.COMPARE_TASK_TYPE, 
				Task.MAGICSUARE_TASK_TYPE,
				Task.CONCLUSION_TASK_TYPE };
		// check the availability of data about the user in
		// TableStatistic.TABLE_NAME
		String selection = TableStatistic.TABLE_FIELD_ID + " = " + id;
		String fields[] = new String[] { TableStatistic.TABLE_NAME,
				TableStatistic.TABLE_FIELD_ID };
		Cursor finding = getContentResolver().query(
				DatabaseProvider.CONTENT_URI, fields, selection, null, null);
		int[] complex = new int[]{
			Task.COMPLEXITY_LOW,
			Task.COMPLEXITY_MEDIUM,
			Task.COMPLEXITY_HIGH,
		};
		if (finding.getCount() == 0) {
			for (int i = 0; i < taskTypeList.length; i++) {
				for(int j=0;j<complex.length;j++){
					ContentValues values = new ContentValues();
					values.put(DatabaseProvider.KEY_OF_TABLE_NAME,TableStatistic.TABLE_NAME);
					values.put(TableStatistic.TABLE_FIELD_ID, id);
					values.put(TableStatistic.TABLE_FIELD_TASK_TYPE, taskTypeList[i]);
					values.put(TableStatistic.TABLE_FIELD_TASK_COMPLEX, complex[j]);
					values.put(TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS,0);
					values.put(TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT, 0);
					getContentResolver().insert(DatabaseProvider.CONTENT_URI,values);
				}
			}
		}
		finding.close();
	}

	private void recordingResult(boolean result, int type, int complex) {
		String selection = TableStatistic.TABLE_FIELD_ID + " = " + id 
				  +" AND "+TableStatistic.TABLE_FIELD_TASK_TYPE+"="+type
				  +" AND "+TableStatistic.TABLE_FIELD_TASK_COMPLEX+"="+complex;
		Log.d(TAG,"at recordingResult: selection of query is "+selection);
		String fields[] = new String[] { TableStatistic.TABLE_NAME,
				TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT,
				TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS };
		Cursor finding = getContentResolver().query(
				DatabaseProvider.CONTENT_URI, fields, selection, null, null);
		finding.moveToFirst();
		int indexCountOfCorrectAnswerColumn = finding
				.getColumnIndex(TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS);
		int indexAttemptsCountColumn = finding
				.getColumnIndex(TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT);
		int attemptsCount = finding.getInt(indexAttemptsCountColumn);
		int countOfCorrectAnswer = finding
				.getInt(indexCountOfCorrectAnswerColumn);
		attemptsCount++;
		if (result) {
			countOfCorrectAnswer++;
		}
		ContentValues values = new ContentValues();
		values.put(DatabaseProvider.KEY_OF_TABLE_NAME,
				TableStatistic.TABLE_NAME);
		values.put(TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT, attemptsCount);
		values.put(TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS,
				countOfCorrectAnswer);
		StringBuilder where = new StringBuilder();
		where.append(TableStatistic.TABLE_FIELD_ID);
		where.append(" = ");
		where.append(id);
		where.append(" AND ");
		where.append(TableStatistic.TABLE_FIELD_TASK_TYPE);
		where.append(" = ");
		where.append(type);
		where.append(" AND ");
		where.append(TableStatistic.TABLE_FIELD_TASK_COMPLEX);
		where.append(" = ");
		where.append(complex);
		getContentResolver().update(DatabaseProvider.CONTENT_URI, values,
				where.toString(), null);
	}

	private void upperComplexity(int type)	{
		Log.d(TAG, "at upperComplexity, resCount"+resultCount);
		if(resultCount==CORRECT_NUM_FOR_COMPLEX_UP){
			Log.d(TAG, "at upperComplexity - UP");
			resultCount = 0;
			String compexityKeyInPref = type+OwletApplication.COMPLEX_POSTFIX;
			int currentComplex = set.getInt(compexityKeyInPref, Task.COMPLEXITY_LOW);
			if (currentComplex == Task.COMPLEXITY_LOW) {	
				currentComplex = Task.COMPLEXITY_MEDIUM;
			} else if(currentComplex == Task.COMPLEXITY_MEDIUM) {
				currentComplex = Task.COMPLEXITY_HIGH;
			} else {}
			set.edit().putInt(compexityKeyInPref, currentComplex).commit();
		} else {
			return;
		}
	}
	
	private void downComplexity(int type) {
		Log.d(TAG, "at downComplexity, resCount"+resultCount);
		if(resultCount==-FAIL_NUM_FOR_COMPLEX_DOWN){
			Log.d(TAG, "at downComplexity - DOWN");
			resultCount = 0;
			String compexityKeyInPref = type+OwletApplication.COMPLEX_POSTFIX;
			int currentComplex = set.getInt(compexityKeyInPref, Task.COMPLEXITY_LOW);
			if (currentComplex == Task.COMPLEXITY_HIGH){
				currentComplex = Task.COMPLEXITY_MEDIUM;
			}
			else if (currentComplex == Task.COMPLEXITY_MEDIUM){
				currentComplex = Task.COMPLEXITY_LOW;
			} else {}
			set.edit().putInt(compexityKeyInPref, currentComplex).commit();
		} else {
			return;
		}
	}

	private int getStatusBarHeigth() {
		Rect rectgle = new Rect();
		Window window = getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		return rectgle.top;
	}
	
	private Task taskFactory(int taskId, Context context,TaskParamsContainer container) {
		Constructor<?> c;
		try {
			Class<?>[] tasks = getTaskList();
			c = tasks[taskId].getConstructor(Context.class, TaskParamsContainer.class);
			Object obj = c.newInstance(context, container);
			return (Task)obj;
		} catch (Exception e) {
			Log.e(TAG, "Not found required constructor for task", e);
		}
		return null;
	}

	
	@Override
	protected void setLanguageFromPref() {
		onceLoadCreate = true;
		super.setLanguageFromPref();
	}

	private void removeViewsFromPrevioslyTask(Task currentTask){
		if(currentTask!=null&task!=null){
			currentTask.removeAnswerViews();
			for(int i=0; i<task.getChildCount();i++){
				View v = task.getChildAt(i);
				if(v.getId()!=R.id.task_board){
					v.setVisibility(View.GONE);
				}
			}
		}
	}
}
