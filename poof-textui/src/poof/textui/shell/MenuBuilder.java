/** @version $Id: MenuBuilder.java,v 1.7 2014/11/28 14:23:08 ist176512 Exp $ */
package poof.textui.shell;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileSystem;
/**
 * Menu builder for shell operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystem system) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new ListAllEntries(system),
				new ListEntry(system),
				new RemoveEntry(system),
				new ChangeWorkingDirectory(system),
				new CreateFile(system),
				new CreateDirectory(system),
				new ShowWorkingDirectory(system),
				new AppendDataToFile(system),
				new ShowFileData(system),
				new ChangeEntryPermissions(system),
				new ChangeOwner(system),
				});
		menu.open();
	}

}
