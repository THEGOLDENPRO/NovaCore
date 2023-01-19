package net.zeeraa.novacore.spigot.await;

import java.util.ArrayList;
import java.util.List;

public class NovaAwaitableGroup implements NovaAwaitable, NovaAwaitableContainer {
	private List<NovaAwaitable> awaitables;

	public NovaAwaitableGroup(NovaAwaitable... awaitables) {
		this.awaitables = new ArrayList<>();
		for (NovaAwaitable a : awaitables) {
			this.awaitables.add(a);
		}
	}

	public NovaAwaitableGroup(List<NovaAwaitable> awaitables) {
		this.awaitables = new ArrayList<>(awaitables);
	}

	@Override
	public boolean isFininshed() {
		return awaitables.stream().allMatch(NovaAwaitable::isFininshed);
	}

	@Override
	public List<NovaAwaitable> getAwaitables() {
		return awaitables;
	}

	@Override
	public void addAwaitable(NovaAwaitable awaitable) {
		awaitables.add(awaitable);
	}
}