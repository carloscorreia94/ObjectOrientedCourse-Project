package poof;

import java.io.Serializable;
import java.util.List;


public abstract class Content implements Serializable {
	private static final long serialVersionUID = 2L;
	private String _name;
	private boolean _allPermissions;
	private User _owner;
	
	public Content(String name,boolean allPermissions,User owner) {
		_name = name;
		_allPermissions = allPermissions;
		_owner = owner;
	}

	public String getName() { return _name; }
	public String getPerm() { 
		if(_allPermissions) return "w";
		return "-"; 
	}
	public void chPerm(boolean perm) { _allPermissions = perm; }
	public User getUser() { return _owner; }
	public void chOwner(User user) {
		_owner = user;
	}
	
	@Override
	public String toString() {
		return getPerm() + " " + getUser().getUserName() + " " + calcSize() + " " + getName();
	}
	
	public void chName(String name) { _name = name; }
	public abstract void accept(Visitor v);
	public abstract int calcSize();
	public abstract void appendData(String text) throws ContentNotFileException;
	public abstract List<String> viewData() throws ContentNotFileException;
	
}
