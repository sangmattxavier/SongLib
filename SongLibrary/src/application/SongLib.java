package application;
	
// Xavier La Rosa 
// Matthew Lee
import interfacemodules.UserInterfaceController;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class SongLib extends Application {
	
	// list of songs
	public static ArrayList<Song> list = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception { // runs UI
		try {
			// retrieve data
			//setupTestData();
			loadData();
			// display user interface
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../interfacemodules/user_interface.fxml"));
			Parent root = loader.load();
			UserInterfaceController UI = loader.getController();
			UI.start();

			// append to scene
			Scene scene = new Scene(root,600,550);
			scene.getStylesheets().add(getClass().getResource("../interfacemodules/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void stop(){ // save the data before closing
		System.out.println("Saving any existing data to saved-data.txt ...");
	    saveData();
	}
	
	public void setupTestData() { // sets up test data
		for(int i = 0; i <3; i++) {
			list.add(new Song("Name"+i, "Artist" + i, "Album" + i, "Year"+i));
		}
	}
	
	public void loadData() { // reads text file for any existing songs
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader("saved-data.txt"))) {
		    String line = bufferedReader.readLine();
		    int i=0;
		    while(line != null) {
		        System.out.println(line);
				   Song s = new Song("","");
				while(i<4) {
					switch(i)
					{
					   case 0 :
						   s.setName(line);
					      break;
					   case 1 :
					      s.setArtist(line);
					      break;
					   case 2 :
						  s.setAlbum(line);
					      break;
					   case 3 :
						  s.setYear(line);
						  list.add(s);
						  break;
					} 
					i++;
					line = bufferedReader.readLine();
		        }
		        	i = 0;
		    }
		} catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
	}
	
	public void saveData() {
		final String FNAME = "saved-data.txt";
		
		try ( BufferedWriter bw = 
				new BufferedWriter (new FileWriter (FNAME)) ) 
		{			
			for (int i = 0; i<list.size(); i++) {
				Song s = list.get(i);
				bw.write (s.getName() + "\n" + s.getArtist() + " \n" + s.getAlbum() + "\n" + s.getYear() + "\n");
			}
			
			bw.close ();
			
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}
	
	public static void main(String[] args) { // call UI
		launch(args);
	}
}
