package model;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.util.Optional;

public class TrackImpl implements Track, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7762866718118204014L;

	private File trackFile;
	private String trackName;
//	private Optional<Image> trackPic;
	private Long duration;
	
	public TrackImpl(File trackFile, String trackName){
		
		
	}
	
	@Override
	public String getName() {
		return new String(this.trackName);
	}

	@Override
	public void setName(String name) {
		this.trackName = name;
	}

//	@Override
//	public Optional<Image> getPic() {
//	}
//
//	@Override
//	public void setPic(Image image) {
//		
//		this.trackPic = image;
//	}

	@Override
	public String getFilePath() {
		return this.trackFile.getAbsolutePath();
	}

	@Override
	public void setFile(String path) {
		this.trackFile = new File(path);
	}

	public String toString(){
		
		return "This track is '"+getName()+"' located at "+getFilePath();
	}

	@Override
	public Long getDuration() {
		// TODO Auto-generated method stub
		return null;
	}
}
