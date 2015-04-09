/** @version $Id: ChangeOwner.java,v 1.11 2014/11/29 13:39:11 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.Content;
import poof.ContentNotExistsException;
import poof.FileSystem;
import poof.User;
import poof.UserNotListedException;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.UserUnknownException;
/**
 * §2.2.11.
 */
public class ChangeOwner extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ChangeOwner(FileSystem system) {
		super(MenuEntry.CHOWN, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String name = IO.readString(Message.nameRequest());
		String newUser = IO.readString(Message.usernameRequest());
			try {
				User u = _receiver.getUser(newUser);
				User current = _receiver.getCurrentUser();
				Content c =_receiver.getEntry(name);
				User assocUser = c.getUser();
				if(!current.isRoot() && !assocUser.equals(current))
					throw new AccessDeniedException(current.getUserName());
				_receiver.chOwner(c,u);
				
			} catch (ContentNotExistsException e) {
				throw new EntryUnknownException(name);
			} catch (UserNotListedException e) {
				throw new UserUnknownException(newUser);
			}
		
	}

}
