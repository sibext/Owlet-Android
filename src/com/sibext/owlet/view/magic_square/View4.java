package com.sibext.owlet.view.magic_square;

import com.sibext.owlet.R;
import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;

public class View4 extends BaseTaskImageView {
	private final int DRAW_RECTANGLE_WITH_CROSS = 0;
	private final int DRAW_ENVELOPE = 1;
	private final int DRAW_RECTANGLE = 2;

	public View4(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 3;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_RECTANGLE_WITH_CROSS:
			TaskImageHelper.Geometry.drawCrossedRectangle(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_ENVELOPE:
			TaskImageHelper.Geometry.drawEnvelope(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_RECTANGLE:
			TaskImageHelper.Geometry.drawBigRectangle(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		default:
			break;
		}

		super.onDraw(canvas);
	}

}
