package poof;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.io.Serializable;


public class Dir extends Content {
	private static final long serialVersionUID = 2L;
	private Dir _parent;
	private TreeMap<String,Content> dirStruct;
	/**
	 * Default constructor for Directories. TreeMap receives an instance of CompareDirs, in order
	 * to compare Directories with ignoreCase, in this case the key value, a String
	 * @param parentDir parentDir is always included in order to keep track of linked parent/child directories.
	 * @param pub Type of access. Public or private (True, or false)
	 */
	public Dir(String name,Dir parentDir,User owner,boolean pub) {
		super(name,pub,owner);
		_parent = parentDir;
		dirStruct = new TreeMap<String,Content>(new CompareDirs());
		if(_parent==null) {
		dirStruct.put(".", this);
		dirStruct.put("..", this);
		}
		else {
			dirStruct.put(".", this);
			dirStruct.put("..", this.getParent());
			try{ 
				_parent.addEntry(this);
			} catch(ContentExistsException a) {
			}
		}
	}
	
	/**
	 * This constructor is only used once, within the creation of the FileSystem. Tree initial folders are built (inside each others)
	 * @param root Root user associated passed as a reference because it has to be constructed in FileSystem class.
	 */
	public Dir(User root) {
		super("root",false,root);
		//cria raiz
		Dir raiz = new Dir("/",null,root,false);
		//cria home
		Dir home = new Dir("home",raiz,root,false);
		//cria root
		_parent = home;
		dirStruct = new TreeMap<String,Content>(new CompareDirs());
		dirStruct.put(".", this);
		dirStruct.put("..", this.getParent());
		try{ 
			_parent.addEntry(this);
		} catch(ContentExistsException a) {}
		
	}
	private class CompareDirs implements Comparator<String>,Serializable{
		private static final long serialVersionUID = 2L;
	    @Override
	    public int compare(String e1, String e2) {
	        return e1.compareToIgnoreCase(e2);
	    }
	}
	
	
	public Dir getParent() { return _parent; }
	public void deleteContent(String name){
		dirStruct.remove(name);
	}
	/**
	 *List the Content(entries) in the current directory.
	 *Special cases are treated here, the "." and ".." ones
	 */
	public List<String> listDir() {
		List<String> resultado = new ArrayList<String>();
		for (String part: dirStruct.keySet()) {
			Content d = dirStruct.get(part);
			if(part.equals(".") || part.equals("..")) { 
				String test = "d " + d.getPerm() +" " + d.getUser().getUserName() + " " + d.calcSize() + " " + part;
				resultado.add(test);
				continue;
			}
			resultado.add(dirStruct.get(part).toString());
		}
		return resultado;
	}
	
	@Override
	public int calcSize() {
		return 8*dirStruct.size();
	}

	/**
	 * This is the only method where Visitor Pattern is used in order to return a directory instead
	 * of a generic content object, so it can be used in FileSystem methods and within functions that need Directories (in our implementation)
	 * @return Directory, from the Visitor object
	 */
	public Dir access(String i) throws DirNotFoundException,ContentNotDirException { 
		Visitor listVis;
		if(i.equals(".")) {
			return this;
		}
		if(i.equals("..")) {
			if(getParent()==null) throw new DirNotFoundException();
			return getParent();
		}
		
		if(dirStruct.get(i)==null) {
			throw new DirNotFoundException();
		}
		listVis = new AccessVis();
		dirStruct.get(i).accept(listVis);
		if(!listVis.getResult().equals("DIR")) {
			throw new ContentNotDirException(dirStruct.get(i)); 
		}
		return listVis.retDir();
		}
	
	
	public String getAbsolutePath() {
		if(_parent==null) { return ""; }
		return _parent.getAbsolutePath() + "/" + getName();
	}
	
	public void addEntry(Content c) throws ContentExistsException {
		if(dirStruct.get(c.getName())!=null) { throw new ContentExistsException(); }
		dirStruct.put(c.getName(),c);
	}
		
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Dir) {
			Dir d = (Dir) other;
			return getAbsolutePath().equals(d.getAbsolutePath());
		}
		return false;
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitDir(this);
	}
	
	/**
	 * Useful method to return the root Directory from the folder we currently are at.
	 */
	public Dir goToRoot() {
		Dir temp = this;
		while(temp.getParent()!=null) 
			temp = temp.getParent();
		return temp;
	}
	
	public Content getEntry(String name) throws ContentNotExistsException {
		if(dirStruct.get(name)==null)
			throw new ContentNotExistsException();
		return dirStruct.get(name);
	}

	@Override
	public void appendData(String text) throws ContentNotFileException {
		throw new ContentNotFileException();	
	}
	
	@Override
	public String toString() {
		return "d " + super.toString();
	}

	@Override
	public List<String> viewData() throws ContentNotFileException {
		throw new ContentNotFileException();
	}
	
}
