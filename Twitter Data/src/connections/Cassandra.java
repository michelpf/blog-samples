package connections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import core.StatusHashTag;
import core.Utilitario;
import twitter4j.Status;
import twitter4j.User;

public class Cassandra {

	private Cluster cluster;
	private Session session;

	private static final Logger logger = LogManager.getLogger(Cassandra.class);
	private File fileUser;
	private File fileStatus;
	private BufferedWriter bwUser;
	private BufferedWriter bwStatus;
	private Set<User> userSet;
	private Set<StatusHashTag> statusSet;
	private boolean csv;

	public Cassandra(boolean csv) throws Exception {
		this.csv=csv;
		connect();
	}

	private void connect() throws Exception {

		if (!csv){
			cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
			session = cluster.connect("twitterdata");
		}
		else
		{
			fileUser = new File("user.csv");
			fileStatus = new File("status.csv");
			
			if (fileUser.exists()) fileUser.delete();
			if (fileStatus.exists()) fileStatus.delete();

			bwUser = new BufferedWriter(new FileWriter(fileUser.getName()));
			StringBuilder linha=new StringBuilder();
			linha.append("id");
			linha.append(";");
			linha.append("created");
			linha.append(";");
			linha.append("description");
			linha.append(";");
			linha.append("favourites");
			linha.append(";");
			linha.append("followers");
			linha.append(";");
			linha.append("friends");
			linha.append(";");
			linha.append("lang");
			linha.append(";");
			linha.append("location");
			linha.append(";");
			linha.append("name");
			linha.append("\r\n");
			bwUser.write(linha.toString());

			bwStatus = new BufferedWriter(new FileWriter(fileStatus.getName()));
			linha=new StringBuilder();
			linha.append("id");
			linha.append(";");
			linha.append("created");
			linha.append(";");
			linha.append("favorited");
			linha.append(";");
			linha.append("hastag");
			linha.append(";");
			linha.append("userid");
			linha.append(";");
			linha.append("lang");
			linha.append(";");
			linha.append("retweeted");
			linha.append(";");
			linha.append("retweets");
			linha.append(";");
			linha.append("tweet");
			bwStatus.write(linha.toString());

			userSet = new HashSet<User>();
			statusSet = new HashSet<StatusHashTag>();
		}




	}

	public void close() throws Exception {
		if (!csv){
			session.close();
			cluster.close();
		}
		else
		{

			for (StatusHashTag status : statusSet) {
				StringBuilder linha=new StringBuilder();
				linha.append(status.getStatus().getId());
				linha.append(";");
				linha.append(new Timestamp(status.getStatus().getCreatedAt().getTime()));
				linha.append(";");
				linha.append(status.getStatus().isFavorited());
				linha.append(";");
				linha.append(status.getHashTag());
				linha.append(";");
				linha.append(status.getStatus().getUser().getId());
				linha.append(";");
				linha.append(status.getStatus().getLang());
				linha.append(";");
				linha.append(status.getStatus().isRetweet());
				linha.append(";");
				linha.append(status.getStatus().getRetweetCount());
				linha.append(";");
				linha.append(Utilitario.removeLineBreak(status.getStatus().getText()));

				linha.append("\r\n");

				bwStatus.write(linha.toString());
			}

			for (User user : userSet) {
				StringBuilder linha=new StringBuilder();
				linha.append(user.getId());
				linha.append(";");
				linha.append(new Timestamp(user.getCreatedAt().getTime()));
				linha.append(";");
				linha.append(Utilitario.removeLineBreak(user.getDescription()));
				linha.append(";");
				linha.append(user.getFavouritesCount());
				linha.append(";");
				linha.append(user.getFollowersCount());
				linha.append(";");
				linha.append(user.getFriendsCount());
				linha.append(";");
				linha.append(user.getLang());
				linha.append(";");
				linha.append(user.getLocation());
				linha.append(";");
				linha.append(Utilitario.removeLineBreak(user.getName()));
				linha.append("\r\n");


				bwUser.write(linha.toString());
			}

			bwUser.close();
			bwStatus.close();
		}
	}

	public void insertUser(User user) throws Exception{

		if (!csv){
			PreparedStatement statement = session.prepare(
					"INSERT INTO user (id, created, description, favourites, followers, friends, lang, location, name) VALUES (?,?,?,?,?,?,?,?,?);");

			BoundStatement boundStatement = new BoundStatement(statement);

			session.execute(boundStatement.bind(user.getId(),new Timestamp(user.getCreatedAt().getTime()),user.getDescription(),user.getFavouritesCount(),
					user.getFollowersCount(),user.getFriendsCount(),user.getLang(),user.getLocation(),user.getName()));

			logger.info("Executando consulta: \n\r"+statement.getQueryString());

		}
		else
		{
			userSet.add(user);


		}







	}

	public void insertStatus(Status status, String hashTag) throws Exception{

		if (!csv){
			PreparedStatement statement = session.prepare(
					"INSERT INTO status (id, created, favorited, hashtag, iduser, lang, retweeted, retweets, text) VALUES (?,?,?,?,?,?,?,?,?);");

			BoundStatement boundStatement = new BoundStatement(statement);

			session.execute(boundStatement.bind(status.getId(),new Timestamp(status.getCreatedAt().getTime()),status.isFavorited(),hashTag,
					status.getUser().getId(),status.getLang(),status.isRetweet(),status.getRetweetCount(),status.getText()));
			logger.info("Executando consulta: \n\r"+statement.getQueryString());
		}
		else
		{

			StatusHashTag statusH = new StatusHashTag (hashTag, status);
			statusSet.add(statusH);


		}






	}

}
