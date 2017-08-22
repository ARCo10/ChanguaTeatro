
package co.arconet.multitouch.changua;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.ExifInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class PhotoSortrActivity extends Activity{
	
	PhotoSortrView photoSorter;
	Intent i;
	Button btnCaptura;
	Button btnEnviar;
	Button btnAyuda;
	Button btnHome;
	final static int constante=0;
	final static int constante1=1;
	Bitmap fotito;
	File f;
	Uri ficheroSalidaUri;
	File file2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoSorter = new PhotoSortrView(this);
		setContentView(R.layout.main);

	    
		photoSorter = (PhotoSortrView)findViewById(R.id.myView);
		btnCaptura = (Button)findViewById(R.id.btnCaptura);
		btnEnviar = (Button)findViewById(R.id.btnEnviar);
		btnAyuda = (Button)findViewById(R.id.btnAyuda);
		btnHome = (Button)findViewById(R.id.btnHome);
		btnCaptura.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	        	i= new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	        	
	        	file2 = new File(Environment.getExternalStorageDirectory(),  "foto.jpg");
	        	
	        	if(file2.exists())
	        		file2.delete();
	        	ficheroSalidaUri = Uri.fromFile(file2);
	        	i.putExtra(MediaStore.EXTRA_OUTPUT, ficheroSalidaUri);
	        	if (i.resolveActivity(getPackageManager()) != null) {
                    
				startActivityForResult(i,constante);}else{
				    Toast.makeText(getApplicationContext(), "Usted tiene su perfil restringido para el uso de la c�mara", Toast.LENGTH_LONG).show();
                	
				}
		    }
	    });
		
		btnAyuda.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	        	Intent j = new Intent(PhotoSortrActivity.this,AyudaActivity.class);
	        	startActivity(j);
            }
	    });
		
		btnHome.setOnClickListener(new OnClickListener() {

	        public void onClick(View v) {
	        	Intent j = new Intent(PhotoSortrActivity.this,PrincipalActivity.class);
	            startActivity(j);
            }
	    });
		
		btnEnviar.setOnClickListener(new OnClickListener() {

			    @Override
	        	public void onClick(View v) {
			    	///1. Toma La captura de la pantalla
			    	photoSorter.setDrawingCacheEnabled(true);
                    Bitmap b = photoSorter.getDrawingCache();
                    File sd = Environment.getExternalStorageDirectory();
                    f = new File(sd, "changa.png");
                    try {
                         if (sd.canWrite()) {
                              f.createNewFile();
                              OutputStream os = new FileOutputStream(f);
                              b.compress(Bitmap.CompressFormat.PNG, 90, os);
                              os.close();
                         }
                    } catch (FileNotFoundException e) {
                         e.printStackTrace();
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
                    photoSorter.setDrawingCacheEnabled(false);
                    ////2. Toma los datos del celular
                   
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    File image = new File(sdCardDirectory.getAbsolutePath() + "/changa.png");
                    Uri uri = Uri.fromFile(image);
                    
                    Intent emailIntent =  new  Intent (Intent.ACTION_SEND ); 
                    emailIntent . setType ( "text/plain" ); 
                    emailIntent . putExtra (Intent.EXTRA_SUBJECT , "#comparteatro" ); 
                    //emailIntent . putExtra ( android . content . Intent . EXTRA_EMAIL , new  String []{ emailto }); 
                    emailIntent . putExtra ( Intent.EXTRA_TEXT , "#comparteatro" ); 
                    emailIntent . addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION ); 
                    emailIntent . setType ( "image/jpeg" ); 
                    emailIntent . putExtra ( Intent.EXTRA_STREAM ,  uri); 
                    
                     if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    	    startActivity(Intent.createChooser(emailIntent, "Comparta su imagen:"));
                    	} else {
                    	    Toast.makeText(getApplicationContext(), "Usted tiene su perfil restringido para compartir", Toast.LENGTH_LONG).show();
                    	}
                    
                    
                        
                   /*
                    * esto es apra abrir la galeria
                    * i= new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
    				startActivityForResult(i,constante1);
                    */
	        	        
	        		
                    
	        	}
			    
				
	        
	    });
	}
	protected void showInputDialog() {
		 
		// get prompts.xml view
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		photoSorter.loadImages(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		photoSorter.unloadImages();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			photoSorter.trackballClicked();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public static Bitmap cropBitmap(Bitmap original, int height, int width) {
		//Matrix matrix = new Matrix();
        //matrix.postRotate(270);
        //  matrix.postRotate(180);
        //  matrix.postRotate(270);
        
       // Bitmap croppedImage = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true); // rotating bitmap
        //BitmapFactory.Options options = new BitmapFactory.Options();
       // options.inPreferredConfig=Bitmap.Config.RGB_565;
       
	    Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    Canvas canvas = new Canvas(croppedImage);
	 
	    Rect srcRect = new Rect(0, 0, original.getWidth(), original.getHeight());
	    Rect dstRect = new Rect(0, 0, width, height);
	 
	    int dx = (srcRect.width() - dstRect.width()) / 2;
	    int dy = (srcRect.height() - dstRect.height()) / 2;
	 
	    // If the srcRect is too big, use the center part of it.
	    srcRect.inset(Math.max(0, dx), Math.max(0, dy));
	 
	    // If the dstRect is too big, use the center part of it.
	    dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));
	 
	    // Draw the cropped bitmap in the center
	    canvas.drawBitmap(original, srcRect, dstRect, null);
	 
	    original.recycle();
	 
	    return croppedImage;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
//		if(requestCode==constante){
		if(resultCode==Activity.RESULT_OK){
		//	ProgressDialog progress=new ProgressDialog(this);
		//	progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//	progress.setMessage("Cargando");
/*
			 ExifInterface ei = new ExifInterface(photoPath);
			  int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			  switch(orientation) {
			      case ExifInterface.ORIENTATION_ROTATE_90:
			          rotateImage(bitmap, 90);
			          break;
			      case ExifInterface.ORIENTATION_ROTATE_180:
			          rotateImage(bitmap, 180);
			          break;
			      // etc.
			  }*/
		  fotito = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/foto.jpg");
          //fotito=cropBitmap(fotito, 1000, 700);
		  
		  //Bundle ext= data.getExtras();
		   
		   //fotito=(Bitmap)ext.getParcelable("data");
		  fotito.extractAlpha();
		  fotito = Bitmap.createScaledBitmap(fotito, 900, 400, true);
		
         
		 // Matrix matrix = new Matrix();
	     // matrix.postRotate(90);

	      // Rotate Bitmap
	     // Bitmap rotated = Bitmap.createBitmap(fotito, 0, 0, 900, 400, matrix, true); 

		  Bitmap rotated=imageOreintationValidator(fotito, Environment.getExternalStorageDirectory() + "/foto.jpg");
			 
		 photoSorter.setFotico(rotated);
		 //dialog.dismiss();
			
			
		}
	/*	}else{
			/*
			new MediaScannerConnectionClient() {
				String name = Environment.getExternalStorageDirectory() + "/changa.png";
				
				private MediaScannerConnection msc = null; {
					msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
				}
				public void onMediaScannerConnected() { 
					msc.scanFile(name, null);
					
				}
				public void onScanCompleted(String path, Uri uri) { 
					
				
					msc.disconnect();
				} 
			};	

			Context context = getApplicationContext();
			CharSequence text = "La imagen esta en tu galer�a para ser compartida!!!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}*/
		
	}
	@SuppressLint("NewApi")
	private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

	    ExifInterface ei;
	   // getContentResolver().;
	    try {
	        ei = new ExifInterface(path);
	        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);
	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            bitmap = rotateImage(bitmap, 90);
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            bitmap = rotateImage(bitmap, 180);
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            bitmap = rotateImage(bitmap, 270);
	            break;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return bitmap;
	}

	private Bitmap rotateImage(Bitmap source, float angle) {

	    Bitmap bitmap = null;
	    Matrix matrix = new Matrix();
	    matrix.postRotate(angle);
	    try {
	        bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
	                matrix, true);
	    } catch (OutOfMemoryError err) {
	        err.printStackTrace();
	    }
	    return bitmap;
	}
}
