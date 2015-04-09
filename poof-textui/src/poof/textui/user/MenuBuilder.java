/** @version $Id: MenuBuilder.java,v 1.6 2014/11/28 14:23:07 ist176512 Exp $ */
package poof.textui.user;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileSystem;

/**
 * Menu builder for search operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystem system) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new CreateUser(system),
				new ListAllUsers(system),
				});
		menu.open();
	}

}
