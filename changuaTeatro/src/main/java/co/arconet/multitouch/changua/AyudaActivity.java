
package co.arconet.multitouch.changua;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;


public class AyudaActivity extends Activity{
	
	Intent i;
	Button btnCaptura;
	Button btnHome;
	final static int constante=0;
	WebView mAyuda;
	Bitmap fotito;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.ayuda);
		 btnCaptura = (Button)findViewById(R.id.btnCaptura);
		 btnHome = (Button)findViewById(R.id.btnHome);
		 mAyuda = (WebView) findViewById(R.id.about_html_text);
		  
	     mAyuda.loadUrl ( "file:///android_asset/ayuda.html" );  
	     
	     btnCaptura.setOnClickListener(new OnClickListener() {

		        public void onClick(View v) {
		        	Intent j = new Intent(AyudaActivity.this,PhotoSortrActivity.class);
		        
		        	startActivity(j);

					
		        }
		    });
	     
	     btnHome.setOnClickListener(new OnClickListener() {

		        public void onClick(View v) {
		        	Intent j = new Intent(AyudaActivity.this,PrincipalActivity.class);
		        
		        	startActivity(j);

					
		        }
		    });
			
	    
	}
	
	
}