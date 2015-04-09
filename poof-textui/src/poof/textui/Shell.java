/** @version $Id: Shell.java,v 1.8 2014/11/28 00:56:18 ist176512 Exp $ */
package poof.textui;

import static ist.po.ui.Dialog.IO;

import java.io.IOException;

import poof.FSManager;
/* 
 * exceptions import here */

/**
 * Class that starts the application's textual interface.
 */
public class Shell {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FSManager manager = new FSManager();
		String datafile = System.getProperty("import"); //$NON-NLS-1$
		if (datafile != null) {
			try 
		{
			manager.openTextFile(datafile);
		}
		catch (IOException            e) { e.printStackTrace(); }
		}
		poof.textui.main.MenuBuilder.menuFor(manager);
		IO.closeDown();
	}

}
