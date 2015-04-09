/** @version $Id: ListEntry.java,v 1.10 2014/11/28 14:23:08 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import java.util.List;

import poof.Content;
import poof.ContentNotExistsException;
import poof.DirNotFoundException;
import poof.FileSystem;
import poof.textui.EntryUnknownException;
/**
 * §2.2.2.
 */
public class ListEntry extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ListEntry(FileSystem system) {
		super(MenuEntry.LS_ENTRY, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String contName = IO.readString(Message.nameRequest());
		try {
			Content c =_receiver.getEntry(contName);
			IO.println(c.toString());
		} catch(ContentNotExistsException a) { throw new EntryUnknownException(contName); }
	}
		

}
