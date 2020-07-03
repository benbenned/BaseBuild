package de.ben.BaseBuild.countdowns;

public abstract class Countdown {

	protected int taskID;
	protected boolean isRunning = false;
	protected int count;
	
	public abstract void start(int seconds);
	public abstract void stop();

	public int getCount() {
		return count;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
