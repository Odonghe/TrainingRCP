package fr.optilogistic.rental.ui.views;

import org.eclipse.core.runtime.Platform;
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

import com.opcoach.training.rental.Customer;

import fr.optilogistic.rental.core.RentalCoreActivator;

public class CustomerPropertyView extends ViewPart implements ISelectionListener {

	private Label lblCustomerName;

	public CustomerPropertyView() {
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

	public void setCustomer(final Customer customer) {
		if (customer != null) {
			lblCustomerName.setText(customer.getDisplayName());
		} else {
			lblCustomerName.setText("");
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Group groupInfos = new Group(parent, SWT.BORDER);
		GridData gd_groupInfos = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_groupInfos.widthHint = 161;
		groupInfos.setLayoutData(gd_groupInfos);
		groupInfos.setLayout(new GridLayout(1, false));
		Label lblLabel = new Label(groupInfos, SWT.NONE);
		lblLabel.setText("Name: ");
		lblLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblCustomerName = new Label(groupInfos, SWT.NONE);
		lblCustomerName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		setLabelAsDragSource(lblCustomerName);

		setCustomer(createSampleCustomer());
	}

	private Customer createSampleCustomer() {
		EList<Customer> customers = RentalCoreActivator.getAgency().getCustomers();
		return customers.isEmpty() ? null : customers.get(0);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if(!selection.isEmpty()) {
			if(selection instanceof IStructuredSelection) {
				Object sel = ((IStructuredSelection) selection).getFirstElement();
				
				Customer c = Platform.getAdapterManager().getAdapter(sel, Customer.class);
				
				setCustomer(c);
			}
		}
	}

	public void setLabelAsDragSource(final Label label) {
		DragSource source = new DragSource(label, DND.DROP_MOVE | DND.DROP_COPY);

		source.setTransfer(new Transfer[] { TextTransfer.getInstance() });

		source.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = label.getText();
				}
			}
		});
	}

}
