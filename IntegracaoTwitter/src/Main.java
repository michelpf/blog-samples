import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		try {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			
			builder.setOAuthConsumerKey("7LbK2SDxOvOI4pGVu4YHHxvIc");
			builder.setOAuthConsumerSecret( "hrNLlc61ZUtYs2mLIrHYD5D1ioAlPnnzeCZu3oIlP8cGF6JaTN");
			
			Configuration configuration = builder.build();

			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();      

			AccessToken accessToken = loadAccessToken();

			twitter.setOAuthAccessToken(accessToken);

			Status status = twitter.updateStatus("Olá Twitter!");
			System.out.println("Tweet postado com sucesso! [" + status.getText() + "].");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static AccessToken loadAccessToken(){
		String token = "26794379-wpBS4awpeMXRshfuLKqpBynLrlUcGQ3H5xYbVwbHa"; 
		String tokenSecret = "c9cnRdDyasC8fclOEva35u1JZAVxlEpwZdwZlaL6Kl0EV"; 
		return new AccessToken(token, tokenSecret);
	}

}