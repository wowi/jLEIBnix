package org.leibnix.repository.client.ui;

import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.core.RepositoryManager;
import org.leibnix.repository.common.Module;

import ch.ethz.iks.r_osgi.RemoteOSGiException;

public class InstallOrUpdate extends ActionDelegate implements IObjectActionDelegate {

	private IWorkbenchPart mPart;
	private TreeSelection mSelection;

	@Override
	public void run(IAction action) {
			System.out.println("Install or update");
			if (mSelection.getFirstElement() != null && mSelection.getFirstElement() instanceof Module) {
				Module module = (Module) mSelection.getFirstElement();
				try {
					RepositoryManager.getInstance().installOrUpdateModule (module);
				} catch (RemoteOSGiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
	}
	

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		mPart = targetPart;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mSelection = (TreeSelection) selection;
		super.selectionChanged(action, selection);
	}


}
