package com.coralc.inspectbox;

import com.coralc.inspectbox.sync.BaseTask;
import com.coralc.inspectbox.sync.IAsyncTaskCallback;
import com.coralc.inspectbox.R;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

// voir : http://creeder.com/?&page=AsyncTask
public class TaskFragment extends DialogFragment {
	// The task we are running.
	private BaseTask mTask;
	private ProgressBar mProgressBar;
	private String mTitle;
	private String mText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Retain this instance so it isn't destroyed when Activity change configuration.
		setRetainInstance(true);

		// Start the task! You could move this outside this activity if you
		// want.
		if (mTask != null && mTask.getStatus() == Status.PENDING)
			mTask.execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.progress_fragment_task, container);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarTask);
		TextView textTask = (TextView) view.findViewById(R.id.textTask);

		getDialog().setTitle(mTitle);
		textTask.setText(mText);

		// If you're doing a long task, you probably don't want people to cancel
		// it just by tapping the screen!
		getDialog().setCanceledOnTouchOutside(false);

		return view;
	}

	// This is to work around what is apparently a bug. If you don't have it
	// here the dialog will be dismissed on rotation, so tell it not to dismiss.
	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
			getDialog().setDismissMessage(null);
		mProgressBar = null;
		super.onDestroyView();
	}

	// Also when we are dismissed we need to cancel the task.
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		// If true, the thread is interrupted immediately, which may do bad
		// things.
		// If false, it guarantees a result is never returned (onPostExecute()
		// isn't called)
		// but you have to repeatedly call isCancelled() in your
		// doInBackground()
		// function to check if it should exit. For some tasks that might not be
		// feasible.
		if (mTask != null) {
			mTask.cancel(false);
			((IAsyncTaskCallback) getActivity()).onTaskComplete(-1);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// This is a little hacky, but we will see if the task has finished
		// while we weren't
		// in this activity, and then we can dismiss ourselves.
		if (mTask == null)
			dismiss();
	}
	
	public void setTask(BaseTask task) {
		mTask = task;

		// Tell the AsyncTask to call updateProgress() and taskFinished() on
		// this fragment.
		mTask.setFragment(this);
	}
	

	public void setTitle(String title) {
		this.mTitle = title;
	}
	
	public void setText(String text) {
		this.mText = text;
	}

	// This is called by the AsyncTask.
	public void updateProgress(int percent) {
		mProgressBar.setProgress(percent);
	}

	// This is also called by the AsyncTask.
	public void taskFinished(int result) {
		// Make sure we check if it is resumed because we will crash if trying
		// to dismiss the dialog
		// after the user has switched to another app.
		if (isResumed())
			dismiss();

		// If we aren't resumed, setting the task to null will allow us to
		// dimiss ourselves in
		// onResume().
		mTask = null;

		// Tell the activity that we are done.
		((IAsyncTaskCallback) getActivity()).onTaskComplete(result);
	}
}
