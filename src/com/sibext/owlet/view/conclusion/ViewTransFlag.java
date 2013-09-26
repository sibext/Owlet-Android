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

package com.sibext.owlet.view.conclusion;

import java.util.ArrayList;

import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.ConclusionRenderer;
import com.sibext.owlet.view.renderer.Renderer;
import com.sibext.owlet.view.resourses.FlagView;

import android.content.Context;

public class ViewTransFlag extends TransformView{
	private int setId;
	
	public ViewTransFlag(Context context, ConclusionRenderer conRenderer) {
		super(context, conRenderer);
		setId = rand.nextInt(getCollectionOfSets().length);
	}

	@Override
	BaseTaskImageView getExampleImage() {
		return getCollectionOfSets()[setId].example;
	}

	@Override
	BaseTaskImageView getExampleAnswerImage() {
		return getCollectionOfSets()[setId].exampleAns;
	}

	@Override
	BaseTaskImageView getTestImage() {
		return getCollectionOfSets()[setId].test;
	}

	@Override
	BaseTaskImageView getTestAnswerImage() {
		return getCollectionOfSets()[setId].testAns;
	}

	@Override
	ArrayList<BaseTaskImageView> getVariants() {
		return getCollectionOfSets()[setId].variants;
	}
	
	public class ConcSetFromRes{
		public BaseTaskImageView example;
		public BaseTaskImageView exampleAns;
		public BaseTaskImageView test;
		public BaseTaskImageView testAns;
		public ArrayList<BaseTaskImageView> variants;
		
		public ConcSetFromRes(int exId,int exAnsId,int testId,int testAnsId,int[] varIds) {
			variants = new ArrayList<BaseTaskImageView>();
			
			example = new FlagView(context, new Renderer(exId));
			exampleAns = new FlagView(context, new Renderer(exAnsId));
			test = new FlagView(context, new Renderer(testId));
			testAns = new FlagView(context, new Renderer(testAnsId));
			for(int i=0;i<varIds.length;i++){
				BaseTaskImageView var = new FlagView(context, new Renderer(varIds[i])); 
				var.setPadding(10, 10, 10, 10);
				variants.add(var);
			}
			example.setPadding(10, 10, 10, 10);
			exampleAns.setPadding(10, 10, 10, 10);
			test.setPadding(10, 10, 10, 10);
			testAns.setPadding(10, 10, 10, 10);
		}
	}
	
	private ConcSetFromRes[] getCollectionOfSets(){
		return new ConcSetFromRes[]{
			new ConcSetFromRes(1, 10, 3, 11,new int[]{2,6,12}),
			new ConcSetFromRes(7, 12, 6, 11, new int[]{4,5,3}),
			new ConcSetFromRes(0, 20, 18, 19, new int[]{15,17,18}),
			new ConcSetFromRes(13, 14, 8, 9, new int[]{16,18,2}),
		};
	}

}
