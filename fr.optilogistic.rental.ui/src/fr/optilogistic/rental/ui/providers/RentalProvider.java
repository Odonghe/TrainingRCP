package fr.optilogistic.rental.ui.providers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

final public class RentalProvider extends LabelProvider implements ITreeContentProvider {

	private static Object[] EMPTY_RESULT = new Object[0];

	private static RentalProvider instance;

	private static final String CUSTOMERS = "Customers";
	private static final String RENTALS = "Locations";
	private static final String OBJECTS_TO_RENT = "Objets � louer";

	private RentalProvider() {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		return EMPTY_RESULT;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RentalAgency) {
			RentalAgency agency = (RentalAgency) parentElement;
			return new Node[] { new Node(agency, CUSTOMERS), new Node(agency, RENTALS),
					new Node(agency, OBJECTS_TO_RENT) };
		} else if (parentElement instanceof Node) {
			return ((Node) parentElement).getChildren();
		}
		return EMPTY_RESULT;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Node) {
			return ((Node) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof RentalAgency || element instanceof Node;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		} else if (element instanceof Node) {
			return ((Node) element).getLabel();
		} else if (element instanceof Customer) {
			return ((Customer) element).getDisplayName();
		} else if (element instanceof Rental) {
			Rental rental = (Rental) element;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM");
			return rental.getRentedObject().getName() + "[" + format.format(rental.getStartDate()) + "->"
					+ format.format(rental.getEndDate()) + "]";
		} else if (element instanceof RentalObject) {
			return ((RentalObject) element).getName();
		}
		return super.getText(element);
	}

	public static synchronized RentalProvider getIntance() {
		if (instance == null) {
			instance = new RentalProvider();
		}
		return instance;
	}

	public class Node {

		private String label;
		private RentalAgency parent;

		public Node(RentalAgency agency, String label) {
			parent = agency;
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public RentalAgency getParent() {
			return parent;
		}

		public Object[] getChildren() {
			if (parent != null) {
				switch (label) {
				case CUSTOMERS:
					return parent.getCustomers().toArray();
				case RENTALS:
					return parent.getRentals().toArray();
				case OBJECTS_TO_RENT:
					return parent.getObjectsToRent().toArray();
				}
			}
			return EMPTY_RESULT;
		}

		@Override
		public String toString() {
			return getLabel();
		}
	}
}
