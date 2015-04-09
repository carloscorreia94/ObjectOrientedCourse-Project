/** @version $Id: CreateUser.java,v 1.9 2014/11/28 14:23:07 ist176512 Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.Dir;
import poof.FileSystem;
import poof.User;
import poof.UserNotListedException;
import poof.textui.AccessDeniedException;
import poof.textui.UserExistsException;
import poof.textui.user.Message;
/**
 * §2.3.1.
 */
public class CreateUser extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public CreateUser(FileSystem system) {
		super(MenuEntry.CREATE_USER, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String userName = IO.readString(Message.usernameRequest());
		String userRealName = IO.readString(Message.nameRequest());
		User u = _receiver.getCurrentUser();
		if(!u.isRoot())
			throw new AccessDeniedException(u.getUserName());
		try{
			_receiver.getUser(userName);
			throw new UserExistsException(userName);
		}
		catch(UserNotListedException a) {
			_receiver.addUser(userName,userRealName);
		}
	}
}
