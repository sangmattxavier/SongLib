package interfacemodules;

import application.Song;
import application.SongLib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator; 
import java.util.Collections; 

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserInterfaceController
{
	@FXML ListView<Song> listView;
	static ObservableList<Song> listObservable;

	@FXML TextField nameInput, artistInput, albumInput, yearInput;
	@FXML Button addButton, clearButton;
	@FXML Text addError;
	
	@FXML Text currentSong;
	@FXML TextField editName, editArtist, editAlbum, editYear;
	@FXML Button editButton, deleteButton, resetButton;
	@FXML Text editError;

	public void start() throws IOException{
		
		// setup logic for visible UI
		addError.setVisible(false);
		editError.setVisible(false);
		
		// sort list, render list, give list functions for when cell is selected, make first cell selected
		sortList();
		renderList();
		listView.getSelectionModel().selectedItemProperty().addListener((listObservable,oldValues,newValues) -> cellWasSelected());
		listView.getSelectionModel().select(0);
		
		// add button clicked
		addButton.setOnAction((event) -> {
		    System.out.println("Add Button Clicked");
		    if(areAddRequirementsMet()) {
		    	Song s = new Song(nameInput.getText(), artistInput.getText(), albumInput.getText(), yearInput.getText());
				if(!nameInput.getText().equals("") && !artistInput.getText().equals("") && !isDuplicate(s, SongLib.list)){	
						addError.setVisible(false);
						SongLib.list.add(s);
						System.out.println("Song added");
						listView.getItems().add(SongLib.list.get(SongLib.list.size()-1));
						sortList();
						renderList();
						listView.getSelectionModel().select(SongLib.list.indexOf(s));
						addError.setVisible(false);
				} else {
					addError.setText("Error: Song Name and Artist Already exist.");
					addError.setVisible(true);
				}
		    } else {
		    	addError.setText("Error: Make sure Song Name and Artist are filled in.");
		    	addError.setVisible(true);
		    }
			
		});
		
		// clear button clicked
		clearButton.setOnAction((event) -> {
		    System.out.println("Clear Button Clicked");
			nameInput.setText("");
			artistInput.setText("");
			albumInput.setText("");
			yearInput.setText("");
		});
	}

	public boolean areAddRequirementsMet() {
		if(!nameInput.getText().equals("") && !artistInput.getText().equals("")) { // met
			return true;
		} else { // not met
			return false;
		}
	}
	
	public void sortList() { // sorts the list alphabetically by song name then artist
		System.out.println("Sorting List...");
		Comparator<Song> compareByName = (Song s1, Song s2) -> {
	         int result = s1.getName().compareToIgnoreCase(s2.getName());
	         if (result == 0){
	            result = s1.getArtist().compareToIgnoreCase(s2.getArtist());
	         }
	         return result;
	      };
		Collections.sort(SongLib.list, compareByName);
	}
	
	public void renderList() { // clears, refills, and construct the UI list
		System.out.println("Rendering List...");
		listObservable = FXCollections.observableArrayList(SongLib.list);
		listView.getItems().clear();
		listView.setItems(listObservable);
		listView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){
            @Override
            public ListCell<Song> call(ListView<Song> p) {     
                ListCell<Song> cell = new ListCell<Song>(){
                    @Override
                    protected void updateItem(Song t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getNameArtist());
                        } else {
                            setText("");
                        }
                    }
                }; 
                return cell;
            }
        });
		printList();
	}
	
	public void printList(){ // helper method for debugging
		String x = "Debugging, current list with size "+SongLib.list.size()+": ";
		for(int i = 0; i < SongLib.list.size(); i++) {
			x = x + "\n" + i +": "+SongLib.list.get(i).getNameArtist();
		}
		System.out.println(x);
	}
	
	public static boolean isDuplicate(Song a, ArrayList<Song> list) { // checks for song duplicates
		for (int i = 0; i < list.size(); i++) {
			if(a.getNameArtist().compareTo(list.get(i).getNameArtist()) == 0) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}
	
	public void cellWasSelected(){ // functions provided when a cell is selected in list
		try
		{
			if(SongLib.list.isEmpty() != true)
			{
				// UI logic
				editButton.setDisable(false);
				deleteButton.setDisable(false);
				resetButton.setDisable(false);
				
				// universal variables for button actions
				int index = listView.getSelectionModel().getSelectedIndex();
				Song selectedSong = listView.getSelectionModel().getSelectedItem();
				
				// sets text of selected song UI
				currentSong.setText("Selected: " + selectedSong.getFullInfo());
				editName.setText(selectedSong.getName());
				editArtist.setText(selectedSong.getArtist());
				editAlbum.setText(selectedSong.getAlbum());
				editYear.setText(selectedSong.getYear());
				
				// edit button clicked
				editButton.setOnAction((event) -> {
					System.out.println("Edit Button clicked");
					Song s = new Song(editName.getText(), editArtist.getText(), editAlbum.getText(), editYear.getText()); 
					if(!isDuplicate(s, SongLib.list)) {
						editError.setVisible(false);
						SongLib.list.get(index).setName(editName.getText());
						SongLib.list.get(index).setArtist(editArtist.getText());
						SongLib.list.get(index).setAlbum(editAlbum.getText());
						SongLib.list.get(index).setYear(editYear.getText());
						
						System.out.println("");
						Song newSong = SongLib.list.get(index);
						SongLib.list.get(index).getNameArtist();
						listView.getItems().set(index, newSong);
						sortList();
						renderList();
						listView.getSelectionModel().select(listView.getItems().indexOf(newSong));
					}
					 else if (isDuplicate(s,SongLib.list) && s.getAlbumYear().compareTo(SongLib.list.get(index).getAlbumYear()) != 0){
						editError.setVisible(false);
						SongLib.list.get(index).setName(editName.getText());
						SongLib.list.get(index).setArtist(editArtist.getText());
						SongLib.list.get(index).setAlbum(editAlbum.getText());
						SongLib.list.get(index).setYear(editYear.getText());
						
						System.out.println("second" + SongLib.list.get(index).getFullInfo());
						Song newSong = SongLib.list.get(index);
						SongLib.list.get(index).getFullInfo();
						listView.getItems().set(index, SongLib.list.get(index));
						currentSong.setText(SongLib.list.get(index).getFullInfo());
						sortList();
						renderList();
						listView.getSelectionModel().select(listView.getItems().indexOf(newSong));
					}
					else {
						System.out.println("Matched");
						editError.setVisible(true);
						
					}
				});
				
				// delete button clicked
				deleteButton.setOnAction((event) -> {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Delete ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						System.out.println("Delete Song + "+index+":"+SongLib.list.get(index).getNameArtist());
						SongLib.list.remove(index);
						listView.getItems().remove(index);
						sortList();
						renderList();
						printList();
						if(index<SongLib.list.size()) { // if there is an existing song after
							listView.getSelectionModel().select(index);
						} else if(index>=SongLib.list.size()-1){ // if there is an existing song before
							listView.getSelectionModel().select(index-1);
						} else {
							System.out.println("Delete: No song to pre-select, list is empty");
						}
					} else {
						alert.close();
					}
					
					
				});
				
				// reset button clicked
				resetButton.setOnAction((event) -> {
					editName.setText(SongLib.list.get(index).getName());
					editArtist.setText(SongLib.list.get(index).getArtist());
					editAlbum.setText(SongLib.list.get(index).getAlbum());
					editYear.setText(SongLib.list.get(index).getYear());
				});
				
			} else { // no songs existing in list
				editButton.setDisable(true);
				deleteButton.setDisable(true);
				resetButton.setDisable(true);
				currentSong.setText("Selected:");
				editName.setText("");
				editArtist.setText("");
				editAlbum.setText("");
				editYear.setText("");
			}
		}
		catch(Exception e){
			System.out.println("cellWasSelected() -> Caught");
			
		}
	}
}
