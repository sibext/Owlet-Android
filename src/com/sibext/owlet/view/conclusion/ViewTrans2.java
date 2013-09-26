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
import com.sibext.owlet.view.transform.OverlapingTramsformation;
import com.sibext.owlet.view.transform.Transformation;

import android.content.Context;

public class ViewTrans2 extends TransformView {
	private int typeUp;
	private int typeDn;
	private boolean fillUp;
	private boolean fillDn;

	private int typeUpTest;
	private int typeDnTest;
	private boolean fillUpTest;
	private boolean fillDnTest;

	public ViewTrans2(Context context, ConclusionRenderer conRenderer) {
		super(context, conRenderer);
		typeUp = rand.nextInt(Transformation.FigureTypes.FIGURE_COUNT);
		typeDn = rand.nextInt(Transformation.FigureTypes.FIGURE_COUNT);
		fillUp = rand.nextBoolean();
		fillDn = rand.nextBoolean();
		do {
			typeUpTest = rand.nextInt(Transformation.FigureTypes.FIGURE_COUNT);
		} while (typeUp == typeUpTest);
		typeDnTest = rand.nextInt(Transformation.FigureTypes.FIGURE_COUNT);
		fillUpTest = rand.nextBoolean();
		fillDnTest = rand.nextBoolean();
		example = new OverlapingTramsformation.Builder(context).color(colorFlag, colors[0])
				.size(w, h)
				.up(fillUp, typeUp)
				.dn(fillDn, typeDn)
				.build().transform();
		test = new OverlapingTramsformation.Builder(context).color(colorFlag, colors[1])
				.size(w, h)
				.up(fillUpTest, typeUpTest)
				.dn(fillDnTest, typeDnTest)
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

		res.add(new OverlapingTramsformation.Builder(context)
				.color(colorFlag, colors[2])
				.size(w, h)
				.up(!fillUpTest, typeUpTest)
				.dn(fillDnTest, typeDnTest)
				.build().transform()[0]);
		res.add(new OverlapingTramsformation.Builder(context)
				.color(colorFlag, colors[3])
				.size(w, h)
				.up(fillUpTest, typeUpTest)
				.dn(!fillDnTest, (typeDnTest+1)%3)
				.build().transform()[0]);
		res.add(new OverlapingTramsformation.Builder(context)
				.color(colorFlag, colors[4])
				.size(w, h)
				.up(!fillUpTest, (typeUpTest+1)%3)
				.dn(!fillDnTest, (typeDnTest+1)%3)
				.build().transform()[0]);
		return res;
	}

}
