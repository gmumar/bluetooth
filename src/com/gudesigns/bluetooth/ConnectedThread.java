package com.gudesigns.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;

public class ConnectedThread extends Thread {
	private BluetoothSocket mmSocket;
	private final InputStream mmInStream;
	private final OutputStream mmOutStream;
	public static String data;
	public static boolean read;

	public ConnectedThread(BluetoothSocket socket) {
		mmSocket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		read = true;

		// Get the input and output streams, using temp objects because
		// member streams are final
		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
		}

		mmInStream = tmpIn;
		mmOutStream = tmpOut;
	}

	public void run() {
		String str;
		byte[] buffer = new byte[1024]; // buffer store for the stream
		int bytes; // bytes returned from read()

		// Keep listening to the InputStream until an exception occurs
		while (true) {
			try {
				// Read from the InputStream
				buffer = new byte[1024];

				if (read) {
					System.out.println("Reading");

					bytes = mmInStream.read(buffer);
					str = new String(buffer);

					// Echo:
					mmOutStream.write(buffer);
					//Blue.toast(str.trim());
					System.out.println("Printing buffer :: "+Integer.toString(bytes) + " "
							+ str.trim());
				} else {
					buffer = data.getBytes();
					mmOutStream.write(buffer);

					System.out.println("Written: " + data.trim());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}

	/* Call this from the main activity to send data to the remote device */
	public void write(byte[] bytes) {
		try {
			mmOutStream.write(bytes);
		} catch (IOException e) {
		}
	}

	public void setData(String str) {
		data = str;
	}

	public void setRead(boolean b) {
		read = b;
	}

	/* Call this from the main activity to shutdown the connection */
	public void cancel() {
		try {
			mmSocket.close();
			mmSocket = null;
		} catch (IOException e) {
		}
	}
}
