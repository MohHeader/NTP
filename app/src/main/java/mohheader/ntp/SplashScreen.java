package mohheader.ntp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import mohheader.ntp.utils.ConectionDetector;

/**
 * Created by mohheader on 6/16/14.
 */
public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new checkConnection().execute();
        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new checkConnection().execute();
            }
        });
    }
    private class checkConnection extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.loader_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.error_layout).setVisibility(View.GONE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return ConectionDetector.isConneced();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result){
                Intent i = new Intent(SplashScreen.this, NTPHome.class);
                startActivity(i);
            }else{
                Toast.makeText(SplashScreen.this,"No Internet",Toast.LENGTH_SHORT).show();
                findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.loader_layout).setVisibility(View.GONE);
            }
        }
    }
}
