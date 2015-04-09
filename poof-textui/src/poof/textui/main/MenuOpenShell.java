/** @version $Id: MenuOpenShell.java,v 1.6 2014/11/28 14:23:09 ist176512 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FSManager;


/**
 * Open shell menu.
 */
public class MenuOpenShell extends Command<FSManager> {

	/**
	 * @param receiver
	 */
	public MenuOpenShell(FSManager manager) {
		super(MenuEntry.MENU_SHELL,manager, new ValidityPredicate<FSManager>(manager) {
            public boolean isValid() {
            	//se existe um sistema de ficheiros activo
                return _receiver.canManage();
        }});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		poof.textui.shell.MenuBuilder.menuFor(_receiver.getActualSystem());
	}

}
