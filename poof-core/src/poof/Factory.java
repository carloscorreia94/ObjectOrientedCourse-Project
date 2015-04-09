package poof;
import java.io.Serializable;

public class Factory implements Serializable {

	private static final long serialVersionUID = 2L;
	private FileSystem _system;
	public Factory(FileSystem system) { _system = system; }
	
	/**
	 * Each line of the text file is treated individually here. 
	 */
	public void decodeLine(String[] line) throws UserNotListedException {
		if(line[0].equals("USER")) 
    		_system.addUser(line[1],line[2]);
    	if(line[0].equals("DIRECTORY")) {
    		boolean temp;
    		if(line[3].equals("public"))  temp=true;
    		else temp=false;
    		createDir(line[1], _system.getUser(line[2]), temp);
    	}
    	if(line[0].equals("FILE")) { 
    		boolean temp;
    		if(line[3].equals("public"))  temp=true;
    		else temp=false;
    		try {
				createFile(line[1], _system.getUser(line[2]), temp, line[4]);
			} catch (ContentExistsException e) { }
    		
    	}
	}
	
	/**
	 * Creates a directory based on an absolute path
	 * @param abs receives abs path
	 * @param user receives User associated with the dir
	 * @param pubAccess kind of access given to the directory
	 */
	public void createDir(String abs, User user,boolean pubAccess) {
		/*Optimizar ciclo para nao ter que ir sempre ate a raiz... 
		 * (comecar a partir de onde string difere (: )
		 */
		Dir temp = _system.getCurrentDir();
		String[] subdirs = abs.split("\\/");
		temp = temp.goToRoot();
		for(int i=1;i<subdirs.length;i++) {
			try {
				temp = temp.access(subdirs[i]);
			}
			catch(DirNotFoundException a) { temp = new Dir(subdirs[i],temp,user,pubAccess); }
			catch(ContentNotDirException a) {}
		}
		if(abs.equals("/home")) { new Dir(user.getUserName(),temp,user,pubAccess); }
		_system.setChanges(true);
		
	}
	/**
	 * Creates a File based on an Absolute Path hierarchy
	 * @param abs receives abs path
	 * @param user receives User associated with the dir
	 * @param pubAccess kind of access given to the directory
	 * @param content Line of text to add to file
	 * @throws ContentExistsException In case the file exists already, or the dir. (Entries are always well formed, 
	 * but this method uses other methods that need the exception)
	 */
	public void createFile(String abs,User user,boolean pubAccess,String content) throws ContentExistsException {
		Dir temp = _system.getCurrentDir();
		String[] subdirs = abs.split("\\/");
		temp = temp.goToRoot();
		for(int i=1;i<subdirs.length-1;i++) {
			try {
				temp = temp.access(subdirs[i]);
			}
			catch(DirNotFoundException a) { temp = new Dir(subdirs[i],temp,user,pubAccess); }
			catch(ContentNotDirException a) {}
		}
		File file = new File(subdirs[subdirs.length-1],user,pubAccess);
		file.appendData(content);
		temp.addEntry(file);
	}
	
	
}
