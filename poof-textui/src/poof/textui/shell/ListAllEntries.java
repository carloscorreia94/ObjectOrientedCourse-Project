/** @version $Id: ListAllEntries.java,v 1.11 2014/11/28 14:23:08 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;


import java.io.IOException;
import java.util.List;

import poof.FileSystem;
/**
 * §2.2.1.
 */
public class ListAllEntries extends Command<FileSystem> {
	/**
	 * @param receiver
	 */
	public ListAllEntries(FileSystem system) {
		super(MenuEntry.LS, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		List<String> out = _receiver.listDir();
		for(String i: out)
			IO.println(i);
	}

}
