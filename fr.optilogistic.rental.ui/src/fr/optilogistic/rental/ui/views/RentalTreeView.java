package fr.optilogistic.rental.ui.views;

import java.util.ArrayList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.RentalAgency;

import fr.optilogistic.rental.core.RentalCoreActivator;
import fr.optilogistic.rental.ui.RentalUiActivator;
import fr.optilogistic.rental.ui.providers.RentalProvider;

public class RentalTreeView extends ViewPart implements IPropertyChangeListener {

	private TreeViewer tv;

	public RentalTreeView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		tv = new TreeViewer(parent);
		RentalProvider rentalProvider = new RentalProvider();
		tv.setContentProvider(rentalProvider);
		tv.setLabelProvider(rentalProvider);
		ArrayList<RentalAgency> input = new ArrayList<>();
		input.add(RentalCoreActivator.getAgency());
		tv.setInput(input);
		tv.expandAll();

		getSite().setSelectionProvider(tv);

		createContextMenu();
	}

	private void createContextMenu() {
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(tv.getControl());
		tv.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tv);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(tv.getContentProvider() instanceof RentalProvider) {
			((RentalProvider) tv.getContentProvider()).updateColourProvider();
		}
		tv.refresh();
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		RentalUiActivator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	@Override
	public void dispose() {
		RentalUiActivator.getDefault().getPreferenceStore().removePropertyChangeListener(this);
		super.dispose();
	}

}
