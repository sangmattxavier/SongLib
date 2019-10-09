package application;

// Xavier La Rosa
// Matthew Lee
public class Song {

    private String name;
    private String artist;
    private String album;
    private String year;

    public Song(String name, String artist, String album, String year) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }
    
    public Song(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }
    
    public Song(String name, String artist, Integer year) {
        this.name = name;
        this.artist = artist;
        this.year = Integer.toString(year);
    }
    
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return this.name;
    }

    public String getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getYear() {
        return this.year;
    }
    
    public String getNameArtist() {
    	return this.name + ", " + this.artist;
    }
    
    public String getAlbumYear() {
    	return this.album + ", " + this.year;
    }
    
    public String getFullInfo() {
    	return this.name + ", " + this.artist + ", " + this.album + ", " + this.year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
