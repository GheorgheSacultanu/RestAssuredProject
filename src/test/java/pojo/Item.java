package pojo;

import com.google.gson.annotations.SerializedName;

public class Item {

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("media_id")
	private int mediaId;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@SerializedName("comment")
	private String comment;


	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setMediaId(int mediaId){
		this.mediaId = mediaId;
	}

	public int getMediaId(){
		return mediaId;
	}
}