package tw.jm.Crawler;


public abstract class Article {

	public abstract String getTitle();

	public void saveToFile() {
		FileOperation fileOperation = new FileOperation();
		fileOperation.saveArticlesToFiles(this);
	}

}
