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

import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;

/**
 * The base class for all set with task image views
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class BaseTaskImageView extends TaskImageView {
	private boolean enabledFlag = true;
	public void setEnabledFlag(boolean enabledFlag){
		this.enabledFlag = enabledFlag;
	}
	public boolean getEnabledFlag(){
		return enabledFlag;
	}
	/**
	 * The config for drawing current set
	 */
	private Renderer renderer;

	/**
	 * The standard constructor for inflate new task image view from current set
	 * 
	 * @param context
	 *            the android context
	 * @param renderer
	 *            the object with information about how display current set
	 */
	public BaseTaskImageView(Context context, Renderer renderer) {
		super(context);
		this.renderer = renderer;
	}
	public Renderer getRenderer(){
		return this.renderer;
	}
	/**
	 * @return true if image view needs multi-color mode otherwise white-black
	 *         color mode
	 */
	protected boolean getColorFlag() {
		return renderer.getColorFlag();
	}

	/**
	 * @return current id for rendering
	 */
	protected int getRenderId() {
		return renderer.getId();
	}

	/**
	 * Sets current id for rendering
	 */
	public void setRenderId(int id) {
		renderer.setId(id);
	}
	public void setColor(int color){
		renderer.setColor(color);
	}
	public void setColorFlag(boolean colorFlag){
		renderer.setColorFlag(colorFlag);
	}
	public int getColor(){
		return renderer.getColor();
	}
	
	public int getRenderIdLimit(){
		return rendererIdLimit;
	}

	public void update() {
		// Needs for redraw current state of view
		// For default nothing to do!
	}
}
