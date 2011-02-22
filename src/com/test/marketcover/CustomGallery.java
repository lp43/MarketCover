package com.test.marketcover;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.test.marketcover.R;

public class CustomGallery extends Gallery {
	
	Context context;
	Camera camera = new Camera();


	
	public CustomGallery(Context context) {
		super(context);
		this.context = context;
		
		this.setStaticTransformationsEnabled(true);
		
	}
	
	public CustomGallery(Context context,AttributeSet attrs) {
	  super(context, attrs);
      this.setStaticTransformationsEnabled(true);
	}
	
	public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
	 super(context, attrs, defStyle);
	 this.setStaticTransformationsEnabled(true);
	}
	
	/**
	*該類別在回傳每個子視圖的各個中心點位置在螢幕的x何處
	* Get the Centre of the View
	* @return The centre of the given view.
	* 
	*/
	    private static int getCenterOfView(View view) {
//	    	Log.i(tag, "childLeft: "+view.getLeft()+", childWidth: "+view.getWidth());
	        return view.getLeft() + view.getWidth() / 2;
	    }
	    
	    /**
	     * 取得CoverFlow的正中央
	     * @return
	     */
	    private int getCenterOfCoverflow() {
//	    	Log.i("tag", "center "+(getWidth()/2+getLeft()));//沒問題
//	    	Log.i("tag", "width: "+getWidth());//沒問題
//	    	Log.i("tag", "Left: "+getLeft());//沒問題
//	    	Log.i("tag", "Right: "+getRight());//沒問題
//	        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	    	return (getWidth()/2+getLeft());
	    }
	    
	    /**
	     * 取得CoverFlow的最左邊
	     * @return
	     */
	    private int getLeftOfCoverflow() {
//	    	Log.i("tag", "coverLeft: "+this.getLeft());
	        return this.getLeft();
	    }
	    /**
	     * 取得CoverFlow的一半寬的長度
	     * @return
	     */
	    private int getHalfOfCoverflow() {
	        return this.getWidth()/2;
	    }	    

	    
	    
	/**
	 * 這裡面的Child是螢幕當前看到的Childs們
	 */
	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
//		Log.i("tag", "into setChildStatic");
		int childCenter = getCenterOfView(child);
		int childWidth = child.getWidth();
		int childLeft = child.getLeft();
		final int mCoveflowCenter = getCenterOfCoverflow();
		final int mCoverflowLeft = getLeftOfCoverflow();
//	    final int imageHeight = child.getLayoutParams().height;
	    final int imageHeight = child.getHeight();
//	    final int imageWidth = child.getLayoutParams().width;
	    final int imageWidth = child.getWidth();
//		Log.i("tag", "center: "+mCoveflowCenter);
	    
	    
		 t.clear();		 
		 t.setTransformationType(Transformation./*TYPE_ALPHA*//*TYPE_BOTH*/TYPE_MATRIX);//Transform type is matrix

		 camera.save();
		 
		
		 
		 Matrix matrix = t.getMatrix();
		 
		 camera.translate(0f, 0f, 100f);
		 
 
		 
		 if (childCenter == mCoveflowCenter) {
			 //Z值影響的是圖片的大小，值越大的話圖片越小
//			camera.translate(-10f, 10f, -150f);
			 camera.translate(0f, 0f, -150f);
			 
			
		 }
		 
		 if(childCenter < mCoveflowCenter){
			 		 //如果CoverFlow的寬小於300dip，分母會小於零造成運算錯誤
			 		//若讓分母加上浮點數，又會讓整體畫面算法怪怪的
			 camera.translate(0f, 0f, -150f+((mCoveflowCenter-childCenter)/((mCoveflowCenter-mCoverflowLeft)/200)));
			
			
		 }
		 
		 if(childCenter > mCoveflowCenter){

			 camera.translate(0f, 0f, -150f-((mCoveflowCenter-childCenter)/((mCoveflowCenter-mCoverflowLeft)/200)));
			 
			
		 }
//		 float unit=getHalfOfCoverflow()/230f;
//		 Log.i("tag", "CoverHalf: "+getHalfOfCoverflow());
//		 Log.i("tag", "unit: "+unit);
		 
		 


		 
		 camera.getMatrix(matrix);
		
		 
		  //动画擴散點設為從中點往外擴散 
		 matrix.preTranslate(-(imageWidth/2), -(imageHeight/2));
//		 matrix.preTranslate(-imageWidth, -imageHeight);
		 //移动动画起始点，把参考点（0,0）移动到View中间
		 matrix.postTranslate((imageWidth/2), (imageHeight/2));
//		 matrix.postTranslate(imageWidth, imageHeight);
		 camera.restore();
		 setChildAlpha(child);
		 
		return true;
	}




	private void setChildAlpha(View child){
//		Log.i("tag", "into setChildAlpha");
//		Log.i("tag", "alpha: "+alpha);
		final int mCoveflowCenter = getCenterOfCoverflow();
//		Log.i("tag", "mCoveflowCenter"+mCoveflowCenter);
		int childCenter = getCenterOfView(child);
//		Log.i("tag", "childCenter"+childCenter);
		float unit=getHalfOfCoverflow()/230f;
//		Log.i("tag", "unit"+unit);
		
		float before_measure = (mCoveflowCenter-childCenter)/unit;
		int measure = Math.abs((int)before_measure);
//		 Log.i("tag", "measure: "+measure);
		int after_measure = measure>230?230:measure;
		 
		 Log.i("tag", "after_measure: "+after_measure);

			 ImageView iv =(ImageView) child;
			 iv.setAlpha(20);
		
//		 Log.i("tag", "id: "+child.getTag().toString());
		 
		
		 
		 
		 
		 try{
			 ImageView iv0=(ImageView) this.getChildAt(0);
			 iv0.setAlpha(255-after_measure);
			 
			 ImageView iv1=(ImageView) this.getChildAt(1);
			 iv1.setAlpha(255-after_measure);
			 
			 ImageView iv2=(ImageView) this.getChildAt(2);
			 iv2.setAlpha(255-after_measure); 
			 
			 ImageView iv3=(ImageView) this.getChildAt(3);
			 iv3.setAlpha(255-after_measure);
		 }catch(NullPointerException e){
			 Log.i("tag", "NullPoinerException: "+e.getMessage());
		 }
		 
		 
//		 ImageView iv2=(ImageView) this.getChildAt(1);
//		 iv2.setAlpha(255-after_measure);
		 
	
//		 Canvas canvas = new Canvas();
//		 canvas.drawText("123", 10, 10, new Paint());
//		 iv.draw(canvas);
	}

//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		Log.i("tag", "into onFling ");
////		ImageView iv0=(ImageView) this.getChildAt(0);
////		
////		iv0.setAlpha(255-after_measure);
//		
//		setChildAlpha(this.findViewWithTag(4));
//		return true;
//	}



	
	
}
