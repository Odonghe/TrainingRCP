package fr.optilogistic.rental.ui.views;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
	private Label lblDateRentalStart;
	private Label lblDateRentalEnd;
	
	public RentalPropertyView() {
		// TODO Auto-generated constructor stub
	}

	public void setRental(Rental rental) {
		if(rental != null) {
			rentedObjectLabel.setText(rental.getRentedObject() != null ? rental.getRentedObject().getName() : "");
			rentedToLabel.setText(rental.getCustomer() != null ? rental.getCustomer().getFirstName() + " " + rental.getCustomer().getLastName() : "");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Random rand = new Random();
			char[] charSequence = new char[2000];
			for(int i = 0; i < 2000; i++) {
				charSequence[i] = (char) (65 + rand.nextInt(28)); 
			}
			String longString = new String(charSequence);
			lblDateRentalStart.setText(dateFormat.format(rental.getStartDate()) + longString);
			lblDateRentalEnd.setText(dateFormat.format(rental.getEndDate()) + longString);
		}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1,false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));
		
		
		rentedObjectLabel = new Label(infoGroup, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);
		
		Label simpleLabel = new Label(infoGroup, SWT.NONE);
		simpleLabel.setText("Loué à: ");
		
		rentedToLabel = new Label(infoGroup, SWT.NONE);
		
		Group grpDatesDeLocation = new Group(parent, SWT.NONE);
		grpDatesDeLocation.setLayout(new GridLayout(2, false));
		GridData gd_grpDatesDeLocation = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_grpDatesDeLocation.widthHint = 128;
		gd_grpDatesDeLocation.heightHint = 69;
		grpDatesDeLocation.setLayoutData(gd_grpDatesDeLocation);
		grpDatesDeLocation.setText("Dates de location");
		
		Label lblDu = new Label(grpDatesDeLocation, SWT.NONE);
		lblDu.setText("du: ");
		
		lblDateRentalStart = new Label(grpDatesDeLocation, SWT.NONE);
		lblDateRentalStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAu = new Label(grpDatesDeLocation, SWT.NONE);
		lblAu.setText("au: ");
		
		lblDateRentalEnd = new Label(grpDatesDeLocation, SWT.NONE);
		lblDateRentalEnd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
