/** @version $Id: Login.java,v 1.7 2014/11/28 14:23:09 ist176512 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;
import poof.User;
import poof.UserNotListedException;
import poof.textui.UserUnknownException;
/**
 * §2.1.2.
 */
public class Login extends Command<FSManager> {

	/**
	 * @param receiver
	 */
	
	public Login(FSManager manager) {
		super(MenuEntry.LOGIN, manager, new ValidityPredicate<FSManager>(manager) {
            public boolean isValid() {
            	//se existe um sistema de ficheiros activo
                return _receiver.canManage();
        }});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String userName = IO.readString(Message.usernameRequest());
		try {
			User temp = _receiver.getUser(userName);
			_receiver.chUser(temp);
		}
		catch(UserNotListedException a) { throw new UserUnknownException(userName); }		
	}
}
