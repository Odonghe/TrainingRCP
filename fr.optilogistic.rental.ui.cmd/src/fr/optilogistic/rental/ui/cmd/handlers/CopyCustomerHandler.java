package fr.optilogistic.rental.ui.cmd.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import com.opcoach.training.rental.Customer;

import fr.optilogistic.rental.ui.RentalUIConstants;
import fr.optilogistic.rental.ui.RentalUiActivator;

public class CopyCustomerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Clipboard clipboard = new Clipboard(Display.getCurrent());
		String textData = "";
		ISelection currSelection = HandlerUtil.getCurrentSelection(event);
		ImageData imgData = null;
		if (currSelection instanceof IStructuredSelection) {
			IStructuredSelection isel = (IStructuredSelection) currSelection;
			for (Iterator<?> it = isel.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof Customer) {
					textData += ((Customer) obj).getDisplayName() + "\n";
				}
			}
			imgData = RentalUiActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_CUSTOMER).getImageData();
		}

		String rtfData = "{\\rtf1\\b\\i " + textData + "}";
		TextTransfer textTransfer = TextTransfer.getInstance();
		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		ImageTransfer imgTransfer = ImageTransfer.getInstance();
		Transfer[] transfers = new Transfer[] { textTransfer, rtfTransfer, imgTransfer };
		Object[] data = new Object[] { textData, rtfData, imgData };
		clipboard.setContents(data, transfers, DND.CLIPBOARD);
		clipboard.dispose();
		return null;
	}

}
