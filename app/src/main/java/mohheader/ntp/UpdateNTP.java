package mohheader.ntp;

import future.android.net.SntpClient;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mohheader on 6/16/14.
 */
public class UpdateNTP extends AsyncTask<Void,Void,Long> {
    NTPHome context;
    final static String NTP_SERVER = "0.ubuntu.pool.ntp.org";

    public UpdateNTP(NTPHome _context){
        context = _context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Void... params) {
        long now = 0;
        SntpClient client = new SntpClient();
        if (client.requestTime(NTP_SERVER,NTPHome.TIMEOUT)) {
            now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();
        }
        return now;
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        if (result != 0) {
            context.connectionSucceed(result);
        }
        else {
            context.connectionError();
        }
    }
}