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

package com.sibext.owlet.view.renderer;

/**
 * The simple renderer contains color flag and unique id.
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class Renderer {
	private int id;
	private boolean colorFlag;
	private int color;

	public Renderer(){
	}
	
	public Renderer(int id){
		setId(id);
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setColorFlag(boolean colorFlag){
		this.colorFlag = colorFlag;
	}
	public int getId(){
		return this.id;
	}
	public boolean getColorFlag(){
		return this.colorFlag;
	}
	public void setColor(int color){
		this.color = color;
	}
	public int getColor(){
		return color;
	}
}
