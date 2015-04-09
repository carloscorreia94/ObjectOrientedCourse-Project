package poof;

public interface Visitor {
	void visitFile(File c);
	void visitDir(Dir c);
	String getResult();
	File retFile();
	Dir retDir();
}
