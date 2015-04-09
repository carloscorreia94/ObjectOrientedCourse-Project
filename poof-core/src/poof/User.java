package poof;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 2L;
	private String _username;
	private String _name;
	private String _mainDir;
	
	public User(String username,String name) {
		_username = username;
		_name = name;
		_mainDir = "/home/"+username;
	}
	
	public String getMainDir() { return _mainDir; }
	public String getUserName() { return _username; }
	public String getFullName() { return _name; }
	
	@Override
	public String toString() {
		return this.getUserName()+":"+this.getFullName()+":"+this.getMainDir();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof User) {
			User other = (User) o;
			return _username.equals(other.getUserName()) && _name.equals(other.getFullName());
		}
		return false;
	}
	
	public boolean isRoot() { return _username.equals("root"); }
	//any one can do stuff if its public
	public boolean hasPerm(Content c) { 
		if(c.getPerm().equals("-"))
			return this.equals(c.getUser());
		return true;	
		}
}
