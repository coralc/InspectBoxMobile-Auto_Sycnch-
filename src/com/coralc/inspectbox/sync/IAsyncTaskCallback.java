package com.coralc.inspectbox.sync;

public interface IAsyncTaskCallback {
	// la t�che asynchrone est termin�e
	void onTaskComplete(int result);
}
