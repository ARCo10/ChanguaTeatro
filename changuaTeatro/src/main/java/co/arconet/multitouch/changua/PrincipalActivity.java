
package co.arconet.multitouch.changua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;


public class PrincipalActivity extends Activity{
	
	Intent i;
	Button btnCaptura;
	Button btnAyuda;
	final static int constante=0;
	WebView mAyuda;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.principal);
		 btnCaptura = (Button)findViewById(R.id.btnCaptura);
		 btnAyuda = (Button)findViewById(R.id.btnAyuda);
		 mAyuda = (WebView) findViewById(R.id.about_html_text);
		  
	     mAyuda.loadUrl ( "file:///android_asset/home.html" );  
	     
	     btnCaptura.setOnClickListener(new OnClickListener() {

		        public void onClick(View v) {
		        	Intent j = new Intent(PrincipalActivity.this,PhotoSortrActivity.class);
		        
		        	startActivity(j);

					
		        }
		    });
			
	     btnAyuda.setOnClickListener(new OnClickListener() {

		        public void onClick(View v) {
		        	Intent j = new Intent(PrincipalActivity.this,AyudaActivity.class);
		        
		        	startActivity(j);

					
		        }
		    });
	}
	
}