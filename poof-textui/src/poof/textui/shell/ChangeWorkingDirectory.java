/** @version $Id: ChangeWorkingDirectory.java,v 1.10 2014/11/28 14:23:08 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.ContentNotDirException;
import poof.DirNotFoundException;
import poof.FileSystem;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotDirectoryException;
import poof.textui.shell.Message;
/**
 * §2.2.4.
 */
public class ChangeWorkingDirectory extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ChangeWorkingDirectory(FileSystem system) {
		super(MenuEntry.CD, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		
		String dir = IO.readString(Message.directoryRequest());
		try { _receiver.chWorkDir(dir); }
		catch(DirNotFoundException a) { throw new EntryUnknownException(dir); }
		catch(ContentNotDirException a) { throw new IsNotDirectoryException(dir); }
	}

}
