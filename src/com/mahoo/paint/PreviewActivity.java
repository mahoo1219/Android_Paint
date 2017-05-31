package com.mahoo.paint;

import java.io.File;
import java.util.ArrayList;

import com.mahoo.paint.R;
import com.mahoo.paint.util.DrawAttribute;
import com.mahoo.paint.util.StorageInSDCard;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PreviewActivity extends Activity {
	private PreviewAdapter previewAdapter;
	private int selectedGridViewPosition;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); 
		setContentView(R.layout.preview);
		gridView = (GridView)this.findViewById(R.id.previewgridview);
		previewAdapter = new PreviewAdapter(this);
		gridView.setAdapter(previewAdapter);
		this.registerForContextMenu(gridView);
		//点击图片
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
	        	intent.setClass(PreviewActivity.this, BrowseActivity.class);	   
	        	intent.putExtra("com.mahoo.paint.BitmapPath",
	        			(previewAdapter.getBitmapsPath()));
	        	intent.putExtra("com.mahoo.paint.Position", arg2);
	        	startActivity(intent);
	        	PreviewActivity.this.finish();
			}		 
		});
		//长按图片
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				PreviewActivity.this.openContextMenu(gridView);
				selectedGridViewPosition = arg2;
				return true;
			}			
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.preview_menu, menu);
	}
			
	@Override	
	public boolean onContextItemSelected(MenuItem item) {
		//删除菜单
		if(item.getItemId() == R.id.preview_delete) {
			AlertDialog.Builder builder = new AlertDialog.Builder(PreviewActivity.this)
			.setIcon(drawable.ic_dialog_alert).setMessage("确定删除？")
	   		.setPositiveButton("OK", new DialogInterface.OnClickListener() { 	
	   			@Override
	   			public void onClick(DialogInterface dialog, int which) {
	   				previewAdapter.deleteBitmap(selectedGridViewPosition);	
	   			}	
	   		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {		
	   			@Override				
	   			public void onClick(DialogInterface dialog, int which) {						
	   				//dialog.cancel();						
	   				dialog.dismiss();										
	   			}
	   		});
			builder.create().show();
		}
		//预览菜单
		else if(item.getItemId() == R.id.preview_view) {
			Intent intent = new Intent();	        	
			intent.setClass(PreviewActivity.this,BrowseActivity.class);	   
			intent.putExtra("com.mahoo.paint.BitmapPath", previewAdapter.getBitmapsPath());
			intent.putExtra("com.mahoo.paint.Position", selectedGridViewPosition);
			startActivity(intent);
			PreviewActivity.this.finish();				
		}
		//分享菜单
		else if(item.getItemId() == R.id.preview_share) {
			Intent picMessageIntent = new Intent(android.content.Intent.ACTION_SEND); 
			String sharePath = previewAdapter.getBitmapsPath().get(selectedGridViewPosition);
			if(sharePath.endsWith(".png")) {
				picMessageIntent.setType("image/png");  
			}
			else if(sharePath.endsWith(".jpg")) {
				picMessageIntent.setType("image/jpg");
			}
			else if(sharePath.endsWith(".bmp")) {
				picMessageIntent.setType("image/bmp");
			}
			File shareFile = new File(sharePath);
			picMessageIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(shareFile));  
			startActivity(Intent.createChooser(picMessageIntent, "选择分享的应用:"));
		}
		return true;
	}
	 	 
	@Override		
	public void onBackPressed() {		 		
		Intent intent = new Intent();  
		intent.setClass(PreviewActivity.this,MainActivity.class);  
		startActivity(intent);
		PreviewActivity.this.finish();
	}	
		
	@Override	
	protected void onDestroy() {		
		super.onDestroy(); 	
		previewAdapter.freeBitmaps();	
		System.gc();	
	}
}

class PreviewAdapter extends BaseAdapter {
	private ArrayList<String> fileList;
	private ArrayList<ImageView> imageViews;
	private ArrayList<Bitmap> bitmaps;

	public PreviewAdapter(Context context) {
		fileList = StorageInSDCard.getBitmapsPathFromExternalStorage();
		imageViews = new ArrayList<ImageView>(fileList.size());
		bitmaps = new ArrayList<Bitmap>(fileList.size());
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 5;
		for(int i = 0; i < fileList.size(); i++) {
			ImageView imageView = new ImageView(context);
			Bitmap bitmap = BitmapFactory.decodeFile(fileList.get(i), opts);
			imageView.setImageBitmap(bitmap);
			imageView.setLayoutParams(new GridView.LayoutParams(DrawAttribute.screenWidth/3-20,120));
		    imageView.setScaleType(ScaleType.CENTER_CROP);
		    imageViews.add(imageView);
		    bitmaps.add(bitmap);
		}
	}
	
	public PreviewAdapter(ArrayList<String> fileList ,ArrayList<ImageView> imageViews ,
			ArrayList<Bitmap> bitmaps) {
		this.fileList = fileList;
		this.bitmaps = bitmaps;
		this.imageViews = imageViews;
	}
	
	@Override
	public int getCount() {
		return fileList.size();
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
		return imageViews.get(position);
	}	
	
	public void freeBitmaps() {
		for(int i = 0; i < bitmaps.size(); i++) {
			bitmaps.get(i).recycle();
		}
	}
	
	public ArrayList<String> getBitmapsPath() {
		return fileList;
	}

	public void deleteBitmap(int position) {
		File file = new File(fileList.remove(position));
		file.delete();
		imageViews.remove(position);		
		bitmaps.remove(position).recycle();
		this.notifyDataSetChanged();
	}
}
