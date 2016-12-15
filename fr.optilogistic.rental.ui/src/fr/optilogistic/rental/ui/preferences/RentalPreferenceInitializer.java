package fr.optilogistic.rental.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import fr.optilogistic.rental.ui.RentalUIConstants;
import fr.optilogistic.rental.ui.RentalUiActivator;

public class RentalPreferenceInitializer extends AbstractPreferenceInitializer implements RentalUIConstants {

	public RentalPreferenceInitializer() {
		
	}

	@Override
	public void initializeDefaultPreferences() {
		
		IPreferenceStore preferenceStore = RentalUiActivator.getDefault().getPreferenceStore();
		
		preferenceStore.setDefault(PREF_CUSTOMER_COLOR, StringConverter.asString(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN).getRGB()));
		preferenceStore.setDefault(PREF_RENTAL_COLOR, StringConverter.asString(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED).getRGB()));
		preferenceStore.setDefault(PREF_RENTAL_OBJECT_COLOR, StringConverter.asString(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_CYAN).getRGB()));
		
		preferenceStore.setDefault(PREF_PALETTE, "fr.optilogistic.ui.rental.ui.pallete.default");
	}

}
