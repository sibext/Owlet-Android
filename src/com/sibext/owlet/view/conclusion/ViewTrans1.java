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
import com.sibext.owlet.view.transform.CountAndFillTramsformation;

import android.content.Context;

public class ViewTrans1 extends TransformView{
	private int num;
	private boolean fill;
	private int figureType;
	private int num_dst;
	private boolean fill_dst;
	private int figureType_dst;
	private int numTest;
	private int numTestDst;
	private int figureTypeTest;
	private CountAndFillTramsformation.Builder builder;
	
	public ViewTrans1(Context context,ConclusionRenderer conRenderer) {
		super(context,conRenderer);
		num = rand.nextInt(4)+1;
		do{
			numTest = rand.nextInt(2)+2;
		} while (num==numTest);
		boolean numUp = rand.nextBoolean();
		if(num==4){
			num_dst = num-1;
			numTestDst = numTest - 1;
		} else if(numUp||num==1){
			num_dst = num+1;
			numTestDst = numTest + 1;
		} else {
			num_dst = num-1;
			numTestDst = numTest - 1;
		}
		fill = rand.nextBoolean();
		figureType = rand.nextInt(3);
		fill_dst = rand.nextBoolean();
		figureType_dst = figureType;
		//for test
		figureTypeTest = rand.nextInt(3);
		example = new CountAndFillTramsformation.Builder(context).color(colorFlag, colors[0])
				.size(w, h)
				.source(figureType, num, fill)
				.destination(figureType_dst, num_dst, fill_dst)
				.build().transform();
		test = new CountAndFillTramsformation.Builder(context).color(colorFlag, colors[1])
				.size(w, h)
				.source(figureTypeTest, numTest, fill)
				.destination(figureTypeTest, numTestDst, fill_dst)
				.build().transform();
	}

	@Override
	BaseTaskImageView getExampleImage() {
		return example[0];
	}

	@Override
	BaseTaskImageView getExampleAnswerImage() {
		return example[1];
	}

	@Override
	BaseTaskImageView getTestImage() {
		return test[0];
	}

	@Override
	BaseTaskImageView getTestAnswerImage() {
		return test[1];
		}

	@Override
	ArrayList<BaseTaskImageView> getVariants() {
		ArrayList<BaseTaskImageView> res = new ArrayList<BaseTaskImageView>();
		
		res.add(new CountAndFillTramsformation.Builder(context).color(colorFlag, colors[2])
					.size(w, h)
					.source(figureTypeTest, numTestDst, !fill_dst)
					.destination(figureTypeTest, Math.max(1,(numTestDst+1)%4), fill_dst)
					.build().transform()[0]);
		res.add(new CountAndFillTramsformation.Builder(context).color(colorFlag, colors[3])
					.size(w, h)
					.source(figureTypeTest, numTestDst, !fill_dst)
					.destination(figureTypeTest, Math.max(1,(numTestDst+1)%4), fill_dst)
					.build().transform()[1]);

		res.add(new CountAndFillTramsformation.Builder(context).color(colorFlag, colors[4])
					.size(w, h)
					.source((figureTypeTest+1)%3, numTestDst, !fill_dst)
					.destination(figureTypeTest, (numTestDst+1)%4+1, fill_dst)
					.build().transform()[0]);

		return res;
	}



}
