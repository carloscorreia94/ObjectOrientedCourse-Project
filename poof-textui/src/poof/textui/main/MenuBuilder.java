/** @version $Id: MenuBuilder.java,v 1.4 2014/11/12 16:28:44 ist176512 Exp $ */
package poof.textui.main;

import ist.po.ui.Command;
import ist.po.ui.Menu;
import poof.FSManager;
/**
 * Menu builder.
 */
public abstract class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FSManager manager) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new New(manager),
				new Open(manager),
				new Save(manager),
				new Login(manager),
				new MenuOpenShell(manager),
				new MenuOpenUserManagement(manager)
		});
		menu.open();
	}

}
