/** @version $Id: Open.java,v 1.5 2014/11/28 14:23:09 ist176512 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;
import poof.FileMissingException;
import poof.FileNameDontExistsException;

/**
 * Open existing file.
 */
public class Open extends Command<FSManager> {

	/**
	 * @param receiver
	 */
	public Open(FSManager manager) {
		super(MenuEntry.OPEN, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		if(_receiver.canManage())
			if(_receiver.hasChanges()) {
				boolean flag = IO.readBoolean(Message.saveBeforeExit());
				if (flag)
				{
					try
					{
						_receiver.save();
					}
					catch (FileNameDontExistsException e) { 
						String newName = IO.readString(Message.newSaveAs());
						_receiver.saveAs(newName);	
					}
				}	
				
			}
		String nameFile = IO.readString(Message.openFile());
		try
			{	
				_receiver.openFileSystem(nameFile);
			}
			catch (FileMissingException e)
			{
				IO.println(Message.fileNotFound());
			}
	}

}
