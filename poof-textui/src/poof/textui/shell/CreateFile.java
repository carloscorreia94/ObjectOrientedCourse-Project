/** @version $Id: CreateFile.java,v 1.14 2014/11/29 13:39:11 ist176512 Exp $ */
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
import poof.textui.shell.Message;
/**
 * §2.2.5.
 */
public class CreateFile extends Command<FileSystem>  {
	/**
	 * @param receiver
	 */
	public CreateFile(FileSystem system) {
		super(MenuEntry.TOUCH, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String fileName = IO.readString(Message.fileRequest());
		User u = _receiver.getCurrentUser();
		try {
			_receiver.createFile(fileName);
		} catch(ContentExistsException a) { throw new EntryExistsException(fileName); }
		  catch(NoAccessException a) { throw new AccessDeniedException(u.getUserName()); }
	}
}
