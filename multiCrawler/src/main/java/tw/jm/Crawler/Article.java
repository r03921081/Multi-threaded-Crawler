package tw.jm.Crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Article {

	private static final Logger logger = LogManager.getLogger("Crawler");

	private String title;

	public String getTitle() {
		return title;
	}

	public void saveToFile() {
		String articleTitle = this.getTitle();
		articleTitle = articleTitle.replaceAll("[\\\\!<>:\"|?\\*\\./]", "");
		String f = "Data\\" + articleTitle + ".txt";
		logger.info(f);

		ObjectMapper mapper = new ObjectMapper();

		try {

			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
			logger.info(jsonInString);

			OutputStreamWriter  writer = new OutputStreamWriter(new FileOutputStream(new File(f)), "utf-8");
			writer.append(jsonInString);
			writer.close();

		} catch (JsonGenerationException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

	}

}
