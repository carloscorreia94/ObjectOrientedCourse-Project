package poof;

public class ContentNotDirException extends Exception {
	private static final long serialVersionUID = 2L;
	private String _fileName;
	private Content _f;
	public ContentNotDirException() { }
	public ContentNotDirException(String a) {
		_fileName = a;
	}
	public ContentNotDirException(Content f) {
		_f = f;
	}
	
	public Content getFile() {
		return _f;
	}
	
	@Override
	public String toString() {
		return _fileName;
	}
}
