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

package com.sibext.owlet.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;

public class TaskImageHelper {

	private static Paint buildPaint() {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		return paint;
	}
	private static void colorPaint(Paint paint, boolean colorFlag, int color){
		if(colorFlag){
			paint.setColor(color);
		} else {
			paint.setColor(Color.WHITE);
		}
	}
	/**
	 * Класс для отрисовки треуголников, направленных в разные стороны, с
	 * закрашенной нижней или верней частью
	 * (drawTriangle[направоение]Fill[часть, которая закрашена]).
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 * 
	 */
	public static class Triangles {
		private static Paint paintUp = buildPaint();
		private static Paint paintDn = buildPaint();

		private static void fillUpPartOfTriangle(Paint paintUp, Paint paintDn) {			
			paintUp.setStyle(Style.FILL_AND_STROKE);
			paintDn.setStyle(Style.STROKE);
		}

		private static void fillDownPartOfTriangle(Paint paintUp, Paint paintDn) {
			paintUp.setStyle(Style.STROKE);
			paintDn.setStyle(Style.FILL_AND_STROKE);
		}

		private static void drawTrianglePath(Canvas canvas, int w, int h,
				Paint paintUp, Paint paintDn) {
			float[] ptsUp = { w / 2, h / 6, w / 3, h / 2, 2 * w / 3, h / 2,
					w / 2, h / 6, };
			float[] ptsDn = { w / 3, h / 2, 2 * w / 3, h / 2, 5 * w / 6,
					5 * h / 6, w / 6, 5 * h / 6, w / 3, h / 2 };
			Path pathUp = new Path();
			Path pathDn = new Path();
			pathUp.moveTo(ptsUp[0], ptsUp[1]);
			for (int i = 2; i < ptsUp.length; i += 2) {
				pathUp.lineTo(ptsUp[i], ptsUp[i + 1]);
			}
			canvas.drawPath(pathUp, paintUp);
			//
			pathDn.moveTo(ptsDn[0], ptsDn[1]);
			for (int i = 2; i < ptsDn.length; i += 2) {
				pathDn.lineTo(ptsDn[i], ptsDn[i + 1]);
			}
			canvas.drawPath(pathDn, paintDn);
		}

		public static void drawTriangleUpFillUp(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillUpPartOfTriangle(paintUp, paintDn);
			drawTrianglePath(canvas, w, h, paintUp, paintDn);

		}

		public static void drawTriangleLeftFillUp(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillUpPartOfTriangle(paintUp, paintDn);
			canvas.rotate(90, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleDownFillUp(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillUpPartOfTriangle(paintUp, paintDn);
			canvas.rotate(180, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleRightFillUp(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillUpPartOfTriangle(paintUp, paintDn);
			canvas.rotate(270, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleUpFillDown(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillDownPartOfTriangle(paintUp, paintDn);
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleLeftFillDown(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillDownPartOfTriangle(paintUp, paintDn);
			canvas.rotate(90, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleDownFillDown(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillDownPartOfTriangle(paintUp, paintDn);
			canvas.rotate(180, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}

		public static void drawTriangleRightFillDown(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paintUp, colorFlag, color);
			colorPaint(paintDn, colorFlag, color);
			fillDownPartOfTriangle(paintUp, paintDn);
			canvas.rotate(270, (float) (w / 2), (float) (h / 2));
			drawTrianglePath(canvas, w, h, paintUp, paintDn);
		}
	}

	/**
	 * Класс для отрисовки много раз повторяющейся стрелки в Умозаключении
	 * 
	 * @author Mike Osipov <osipov@sibext.com>
	 * 
	 */
	
	public static class Arrow {
		
		public static void drawArrow (Canvas canvas, int w, int h,
				Paint paint) {
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(3);
			Path path = new Path();
			path.moveTo(w/5, h/2);
			path.lineTo(4*w/5, h/2);
			paint.setStrokeWidth(2);
			path.lineTo(4*w/5-10, h/2+7);
			path.moveTo(4*w/5, h/2);
			path.lineTo(4*w/5-10, h/2-7);
			canvas.drawPath(path, paint);
		}
	}
	/**
	 * Класс для отрисовки стрелок в разные стороны с заливкой(fill) или без.
	 * 
	 * @author Mike Osipov <osipov@sibext.com>
	 * 
	 */
	public static class Arrows {
		private static Paint paint = buildPaint();

		private static void setPaint(Paint paint) {
			paint.setStrokeWidth(3);
			paint.setStyle(Style.STROKE);
		}

		private static void setPaintFill(Paint paint) {
			paint.setStrokeWidth(0);
			paint.setStyle(Style.FILL_AND_STROKE);
		}

		private static void drawArrowPath(Canvas canvas, int w, int h,
				Paint paint) {
			float[] pts = { w / 6, 3 * h / 8, w / 6, 5 * h / 8, 2 * w / 3,
					5 * h / 8, 2 * w / 3, 3 * h / 4, 5 * w / 6, h / 2,
					2 * w / 3, h / 4, 2 * w / 3, 3 * h / 8, w / 6, 3 * h / 8 };
			Path path = new Path();
			path.moveTo(pts[0], pts[1]);
			for (int i = 2; i < pts.length; i += 2) {
				path.lineTo(pts[i], pts[i + 1]);
			}
			canvas.drawPath(path, paint);
		}

		public static void drawArrowLeft(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaint(paint);
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowDown(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaint(paint);
			canvas.rotate(90, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowRight(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaint(paint);
			canvas.rotate(180, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowUp(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaint(paint);
			canvas.rotate(270, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowLeftFill(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaintFill(paint);
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowDownFill(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaintFill(paint);
			canvas.rotate(90, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowRightFill(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaintFill(paint);
			canvas.rotate(180, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}

		public static void drawArrowUpFill(Canvas canvas, boolean colorFlag,int color, int w, int h) {
			colorPaint(paint, colorFlag, color);
			setPaintFill(paint);
			canvas.rotate(270, (float) (w / 2), (float) (h / 2));
			drawArrowPath(canvas, w, h, paint);
		}	

	}

	/**
	 * Класс отрисовывает веселый, нейтральный и грустный смайлики, если
	 * fillFlag - true, то глаза будут закрашены.
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 * 
	 */
	public static class Smiles {
		private static RectF oval = new RectF();
		private static Paint paint = buildPaint();

		private static void drawFace(Canvas canvas, int w, int h, Paint paint,
				boolean fillFlag) {
			paint.setStrokeWidth(2);
			paint.setStyle(Style.STROKE);
			canvas.drawCircle((int) (w / 2), (int) (h / 2), (int) (4*h / 5) - 2,
					paint);
			if (fillFlag) {
				paint.setStyle(Style.FILL);
			}
			canvas.drawCircle(w / 3, h / 3, (int) (h / 2) / 5, paint);
			canvas.drawCircle(2 * w / 3, h / 3, (int) (h / 2) / 5, paint);
			paint.setStyle(Style.STROKE);
		}

		public static void drawSmile(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawFace(canvas, w, h, paint, fillFlag);
			oval.set((int) (w / 2) - (int) (h / 2) / 2, (int) (h / 2)
					- (int) (h / 2) / 2, (int) (w / 2) + (int) (h / 2) / 2,
					(int) (h / 2) + (int) (h / 2) / 2);
			canvas.drawArc(oval, 20, 140, false, paint);
		}

		public static void drawSmileNeutral(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawFace(canvas, w, h, paint, fillFlag);
			canvas.drawLine(w / 4, 2 * h / 3, 3 * w / 4, 2 * h / 3, paint);
		}

		public static void drawSmileSad(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawFace(canvas, w, h, paint, fillFlag);
			oval.set((int) (w / 2) - (int) (h / 2) / 2, (int) (h / 2),
					(int) (w / 2) + (int) (h / 2) / 2, (int) (h / 2)
							+ (int) (h / 2));
			canvas.drawArc(oval, 200, 140, false, paint);
		}
	}
	/**
	 * Класс отрисовывает человечков, если fillFlag - true, то голова закрашена.
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 *
	 */
	public static class Peoples {
		private static Paint paint = buildPaint();
		static{
			paint.setStrokeWidth(1.5f);
		}
		private static void drawHeadAndBody(Canvas canvas, int w, int h,
				Paint paint, boolean fillFlag) {
			if (fillFlag) {
				paint.setStyle(Style.FILL);
			} else {
				paint.setStyle(Style.STROKE);
			}
			canvas.drawCircle(w / 2, h / 4, (int) (h / 8), paint);
			canvas.drawLine(w / 2, h / 4 + (int) (h / 8), w / 2, 2 * h / 3,
					paint);
			canvas.drawLine(w / 2, 2 * h / 3, w / 3, 5 * h / 6, paint);
			canvas.drawLine(w / 2, 2 * h / 3, 2 * w / 3, 5 * h / 6, paint);
		}

		public static void drawPeopleHandsUp(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawHeadAndBody(canvas, w, h, paint, fillFlag);
			canvas.drawLine(w / 2, h / 2, 2 * w / 3, h / 3, paint);
			canvas.drawLine(w / 2, h / 2, w / 3, h / 3, paint);
		}

		public static void drawPeopleHandsRight(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawHeadAndBody(canvas, w, h, paint, fillFlag);
			canvas.drawLine(w / 3.5f, 
					h / 2 - h / 17, 
					2.5f * w / 3.5f, 
					h / 2 - h / 17, paint);
		}

		public static void drawPeopleHandsDown(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag) {
			colorPaint(paint, colorFlag, color);
			drawHeadAndBody(canvas, w, h, paint, fillFlag);
			canvas.drawLine(w / 2, h / 2 - h / 8, 2 * w / 3, 2 * h / 3 - h / 8,
					paint);
			canvas.drawLine(w / 2, h / 2 - h / 8, w / 3, 2 * h / 3 - h / 8,
					paint);
		}
	}
	/**
	 * Класс отрисовывает углы, если fillFlag - true, то угол будет закрашен.
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 *
	 */
	public static class Corners{
		private static Paint paint = buildPaint();
		static{
			paint.setStrokeWidth(3);
		}
		private static float[] getPts(int w,int h){
			return new float[] { w / 6, h / 2, w / 6, h / 6, w / 2, h / 6, 5 * w / 6,		
			h / 6, 5 * w / 6, h / 2, 5 * w / 6, 5 * h / 6, w / 2,
			5 * h / 6, w / 6, 5 * h / 6, w / 6, h / 2};
		}
		private static void drawCornerPath(Canvas canvas, float[] pts,
				Paint paint, boolean fillFlag){
			if(fillFlag){
				paint.setStyle(Style.FILL);
			} else {
				paint.setStyle(Style.STROKE);
			}
			Path path = new Path();
			path.moveTo(pts[0], pts[1]);
			for (int i = 2; i < pts.length; i += 2) {
				path.lineTo(pts[i], pts[i + 1]);
			}
			canvas.drawPath(path, paint);
		}
		public static void drawUpLeftCorner(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag){
			colorPaint(paint, colorFlag, color);
			float[] pts = getPts(w, h);
			pts[2] = (int) (w / 2);
			pts[3] = (int) (h / 2);
			drawCornerPath(canvas, pts, paint, fillFlag);
		}
		public static void drawUpRightCorner(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag){
			colorPaint(paint, colorFlag, color);
			float[] pts = getPts(w, h);
			pts[6] = (int) (w / 2);
			pts[7] = (int) (h / 2);
			drawCornerPath(canvas, pts, paint, fillFlag);
		}
		public static void drawDownRightCorner(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag){
			colorPaint(paint, colorFlag, color);
			float[] pts = getPts(w, h);
			pts[10] = (int) (w / 2);
			pts[11] = (int) (h / 2);
			drawCornerPath(canvas, pts, paint, fillFlag);
		}
		public static void drawDownLeftCorner(Canvas canvas, boolean colorFlag,int color, int w, int h,
				boolean fillFlag){
			colorPaint(paint, colorFlag, color);
			float[] pts = getPts(w, h);
			pts[14] = (int) (w / 2);
			pts[15] = (int) (h / 2);
			drawCornerPath(canvas, pts, paint, fillFlag);
		}
	}
	/**
	 * Класс отрисовывает различные геометрические объекты
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 *
	 */
	public static class Geometry{
		private static Paint paint = buildPaint();
		static {
			paint.setStrokeWidth(3);
			paint.setStyle(Style.STROKE);
		}
		public static void drawCircle(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/3), paint);
		}
		public static void drawCircleFill(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/3), paint);
			paint.setStyle(Style.STROKE);
		}
		public static void drawCrossedTwoLinesCircle(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(w/2, h/2, h/3, paint);
			canvas.drawLine(w/6, h/6, 5*w/6, 5*h/6, paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, h/6, paint);
		}
		public static void drawCrossedOneLinesCircle(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/3), paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, h/6, paint);
		}
		public static void drawTriangle(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawLine(w/2, h/6, w/6, 5*h/6, paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, 5*h/6, paint);
			canvas.drawLine(5*w/6, 5*h/6, w/2, h/6, paint);
		}
		public static void drawTriangleFill(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path = new Path();
			paint.setStyle(Style.FILL);
			path.moveTo(w/2, h/6);
			path.lineTo(w/6, 5*h/6);
			path.lineTo(5*w/6,5*h/6);
			path.lineTo(w/2, h/6);
			canvas.drawPath(path, paint);
			paint.setStyle(Style.STROKE);
		}
		public static void drawSquare(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/6, h/6, 5*w/6, 5*h/6, paint);
		}
		public static void drawSquareFill(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL_AND_STROKE);
			canvas.drawRect(w/6, h/6, 5*w/6, 5*h/6, paint);
			paint.setStyle(Style.STROKE);
		}
		/**
		 * Крест
		 */
		public static void drawCross(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawLine(w/6, h/6, 5*w/6, 5*h/6, paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, h/6, paint);
		}
		/**
		 * Три параллельные линии
		 */
		public static void drawParallelLines(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawLine(w/6, h/6, 5*w/6, h/6, paint);
			canvas.drawLine(w/6, h/2, 5*w/6, h/2, paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, 5*h/6, paint);
		}
		/**
		 * Квадрат с крестиком внутри
		 */
		public static void drawCrossedSquare(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/6, h/6, 5*w/6, 5*h/6, paint);
			canvas.drawLine(w/6, h/6, 5*w/6, 5*h/6, paint);
			canvas.drawLine(w/6, 5*h/6, 5*w/6, h/6, paint);
		}
		/**
		 * 
		 */
		public static void drawHalfFillOval(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL_AND_STROKE);
			RectF oval1 = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawArc(oval1, 90, 180, false, paint);
			paint.setStyle(Style.STROKE);
			canvas.drawArc(oval1, 180, 270, false, paint);
		}
		
		public static void drawHalfEmptyOval(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF oval2 = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawArc(oval2, 90, 180, false, paint);
			paint.setStyle(Style.FILL_AND_STROKE);
			canvas.drawArc(oval2, -90, 180, false, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawHalfFillSquare(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF rec1 = new RectF(w/6, h/6, w/2, 5*h/6);
			canvas.drawRect(rec1, paint);
			paint.setStyle(Style.FILL_AND_STROKE);
			RectF rec2 = new RectF(w/2, h/6, 5*w/6, 5*h/6);
			canvas.drawRect(rec2, paint);
			paint.setStyle(Style.STROKE);			
		}
		
		public static void drawHalfEmptySquareAns(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL_AND_STROKE);
			RectF rec3 = new RectF(w/6, h/6, w/2, 5*h/6);
			canvas.drawRect(rec3, paint);
			paint.setStyle(Style.STROKE);
			RectF rec4 = new RectF(w/2, h/6, 5*w/6, 5*h/6);
			canvas.drawRect(rec4, paint);
		}
		/**
		 * 
		 */
		public static void drawTwoPerpendLines(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path = new Path();
			path.moveTo(w/5, h/2);
			path.lineTo(4*w/5, h/2);
			path.moveTo(w/2, h/5);
			path.lineTo(w/2, 4*h/5);
			canvas.drawPath(path, paint);
		}
		
		public static void drawTwoWavePerpendLines(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p = new Path();
            p.moveTo(w/5, h/2);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p.lineTo(w/5+(i)*(3*w/5/9), h/2);
            	else p.lineTo(w/5+(i)*(3*w/5/9), h/2-h/12);
            }
            canvas.drawPath(p, paint);
			
            Path p1 = new Path();
            p1.moveTo(w/2+w/24, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p1.lineTo(w/2+w/24, h/5+(i)*(3*h/5/9));
            	else p1.lineTo(w/2-w/24, h/5+(i)*(3*h/5/9));
            }
            canvas.drawPath(p1, paint);
		}
		
		public static void drawFourPerpendLines(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path = new Path();
			path.moveTo(w/5, 2*h/5);
			path.lineTo(4*w/5,2* h/5);
			path.moveTo(w/5, 3*h/5);
			path.lineTo(4*w/5, 3*h/5);
			path.moveTo(2*w/5, h/5);
			path.lineTo(2*w/5, 4*h/5);
			path.moveTo(3*w/5, h/5);
			path.lineTo(3*w/5, 4*h/5);
			canvas.drawPath(path, paint);
		}
		
		public static void drawFourWavePerpendLinesAns(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p2 = new Path();
            p2.moveTo(w/5, 2*h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p2.lineTo(i*w/5, 2*h/5+h/12);
            	else p2.lineTo(i*w/5, 2*h/5);
            }
            canvas.drawPath(p2, paint);
            
            Path p3 = new Path();
            p3.moveTo(w/5, 3*h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p3.lineTo(i*w/5, 3*h/5+h/12);
            	else p3.lineTo(i*w/5, 3*h/5);
            }
            canvas.drawPath(p3, paint);
            
            Path p4 = new Path();
            p4.moveTo(2*w/5, h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p4.lineTo(2*w/5+w/12, i*h/5);
            	else p4.lineTo(2*w/5, i*h/5);
            }
            canvas.drawPath(p4, paint);
            
            Path p5 = new Path();
            p5.moveTo(3*w/5, h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p5.lineTo(3*w/5+w/12, i*h/5);
            	else p5.lineTo(3*w/5, i*h/5);
            }
            canvas.drawPath(p5, paint);
		}
		
		public static void drawFourWavePerpendLines(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p12 = new Path();
            p12.moveTo(w/5, 2*h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p12.lineTo(w/5+(i)*(3*w/5/9), 2*h/5);
            	else p12.lineTo(w/5+(i)*(3*w/5/9), 2*h/5+h/12);
            }
            canvas.drawPath(p12, paint);
            
            Path p13 = new Path();
            p13.moveTo(w/5, 3*h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p13.lineTo(w/5+(i)*(3*w/5/9), 3*h/5);
            	else p13.lineTo(w/5+(i)*(3*w/5/9), 3*h/5+h/12);
            }
            canvas.drawPath(p13, paint);
            
            Path p14 = new Path();
            p14.moveTo(2*w/5+w/24, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p14.lineTo(2*w/5+w/24, h/5+(i)*(3*h/5/9));
            	else p14.lineTo(2*w/5-w/24,  h/5+(i)*(3*h/5/9));
            }
            canvas.drawPath(p14, paint);
            
            Path p15 = new Path();
            p15.moveTo(3*w/5, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p15.lineTo(3*w/5+w/24,  h/5+(i)*(3*h/5/9));
            	else p15.lineTo(3*w/5-w/24,  h/5+(i)*(3*h/5/9));
            }
            canvas.drawPath(p15, paint);
		}
		
		public static void drawTwoWaveTwoDirectLines(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
		    Path p11 = new Path();
            p11.moveTo(2*w/5+w/24, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p11.lineTo(2*w/5+w/24,  h/5+(i)*(3*h/5/9));
            	else p11.lineTo(2*w/5-w/24,  h/5+(i)*(3*h/5/9));
            }
            canvas.drawPath(p11, paint);
	            
            Path p16 = new Path();
            p16.moveTo(3*w/5+w/24, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0){
            		p16.lineTo(3*w/5+w/24,  h/5+(i)*(3*h/5/9));
            	} else {
            		p16.lineTo(3*w/5-w/24,  h/5+(i)*(3*h/5/9));
            	}
            }
            canvas.drawPath(p16, paint);
	            
			Path path9 = new Path();
			path9.moveTo(w/5, 2*h/5);
			path9.lineTo(4*w/5,2* h/5);
			path9.moveTo(w/5, 3*h/5);
			path9.lineTo(4*w/5, 3*h/5);

			canvas.drawPath(path9, paint);
		}
		
		public static void drawOneWaveOneDirectLines(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p02 = new Path();
            p02.moveTo(w/5+w/24, h/2);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0){
            		p02.lineTo(w/5+(i)*(3*w/5/9), h/2);
            	} else {
            		p02.lineTo(w/5+(i)*(3*w/5/9), h/2+h/12);
            	}
            }
            canvas.drawPath(p02, paint);
            
			Path path5 = new Path();

			path5.moveTo(w/2, h/5);
			path5.lineTo(w/2, 4*h/5);
			canvas.drawPath(path5, paint);
		}
	
		public static void drawOneDirectOneWaveLines(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p03 = new Path();
            p03.moveTo(w/2+w/24, h/5);
            for (int i = 1; i < 11; i++) {
            	if (i%2==0) p03.lineTo(w/2+w/24,  h/5+(i)*(3*h/5/9));
            	else p03.lineTo(w/2-w/24, h/5+(i)*(3*h/5/9));
            }
            canvas.drawPath(p03, paint);
            
			Path path6 = new Path();
			path6.moveTo(w/5, h/2);
			path6.lineTo(4*w/5, h/2);
			canvas.drawPath(path6, paint);
		}
		/**
		 * 
		 */
		public static void drawFillTriangleInEmptySquare(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF rec = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawRect(rec, paint);			
			paint.setStyle(Style.FILL);
			Path path = new Path();
			path.moveTo(w/2, h/2+h/7);
			path.lineTo(w/2+w/6, h/2-h/7);
			path.lineTo(w/2-w/6, h/2-h/7);	
			path.lineTo(w/2, h/2+h/7);
			canvas.drawPath(path, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawFillSquareInEmptyTriangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL);
			Path path4 = new Path();
			path4.moveTo(w/2-w/3, h/2-h/3);
			path4.lineTo(w/2+w/3, h/2-h/3);
			
			path4.lineTo(w/2+w/10, h/2-h/10-10);
			path4.lineTo(w/2-w/10, h/2-h/10-10);
			path4.lineTo(w/2-w/3, h/2-h/3);
			
			path4.lineTo(w/2, h/2+h/3);	
			path4.lineTo(w/2-w/10, h/2+h/10-10);
			path4.lineTo(w/2-w/10, h/2-h/10-10);
			
			path4.moveTo(w/2, h/2+h/3);	
			path4.lineTo(w/2-w/10, h/2+h/10-10);
			path4.lineTo(w/2+w/10, h/2+h/10-10);
			
			path4.moveTo(w/2, h/2+h/3);	
			path4.lineTo(w/2+w/10, h/2+h/10-10);
			path4.lineTo(w/2+w/10, h/2-h/10-10);
			path4.lineTo(w/2+w/3, h/2-h/3);	
			path4.lineTo(w/2, h/2+h/3);
			
			canvas.drawPath(path4, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawOvalInRomb(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF rec6 = new RectF(w/2+w/8, h/2+h/8, w/2-w/8, h/2-h/8);
			canvas.drawOval(rec6, paint);
			Path path0 = new Path();
			path0.moveTo(w/2, h/2+h/3);
			path0.lineTo(w/2+w/3, h/2);
			path0.lineTo(w/2, h/2-h/3);
			path0.lineTo(w/2, h/2-h/3);
			path0.lineTo(w/2-w/3, h/2);
			path0.lineTo(w/2, h/2+h/3);		
			canvas.drawPath(path0, paint);
		}
		
		public static void drawRombInOval(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path5 = new Path();
			path5.moveTo(w/2, h/2+h/7);
			path5.lineTo(w/2+w/7, h/2);
			path5.lineTo(w/2, h/2-h/7);
			path5.lineTo(w/2, h/2-h/7);
			path5.lineTo(w/2-w/7, h/2);
			path5.lineTo(w/2, h/2+h/7);		
			canvas.drawPath(path5, paint);
			
			RectF rec3 = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawOval(rec3, paint);
		}
		
		public static void drawFillOvalInEmptyRomb(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF rec8 = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawOval(rec8, paint);
						
			paint.setStyle(Style.FILL);
			Path path6 = new Path();
			path6.moveTo(w/2, h/2+h/7);
			path6.lineTo(w/2+w/7, h/2);
			path6.lineTo(w/2, h/2-h/7);
			path6.lineTo(w/2, h/2-h/7);
			path6.lineTo(w/2-w/7, h/2);
			path6.lineTo(w/2, h/2+h/7);		
			canvas.drawPath(path6, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawFillRombInOval(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF rec7 = new RectF(w/6, h/6, 5*w/6, 5*h/6);
			canvas.drawRect(rec7, paint);
			
			Path path8 = new Path();
			paint.setStyle(Style.FILL);
			path8.moveTo(w/2, h/2+h/7);
			path8.lineTo(w/2+w/6, h/2-h/7);
			path8.lineTo(w/2-w/6, h/2-h/7);	
			path8.lineTo(w/2, h/2+h/7);
			canvas.drawPath(path8, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawTriangleInSuare(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path9 = new Path();
			path9.moveTo(w/2, h/2+h/3);
			path9.lineTo(w/2+w/3, h/2);
			path9.lineTo(w/2, h/2-h/3);
			path9.lineTo(w/2, h/2-h/3);
			path9.lineTo(w/2-w/3, h/2);
			path9.lineTo(w/2, h/2+h/3);		
			canvas.drawPath(path9, paint);
			
			paint.setStyle(Style.FILL);	
			RectF rec9 = new RectF(w/2+w/8, h/2+h/8, w/2-w/8, h/2-h/8);
			canvas.drawOval(rec9, paint);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawTwoWaveLines(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p = new Path();
            p.moveTo(w/5, h/2);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p.lineTo(i*w/5, h/2+h/10);
            	else p.lineTo(i*w/5, h/2);
            }
            canvas.drawPath(p, paint);
		
            Path p1 = new Path();
            p1.moveTo(w/5, h/3);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p1.lineTo(i*w/5, h/3+h/10);
            	else p1.lineTo(i*w/5, h/3);
            }
            canvas.drawPath(p1, paint); 
		}
		
		public static void drawTwoDirectLines(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path2 = new Path();
			path2.moveTo(w/5, h/2+h/10);
			path2.lineTo(4*w/5, h/2+h/10);
			path2.moveTo(w/5, h/3+h/10);
			path2.lineTo(4*w/5, h/3+h/10);
			canvas.drawPath(path2, paint);
		}
		
		public static void drawWaveRectangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path p2 = new Path();
            p2.moveTo(w/5, 2*h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p2.lineTo(i*w/5, 2*h/5+h/10);
            	else p2.lineTo(i*w/5, 2*h/5);
            }
            canvas.drawPath(p2, paint);
            
            Path p3 = new Path();
            p3.moveTo(w/5, 3*h/5);
            for (int i = 1; i < 5; i++) {
            	if (i%2==0) p3.lineTo(i*w/5, 3*h/5+h/10);
            	else p3.lineTo(i*w/5, 3*h/5);
            }
            canvas.drawPath(p3, paint);
            
            Path p4 = new Path();
            p4.moveTo(w/5, 2*h/5);
            p4.lineTo(w/5, 3*h/5);
            canvas.drawPath(p4, paint);
            
            Path p5 = new Path();
            p5.moveTo(4*w/5, 2*h/5+h/10);
            p5.lineTo(4*w/5, 3*h/5+h/10); 
            canvas.drawPath(p5, paint);
		}
		
		public static void drawRectangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/5, 2*h/5, 4*w/5, 3*h/5, paint);
		}
		
		public static void drawFillRectangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL_AND_STROKE);
			canvas.drawRect(w/5, 2*h/5, 4*w/5, 3*h/5, paint);
			paint.setStyle(Style.STROKE);
		}
		/**
		 * 
		 */
		public static void drawZ(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path = new Path();
			path.moveTo(w/5, h/5);
			path.lineTo(4*w/5, h/5);
			path.lineTo(w/5, 4*h/5);
			path.lineTo(4*w/5, 4*h/5);
			canvas.drawPath(path, paint);
		}
		
		public static void drawTraingleDividedByLine(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path1 = new Path();
			path1.moveTo(w/2, h/6);
			path1.lineTo(w/6, 5*h/6);
			path1.lineTo(5*w/6, 5*h/6);
			path1.lineTo(w/2, h/6);
			path1.lineTo(w/2, 5*h/6);			
			canvas.drawPath(path1, paint);
		}	
		
		public static void drawLabirint(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path2 = new Path();
			path2.moveTo(w/6, h/6);
			path2.lineTo(w/6, 5*h/6);
			path2.lineTo(5*w/6, 5*h/6);
			path2.lineTo(5*w/6, h/6);
			path2.lineTo(2*w/6, h/6);
			path2.lineTo(2*w/6, 4*h/6);
			path2.lineTo(4*w/6, 4*h/6);
			path2.lineTo(4*w/6, 2*h/6);
			path2.lineTo(3*w/6, 2*h/6);
			path2.lineTo(3*w/6, 3*h/6);
			canvas.drawPath(path2, paint);
		}
		/**
		 * 
		 */
		public static void drawOvalInHexagon(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF oval = new RectF();
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/6)-1, paint);
			canvas.drawArc(oval, 20, 140, false, paint);
			
			Path path = new Path();
			path.moveTo(2*w/6, h/6);
			path.lineTo(4*w/6, h/6);
			path.lineTo(5*w/6, h/2);
			path.lineTo(4*w/6, 5*h/6);
			path.lineTo(2*w/6, 5*h/6);
			path.lineTo(w/6, h/2);
			path.lineTo(2*w/6, h/6);
			canvas.drawPath(path, paint);
		}
		
		public static void drawTriangleInTrapezoid(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path1 = new Path();
			path1.moveTo(w/6, h/6);
			path1.lineTo(5*w/6, h/6);
			path1.lineTo(4*w/6, 5*h/6);
			path1.lineTo(2*w/6, 5*h/6);
			path1.lineTo(w/6, h/6);			
			
			path1.moveTo(2*w/6, 2*h/6);
			path1.lineTo(4*w/6, 2*h/6);
			path1.lineTo(3*w/6, 4*h/6);
			path1.lineTo(2*w/6, 2*h/6);
			canvas.drawPath(path1, paint);
		}
		
		public static void drawSquareInPentagon(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path2 = new Path();
			path2.moveTo(w/2, h/6);
			path2.lineTo(5*w/6, h/2);
			path2.lineTo(4*w/6, 5*h/6);
			path2.lineTo(2*w/6, 5*h/6);
			path2.lineTo(w/6, h/2);			
			path2.lineTo(w/2, h/6);
			
			path2.moveTo(2*w/5, (int) (2.5*h/5));
			path2.lineTo(3*w/5, (int) (2.5*h/5));
			path2.lineTo(3*w/5, (int) (3.5*h/5));
			path2.lineTo(2*w/5, (int) (3.5*h/5));
			path2.lineTo(2*w/5, (int) (2.5*h/5));
			
			canvas.drawPath(path2, paint);
		}
		/**
		 * 
		 */
		public static void drawSun(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF oval = new RectF();
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/4)-1, paint);
			canvas.drawArc(oval, 20, 140, false, paint);
			
			Path path = new Path();
			//
			path.moveTo(w/6, (int)(h/2)-(int)(h/4)+2);
			path.lineTo((int)(w/2)-2*(int)(h/4)/3-4, (int)(h/2)-(int)(h/4)/2-1);
			
			path.moveTo(2*w/6, h/6);
			path.lineTo((int)(w/2)-(int)(h/4)/2, (int)(h/2)-(int)(h/4)+2);
			
			path.moveTo(w/2, h/6-4);
			path.lineTo((int)(w/2), (int)(h/2)-(int)(h/4)+1);
			
			path.moveTo(4*w/6, h/6);
			path.lineTo((int)(w/2)+(int)(h/4)/2, (int)(h/2)-(int)(h/4)+2);
			//
			path.moveTo(5*w/6, (int)(h/2)-(int)(h/4)+2);
			path.lineTo((int)(w/2)+2*(int)(h/4)/3+4, (int)(h/2)-(int)(h/4)/2-1);
			
			path.moveTo(5*w/6+5, h/2);
			path.lineTo((int)(w/2)+(int)(h/4)-1, (int)(h/2));
			//
			path.moveTo(5*w/6, (int)(h/2)+(int)(h/4)-2);
			path.lineTo((int)(w/2)+2*(int)(h/4)/3+4, (int)(h/2)+(int)(h/4)/2+1);
			
			path.moveTo(4*w/6, 5*h/6);
			path.lineTo((int)(w/2)+(int)(h/4)/2, (int)(h/2)+(int)(h/4)-2);
			
			path.moveTo(w/2, 5*h/6+4);
			path.lineTo((int)(w/2), (int)(h/2)+(int)(h/4)-1);
			
			path.moveTo(2*w/6, 5*h/6);
			path.lineTo((int)(w/2)-(int)(h/4)/2, (int)(h/2)+(int)(h/4)-2);
			//
			path.moveTo(w/6, (int)(h/2)+(int)(h/4)-2);
			path.lineTo((int)(w/2)-2*(int)(h/4)/3-4, (int)(h/2)+(int)(h/4)/2+1);

			path.moveTo(w/6-5, h/2);
			path.lineTo((int)(w/2)-(int)(h/4)+1, (int)(h/2));
			
			canvas.drawPath(path, paint);
		}
		
		public static void drawMoon(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			RectF oval1 = new RectF(w/3, h/4, 2*(int)(w/2), 3*h/4);
			canvas.drawArc(oval1, 90, 180, false, paint);
			RectF oval2 = new RectF((int)(w/2), h/4, 2*(int)(w/2), 3*h/4);
			canvas.drawArc(oval2, 100, 160, false, paint);
		}		
		public static void drawStar(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path2 = new Path();
			path2.moveTo(w/2, h/6);
			path2.lineTo(4*w/6+5, 5*h/6);
			path2.lineTo(w/6, (int) (h/2.4));
			path2.lineTo(5*w/6, (int) (h/2.4));
			path2.lineTo(2*w/6-5, 5*h/6);
			path2.lineTo(w/2, h/6);
						
			canvas.drawPath(path2, paint);
		}
		/**
		 * 
		 */
		public static void drawBigRectangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/6, h/3, 5*w/6, 2*h/3, paint);
			
		}	
		
		public static void drawEnvelope(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/6, h/3, 5*w/6, 2*h/3, paint);	
			Path path1 = new Path();
			path1.moveTo(w/6, h/3);
			path1.lineTo(w/2, 2*h/3);
			path1.lineTo(5*w/6, h/3);
			canvas.drawPath(path1, paint);
		}	
		
		public static void drawCrossedRectangle(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/6, h/3, 5*w/6, 2*h/3, paint);	
			
			Path path = new Path();
			path.moveTo(w/6, h/3);
			path.lineTo(5*w/6, 2*h/3);
			path.moveTo(w/6, 2*h/3);
			path.lineTo(5*w/6, h/3);
			canvas.drawPath(path, paint);
		}		
	}
	/**
	 * Класс отрисовывает различные варианты Т-образной фигуры
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 *
	 */
	public static class TLikeFigure{
		private static Paint paint = buildPaint();
		
		static {
			paint.setStrokeWidth(3);
			paint.setStyle(Style.STROKE);
		}
		
		public static void drawInvertedT(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path = new Path();
			path.moveTo(w/2, h/5);
			path.lineTo(w/2, 4*h/5);
			path.moveTo(w/5, 4*h/5);
			path.lineTo(4*w/5, 4*h/5);
			canvas.drawPath(path, paint);
		}
		
		public static void drawThickInvertedT(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path12 = new Path();
			path12.moveTo(w/5, 4*h/5+h/10);
			path12.lineTo(4*w/5, 4*h/5+h/10);
			path12.lineTo(4*w/5, 4*h/5-h/10);
			path12.lineTo(w/2+w/10, 4*h/5-h/10);
			path12.lineTo(w/2+w/10, h/5);
			path12.lineTo(w/2-w/10, h/5);
			path12.lineTo(w/2-w/10, 4*h/5-h/10);
			path12.lineTo(w/5, 4*h/5-h/10);
			path12.lineTo(w/5, 4*h/5+h/10);			
			canvas.drawPath(path12, paint);
		}
		
		public static void drawT(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path0 = new Path();
			path0.moveTo(w/2, h/5);
			path0.lineTo(w/2, 4*h/5);
			path0.moveTo(w/5, h/5);
			path0.lineTo(4*w/5, h/5);
			canvas.drawPath(path0, paint);
		}
		
		public static void drawThickTAns(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			paint.setStyle(Style.FILL_AND_STROKE);
			RectF rec3 = new RectF(w/6, h/6, w/2, 5*h/6);
			canvas.drawRect(rec3, paint);
			paint.setStyle(Style.STROKE);
			RectF rec4 = new RectF(w/2, h/6, 5*w/6, 5*h/6);
			canvas.drawRect(rec4, paint);
		}	
		
		public static void drawThickT(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path112 = new Path();
			path112.moveTo(w/5, h/5-h/10);
			path112.lineTo(4*w/5, h/5-h/10);
			path112.lineTo(4*w/5, h/5+h/10);
			path112.lineTo(w/2+w/10, h/5+h/10);
			
			path112.lineTo(w/2+w/10, 4*h/5);
			path112.lineTo(w/2-w/10, 4*h/5);
			path112.lineTo(w/2-w/10, h/5+h/10);
			path112.lineTo(w/5, h/5+h/10);
			path112.lineTo(w/5, h/5-h/10);		
			canvas.drawPath(path112, paint);
		}
		
		public static void drawTwithThickHat(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/5, h/5+h/10, 4*w/5, h/5-h/10, paint);
			Path path11 = new Path();
			path11.moveTo(w/2, 2*h/5-h/10);
			path11.lineTo(w/2, 4*h/5);
			canvas.drawPath(path11, paint);	
		}
		
		public static void drawTwithThickLeg(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawRect(w/2-w/10, h/5, w/2+w/10, 4*h/5, paint);
			Path p = new Path();
			p.moveTo(w/5, h/5);
			p.lineTo(4*w/5, h/5);
			canvas.drawPath(p, paint);
		}
		public static void drawTwoT(
				Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			Path path9 = new Path();
			path9.moveTo(w/2-w/10, h/5+h/10);
			path9.lineTo(w/2-w/10, 4*h/5+h/10);

			path9.moveTo(w/5-w/10, 4*h/5+h/10);
			path9.lineTo(4*w/5-w/10, 4*h/5+h/10);
			canvas.drawPath(path9, paint);
			
			Path path8 = new Path();
			path8.moveTo(w/2+w/10, h/5-h/10);
			path8.lineTo(w/2+w/10, 4*h/5-h/10);

			path8.moveTo(w/5+w/10, h/5-h/10);
			path8.lineTo(4*w/5+h/10, h/5-h/10);
			canvas.drawPath(path8, paint);
		}
	}
	/**
	 * Класс отрисовывает грани игральных костей
	 * 
	 * @author Mike Osipov <mike.osipov@netcook.org>
	 *
	 */
	public static class Dices{
		private static Paint paint = buildPaint();
		static {
			paint.setStrokeWidth(0);
			paint.setStyle(Style.FILL_AND_STROKE);
		}

	    public static void drawOne(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/8), paint);
		}
		public static void drawTwo(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(5*w/6, h/6, (int)(h/8), paint);
			canvas.drawCircle(w/6, 5*h/6, (int)(h/8), paint);
		}
		public static void drawThree(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(5*w/6, h/6, (int)(h/8), paint);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/8), paint);
			canvas.drawCircle(w/6, 5*h/6, (int)(h/8), paint);
		}
		public static void drawFour(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(5*w/6, h/6, (int)(h/8), paint);
			canvas.drawCircle(w/6, 5*h/6, (int)(h/8), paint);
			canvas.drawCircle(w/6, h/6,  (int)(h/8), paint);
			canvas.drawCircle(5*w/6,5*h/6,  (int)(h/8), paint);
		}
		public static void drawFive(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(5*w/6, h/6, (int)(h/8), paint);
			canvas.drawCircle(w/6, 5*h/6, (int)(h/8), paint);
			canvas.drawCircle((int)(w/2), (int)(h/2), (int)(h/8), paint);
			canvas.drawCircle(h/6, w/6,  (int)(h/8), paint);
			canvas.drawCircle(5*h/6,5*w/6,  (int)(h/8), paint);
		}
		public static void drawSix(Canvas canvas, boolean colorFlag,int color, int w, int h){
			colorPaint(paint, colorFlag, color);
			canvas.drawCircle(5*w/6, h/6, (int)(h/8), paint);
			canvas.drawCircle(5*w/6, (int)(h/2),  (int)(h/8), paint);
			canvas.drawCircle(5*w/6,5*h/6,  (int)(h/8), paint);
			canvas.drawCircle(w/6, 5*h/6, (int)(h/8), paint);
			canvas.drawCircle(w/6, (int)(h/2),  (int)(h/8), paint);
			canvas.drawCircle(w/6, h/6,  (int)(h/8), paint);
		}
	}
}
