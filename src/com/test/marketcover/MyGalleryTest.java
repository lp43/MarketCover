package com.test.marketcover;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import com.test.marketcover.R;

public class MyGalleryTest extends Activity {
	public static int[] image={
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    		R.drawable.stage_card,
    };
	 int mGalleryItemBackground;	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        CustomGallery custom_gallery=(CustomGallery) findViewById(R.id.custom_gallery);
        custom_gallery.setAdapter(new myAdapter(this));

        custom_gallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MyGalleryTest.this, "positon: "+position, Toast.LENGTH_SHORT).show();
				
			}
        	
        });
        custom_gallery.setSpacing(-50);
        custom_gallery.setSelection(image.length/2);
        
    }
    
    
    public ImageView createReflectedImages(int position) {
    	int gap = 2;
    	//get original image
    	Bitmap originalImage = BitmapFactory.decodeResource(this.getResources(), image[0]);
    	int width = originalImage.getWidth();
    	int height = originalImage.getHeight();
    	
    	Matrix matrix = new Matrix();
    	matrix.preScale(1, -1);
    	//create a new mirror
    	Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/3, width, height/3, matrix, false);
    	
    	//set a range which use by image and mirror
    	Bitmap image_with_mirror = Bitmap.createBitmap(width, height+height/3, Config.ARGB_8888);
    	
    	Canvas canvas = new Canvas(image_with_mirror);
    	//Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        //Draw in the reflection
        canvas.drawBitmap(reflectionImage,0, height + gap, null);
        
        //開始畫漸層
        LinearGradient shader = new LinearGradient(0, height, 0, image_with_mirror.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        //去邊
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        //從下半邊重繪
        canvas.drawRect(0, height, width,
        		image_with_mirror.getHeight() + gap, paint);
        
        //將重新製定的ImageView套進來
        ImageView img = new ImageView(this);
        img.setImageBitmap(image_with_mirror);
        img.setTag(position);
        
        
		return img;
    	
    }
    
    private class myAdapter extends BaseAdapter{
    	
    	public myAdapter(Context c){
//    		 TypedArray a = c.obtainStyledAttributes(R.styleable./*Custom_Gallery.*/Gallery1);
//    	        mGalleryItemBackground = a.getResourceId(
//    	              R.drawable.tag /* R.styleable.Gallery1_android_galleryItemBackground*/, 0);
//    	        a.recycle();
    	}
    	
		@Override
		public int getCount() {
			
			return image.length;
		}

		@Override
		public Object getItem(int position) {
	
			return position;
		}

		@Override
		public long getItemId(int position) {
	
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
//			ImageView imageview=new ImageView(MyGalleryTest.this);
//			imageview.setImageResource(image[position]);
////			imageview.setLayoutParams(new Gallery.LayoutParams(160, 200));
////			imageview.setBackgroundResource(R.drawable.gallery/*mGalleryItemBackground*/);
//			imageview.setTag(position);
			
			return createReflectedImages(position);
//			return imageview;
		}
    	
    }
}