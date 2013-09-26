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

package com.sibext.owlet.tasks;

import java.util.Observer;

import com.sibext.owlet.player.MediaPlayerSingleton;

import android.widget.TextView;

public class TaskParamsContainer {
	private TextView textView;
	private int widthScreen;
	private int heightScreen;
	private int[] boardParams;
	private Observer observer;
	private MediaPlayerSingleton sound;
	private int complexity;
	
	public TaskParamsContainer(){
	}
	
	public void setTextView(TextView textView){
		this.textView = textView;
	}
	
	public void setWidthScreen(int widthScreen){
		this.widthScreen = widthScreen;
	}
	
	public void setHeightScreen(int heightScreen){
		this.heightScreen = heightScreen;
	}
	
	public void setBoardParams(int[] boardParams){
		this.boardParams = boardParams;
	}

	public void setObserver(Observer observer){
		this.observer = observer;
	}
	public void setSound(MediaPlayerSingleton sound){
		this.sound = sound;
	}
	
	public void setComplexity(int complexity){
		this.complexity = complexity;
	}

	public TextView getTextView(){
		return textView;
	}
	
	public int getWidthScreen(){
		return widthScreen;
	}
	
	public int getHeightScreen(){
		return heightScreen;
	}
	
	public int[] getBoardParams(){
		return boardParams;
	}

	public Observer getObserver(){
		return observer;
	}

	public MediaPlayerSingleton getSound(){
		return sound;
	}

	public int getComplexity(){
		return complexity;
	}
}
