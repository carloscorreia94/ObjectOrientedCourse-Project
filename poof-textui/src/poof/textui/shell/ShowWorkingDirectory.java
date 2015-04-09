/** @version $Id: ShowWorkingDirectory.java,v 1.9 2014/11/28 14:23:07 ist176512 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystem;
/**
 * §2.2.7.
 */
public class ShowWorkingDirectory extends Command<FileSystem>  {
	/**
	 * @param receiver
	 */
	public ShowWorkingDirectory(FileSystem system) {
		super(MenuEntry.PWD, system);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		IO.println(_receiver.getWorkDir());
	}

}
