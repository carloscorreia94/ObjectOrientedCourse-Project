/** @version $Id: ChangeEntryPermissions.java,v 1.11 2014/11/29 13:39:11 ist176512 Exp $ */
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
/**
 * §2.2.10.
 */
public class ChangeEntryPermissions extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ChangeEntryPermissions(FileSystem system) {
		super(MenuEntry.CHMOD, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String name = IO.readString(Message.nameRequest());
		boolean perm = IO.readBoolean(Message.publicAccess());
		
		try{
			Content c =_receiver.getEntry(name);
			User u = c.getUser();
			User current = _receiver.getCurrentUser();
			_receiver.chPerm(c,perm);
		if(!current.isRoot() && !current.equals(u))
			throw new AccessDeniedException(current.getUserName());
		} 
		catch(ContentNotExistsException a) { throw new EntryUnknownException(name); }
		
	
	} 

}
