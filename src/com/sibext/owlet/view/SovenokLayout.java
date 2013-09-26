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

import java.util.Random;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sibext.owlet.R;

public class SovenokLayout extends RelativeLayout{
	private final int EYES = 0;
	private final int EYEBROW = 1;
	private final int BOW = 2;
	private final int WING = 3;
	private final int PAWLEFT = 4;
	private final int PAWRIGHT = 5;
	
	private static final int ANIMATION_COUNT = 4;
	private static final int BOW_ANIMATION = 0;
	private static final int EYES_ANIMATION = 1;
	private static final int PAW_ANIMATION= 2;
	private static final int WING_ANIMATION = 3;
	
	private ImageView sovenok;
	private ImageView bow;// бабочка
	private ImageView wing;// крыло
	private ImageView paw_left; // левая лапа
	private ImageView paw_right; // правая лапа
	private ImageView eyes; // глаза
	private ImageView eyebrow; // брови
	
	private ImageView[] parts = new ImageView[]{
			eyes,
			eyebrow,			
			bow,
			wing,
			paw_left,
			paw_right,
	};
	
	public SovenokLayout(Context context) {
		super(context);
		sovenok = new ImageView(context);
		for(int i=0;i<parts.length;i++){
			parts[i] = new ImageView(context);
		}

		sovenok.setImageResource(R.drawable.sovenok_body);
		parts[EYES].setImageResource(R.drawable.sovenok_eyes_side);
		parts[EYEBROW].setImageResource(R.drawable.sovenok_eyebrow);
		parts[BOW].setImageResource(R.drawable.sovenok_bow);
		parts[WING].setImageResource(R.drawable.sovenok_wing);
		parts[PAWLEFT].setImageResource(R.drawable.sovenok_paw_left);
		parts[PAWRIGHT].setImageResource(R.drawable.sovenok_paw_right);
		
		this.addView(sovenok);
		for(int i=0;i<6;i++){
			this.addView(parts[i]);
		}
	}
	public void startPawAnimation(){
		Animation animLeft = AnimationUtils.loadAnimation(getContext(), R.anim.paws_animation);
		parts[PAWLEFT].startAnimation(animLeft);
		Animation animRight = AnimationUtils.loadAnimation(getContext(), R.anim.paws_animation);
		animRight.setStartOffset(700);
		parts[PAWRIGHT].startAnimation(animRight);
	}
	public void startRandomAnimation(){
		Random rand = new Random();
		int choise = rand.nextInt(ANIMATION_COUNT);
		switch (choise) {
		case BOW_ANIMATION:
			startBowAnimation();
			break;
		case WING_ANIMATION:
			startWingAnimation();
			break;
		case PAW_ANIMATION:
			startPawAnimation();
			break;
		case EYES_ANIMATION:
			startEyesMovemant();
			break;
		default:
			return;
		}
	}
	
	public void startEyesMovemant(){
		parts[EYES].setImageResource(R.drawable.sovenok_eyes_right);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.brow_animation);
		animation.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				parts[EYES].setImageResource(R.drawable.sovenok_eyes_side);
			}
		});
		parts[EYEBROW].startAnimation(animation);
		
	}
	
	public void startWingAnimation(){
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.wing_animation);
		parts[WING].startAnimation(animation);
	}
	public void startBowAnimation(){
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bow_animation);
		parts[BOW].startAnimation(animation);		
	}
	
	@Override
	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		super.setLayoutParams(params);
		setSizeParamsOfSovenokParts(params.width, params.height);
	}	
	private void setSizeParamsOfSovenokParts(int w,int h){
		sovenok.setLayoutParams(new RelativeLayout.LayoutParams(w, h));
		int[] width = new int[parts.length];
		int[] height = new int[parts.length];
		int[] marginLeft = new int[parts.length];
		int[] marginTop = new int[parts.length];
		//eyes
		width[0]=(int)(w*0.51);
		height[0]=(int)(h*0.14);
		marginLeft[0]=(int)(w*0.3);
		marginTop[0]=(int)(h*0.26);
		//brow
		width[1]=(int)(w*0.46);
		height[1]=(int)(h*0.057);
		marginLeft[1]=(int)(w*0.28);
		marginTop[1]=(int)(h*0.17);
		//bow
		width[2]=(int)(w*0.2);
		height[2]=(int)(h*0.1);
		marginLeft[2]=(int)(w*0.56);
		marginTop[2]=(int)(h*0.46);
		//wing
		width[3]=(int)(w*0.33);
		height[3]=(int)(h*0.35);
		marginLeft[3]=(int)(w*0.16);
		marginTop[3]=(int)(h*0.46);
		//left paw
		width[4]=(int)(w*0.14);
		height[4]=(int)(h*0.1);
		marginLeft[4]=(int)(w*0.47);
		marginTop[4]=(int)(h*0.87);
		//right paw
		width[5]=(int)(w*0.16);
		height[5]=(int)(h*0.11);
		marginLeft[5]=(int)(w*0.64);
		marginTop[5]=(int)(h*0.86);
		
		for (int i = 0; i < 6; i++) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					width[i], height[i]);
			params.leftMargin = marginLeft[i];
			params.topMargin = marginTop[i];
			parts[i].setLayoutParams(params);
		}
	}
}
