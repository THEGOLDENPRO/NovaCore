package net.zeeraa.novacore.spigot.tasks;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.NovaCore;

/**
 * A task that cancels it self after reaching a certain amount of executions
 * 
 * @author Anton
 */
public class SelfCancelingTask extends Task {
	protected Runnable runnable;
	protected Plugin plugin;
	protected long delay;
	protected long period;

	private int targetExectuionTimes;
	private int executionTimes;

	protected BukkitTask bukkitTask;

	protected TaskExecutionMode taskExecutionMode;

	public SelfCancelingTask(Runnable runnable, int targetExectuionTimes, long period, TaskExecutionMode taskExecutionMode) {
		this(NovaCore.getInstance(), runnable, targetExectuionTimes, period, period, taskExecutionMode);
	}

	public SelfCancelingTask(Runnable runnable, int targetExectuionTimes, long period) {
		this(NovaCore.getInstance(), runnable, targetExectuionTimes, period, period, TaskExecutionMode.SYNCHRONOUS);
	}

	public SelfCancelingTask(Plugin plugin, Runnable runnable, int targetExectuionTimes, long period) {
		this(plugin, runnable, targetExectuionTimes, period, period, TaskExecutionMode.SYNCHRONOUS);
	}

	public SelfCancelingTask(Runnable runnable, int targetExectuionTimes, long delay, long period) {
		this(NovaCore.getInstance(), runnable, targetExectuionTimes, delay, period, TaskExecutionMode.SYNCHRONOUS);
	}

	public SelfCancelingTask(Plugin plugin, Runnable runnable, int targetExectuionTimes, long delay, long period) {
		this(plugin, runnable, targetExectuionTimes, delay, period, TaskExecutionMode.SYNCHRONOUS);
	}

	public SelfCancelingTask(Plugin plugin, Runnable runnable, int targetExectuionTimes, long delay, long period, TaskExecutionMode taskExecutionMode) {
		this.bukkitTask = null;

		this.plugin = plugin;
		this.runnable = runnable;
		this.delay = delay;
		this.period = period;

		this.targetExectuionTimes = targetExectuionTimes;
		this.executionTimes = 0;

		if (taskExecutionMode == null) {
			throw new IllegalArgumentException("TaskExecutionMode cant be null");
		}

		this.taskExecutionMode = taskExecutionMode;
	}

	public int getTargetExectuionTimes() {
		return targetExectuionTimes;
	}

	public void setTargetExectuionTimes(int targetExectuionTimes) {
		this.targetExectuionTimes = targetExectuionTimes;
	}

	public int getExecutionTimes() {
		return executionTimes;
	}

	public void setExecutionTimes(int executionTimes) {
		this.executionTimes = executionTimes;
	}

	public void resetExecutionTimes() {
		this.setExecutionTimes(0);
	}

	/**
	 * Get the {@link TaskExecutionMode}
	 * 
	 * @return {@link TaskExecutionMode} the has been set, not that this might not
	 *         correctly reflect the active execution mode since if the mode was
	 *         changed after the task started the new mode wont be used until the
	 *         task has restarted
	 */
	public TaskExecutionMode getTaskExecutionMode() {
		return taskExecutionMode;
	}

	/**
	 * Change the {@link TaskExecutionMode}. The task needs to restart to apply the
	 * mode, see {@link Task#restart()}
	 * 
	 * @param taskExecutionMode The new {@link TaskExecutionMode} to use
	 */
	public void setTaskExecutionMode(TaskExecutionMode taskExecutionMode) {
		if (taskExecutionMode == null) {
			throw new IllegalArgumentException("TaskExecutionMode cant be null");
		}

		this.taskExecutionMode = taskExecutionMode;
	}

	@Override
	public boolean start() {
		if (bukkitTask != null) {
			return false;
		}

		switch (taskExecutionMode) {
		case ASYNCHRONOUS:
			bukkitTask = new BukkitRunnable() {
				@Override
				public void run() {
					if (executionTimes >= targetExectuionTimes) {
						stop();
						return;
					}
					executionTimes++;
					runnable.run();
				}
			}.runTaskTimerAsynchronously(plugin, delay, period);
			break;

		case SYNCHRONOUS:
			bukkitTask = new BukkitRunnable() {
				@Override
				public void run() {
					if (executionTimes >= targetExectuionTimes) {
						stop();
						return;
					}
					executionTimes++;
					runnable.run();
				}
			}.runTaskTimer(plugin, delay, period);

			break;

		default:
			return false;
		}

		return true;
	}

	@Override
	public boolean stop() {
		if (bukkitTask == null) {
			return false;
		}
		bukkitTask.cancel();
		bukkitTask = null;
		return true;
	}

	@Override
	public boolean isRunning() {
		return bukkitTask != null;
	}
}