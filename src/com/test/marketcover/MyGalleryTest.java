package com.test.marketcover;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
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
			ImageView imageview=new ImageView(MyGalleryTest.this);
			imageview.setImageResource(image[position]);
//			imageview.setLayoutParams(new Gallery.LayoutParams(160, 200));
//			imageview.setBackgroundResource(R.drawable.gallery/*mGalleryItemBackground*/);
			imageview.setTag(position);
			return imageview;
		}
    	
    }
}