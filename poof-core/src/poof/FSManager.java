package poof;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * This is the "root" class of the project. It contains a bunch of classes just to encapsulate
 * content for the interface program, textui
 * @author Carlos Correia 76512, Goncalo Gaspar 58803
 *
 */
public class FSManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private FileSystem _actualSystem;
	private boolean _flagManage = false;
	private User _currentUser;
	public FSManager() { }
	
	/*  Getters and setters */ 
	public boolean canManage() { return _flagManage; }
	public User getCurrentUser() { return _currentUser; }
	public FileSystem getActualSystem() { return _actualSystem; }
	
	
	
	public void createFileSystem() {
		_currentUser = new User("root", "Super User");
		_actualSystem = new FileSystem(_currentUser);
		_flagManage = true;
	}
	/**
	 * _flagManage is updated here in order to ValidityPredicate return the isValid with true 
	 * @param name Name of the file to open, and to give to the System Name
	 */
	public void openFileSystem(String name) throws IOException, FileMissingException { 
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
			_actualSystem=(FileSystem)in.readObject();
			_currentUser=(User)in.readObject();
			in.close();
			_actualSystem.setName(name); 
			_flagManage = true;
			_actualSystem.chUser(_currentUser); //updates the currentDir
		}
		catch (FileNotFoundException  e) { throw new FileMissingException(); }
		catch (IOException            e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	/**
	 * This simple class just reads each line of a text file and the Factory Class accepts a string
	 * and treats it in order to make core objects out of it "decodeLine"
	 */
	public void openTextFile(String fileInputName) throws IOException { 
		
			createFileSystem();
			BufferedReader in = new BufferedReader(new FileReader(fileInputName));
		    String s = new String();
		    while((s = in.readLine()) != null) {
		    	String[] line = s.split("\\|");
		    	try{
		    		_actualSystem.getFactory().decodeLine(line);
		    	}
		    	catch(UserNotListedException a) {a.printStackTrace();}
		    }
		    _actualSystem.setChanges(true);
		    in.close();
	}

	
	public void save() throws IOException, FileNameDontExistsException {
		if (_actualSystem.getName().equals(""))
		{
			throw new FileNameDontExistsException();
		}
		else {
			try
			{
				ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_actualSystem.getName())));
				_actualSystem.setChanges(false);
				out.writeObject(_actualSystem);
				out.writeObject(_currentUser);
				out.close();
				
			}
			catch (IOException            e) { e.printStackTrace(); }
		}
		
	}
		
	public void saveAs(String name) throws IOException {
		_actualSystem.setName(name);
		try {
			save();
		} catch (FileNameDontExistsException e) {
			e.printStackTrace();
		}
	}	
	
	public void chUser(User user) { 
		   _actualSystem.chUser(user);
		   _currentUser=user;
	}
	
	public User getUser(String username) throws UserNotListedException {
		return _actualSystem.getUser(username);
	}
	
	public boolean hasChanges() {
		return _actualSystem.hasChanges();
	}
}