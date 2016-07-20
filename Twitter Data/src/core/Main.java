package core;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import connections.Cassandra;
import twitter4j.QueryResult;
import twitter4j.Status;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Properties prop;
		FileInputStream arquivoPropr;
		try {
			PropertyConfigurator.configure("log4j.properties");
			arquivoPropr = new FileInputStream("application.properties");
			prop = new Properties(System.getProperties());
			prop.load(arquivoPropr);
			System.setProperties(prop);

			
			String[] hashTags = System.getProperty("query.hashTags").split(";");

			TwitterAPI twitter = new TwitterAPI();
			Cassandra db = new Cassandra(true);
			
			int maxTweets=100;
			int i=0;
			
			for (String hashTag : hashTags) {
				logger.info("Analisando hashTag: "+hashTag);
				QueryResult result = twitter.getQueryResult(hashTag);
				 i=0;

				do 
				{
					for (Status status : result.getTweets()) {
						logger.info("Coletando Tweet "+i+" das hashtag "+hashTag);
						logger.info("Usuário @" + status.getUser().getScreenName() + " Tweet " + status.getText());
						logger.info("Data: "+status.getCreatedAt());
						
						db.insertUser(status.getUser());
						db.insertStatus(status, hashTag);
						
						i++;
					}

				}while (result.hasNext() & i<maxTweets);
			}

			db.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}



	}


}
