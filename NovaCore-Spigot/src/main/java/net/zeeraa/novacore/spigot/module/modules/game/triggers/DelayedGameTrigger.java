package net.zeeraa.novacore.spigot.module.modules.game.triggers;

import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

/**
 * This trigger will be activated with a provided delay after
 * {@link DelayedGameTrigger#start()} is called
 * 
 * @author Zeeraa
 *
 */
public class DelayedGameTrigger extends ScheduledGameTrigger {
	private long delay;

	private BasicTimer timer;

	public DelayedGameTrigger(String name, long delay, TriggerCallback callback) {
		super(name, callback);
		this.delay = delay;
		this.timer = null;
	}

	public DelayedGameTrigger(String name, long delay) {
		this(name, delay, null);
	}

	/**
	 * Get the delay this trigger uses
	 * 
	 * @return The delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Set the delay for this trigger
	 * <p>
	 * Note that this wont apply if the trigger has been started and will require
	 * restarting the trigger to apply
	 * 
	 * @param delay The new delay for the trigger
	 * @return this instance so the functions can be chained
	 */
	public DelayedGameTrigger setDelay(long delay) {
		this.delay = delay;
		return this;
	}

	@Override
	public boolean start() {
		if (timer != null) {
			if(timer.isRunning()) {
			return false;
			} else {
				timer = null;
			}
		}

		timer = new BasicTimer(delay, 1);
		timer.addFinishCallback(new Callback() {
			@Override
			public void execute() {
				stop();
				trigger(TriggerFlag.SCHEDULED_TRIGGER_ACTIVATION);
			}
		});

		timer.start();

		return true;
	}

	@Override
	public boolean stop() {
		if (timer != null) {
			if (timer.isRunning()) {
				timer.cancel();
			}
			timer = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean isRunning() {
		if (timer != null) {
			return timer.isRunning();
		}

		return false;
	}

	/**
	 * Get the ticks until execution.
	 * <p>
	 * Will be 0 if the timer is not running
	 * 
	 * @return the time left in ticks
	 */
	public long getTicksLeft() {
		if (isRunning()) {
			return timer.getTimeLeft();
		}

		return 0;
	}
}