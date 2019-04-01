package tw.jm.Crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileOperation {

	private static final Logger logger = LogManager.getLogger("Crawler");

	public List<String> getBoardConfig() {
		List<String> pttBoard = new ArrayList<>();
		String file = UserDefinedFunction.websitesPath;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				pttBoard.add(line);
			}
			br.close();
		} catch (IOException e) {
			logger.error("FileReader", e);
		}
		return pttBoard;
	}

	public void getDoneListConfig() {
		String file = UserDefinedFunction.doneListPath;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(" ");
				String board = token[0];
				String url = token[1];
				String date = token[2];

				if (DoneList.getInstance().getDoneList().containsKey(board) == false) {
					DoneList.getInstance().getDoneList().put(board, new HashMap<>());
				}
				DoneList.getInstance().getDoneList().get(board).put(url, date);
			}
			br.close();
		} catch (IOException e) {
			logger.error("FileReader", e);
		}
	}

	public void setDoneListConfig() {
		String file = UserDefinedFunction.doneListPath;
		File deleteFile = new File(file);
		deleteFile.delete();
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(file, true));
			for (Map.Entry<String, Map<String, String>> articleSet : DoneList.getInstance().getDoneList().entrySet()) {
				String board = articleSet.getKey();
				for (Map.Entry<String, String> entry : articleSet.getValue().entrySet()) {
					String url = entry.getKey();
					String date = entry.getValue();

					String writeTemplate = board + " " + url + " " + date;
					out.write(writeTemplate);
					out.newLine();
				}
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("FileWriter", e);
		}
	}
	
	public void saveArticlesToFiles(Article article) {
		String articleTitle = article.getTitle();
		articleTitle = articleTitle.replaceAll("[\\\\!<>:\"|?\\*\\./]", "");
		String f = "Data\\" + articleTitle + ".txt";
		logger.info(f);

		ObjectMapper mapper = new ObjectMapper();

		try {
			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(article);
			logger.info(jsonInString);

			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(f)), "utf-8");
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
