package jp.sample.ramentimer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * メインのクラス
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private final Handler handler = new Handler();
	private TextView timeView;
	private Button btn1;

	/**
	 * 画面の生成等はResumeで行う
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// activity_mainを読み込み
		setContentView(R.layout.activity_main);
		// textviewとbuttonのセット
		timeView = (TextView) findViewById(R.id.time);
		btn1 = (Button) findViewById(R.id.button1);
		// ボタンをリスナーに追加
		btn1.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			startTimer();
			break;
		default:
			break;
		}
	}

	private void startTimer() {
		int time = 0;
		int cnt = 1000;
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
					}
				});
			}
		}, cnt, cnt);
	}
}
