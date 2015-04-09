package poof;

import java.io.Serializable;

/**
 */
public class AccessVis implements Visitor, Serializable {
	
	private static final long serialVersionUID = 2L;
	private File _file;
	private Dir _dir;
	private String _type;
	public AccessVis() {
		_type = "";
	}
	public void visitFile(File f) {
		_type = "FILE";
		_file = f;
		
	}
	public void visitDir(Dir d) {
		_type = "DIR";
		_dir = d;
		
	}
	public String getResult() {
		return _type;
	}
	@Override
	public File retFile() {
		return _file;
	}
	@Override
	public Dir retDir() {
		return _dir;
	}
}
