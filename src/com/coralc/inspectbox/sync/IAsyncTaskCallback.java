package com.coralc.inspectbox.sync;

public interface IAsyncTaskCallback {
	// la tâche asynchrone est terminée
	void onTaskComplete(int result);
}
