/** @version $Id: AppendDataToFile.java,v 1.12 2014/11/29 13:39:11 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;


import java.io.IOException;

import poof.Content;
import poof.ContentNotExistsException;
import poof.ContentNotFileException;
import poof.FileSystem;
import poof.User;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;
/**
 * §2.2.8.
 */
public class AppendDataToFile extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public AppendDataToFile(FileSystem system) {
		super(MenuEntry.APPEND, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String file = IO.readString(Message.fileRequest());
		String text = IO.readString(Message.textRequest());
		try {
			User u = _receiver.getCurrentUser();
			Content c =_receiver.getEntry(file);
			if(!u.isRoot() && !u.hasPerm(c))
				throw new AccessDeniedException(u.getUserName());
			_receiver.appendData(c,text);
		} catch(ContentNotExistsException a) { throw new EntryUnknownException(file); }
		catch(ContentNotFileException a) { throw new IsNotFileException(file); }
		
	}

}
