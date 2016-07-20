package core;

import twitter4j.Status;



public class StatusHashTag {
	
	private String hashTag;
	private Status status;
	

	public StatusHashTag(String hashTag, Status status) {
		super();
		this.hashTag = hashTag;
		this.status = status;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	

}
