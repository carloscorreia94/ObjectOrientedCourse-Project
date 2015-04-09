/** @version $Id: CreateDirectory.java,v 1.12 2014/11/29 13:39:11 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.Content;
import poof.ContentExistsException;
import poof.FileSystem;
import poof.NoAccessException;
import poof.User;
import poof.textui.AccessDeniedException;
import poof.textui.EntryExistsException;
/**
 * §2.2.6.
 */
public class CreateDirectory extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public CreateDirectory(FileSystem system) {
		super(MenuEntry.MKDIR, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String dirName = IO.readString(Message.directoryRequest());
		User u = _receiver.getCurrentUser();
		try {
			_receiver.createDir(dirName);
		} catch(ContentExistsException a) { throw new EntryExistsException(dirName); }
		  catch(NoAccessException a) { throw new AccessDeniedException(u.getUserName()); }
	}
}
