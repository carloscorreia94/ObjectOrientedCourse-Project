/** @version $Id: New.java,v 1.5 2014/11/13 03:35:11 ist176512 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;
import poof.FileNameDontExistsException;

/**
 * Open a new file.
 */
public class New extends Command<FSManager> {

	/**
	 * @param receiver
	 */
	public New(FSManager manager) {
		super(MenuEntry.NEW,manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		//cria filesystem. atributos da class Manager
		if(_receiver.canManage()) { 
			if(_receiver.getActualSystem().hasChanges()) {
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
		}
		
		_receiver.createFileSystem();
	}
}
