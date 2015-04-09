package poof;

import java.util.ArrayList;
import java.util.List;

public class File extends Content {
	private static final long serialVersionUID = 2L;
	private List<String> _data;
	private int _size;
	public File(String name,User user,boolean perm) {
		super(name,perm,user);
		_size = 0;
		_data = new ArrayList<String>();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitFile(this);
	}
	
	@Override
	public void appendData(String text) {
		_data.add(text);
		_size += text.length() + 1;
	}
	@Override
	public List<String> viewData() {
		if(_data.size()!=0) _data.add("");
		return _data;
	}
	
	@Override
	public int calcSize() {
		return _size;
	}
	
	@Override
	public String toString() {
		return "- " + super.toString();
	}
	
}
