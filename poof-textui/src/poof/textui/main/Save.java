/** @version $Id: Save.java,v 1.4 2014/11/12 16:28:45 ist176512 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;
import poof.FileNameDontExistsException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
public class Save extends Command<FSManager> {
	/**
	 * @param receiver
	 */
	public Save(FSManager manager) {
		super(MenuEntry.SAVE, manager, new ValidityPredicate<FSManager>(manager) {
            public boolean isValid() {
            	return _receiver.canManage();
        }});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		try {
			_receiver.save();
		}
		catch(FileNameDontExistsException e) {
			String nameToSave = IO.readString(Message.newSaveAs());
			_receiver.saveAs(nameToSave);	
		}
	}
}
