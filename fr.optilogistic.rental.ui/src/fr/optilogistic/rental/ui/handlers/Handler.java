package fr.optilogistic.rental.ui.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class Handler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currSelection = HandlerUtil.getCurrentSelection(event);
		if(currSelection instanceof IStructuredSelection) {
			IStructuredSelection isel = (IStructuredSelection) currSelection;
			for(Iterator<?> it = isel.iterator(); it.hasNext();) {
				System.out.println("Object sélectionné: " + it.next());
			}
		}
		return null;
	}

}
