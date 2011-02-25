package goodfeeling.userstate.swing_test;

import goodfeeling.gui.R;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import org.openintents.sensorsimulator.hardware.Sensor;
//import org.openintents.sensorsimulator.hardware.SensorEvent;
//import org.openintents.sensorsimulator.hardware.SensorEventListener;
//import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SwingTest extends Activity implements SensorEventListener, OnClickListener, Handler.Callback {

	private enum TestState {
		READY,
		WORK,
		FINISH
	}
	/*
	 * number of rounds
	 */
	private final int ROUND_NUM = 5;
	/*
	 * the duration of the round, in milliseconds
	 */
	private final long ROUND_DURATION = 3000l;
	
	private final int MSG_TEST_STOP = 0;
	
	private TestState state;
	
	private int round;
	
	private boolean testStart;
	
	private SwingTestResult result;
	
	private Handler handler;
	
	//private SensorManagerSimulator sensorManager; //it goes with simulator
	private SensorManager sensorManager; //it goes without simulator
	private Sensor accelerometer;
	
	TextView info;
	TextView msg;
	Button button;
	TextView msg_swing_it;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swing_test);
		this.info = (TextView)findViewById(R.id.swing_test_info);
		this.info.setText(String.format(getResources().getString(R.string.swing_test_info), ROUND_NUM, ROUND_DURATION / 1000));
		this.msg = (TextView)findViewById(R.id.swing_test_msg);
		this.button = (Button)findViewById(R.id.swing_test_button);
		this.button.setOnClickListener(this);
		this.msg_swing_it = (TextView)findViewById(R.id.swing_test_msg_swing_it);
		this.msg_swing_it.setVisibility(View.INVISIBLE);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		//this.sensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
		//this.sensorManager.connectSimulator();
		this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		this.state = TestState.READY;
		this.round = 0;
		this.testStart = false;
		this.result = new SwingTestResult();
		
		this.handler = new Handler(this);
	}

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	}
	protected void onPause() {
		sensorManager.unregisterListener(this);
		super.onPause();
	}
	
	// implements SensorEventListener
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		//if(event.type == Sensor.TYPE_ACCELEROMETER) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if(this.round < ROUND_NUM && this.testStart) {
				float accelX = (float)Math.pow(event.values[0], 2.0);
				float accelY = (float)Math.pow(event.values[1], 2.0);
				float accelZ = (float)Math.pow(event.values[2], 2.0);
				float accel = (float)Math.sqrt(accelX + accelY + accelZ) - SensorManager.GRAVITY_EARTH;
				this.result.addResult(accel);
			}
		}
	}

	// implements OnClickListener
	
	public void onClick(View button) {
		switch (this.state) {
			case READY:
				this.msg.setText(String.format(getResources().getString(R.string.swing_test_msg_attempt), int2str(this.round + 1)));
				this.button.setText(R.string.swing_test_button_attempt);
				this.state = TestState.WORK;
				return;
			case WORK:
				this.button.setVisibility(View.INVISIBLE);
				this.msg_swing_it.setVisibility(View.VISIBLE);
				if(this.round < ROUND_NUM) {
					this.testStart = true;
					this.handler.sendEmptyMessageDelayed(MSG_TEST_STOP, ROUND_DURATION);					
				}
				return;
			case FINISH:
				Intent data = new Intent();
				this.result.put(data);
				setResult(RESULT_OK, data);
				finish();
				return;
		}
	}
	
	// implements Handler.Callback
	
	@Override
	public boolean handleMessage(Message msg) {
		if(msg.what == MSG_TEST_STOP) {
			this.button.setVisibility(View.VISIBLE);
			this.msg_swing_it.setVisibility(View.INVISIBLE);
			this.testStart = false;
			if(++this.round < ROUND_NUM) {
				this.msg.setText(String.format(getResources().getString(R.string.swing_test_msg_attempt), int2str(this.round + 1)));
			}
			else {
				this.msg.setText(String.format(getResources().getString(R.string.swing_test_msg_best), this.result.getResult()));
				this.button.setText(R.string.swing_test_button_finish);
				this.state = TestState.FINISH;
			}
			return true;
		}
		return false;
	}
	
	// private 
	
	private String int2str(int i) {
		switch (i % 10) {
		case 1:
			return i + "st";
		case 2:
			return i + "nd";
		case 3:
			return i + "rd";
		default:
			return i + "th";
		}
	}
}
