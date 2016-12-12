package fr.optilogistic.rental.ui.views;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.Rental;

import fr.optilogistic.rental.core.RentalCoreActivator;

public class RentalPropertyView extends ViewPart {

	private Label rentedObjectLabel;
	private Label rentedToLabel;
	
	public RentalPropertyView() {
		// TODO Auto-generated constructor stub
	}

	public void setRental(Rental rental) {
		if(rental != null) {
			rentedObjectLabel.setText(rental.getRentedObject() != null ? rental.getRentedObject().getName() : "");
			rentedToLabel.setText(rental.getCustomer() != null ? rental.getCustomer().getFirstName() + " " + rental.getCustomer().getLastName() : "");
		}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1,false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(1, false));
		
		
		rentedObjectLabel = new Label(infoGroup, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		
		Label simpleLabel = new Label(infoGroup, SWT.NONE);
		simpleLabel.setText("Loué à: ");
		
		rentedToLabel = new Label(infoGroup, SWT.NONE);
		
		setRental(createSampleRental());
	}

	private Rental createSampleRental() {
		EList<Rental> rentals = RentalCoreActivator.getAgency().getRentals();
		return rentals.isEmpty() ? null : rentals.get(0);
	}

	@Override
	public void setFocus() {
	}

}
