package base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	
	// stage of the display
	Stage stage;
    
    /**
     * current note selected by the user
     */
    
    String currentNote = "";

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		this.stage = stage;
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
		buttonLoad.setPrefSize(100, 20);
		//buttonLoad.setDisable(true);
		
		Button buttonSave = new Button("Save to File");
		buttonSave.setPrefSize(100, 20);
		//buttonSave.setDisable(true);
		
		Label label = new Label("Search: ");
		
		TextField searchBar = new TextField();
		
		Button buttonSearch = new Button("Search");
		
		Button clearSearch = new Button("Clear Search");
		
		

		hbox.getChildren().addAll(buttonLoad, buttonSave, label, searchBar, buttonSearch, clearSearch);
		
		buttonSearch.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = searchBar.getText();
				textAreaNote.setText("");
				//TODO
				updateListView();
				
			}
		});
		
		clearSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentSearch = "";
				searchBar.setText("");
				textAreaNote.setText("");
				//TODO
				updateListView();
			}
		});
		
		buttonLoad.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO load a nb from file
				
				// set title for file chooser
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				
				// add ser file filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				// show and open dialog
				File file = fileChooser.showOpenDialog(stage);
				
				if(file != null) {
					//TODO
					loadNoteBook(file);
				}
				
				
				
			}
		});
		
		buttonSave.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO save a nb to file
				// set title for file chooser
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object to Save!");
				
				// add ser file filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				// show and open dialog
				File file = fileChooser.showOpenDialog(stage);
				
				if(file != null) {
					//TODO
					saveNoteBook(file);
				}
				
			}
		});

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private HBox addVHBox() {

		HBox hbox = new HBox();
		//hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes
		Button addFolder = new Button("Add a Folder");
		addFolder.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
			    dialog.setTitle("Input");
			    dialog.setHeaderText("Add a new folder for your notebook:");
			    dialog.setContentText("Please enter the name you want to create:");

			    // Traditional way to get the response value.
			    Optional<String> result = dialog.showAndWait();
			    if (result.isPresent()){
			        // TODO
			    	if(result.get().equals("")) {
			    		//empty
			    		Alert alert = new Alert(AlertType.WARNING);
			    		alert.setTitle("Warning");
			    		alert.setContentText("Please input an valid folder name");
			    		alert.showAndWait().ifPresent(rs -> {
			    		    if (rs == ButtonType.OK) {
			    		        System.out.println("Pressed OK.");
			    		        return;
			    		    }
			    		});
			    	}
			    	else{
			    		//not empty
			    		Boolean haveSameFolder = false;
				    	String newFolderName = result.get();
				    	for(Folder f : noteBook.getFolders()) {
				    		if(f.getName().equals(newFolderName)) {
				    			haveSameFolder = true;
				    			break;
				    		}			    		
				    	}
				    	if(haveSameFolder) {
				    		Alert alert = new Alert(AlertType.WARNING);
				    		alert.setTitle("Warning");
				    		alert.setContentText("You already have a folder named with " + newFolderName);
				    		alert.showAndWait().ifPresent(rs -> {
				    		    if (rs == ButtonType.OK) {
				    		        System.out.println("Pressed OK.");
				    		        return;
				    		    }
				    		});
			    		}
			    	}
			    	// all ok
			    	System.out.println("Have totally new name");
			    	noteBook.addFolder(result.get());
					foldersComboBox.getItems().add(result.get());
			    	
			    }				
			}
		});
		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(addFolder);
		return hbox;
	}
	
	private VBox addVBox() {
		
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		ArrayList<String> names = new ArrayList<String>();
		for(Folder f : noteBook.getFolders()) {
			names.add(f.getName());
		}
		foldersComboBox.getItems().addAll(names);

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				// System.out.println(currentFolder);
				updateListView();
			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				Folder f = null;
				Note required = null;
				for (Folder f1:noteBook.getFolders()) {
					if(f1.getName().equals(currentFolder)) {
						f = f1;	
						break;
					}
				}
				// folder get
				if (f!=null) {
					for (Note n:f.getNotes()) {
							if(n.getTitle().equals(title)) {
								required = n;
								break;
							}
					}
				}
				String content = "";
				if (required !=null) {
					currentNote = required.getTitle();
					TextNote text = (TextNote)required;
					content = text.content;					
				}
				textAreaNote.setText(content);

			}
		});
		Button addNote = new Button("Add a Note");
		addNote.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO 
				System.out.println("Folder selected: " + currentFolder);
				if (currentFolder.equals("-----")) {
					//empty
					Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setContentText("Please choose a folder first!");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		        return;
		    		    }
		    		});					
				}
				else {
					// chose a folder
					System.out.println("Chose a folder");
					TextInputDialog dialog = new TextInputDialog("Add a Note");
				    dialog.setTitle("Input");
				    dialog.setHeaderText("Add a new note to current folder");
				    dialog.setContentText("Please enter the name of your note:");
				    Optional<String> result = dialog.showAndWait();
				    if(result.isPresent()) {
				    	System.out.println("New Note name: " + result.get());
				    	if(noteBook.createTextNote(currentFolder, result.get())) {
				    		//success add
				    		Alert alert = new Alert(AlertType.INFORMATION);
				    		alert.setTitle("Successful!");
				    		alert.setContentText("Insert note " + result.get() + " to folder "+ currentFolder + " successfully!");
				    		alert.showAndWait().ifPresent(rs -> {
				    		    if (rs == ButtonType.OK) {
				    		        System.out.println("Pressed OK.");
				    		        updateListView();
				    		        return;
				    		    }
				    		});
				    	}
				    	else {
				    		// add fail
				    		Alert alert = new Alert(AlertType.WARNING);
				    		alert.setTitle("Warning");
				    		alert.setContentText("Insert note " + result.get() + " to folder "+ currentFolder + " failed!");
				    		alert.showAndWait().ifPresent(rs -> {
				    		    if (rs == ButtonType.OK) {
				    		        System.out.println("Pressed OK.");
				    		        return;
				    		    }
				    		});
				    	}
				    }
				}		    
			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		//vbox.getChildren().add(foldersComboBox);
		vbox.getChildren().add(addVHBox());		
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(addNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();
		System.out.println("update called");
		// System.out.println("Search: " + currentSearch);
		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		// look through folders to get file object
		Folder f = null;
		for (Folder f1:noteBook.getFolders()) {
			if(f1.getName().equals(currentFolder)) {
				f = f1;	
				break;
			}
		}
		// folder get!
		if (f!=null) {
			if (currentSearch == "") {
				for (Note n:f.getNotes()) {
					list.add(n.getTitle());					
				}
			}
			else {
				System.out.println("search is not empty" + f.getName());
				List<Note> result_list = new ArrayList<Note>();
				result_list = f.searchNotes(currentSearch);
				System.out.println(result_list);
				for (Note n: result_list) {
					list.add(n.getTitle());
					System.out.println(n.getTitle());
				}
			}
			
		}
		
		
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private HBox addGHBox() {

		HBox hbox = new HBox();
		//hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		
		Button buttonSaveNote = new Button("Save Note");
		buttonSaveNote.setPrefSize(100, 20);
		
		ImageView trashView = new ImageView(new Image(new File("trash.png").toURI().toString()));
		trashView.setFitHeight(18);
		trashView.setFitWidth(18);
		trashView.setPreserveRatio(true);
		
		Button buttonDeleteNote = new Button("Delete Note");
		buttonDeleteNote.setPrefSize(100, 20);
		hbox.getChildren().add(saveView);
		hbox.getChildren().add(buttonSaveNote);
		hbox.getChildren().add(trashView);
		hbox.getChildren().add(buttonDeleteNote);
		
		buttonSaveNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!(currentFolder.equals("-----")) && !(currentNote.equals(""))) {
					// valid
					String updateContent = textAreaNote.getText();
					// System.out.println("Note: " + updateContent);
					Folder fRequired = null;
					TextNote nRequired = null;
					for (Folder f : noteBook.getFolders()) {
						if(currentFolder.equals(f.getName())) {
							fRequired = f;
							break;
						}
					}
					for(Note n : fRequired.getNotes()) {
						if(currentNote.equals(n.getTitle())) {
							nRequired = (TextNote) n;
						}
					}
					nRequired.content = updateContent;
				}
				else {
					// invalid
					Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setContentText("Please select a folder and a note");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		        return;
		    		    }
		    		});
				}
			}
		});
		
		buttonDeleteNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!(currentFolder.equals("-----")) && !(currentNote.equals(""))) {
					// valid
					// get current Folder
					Folder fRequired = null;
					for (Folder f : noteBook.getFolders()) {
						if(currentFolder.equals(f.getName())) {
							fRequired = f;
							break;
						}
					}
					if(fRequired.removeNotes(currentNote)) {
						// success
						Alert alert = new Alert(AlertType.CONFIRMATION);
			    		alert.setTitle("Succeed!");
			    		alert.setContentText("Your note has been successfully removed!");
			    		alert.showAndWait().ifPresent(rs -> {
			    		    if (rs == ButtonType.OK) {
			    		        System.out.println("Pressed OK.");
			    		        updateListView();
			    		        return;
			    		    }
			    		});
						
					}
					else {
						// failed
						Alert alert = new Alert(AlertType.WARNING);
			    		alert.setTitle("Warning");
			    		alert.setContentText("Failed to delete your note!");
			    		alert.showAndWait().ifPresent(rs -> {
			    		    if (rs == ButtonType.OK) {
			    		        System.out.println("Pressed OK.");
			    		        return;
			    		    }
			    		});
					}
				}
				else {
					// invalid
					Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setContentText("Failed to delete your note!");
		    		alert.showAndWait().ifPresent(rs -> {
		    		    if (rs == ButtonType.OK) {
		    		        System.out.println("Pressed OK.");
		    		        return;
		    		    }
		    		});
				}
			}
		});
		
		
		return hbox;
	}
	private GridPane addGridPane() {
		
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(addGHBox(), 0, 0);
		grid.add(textAreaNote, 0, 1);
		
		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java. And this is old load.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	
	private void loadNoteBook(File file) {
		//TODO
		String fileName = file.getName();
		String pathFile = file.getAbsolutePath();
		System.out.println("Loading " + fileName + " at "+ pathFile);
		NoteBook nb = new NoteBook(pathFile);
		noteBook = nb;
	}
	
	private void saveNoteBook(File file) {
		//TODO
		String fileName = file.getName();
		String pathFile = file.getAbsolutePath();
		System.out.println("Saving " + fileName + " at "+ pathFile);
		if(noteBook.save(pathFile)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successfully saved");
			alert.setContentText("You file has been saved to file " + file.getName());
			alert.showAndWait().ifPresent(rs -> {
			    if (rs == ButtonType.OK) {
			        System.out.println("Pressed OK.");
			    }
			});
		}
	}

}
