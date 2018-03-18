package com.coralc.inspectbox.sync;

import com.coralc.inspectbox.TaskFragment;

import android.os.AsyncTask;
import android.os.SystemClock;

//This is a fairly standard AsyncTask that does some dummy work.
public class BaseTask extends AsyncTask<Void, Integer, Integer> {
	protected TaskFragment mFragment;
	protected int mProgress = 0;

	@Override
	protected Integer doInBackground(Void... params) {
		// Do some longish task. This should be a task that we don't really
		// care about continuing
		// if the user exits the app.
		// Examples of these things:
		// * Logging in to an app.
		// * Downloading something for the user to view.
		// * Calculating something for the user to view.
		// Examples of where you should probably use a service instead:
		// * Downloading files for the user to save (like the browser does).
		// * Sending messages to people.
		// * Uploading data to a server.
		for (int i = 0; i < 10; i++) {
			// Check if this has been cancelled, e.g. when the dialog is
			// dismissed.
			if (isCancelled())
				return i * 10;

			SystemClock.sleep(500);
			mProgress = i * 10;
			publishProgress(mProgress);
		}
		return mProgress;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mFragment == null)
			return;
		mFragment.updateProgress(values[0]);
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (mFragment == null)
			return;
		
		mFragment.taskFinished(result);
	}
	
	public void setFragment(TaskFragment fragment) {
		mFragment = fragment;
	}
}
