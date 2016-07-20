package core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI {

	private static final Logger logger = LogManager.getLogger(TwitterAPI.class);

	@SuppressWarnings("finally")
	public QueryResult getQueryResult(String hashTag) {
		// TODO Auto-generated method stub

		QueryResult result = null;
	
		try {
		

			ConfigurationBuilder builder = new ConfigurationBuilder();

			builder.setOAuthConsumerKey("GlZ0NHGKcf6JMVNccPQ85A");
			builder.setOAuthConsumerSecret( "FDKhhSlyzptVdauN36VmNeEtxI9Vpf4Z1GbGAX3");

			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();      

			AccessToken accessToken = loadAccessToken();

			twitter.setOAuthAccessToken(accessToken);
			
			logger.info("Conectado ao Twitter com Sucesso!");
			
			
			Query query = new Query(hashTag);

			
			result = twitter.search(query);
			

		} catch (Exception e) {
			logger.error("",e);
		}
		finally
		{
			return result;
		}
	
	}

	private static AccessToken loadAccessToken(){
		String token = "26794379-rPknAO6Mc5fabMSBNiGeP6kTeY870ETJBK4aDlWZ4"; 
		String tokenSecret = "5Xg52pRUs7Fp8a6TX5ha1Clj8aNrshzSBek8JTJ2w"; 
		return new AccessToken(token, tokenSecret);
	}

}
