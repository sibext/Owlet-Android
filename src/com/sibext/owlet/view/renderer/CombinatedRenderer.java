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

import java.util.HashMap;

public class CombinatedRenderer extends Renderer {
	private int limit;
	private Class<?> type;
	private int[] multId;
	private HashMap<Integer, Integer> multColors;
	private boolean excessFlag;

	public CombinatedRenderer(Class<?> type, int limit) {
		this.limit = limit;
		this.type = type;
	}

	public int getCombinatedLimit() {
		return limit;
	}

	public void setCombinatedLimit(int limit) {
		this.limit = limit;
	}

	public Class<?> getCombinatedType() {
		return type;
	}

	public void setCombinatedType(Class<?> type) {
		this.type = type;
	}

	public void setExcessFlag(boolean excessFlag) {
		this.excessFlag = excessFlag;
	}

	public boolean getExcessFlag() {
		return this.excessFlag;
	}

	public void setMultId(int[] multId) {
		this.multId = multId;
	}

	public int[] getMultId() {
		return this.multId;
	}
	
	public void setMultColors(HashMap<Integer, Integer> multColors) {
		this.multColors = multColors;
	}

	public HashMap<Integer, Integer> getMultColors() {
		return this.multColors;
	}

}
