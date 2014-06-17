package mohheader.ntp;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mohheader.ntp.utils.Converter;


public class NTPHome extends Activity {

    private long time;

    private Handler timerIntervalHandler, updateNTPHandler;
    private Runnable timerIntervalRunnable, updateNTPRunnable;

    final static int ONE_SECOND = 1000;
    final static int TIMEOUT = 20000;
    final static int UPDATE_INTERVAL = 600000;
    final static int UPDATE_INTERVAL_ON_ERROR = 30000;

    private int milliSecondsToUpdate;

    private RelativeLayout timer_layout;
    private LinearLayout loader_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntphome);

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat timeFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        final SimpleDateFormat digitaltimeFormat = new SimpleDateFormat("hh:mm:ss");

        final TextView digitalClock = (TextView)findViewById(R.id.digital_clock);
        final TextView timer = (TextView)findViewById(R.id.time);
        final TextView countDown = (TextView)findViewById(R.id.count_down);

        timer_layout = (RelativeLayout) findViewById(R.id.timer_layout);
        loader_layout = (LinearLayout) findViewById(R.id.loader_layout);

        timerIntervalHandler = new Handler();
        timerIntervalRunnable = new Runnable(){
            @Override
            public void run() {
                calendar.setTimeInMillis(time);
                timer.setText(timeFormat.format(calendar.getTime()));
                digitalClock.setText(digitaltimeFormat.format(calendar.getTime()));
                time += 1000;

                if(milliSecondsToUpdate >= 0) {
                    countDown.setText(Converter.milliSecToMinutes(milliSecondsToUpdate));
                    milliSecondsToUpdate -= 1000;
                }else {
                    countDown.setText("Updating ...");
                }
                timerIntervalHandler.postDelayed(this, ONE_SECOND);
            }
        };
        updateNTPRunnable = new Runnable() {
            @Override
            public void run() {
                UpdateNTP ntp = new UpdateNTP(NTPHome.this);
                ntp.execute();
            }
        };
        updateNTPHandler = new Handler();
        updateNTPHandler.post(updateNTPRunnable);

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNTP(0);
            }
        });
    }

    public void refreshTimeInterval() {
        timerIntervalHandler.removeCallbacks(timerIntervalRunnable);
        timerIntervalHandler.post(timerIntervalRunnable);
    }
    public void updateNTP(int after) {
        milliSecondsToUpdate = after;
        updateNTPHandler.removeCallbacks(updateNTPRunnable);
        updateNTPHandler.postDelayed(updateNTPRunnable, after);
    }


    public void setTime(Long time) {
        this.time = time;
    }

    public void connectionError() {
        timer_layout.setVisibility(View.GONE);
        loader_layout.setVisibility(View.VISIBLE);
        Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        updateNTP(NTPHome.UPDATE_INTERVAL_ON_ERROR);
    }

    public void connectionSucceed(Long result) {
        timer_layout.setVisibility(View.VISIBLE);
        loader_layout.setVisibility(View.GONE);
        setTime(result);
        refreshTimeInterval();
        updateNTP(NTPHome.UPDATE_INTERVAL);
    }
}
