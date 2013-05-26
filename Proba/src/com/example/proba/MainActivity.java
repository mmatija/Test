package com.example.proba;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	boolean go=true;
	boolean skaliraj=false;
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Duck d = new Duck(this);
		
		LinearLayout contentView=(LinearLayout) findViewById(R.id.layout);
		contentView.addView(d);
		ExecutorService executor=Executors.newFixedThreadPool(1);
		Thread thread=new Thread(new Runnable() {			//Dretva koja svaku sekundu mijenja zastavicu go
			
			@Override
			public void run() {	
				while(true){
					try {
						Thread.sleep(1000);				//cekaj jednu sekundu
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					go=true;
					if(i%2==0) skaliraj=true;
					i++;
				}
			}
		});
		
		executor.execute(thread);		//pokreni dretvu
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class Duck extends View{
		public Duck(Context context) {
			super(context);
			initialY=screenH;
			initialX=0;
			duck=BitmapFactory.decodeResource(getResources(), R.drawable.duck);
			bgr=BitmapFactory.decodeResource(getResources(), R.drawable.river);
		}
		boolean keepRunning=true;
		boolean move=false;
		int x=0;
		int dx=1;
		int y=0;
		int initialY;
		int initialX;
		int screenW, screenH;
		Bitmap duck, bgr;
		
		public void onSizeChanged(int w, int h, int oldw, int oldh){
			super.onSizeChanged(w, h, oldw, oldh);
			screenW=w;
			screenH=h;
		}
		
		public void onDraw(Canvas canvas){
			super.onDraw(canvas);
			x+=10;
			y=screenH-duck.getHeight();
			canvas.drawBitmap(bgr, 0, 0, null);
			//canvas.drawBitmap(duck, x, y, null);
			while(go==false){
				//cekaj u beskonacnoj petlji dok se go ne promijeni u true
			}
			if (skaliraj==true){
				skaliraj=false;
				canvas.scale(1.5f, 1.50f, x+duck.getWidth()/2, y+duck.getHeight()/2);
			}
			if (go==true){
			go=false; 
			canvas.drawBitmap(duck, x, y,null);
			invalidate();
			}
		}
		
	}

}
