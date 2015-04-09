/** @version $Id: RemoveEntry.java,v 1.13 2014/11/29 13:39:11 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;


import java.io.IOException;

import poof.Content;
import poof.ContentNotExistsException;
import poof.FileSystem;
import poof.User;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IllegalRemovalException;
/**
 * §2.2.3.
 */
public class RemoveEntry extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public RemoveEntry(FileSystem system) {
		super(MenuEntry.RM, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String dirName = IO.readString(Message.nameRequest());
		if(dirName.equals(".") || dirName.equals(".."))
			throw new IllegalRemovalException();
		try {
			User u = _receiver.getCurrentUser();
			Content actDir = _receiver.getCurrentDir();
			Content c =_receiver.getEntry(dirName);
			if(!u.isRoot() && !u.hasPerm(actDir)) {
				throw new AccessDeniedException(u.getUserName());
			}
			if(!u.isRoot() && !u.hasPerm(c))
				throw new AccessDeniedException(u.getUserName());
			_receiver.deleteContent(dirName);
		} catch(ContentNotExistsException a) { throw new EntryUnknownException(dirName); }
	}
}
