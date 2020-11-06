package ru.cyberbiology.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.cyberbiology.test.bot.Bot;
import ru.cyberbiology.test.prototype.IBot;
import ru.cyberbiology.test.prototype.IWindow;
import ru.cyberbiology.test.prototype.IWorld;
import ru.cyberbiology.test.prototype.record.IRecordManager;
import ru.cyberbiology.test.record.v0.PlaybackManager;
import ru.cyberbiology.test.record.v0.RecordManager;
import ru.cyberbiology.test.util.ProjectProperties;
import ru.cyberbiology.test.util.Worker;

public class World implements IWorld
{
	public World world;
	public IWindow window;
	
	PlaybackManager playback;
	IRecordManager recorder;

	public int width;
	public int height;

	public IBot[][] matrix; // Матрица мира
	public IBot[][] swapMatrix; // Матрица мира
	public int generation;
	public int population;
	public int organic;

	boolean started;
	private Worker mainThread;
	private Runnable onStart;
	private Runnable onStop;
	public World(IWindow win)
	{
		world = this;
		window = win;
		population = 0;
		generation = 0;
		organic = 0;
		recorder = new RecordManager(this);
	}
	public World(IWindow win, int width, int height)
	{
		this(win);
		this.setSize(width, height);
	}

	@Override
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.matrix = new Bot[width][height];
		this.swapMatrix = new Bot[width][height];
	}

	@Override
	public void setBot(Bot bot)
	{
		this.matrix[bot.x][bot.y] = bot;
		this.swapMatrix[bot.x][bot.y] = bot;
	}

	public void paint()
	{
		window.paint();
	}
	@Override
	public ProjectProperties getProperties()
	{
		return window.getProperties();
	}
	class WorldStopper implements Runnable {
		@Override
		public void run() {
			execQueue();
			//			world.matrix = world.swapMatrix;
			paint();// если запаузили рисуем актуальную картинку
			started = false;// Закончили работу
		}
	}
	class WorldWorker implements Runnable
	{
		public void run()
		{
				boolean rec = recorder.isRecording(); // запоминаем флаг
														// "записывать" на
														// полную итерацию кадра
				if (rec)// вызываем обработчика "старт кадра"
					recorder.startFrame();
				// обновляем матрицу
				for (int y = 0; y < height; y++)
				{
					for (int x = 0; x < width; x++)
					{
						if (matrix[x][y] != null)
						{
							// if (matrix[x][y].alive == 3)
							{
								matrix[x][y].step(); // выполняем шаг бота
								if (rec)
								{
									// вызываем обработчика записи бота
									recorder.writeBot(matrix[x][y], x, y);
								}
							}
						}
					}
				}
				if (rec)// вызываем обработчика "конец кадра"
					recorder.stopFrame();
				generation = generation + 1;
				execQueue();
				if (generation % 10 == 0)
				{ // отрисовка на экран через каждые ... шагов
					paint(); // отображаем текущее состояние симуляции на экран
				}
				// sleep(); // пауза между ходами, если надо уменьшить скорость
		}
	}

	private void execQueue() {
		synchronized (runQueue) {
			for (Runnable runner: runQueue) {
				runner.run();
			}
			runQueue.clear();
		}
	}

	private final List<Runnable> runQueue = new ArrayList<>();
	public void putToQueue(Runnable runnable) {
		synchronized (runQueue) {
			runQueue.add(runnable);
		}
	}

	public void generateAdam()
	{
		// ========== 1 ==============
		// бот номер 1 - это уже реальный бот
		Bot bot = new Bot(this);

		bot.adr = 0;
		bot.x = width / 2; // координаты бота
		bot.y = height / 2;
		bot.health = 990; // энергия
		bot.mineral = 0; // минералы
		bot.alive = 3; // отмечаем, что бот живой
		bot.c_red = 170; // задаем цвет бота
		bot.c_blue = 170;
		bot.c_green = 170;
		bot.direction = 5; // направление
		bot.mprev = null; // бот не входит в многоклеточные цепочки, поэтому
							// ссылки
		bot.mnext = null; // на предыдущего, следующего в многоклеточной цепочке
							// пусты
		for (int i = 0; i < 64; i++)
		{ // заполняем геном командой 25 - фотосинтез
			bot.mind[i] = 25;
		}

		matrix[bot.x][bot.y] = bot; // даём ссылку на бота в массиве world[]
	}

	public void restoreLinks()
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (matrix[x][y] != null)
				{
					if (matrix[x][y].getAlive() == 3)
					{
						matrix[x][y].relink(matrix);
					}
				}
			}
		}
	}
	public boolean started()
	{
		return this.mainThread != null;
	}

	public void start() {
		if (!this.started()) {
			mainThread = start(new WorldWorker(), new WorldStopper(), onStart);
		}
	}

	private List<Worker> threadPool = new ArrayList<>();
	public Worker start(Runnable runner, Runnable stopper, Runnable onStart)
	{
		var thread = new Worker(runner, stopper);
		thread.begin();
		threadPool.add(thread);
		thread.start();
		if (onStart != null) {
			onStart.run();
		}
		return thread;
	}

	public void stop() {
		stop(mainThread, onStop);
		mainThread = null;
	}

	public void stop(Worker worker, Runnable onStop) {
		started = false;
		if (worker != null) {
			worker.end();
//			worker.stop();
			threadPool.remove(worker);
			if (onStop != null) {
				onStop.run();
			}
		}
	}

	public void setOnStart(Runnable onStart) {
		this.onStart = onStart;
	}

	public void setOnStop(Runnable onStop) {
		this.onStop = onStop;
	}

	public boolean isRecording()
	{
		return this.recorder.isRecording();
	}

	public void startRecording()
	{
		this.recorder.startRecording();
	}

	public boolean stopRecording()
	{
		return this.recorder.stopRecording();
	}

	public IBot getBot(int botX, int botY)
	{
		return this.matrix[botX][botY];
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	public boolean haveRecord()
	{
		return this.recorder.haveRecord();
	}
	/*
	public void deleteRecord()
	{
		this.recorder.deleteRecord();
	}*/

	public void makeSnapShot()
	{
		this.recorder.makeSnapShot();
	}
	@Override
	public IBot[][] getWorldArray()
	{
		return  this.matrix;
	}
	public void openFile(File file)
	{
		playback = new PlaybackManager(this, file);
	}
}
