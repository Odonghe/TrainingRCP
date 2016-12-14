package fr.optilogistic.rental.ui.providers;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

import fr.optilogistic.rental.ui.RentalUIConstants;
import fr.optilogistic.rental.ui.RentalUiActivator;

final public class RentalProvider extends LabelProvider
		implements ITreeContentProvider, IColorProvider, RentalUIConstants {

	private static Object[] EMPTY_RESULT = new Object[0];

	private static RentalProvider instance;

	private static final String CUSTOMERS = "Customers";
	private static final String RENTALS = "Locations";
	private static final String OBJECTS_TO_RENT = "Objets à louer";

	@Override
	public Image getImage(Object element) {
		if (element instanceof RentalAgency) {
			return RentalUiActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_AGENCY);
		}
		if (element instanceof Node) {
			switch (((Node) element).getLabel()) {
			case CUSTOMERS:
				return RentalUiActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_CUSTOMER);
			case RENTALS:
				return RentalUiActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_RENTAL);
			case OBJECTS_TO_RENT:
				return RentalUiActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_RENTAL_OBJECT);
			}
		}
		return super.getImage(element);
	}

	private RentalProvider() {

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

	private Color getRGBColor(String rgbKey) {
		ColorRegistry colorReg = JFaceResources.getColorRegistry();
		Color col = colorReg.get(rgbKey);

		if (col == null) {
			colorReg.put(rgbKey, StringConverter.asRGB(rgbKey));
			col = colorReg.get(rgbKey);
		}
		return col;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((parent == null) ? 0 : parent.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			if (parent == null) {
				if (other.parent != null)
					return false;
			} else if (!parent.equals(other.parent))
				return false;
			return true;
		}

		private RentalProvider getOuterType() {
			return RentalProvider.this;
		}
	}

}
