package poof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;


/**
 * Main class of the core structure. Almost all operations are linked within this class.
 * CurrentUser attribute is duplicated here and its always updated, because in textui I couldn't add another receiver
 * as Command(The one implemented by the Professor) (FSManager one) so I can check users there.
 *
 */
public class FileSystem implements Serializable {
	private static final long serialVersionUID = 2L;
	private String _name;
	private boolean _hasChanges = false;
	private TreeMap<String,User> _users;
	private Dir _currentDir;
	public User _currentUser;
	private Factory _factory;
	
	public FileSystem(User currentUser) {
		_users = new TreeMap<String,User>(new CompareUsers());
		_users.put("root", currentUser);
		_currentUser = currentUser;
		_currentDir = new Dir(currentUser);
		_name = "";
		_factory = new Factory(this);
		
	}
	/**
	 * Set the flag of changes been made, in order to alert when abandoning the FileSystem.
	 */
	public boolean hasChanges() { return _hasChanges; }
	public void setChanges(boolean hasChanges) { _hasChanges=hasChanges; }
	public String getWorkDir() { 
		if(_currentDir.getAbsolutePath().equals(""))
			return "/";
		return _currentDir.getAbsolutePath(); 
		}
	public String getName() { return _name; }
	public void setName(String name) { _name = name; }
	public Factory getFactory() { return _factory; }
	public User getCurrentUser() { return _currentUser; }
	/**
	 * Returns the actual Directory associated with the fileSystem.
	 */
	public Dir getCurrentDir() { return _currentDir; }
	
	/**
	 * Changes the directory based on the directories in the current dir. It can accept 
	 * a composed path, I don't think of it as an improvement, but as a logic and simple thing to do,
	 * as well as more practical.
	 * @param dir Simple or composed relative path
	 */
	public void chWorkDir(String dir) throws DirNotFoundException,ContentNotDirException { 
		String[] subdirs = dir.split("\\/");
		/* Se a string indicar a raiz */
		for(int i=0;i<subdirs.length;i++) 
			_currentDir = _currentDir.access(subdirs[i]);
	}
	
	/**
	 * Creates a Dir inside the current Dir
	 *  @param user receives User associated with the dir
	 *  @param pubAccess kind of access given to the directory
	 * @throws NoAccessException 
	 * @throws ContentExistsException
	 */
	public void createDir(String name) throws ContentExistsException, NoAccessException {
		if(!_currentUser.isRoot() && !_currentUser.hasPerm(getCurrentDir()))
			throw new NoAccessException();
		try {
			_currentDir.getEntry(name);
			throw new ContentExistsException();
		} catch (ContentNotExistsException e) {
			new Dir(name,_currentDir,_currentUser,false);
			setChanges(true);
		}
		
	}
	
	/**
	 * It is passed to the User TreeMap so it can compare users with the criteria of ignoreCase
	 * 
	 *
	 */
	private class CompareUsers implements Comparator<String>,Serializable{
		
		private static final long serialVersionUID = 2L;

		@Override
	    public int compare(String e1, String e2) {
	        return e1.compareToIgnoreCase(e2);
	    }
	}
	
	/**
	 * Create a new user and add it to the user structure.
	 * @param username
	 * @param userRealName Full Name
	 */
	public void addUser(String username,String userRealName) {
		User tempUser = new User(username,userRealName);
		_users.put(username,tempUser);
		_factory.createDir("/home",tempUser,false);
		setChanges(true);
	}
	
	/**
	 * Changes actual User. If caught exceptions, because root user or main user deleted is own folder, 
	 * or root created a file in the place of the dir instead, the Directory is recreated.
	 * @param user User to change
	 */
	public void chUser(User user) {
		try{
			_currentDir = _currentDir.goToRoot();
			chWorkDir("home/" + user.getUserName());
			_currentUser = user;
			setChanges(true);
		}
		catch(ContentNotDirException a) { 
			_currentDir.deleteContent(user.getUserName());
			_factory.createDir("/home", user, false);
			chUser(user);
			
		}
		catch(DirNotFoundException a) { 
			_factory.createDir("/home", user, false);
			chUser(user);
			}
	}
	
	/**
	 * Gets User to verify or to do certain ops.
	 * @param username
	 * @throws UserNotListedException
	 */
	public User getUser(String username) throws UserNotListedException {
		if(_users.get(username)!=null) 
			return _users.get(username);
		else 
			throw new UserNotListedException();
	}
	/**
	 * Returns users ascending order, ignoring case.
	 */
	public List<String> listUsers() {
		List<String> outBuffer = new ArrayList<String>();
		for (String part: _users.keySet())
		      outBuffer.add(_users.get(part).toString());
		return outBuffer;
	}
	
	
	public void createFile(String name) throws ContentExistsException,NoAccessException {
		if(!_currentUser.isRoot() && !_currentUser.hasPerm(getCurrentDir()))
			throw new NoAccessException();
		Content file = new File(name,_currentUser,false);
		_currentDir.addEntry(file);
		setChanges(true);
	}
	
	/**
	 * Method only exists to encapsulate directory info for textui.
	 */
	public Content getEntry(String name) throws ContentNotExistsException {
		return _currentDir.getEntry(name);
	}
	/**
	 * Method only exists to encapsulate directory info for textui.
	 */
	public List<String> listDir() {
		return _currentDir.listDir();
	}
	
	public void deleteContent(String dirName) {
		_currentDir.deleteContent(dirName);
		setChanges(true);
	}
	public void appendData(Content c, String text) throws ContentNotFileException {
		c.appendData(text);
		setChanges(true);
	}
	public void chPerm(Content c, boolean perm) {
		c.chPerm(perm);
		setChanges(true);
	}
	public void chOwner(Content c, User u) {
		c.chOwner(u);
		setChanges(true);
	}
	
}