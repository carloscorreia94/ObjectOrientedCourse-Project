/** @version $Id: ShowFileData.java,v 1.11 2014/11/28 14:23:07 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;


import java.io.IOException;
import java.util.List;

import poof.Content;
import poof.ContentNotExistsException;
import poof.ContentNotFileException;
import poof.FileSystem;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;
/**
 * §2.2.9.
 */
public class ShowFileData extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ShowFileData(FileSystem system) {
		super(MenuEntry.CAT, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String file = IO.readString(Message.fileRequest());
		try {
			Content c = _receiver.getEntry(file);
			List<String> buffer = c.viewData();
			for(String i : buffer)
				IO.println(i);
		} catch(ContentNotExistsException a) { throw new EntryUnknownException(file); }
		catch(ContentNotFileException a) { throw new IsNotFileException(file); }
	}
}
