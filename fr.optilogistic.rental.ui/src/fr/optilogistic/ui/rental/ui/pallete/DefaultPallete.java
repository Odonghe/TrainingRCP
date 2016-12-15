package fr.optilogistic.ui.rental.ui.pallete;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

import fr.optilogistic.rental.ui.RentalUIConstants;
import fr.optilogistic.rental.ui.RentalUiActivator;

public class DefaultPallete implements IColorProvider, RentalUIConstants {

	public DefaultPallete() {
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof RentalAgency) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		}
		if (element instanceof Customer) {
			return getRGBColor(RentalUiActivator.getDefault().getPreferenceStore().getString(PREF_CUSTOMER_COLOR));
		}
		if (element instanceof RentalObject) {
			return getRGBColor(RentalUiActivator.getDefault().getPreferenceStore().getString(PREF_RENTAL_OBJECT_COLOR));
		}
		if (element instanceof Rental) {
			return getRGBColor(RentalUiActivator.getDefault().getPreferenceStore().getString(PREF_RENTAL_COLOR));
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

	@Override
	public Color getBackground(Object element) {
		if (element instanceof RentalAgency) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	}

	private Color getRGBColor(String rgbKey) {
		ColorRegistry colorReg = JFaceResources.getColorRegistry();
		Color col = colorReg.get(rgbKey);

		if (col == null) {
			colorReg.put(rgbKey, StringConverter.asRGB(rgbKey));
			col = colorReg.get(rgbKey);
		}
		return col;
	}
}
