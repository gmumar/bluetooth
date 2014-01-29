package com.gudesigns.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Blue extends Activity {

	ListView lv;
	public static TextView tv;
	String Mac;
	BluetoothDevice bd;
	BluetoothAdapter ba;
	ConnectThread ct;
	static Context c;
	Button b, b1;
	Thread t;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blue);
		
		

		c = this;
		ba = BluetoothAdapter.getDefaultAdapter();
		tv = (TextView) findViewById(R.id.textView1);
		lv = (ListView) findViewById(R.id.mylist);
		b = (Button) findViewById(R.id.button1);
		b1 = (Button) findViewById(R.id.button2);
		ct = null;

		if (ba == null) {
			toast("Bluetooth not available");
			return;
		}

		if (ba.isEnabled()) {
			// Enabled
			//toast("Bluetooth Enabled");
		} else {
			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(i);
		}

		// ^ enable bluetooth
		Set<BluetoothDevice> pairedDevices = ba.getBondedDevices();
		
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		if (pairedDevices.size() > 0) {
			// Loop through paired devices
			for (BluetoothDevice device : pairedDevices) {

				aa.add(device.getName() + "\n" + device.getAddress());
			}

			lv.setAdapter(aa);
		}

		
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Mac = (((aa.getItem(arg2)).split("\n"))[1]).trim();

				tv.setText(Mac);
				bd = ba.getRemoteDevice(Mac);
				toast(bd.getAddress());

				ct = new ConnectThread(bd);

			}
		});

		// ^ list view setup and execution 
		b.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				toast("button");
				if (ct != null)
					ct.start();

			}
		});

		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ct != null){
					ct.cancel();
				}
				

			}
		});

		tv.setText("done");
	}

	public static void toast(String str) {
		Toast.makeText(c, str, Toast.LENGTH_SHORT).show();
		
	}

}
