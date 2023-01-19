package net.zeeraa.novacore.spigot.await;

import java.util.List;

public interface NovaAwaitableContainer {
	List<NovaAwaitable> getAwaitables();
	
	void addAwaitable(NovaAwaitable awaitable);
}