package fr.optilogistic.rental.ui.preferences;

import java.util.Map;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import fr.optilogistic.rental.ui.RentalUIConstants;
import fr.optilogistic.rental.ui.RentalUiActivator;
import fr.optilogistic.rental.ui.pallete.Pallete;

public class PalettePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage, RentalUIConstants {

	public PalettePreferencePage() {
		super(GRID);
		setPreferenceStore(RentalUiActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		// Extract the double String array for name and color provider (value is
		// the key)
		Map<String, Pallete> palettes = RentalUiActivator.getDefault().getPalettes();

		String[][] comboValues = new String[palettes.size()][2];
		int i = 0;
		for (Pallete p : palettes.values()) {
			comboValues[i][0] = p.getName(); // Displayed name
			comboValues[i][1] = p.getId(); // Returned value if selected
			i++;
		}

		addField(new ComboFieldEditor(PREF_PALETTE, "Palette :", comboValues, getFieldEditorParent()));

	}

	@Override
	public void init(IWorkbench workbench) {

	}
	
	

}
