/** @version $Id: ListAllUsers.java,v 1.8 2014/11/28 14:23:07 ist176512 Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import java.util.List;

import poof.FileSystem;
/**
 * §2.3.2.
 */
public class ListAllUsers extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ListAllUsers(FileSystem system) {
		super(MenuEntry.LIST_USERS, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException  {
		List<String> outBuffer = _receiver.listUsers();
		for(String i: outBuffer)
			IO.println(i);
	}
}
