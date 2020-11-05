package ru.cyberbiology.test.prototype;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.util.ProjectProperties;

public interface IWorld
{

	public int getWidth();

	public int getHeight();

	public void setSize(int width, int height);

	public void setBot(Bot bot);

	public void paint();

	public ProjectProperties getProperties();

	public IBot[][] getWorldArray();

	public void restoreLinks();

	void putToQueue(Runnable runnable);
}
