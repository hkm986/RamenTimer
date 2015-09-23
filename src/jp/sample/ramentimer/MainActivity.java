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

	// セットするタイマーの値を入れる(ミリ秒)
	private static final int TIMER_SET = 180000;
	private int time;

	private final Handler handler = new Handler();
	private TimerTask tTask;
	private TextView timeView;
	private Button startBtn;
	private Button stopBtn;

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
		startBtn = (Button) findViewById(R.id.startBtn);
		stopBtn = (Button) findViewById(R.id.stopBtn);
		initView();
		// ボタンをリスナーに追加
		startBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
	}

	/**
	 * 画面の初期化処理
	 */
	private void initView() {
		time = TIMER_SET;
		String timeStr = msToMinSec(TIMER_SET);
		timeView.setText(timeStr);
		startBtn.setEnabled(true);
		stopBtn.setEnabled(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * ボタンの非活性を入れ替えるメソッド
	 */
	private void changeButtonEnable() {
		// 開始ボタンが活性なら
		if (startBtn.isEnabled()) {
			// 開始ボタンを非活性に
			startBtn.setEnabled(false);
			// 停止ボタンを活性に
			stopBtn.setEnabled(true);
		} else {
			// 上記の逆
			startBtn.setEnabled(true);
			stopBtn.setEnabled(false);
		}
	}

	/**
	 * ミリ秒から分と秒を取得するメソッド
	 *
	 * @param ms
	 *            ミリ秒
	 * @return mm：ss
	 */
	private String msToMinSec(int ms) {
		// ミリ秒から分と秒を取得
		int min = ms / 60000;
		int sec = (ms - (min * 60000)) / 1000;
		// 秒が１０より小さければ先頭に０を追加
		String secStr = (sec < 10) ? "0" + sec : String.valueOf(sec);
		String timeStr = min + "：" + secStr;
		return timeStr;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 開始ボタンを押したとき
		case R.id.startBtn:
			changeButtonEnable();
			startTimer();
			break;
		// 停止ボタンを押したとき
		case R.id.stopBtn:
			changeButtonEnable();
			tTask.cancel();
			tTask = null;
			break;
		default:
			break;
		}
	}

	/**
	 * タイマーの処理メソッド
	 */
	private void startTimer() {
		// １秒
		final int cnt = 1000;
		Timer timer = new Timer();
		timer.schedule(tTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						if (time >= 1000) {
							// １秒減らす
							time = time - cnt;
							String timeStr = msToMinSec(time);
							// textViewに値をセット
							timeView.setText(timeStr);
						} else {
							// タイマー終了時の処理を書く
							initView();
							cancel();
							tTask = null;
						}
					}
				});
			}
		}, cnt, cnt);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (tTask != null) {
			tTask.cancel();
			tTask = null;
		}
	}
}
