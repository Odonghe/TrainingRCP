package fr.optilogistic.rental.ui.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.RentalAgency;

import fr.optilogistic.rental.core.RentalCoreActivator;
import fr.optilogistic.rental.ui.providers.RentalProvider;

public class RentalTreeView extends ViewPart {

	public RentalTreeView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		TreeViewer tv = new TreeViewer(parent);
		tv.setContentProvider(RentalProvider.getIntance());
		tv.setLabelProvider(RentalProvider.getIntance());
		ArrayList<RentalAgency> input = new ArrayList<>();
		input.add(RentalCoreActivator.getAgency());
		tv.setInput(input);
		tv.expandAll();
		getSite().setSelectionProvider(tv);
	}
	
	@Override
	public void setFocus() {
	}

}
