package com.mahoo.paint;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.mahoo.paint.R;
import com.mahoo.paint.util.BackgroundUtil;
import com.mahoo.paint.util.CasualColorUtil;
import com.mahoo.paint.util.CasualCrayonUtil;
import com.mahoo.paint.util.CasualWaterUtil;
import com.mahoo.paint.util.DrawAttribute;
import com.mahoo.paint.util.EraserUtil;
import com.mahoo.paint.util.GeometryUtil;
import com.mahoo.paint.util.PicUtil;
import com.mahoo.paint.util.StamppenUtil;
import com.mahoo.paint.util.StickerUtil;
import com.mahoo.paint.view.DrawView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DrawActivity extends Activity {	
	private DrawView drawView;
	private LinearLayout topBar;
    private LinearLayout bottomBar;
    public int brushDrawableId;
    private HorizontalScrollView markerList;
    private HorizontalScrollView crayonList;
    private HorizontalScrollView colorList;
    private HorizontalScrollView stamppenList;
    private HorizontalScrollView backgroundList;
    private HorizontalScrollView eraserList;
    private LinearLayout stickerList;
    private LinearLayout geometryTool;
    private LinearLayout picTool; 
    private LinearLayout wordTool; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.draw);        
	    //设置默认选择黑色水笔
		ImageView imageView = (ImageView)findViewById(R.id.marker01);
	    imageView.scrollTo(0, 0);
	    brushDrawableId = R.id.marker01;
	    topBar = (LinearLayout)this.findViewById(R.id.drawtop);
	    bottomBar = (LinearLayout)this.findViewById(R.id.drawbottom);
	    //此处有用处，切勿删除
	    topBar.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {		
	    	}
	    });
	    //此处有用处，切勿删除
	    bottomBar.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View v) {
			}	        		    	
	    });
	    drawView = (DrawView)this.findViewById(R.id.drawview);	    
	    Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if(bundle != null) {
			String backgroundBitmapPath = 
				bundle.getString("com.mahoo.paint.BackgroundBitmapPath");
			Bitmap bitmap = BitmapFactory.decodeFile(backgroundBitmapPath);
			drawView.setBackgroundBitmap(bitmap, false);
		}
		markerList = (HorizontalScrollView)this.findViewById(R.id.markerlist);
		crayonList = (HorizontalScrollView)this.findViewById(R.id.crayonlist);
		colorList = (HorizontalScrollView)this.findViewById(R.id.colorlist);
		geometryTool = (LinearLayout)this.findViewById(R.id.geometrytool);
		stamppenList = (HorizontalScrollView)this.findViewById(R.id.stamppenlist);
		backgroundList = (HorizontalScrollView)this.findViewById(R.id.backgroundlist);
		stickerList = (LinearLayout)this.findViewById(R.id.stickerlist);
		eraserList = (HorizontalScrollView)this.findViewById(R.id.eraserlist);	
		picTool = (LinearLayout)this.findViewById(R.id.pictool);
		wordTool = (LinearLayout)this.findViewById(R.id.wordtool);
		markerList.setVisibility(View.VISIBLE);
		crayonList.setVisibility(View.INVISIBLE);
		colorList.setVisibility(View.INVISIBLE);
		geometryTool.setVisibility(View.INVISIBLE);
		stamppenList.setVisibility(View.INVISIBLE);
		backgroundList.setVisibility(View.INVISIBLE);
		stickerList.setVisibility(View.INVISIBLE);
		eraserList.setVisibility(View.INVISIBLE);
		picTool.setVisibility(View.INVISIBLE);
		wordTool.setVisibility(View.INVISIBLE);
		//下面的菜单
	    ImageView casualMarkerButton = (ImageView)this.findViewById(R.id.drawcasualmarkerbtn);
	    ImageView casualCrayonButton = (ImageView)this.findViewById(R.id.drawcasualcrayonbtn);
	    ImageView casualColorButton = (ImageView)this.findViewById(R.id.drawcasualcolorbtn);
	    ImageView drawGraphicButton = (ImageView)this.findViewById(R.id.drawgraphicbtn);
	    ImageView stamppenButton = (ImageView)this.findViewById(R.id.stamppenbtn);
	    ImageView backgroundButton = (ImageView)this.findViewById(R.id.drawbackgroundbtn);
	    ImageView stickerButton = (ImageView)this.findViewById(R.id.stickerbtn);
	    ImageView eraserButton = (ImageView)this.findViewById(R.id.draweraserbtn);
	    ImageView picButton = (ImageView)this.findViewById(R.id.drawpicbtn);
	    ImageView wordsButton = (ImageView)this.findViewById(R.id.drawwordsbtn);
	    //上面的菜单
	    ImageButton drawbackButton = (ImageButton)this.findViewById(R.id.drawbackbtn);
	    ImageButton saveButton = (ImageButton)this.findViewById(R.id.drawsavebtn);
	    ImageButton visibleButton = (ImageButton)this.findViewById(R.id.drawvisiblebtn);
	    ImageView undoButton = (ImageView)this.findViewById(R.id.drawundobtn);
	    ImageView redoButton = (ImageView)this.findViewById(R.id.drawredobtn);
	    //选择水彩笔按钮
	    casualMarkerButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.VISIBLE);					
		    		crayonList.setVisibility(View.INVISIBLE);				
		    		colorList.setVisibility(View.INVISIBLE);					
		    		geometryTool.setVisibility(View.INVISIBLE);				
		    		stamppenList.setVisibility(View.INVISIBLE);	    		
		    		backgroundList.setVisibility(View.INVISIBLE);					
		    		stickerList.setVisibility(View.INVISIBLE);					
		    		eraserList.setVisibility(View.INVISIBLE);					
		    		picTool.setVisibility(View.INVISIBLE);
		    		wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        	
	    });	        
	    //选择蜡笔按钮        
	    casualCrayonButton.setOnTouchListener(new OnTouchListener(){				

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);				
		    		crayonList.setVisibility(View.VISIBLE);				
		    		colorList.setVisibility(View.INVISIBLE);					
		    		geometryTool.setVisibility(View.INVISIBLE);				
		    		stamppenList.setVisibility(View.INVISIBLE);					
		    		backgroundList.setVisibility(View.INVISIBLE);				
		    		stickerList.setVisibility(View.INVISIBLE);					
		    		eraserList.setVisibility(View.INVISIBLE);				
		    		picTool.setVisibility(View.INVISIBLE);
		    		wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        
	    });	        
	    //选择颜料笔按钮	        
	    casualColorButton.setOnTouchListener(new OnTouchListener(){				

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);					
		    		crayonList.setVisibility(View.INVISIBLE);
		    		colorList.setVisibility(View.VISIBLE);
		    		geometryTool.setVisibility(View.INVISIBLE);
		    		stamppenList.setVisibility(View.INVISIBLE);					
		    		backgroundList.setVisibility(View.INVISIBLE);
		    		stickerList.setVisibility(View.INVISIBLE);
		    		eraserList.setVisibility(View.INVISIBLE);	    		
		    		picTool.setVisibility(View.INVISIBLE);
		    		wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        	
	    });
	    //选择了图形按钮
	    drawGraphicButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
		    		crayonList.setVisibility(View.INVISIBLE);
		    		colorList.setVisibility(View.INVISIBLE);
		    		geometryTool.setVisibility(View.VISIBLE);
		    		stamppenList.setVisibility(View.INVISIBLE);
		    		backgroundList.setVisibility(View.INVISIBLE);
		    		stickerList.setVisibility(View.INVISIBLE);
		    		eraserList.setVisibility(View.INVISIBLE);
		    		picTool.setVisibility(View.INVISIBLE);
		    		wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				//flawOperation();
				return true;
			}  		    	
	    });
	    //选择印花按钮        
	    stamppenButton.setOnTouchListener(new OnTouchListener(){				

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
		    		crayonList.setVisibility(View.INVISIBLE);
		    		colorList.setVisibility(View.INVISIBLE);
		    		geometryTool.setVisibility(View.INVISIBLE);
		    		stamppenList.setVisibility(View.VISIBLE);
		    		backgroundList.setVisibility(View.INVISIBLE);
		    		stickerList.setVisibility(View.INVISIBLE);
		    		eraserList.setVisibility(View.INVISIBLE);
		    		picTool.setVisibility(View.INVISIBLE);
		    		wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        		    	
	    });
	    //选择壁纸按钮
	    backgroundButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
					crayonList.setVisibility(View.INVISIBLE);
					colorList.setVisibility(View.INVISIBLE);
					geometryTool.setVisibility(View.INVISIBLE);
					stamppenList.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.VISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					eraserList.setVisibility(View.INVISIBLE);
					picTool.setVisibility(View.INVISIBLE);	
					wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        		        
	    });   
	    //选择素材按钮
	    stickerButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
					crayonList.setVisibility(View.INVISIBLE);
					colorList.setVisibility(View.INVISIBLE);
					geometryTool.setVisibility(View.INVISIBLE);
					stamppenList.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.VISIBLE);
					eraserList.setVisibility(View.INVISIBLE);
					picTool.setVisibility(View.INVISIBLE);	
					wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        		       
	    });	        
	    //选择橡皮按钮
	    eraserButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
					crayonList.setVisibility(View.INVISIBLE);
					colorList.setVisibility(View.INVISIBLE);
					geometryTool.setVisibility(View.INVISIBLE);
					stamppenList.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					eraserList.setVisibility(View.VISIBLE);
					picTool.setVisibility(View.INVISIBLE);
					wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        	
	    });	        
	    //选择图片按钮
	    picButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
					crayonList.setVisibility(View.INVISIBLE);
					colorList.setVisibility(View.INVISIBLE);
					geometryTool.setVisibility(View.INVISIBLE);
					stamppenList.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					eraserList.setVisibility(View.INVISIBLE);
					picTool.setVisibility(View.VISIBLE);	
					wordTool.setVisibility(View.INVISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        		      
	    });
	    //点击文字按钮
	    wordsButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(DrawAttribute.backgroundOnClickColor);break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(0x00ffffff);
					markerList.setVisibility(View.INVISIBLE);
					crayonList.setVisibility(View.INVISIBLE);
					colorList.setVisibility(View.INVISIBLE);
					geometryTool.setVisibility(View.INVISIBLE);
					stamppenList.setVisibility(View.INVISIBLE);
					backgroundList.setVisibility(View.INVISIBLE);
					stickerList.setVisibility(View.INVISIBLE);
					eraserList.setVisibility(View.INVISIBLE);
					picTool.setVisibility(View.INVISIBLE);
					wordTool.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_CANCEL:v.setBackgroundColor(0x00ff0000);				
				}
				flawOperation();
				return true;
			}	        		      
	    });
	    //设置水彩笔点击监听
	    CasualWaterUtil casualWaterUtil = new CasualWaterUtil(this, drawView);
	    casualWaterUtil.casualWaterPicSetOnClickListener();
	    //设置蜡笔点击监听
	    CasualCrayonUtil casualCrayonUtil = new CasualCrayonUtil(this, drawView);
	    casualCrayonUtil.casualCrayonPicSetOnClickListener();
	    //设置颜料笔点击监听
	    CasualColorUtil casualColorUtil = new CasualColorUtil(this, drawView);
	    casualColorUtil.casualColorPicSetOnClickListener();
	    //设置图形点击监听
	    GeometryUtil graphicUtil = new GeometryUtil(this, drawView);
	    graphicUtil.graphicPicSetOnClickListener();
	    //设置印花点击的监听
	    StamppenUtil stamppenUtil = new StamppenUtil(this, drawView);
	    stamppenUtil.stampPenPicSetOnClickListener();
	    //设置背景图片点击的监听
	    BackgroundUtil backgroundUtil = new BackgroundUtil(this, drawView);
	    backgroundUtil.backgroundPicSetOnClickListener();
	    //设置橡皮点击的监听
	    EraserUtil eraserUtil = new EraserUtil(this, drawView);
	    eraserUtil.eraserPicSetOnClickListener();
	    //设置素材点击的监听
	    StickerUtil stickerUtil = new StickerUtil(this, drawView);
	    stickerUtil.stickerPicSetOnClickListener();
	    //设置图片点击的监听
	    PicUtil picUtil = new PicUtil(this);
	    picUtil.picPicSetOnClickListener();	       
	    //点击返回按钮
        drawbackButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
	        	intent.setClass(DrawActivity.this,MainActivity.class);
	        	startActivity(intent);
	        	DrawActivity.this.finish();	        		
        	}	        
        }); 
        //点击保存按钮
        saveButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		drawView.saveBitmap();
        		Intent intent = new Intent();
        		intent.setClass(DrawActivity.this,PreviewActivity.class);
        		startActivity(intent);
        		DrawActivity.this.finish();
        	}
        });
        //点击undo按钮
        undoButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		drawView.undo();
        	}
        });  
        //点击undo按钮
        redoButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		drawView.redo();
        	}
        });  
        //点击隐形按钮
        visibleButton.setOnClickListener(new Button.OnClickListener(){
        	public void onClick(View v)
        	{
        		setUpAndButtomBarVisible(false);
        	}
        });        
	}
	
	public void setUpAndButtomBarVisible(boolean isVisible) {
		if(isVisible) {
			topBar.setVisibility(View.VISIBLE);
    		bottomBar.setVisibility(View.VISIBLE);
		}
		else {
			topBar.setVisibility(View.INVISIBLE);
    		bottomBar.setVisibility(View.INVISIBLE);
		}
	}
		
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindDrawables(findViewById(R.id.drawroot));
		drawView.freeBitmaps();
		System.gc();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//PIC_STORAGE_BG=1 PIC_STORAGE_STICKER=2 PIC_CAMERA_BG=3 PIC_CAMERA_STICKER=4
		if ((requestCode >= 1 && requestCode <= 4) && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			Bitmap bitmap = null;
			AssetFileDescriptor fileDescriptor = null; 
			BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;  
	        try {
	        	fileDescriptor = this.getContentResolver().openAssetFileDescriptor(selectedImage,"r"); 	
	        }
	        catch (FileNotFoundException e) { 
	    	} 
	        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options); 
	        //获取这个图片的宽和高  
	        float scaleWidth = options.outWidth * 1.0f / DrawAttribute.screenWidth;
		    float scaleHeight = options.outHeight * 1.0f / DrawAttribute.screenHeight;
		    float scale = scaleWidth > scaleHeight?scaleWidth : scaleHeight;
		    if(scale < 1.0f) {
		    	scale = 1.0f;
		    }
		    options.inJustDecodeBounds = false;
		    options.inSampleSize = (int)scale;
		    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
		    try {
		    	fileDescriptor.close(); 
		    }
		    catch(IOException e) {
		    }	
		    if(requestCode == 1 || requestCode == 3) {
				drawView.setBackgroundBitmap(bitmap, false);
			}
			else {
				drawView.addStickerBitmap(bitmap);
			}
		}
	}
	
	private void flawOperation() {
		drawView.setBasicGeometry(null);
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
    	intent.setClass(DrawActivity.this,MainActivity.class);
    	startActivity(intent);
    	DrawActivity.this.finish();	
	}	
	
	private void unbindDrawables(View view) {
		if(view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if(view instanceof ViewGroup) {
			for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();  
		}
	}
}
