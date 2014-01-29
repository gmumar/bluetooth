package com.gudesigns.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class reciever extends BroadcastReceiver {

	ConnectedThread ct;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 0 = error
		// 1 = sms
		// 2 = call started
		// 3 = call ended

		if (intent.getAction().contains("SMS_RECEIVED")) {
			try {
				Blue.toast("SMS");
			} catch (Exception e) {
				Blue.toast("e");
			}
			writeToBluetooth("1");
		} else if (intent.getAction().contains("PHONE_STATE")) {
			// Blue.toast("phone");

			// Blue.toast(intent.getAction());

			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			tm.listen(new PhoneStateListener() {

				@Override
				public void onCallStateChanged(int state, String incomingNumber) {
					// TODO Auto-generated method stub
					super.onCallStateChanged(state, incomingNumber);

					if (state == TelephonyManager.CALL_STATE_RINGING) {

						writeToBluetooth("2");
						try {
							Blue.toast("Ringing ");
						} catch (Exception e) {
							Blue.toast("e");
						}
					} else if (state == TelephonyManager.CALL_STATE_IDLE) {

						writeToBluetooth("3");
						try {
							Blue.toast("Idle ");
						} catch (Exception e) {
							Blue.toast("e");
						}
					} else {
						writeToBluetooth("0");
					}

				}

			}, PhoneStateListener.LISTEN_CALL_STATE);
		} else {
			try {
				Blue.toast("other");
			} catch (Exception e) {
				Blue.toast("e");
			}
			writeToBluetooth("0");
		}

		/*
		 * if (ConnectThread.mmSocket != null) {
		 * 
		 * ct = new ConnectedThread(ConnectThread.mmSocket); ct.setData(str);
		 * ct.setRead(false); ct.start(); Blue.toast("writing");
		 * ct.setRead(true); }
		 */
		// ct.cancel();
	}

	void writeToBluetooth(String str_in) {
		if (ConnectThread.mmSocket != null) {

			ct = new ConnectedThread(ConnectThread.mmSocket);
			ct.setData(str_in);
			ct.setRead(false);
			ct.start();
			Blue.toast("writing");
			ct.setRead(true);
		}

	}

}
