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

package com.sibext.owlet.view;

import com.sibext.owlet.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TaskImageView extends ImageView {
	protected final int IMAGE_GLASSY_SQUARE_DRAWABLE_NAME = R.drawable.glassy_square;
	protected final int IMAGE_QUESTION_DRAWABLE_NAME = R.drawable.question; 
	
	private int correctAnswer;

	private int xLocation;
	private int yLocation;
	private int variantAnswer;
	private int leftMargin, topMargin;
	protected int rendererIdLimit;
	
	public TaskImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setDrawingCacheEnabled(true);
		setupBackground(context, getBackgroundId());		
	}
	
	public TaskImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TaskImageView(Context context) {
		this(context, null);
	}

	/**
	 * Установка фона для данного view
	 * @param context
	 * @param resId
	 */
	public void setupBackground(Context context, int resId){		
		this.setBackgroundResource(resId);
	}

	protected int getBackgroundId(){
		return IMAGE_GLASSY_SQUARE_DRAWABLE_NAME;
	}
	
	
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public void setCoordinate(int x,int y){
		this.xLocation = x;
		this.yLocation = y;
	}
	public int getXcoordinate(){
		return xLocation;
	}
	public int getYcoordinate(){
		return yLocation;
	}
	public int getVariantAnswer() {
		return variantAnswer;
	}

	public void setVariantAnswer(int variantAnswer) {
		this.variantAnswer = variantAnswer;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	public int getRenderedLimit() {
		return rendererIdLimit;
	}
}
