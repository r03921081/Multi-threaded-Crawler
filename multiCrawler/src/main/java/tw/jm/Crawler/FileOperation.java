package tw.jm.Crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileOperation {

	private static final Logger logger = LogManager.getLogger("Crawler");

	public static final String jdbcUrl = "jdbc:mysql://localhost:3306/myCrawler?useSSL=false&serverTimezone=UTC";
	public static final String user = "crawler";
	public static final String pwd = "crawler";

	public List<String> getBoardConfig() {

		List<String> pttBoard = new ArrayList<>();
		try {
			Connection connect = DriverManager.getConnection(jdbcUrl, user, pwd);
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("Select * From website");
			String line;

			while (rs.next()) {
				line = rs.getString("web") + "-" + rs.getString("board");
				pttBoard.add(line);
			}

		} catch (SQLException e) {
			logger.error(e);
		}
		return pttBoard;

	}

	public void getDoneListConfig() {

		try {
			Connection connect = DriverManager.getConnection(jdbcUrl, user, pwd);
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("Select * From donelist");

			while (rs.next()) {

				String board = rs.getString("web") + "-" + rs.getString("board");
				String url = rs.getString("article");
				String date = rs.getString("articleDate");

				if (DoneList.getInstance().getDoneList().containsKey(board) == false) {
					DoneList.getInstance().getDoneList().put(board, new HashMap<>());
				}
				DoneList.getInstance().getDoneList().get(board).put(url, date);
				DoneList.getInstance().setOldDoneArticle(url);
			}

		} catch (SQLException e) {
			logger.error(e);
		}

	}

	public void setDoneListConfig() {

		try {
			Connection connect = DriverManager.getConnection(jdbcUrl, user, pwd);
			String insertSql = "INSERT INTO donelist (web, board, article, articleDate) VALUES (?,?,?,?)";
			Set<String> oldArticles = DoneList.getInstance().getOldDoneArticle();

			for (Map.Entry<String, Map<String, String>> articleSet : DoneList.getInstance().getDoneList().entrySet()) {
				String boardKey = articleSet.getKey();
				String web = boardKey.split("-")[0];
				String board = boardKey.split("-")[1];
				for (Map.Entry<String, String> entry : articleSet.getValue().entrySet()) {
					String url = entry.getKey();
					String date = entry.getValue();

					if (!oldArticles.contains(url)) {
						PreparedStatement preStmt = connect.prepareStatement(insertSql);
						preStmt.setString(1, web);
						preStmt.setString(2, board);
						preStmt.setString(3, url);
						preStmt.setString(4, date);
						preStmt.executeUpdate();
						logger.info(preStmt.toString());
					}

				}
			}

		} catch (SQLException e) {
			logger.error(e);
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
//			logger.info(jsonInString);

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
