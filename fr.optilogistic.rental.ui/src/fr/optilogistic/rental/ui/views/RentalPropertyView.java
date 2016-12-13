package fr.optilogistic.rental.ui.views;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.opcoach.training.rental.Rental;

import fr.optilogistic.rental.core.RentalCoreActivator;

public class RentalPropertyView extends ViewPart implements ISelectionListener {

	private Label rentedObjectLabel;
	private Label rentedToLabel;
	private Label lblDateRentalStart;
	private Label lblDateRentalEnd;

	public RentalPropertyView() {
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		site.getPage().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	public void setRental(Rental rental) {
		if (rental != null) {
			rentedObjectLabel.setText(rental.getRentedObject() != null ? rental.getRentedObject().getName() : "");
			rentedToLabel.setText(rental.getCustomer() != null
					? rental.getCustomer().getFirstName() + " " + rental.getCustomer().getLastName() : "");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Random rand = new Random();
			char[] charSequence = new char[2000];
			for (int i = 0; i < 2000; i++) {
				charSequence[i] = (char) (65 + rand.nextInt(26));
			}
			String longString = new String(charSequence);
			lblDateRentalStart.setText(dateFormat.format(rental.getStartDate()) + longString);
			lblDateRentalEnd.setText(dateFormat.format(rental.getEndDate()) + longString);
		} else {
			rentedObjectLabel.setText("");
			rentedToLabel.setText(""); 
			lblDateRentalStart.setText("");
			lblDateRentalEnd.setText("");
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Group infoGroup = new Group(parent, SWT.NONE);
		GridData gd_infoGroup = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_infoGroup.widthHint = 307;
		infoGroup.setLayoutData(gd_infoGroup);
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
		rentedToLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpDatesDeLocation = new Group(parent, SWT.NONE);
		grpDatesDeLocation.setLayout(new GridLayout(2, false));
		GridData gd_grpDatesDeLocation = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_grpDatesDeLocation.widthHint = 311;
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

		setLabelAsDragSource(rentedObjectLabel);
		setLabelAsDragSource(rentedToLabel);
		setLabelAsDragSource(lblDateRentalStart);
		setLabelAsDragSource(lblDateRentalEnd);
		
		setRental(createSampleRental());
	}

	private Rental createSampleRental() {
		EList<Rental> rentals = RentalCoreActivator.getAgency().getRentals();
		return rentals.isEmpty() ? null : rentals.get(0);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) selection).getFirstElement();
			if (selected instanceof Rental) {
				setRental((Rental) selected);
			} else {
				setRental(null);
			}
		} else {
			setRental(null);
		}
	}
	
	public void setLabelAsDragSource(final Label label) {
		DragSource source = new DragSource(label, DND.DROP_MOVE | DND.DROP_COPY);
		
		source.setTransfer(new Transfer[] {TextTransfer.getInstance()} );
		
		source.addDragListener( new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				if(TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = label.getText();
				}
			}
		});
	}
	
}
