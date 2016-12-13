package fr.optilogistic.rental.ui.providers;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;

final public class RentalProvider  extends LabelProvider implements ITreeContentProvider {

	private static Object[] EMPTY_RESULT = new Object[0];
	
	private static RentalProvider instance;
	
	private RentalProvider() {
		
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		return EMPTY_RESULT;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof RentalAgency) {
			return ((RentalAgency)parentElement).getCustomers().toArray();
		}
		return EMPTY_RESULT;
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof Customer) {
			return ((Customer)element).getParentAgency();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof RentalAgency) {
			if( !((RentalAgency) element).getCustomers().isEmpty() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		} else if(element instanceof Customer) {
			return ((Customer) element).getDisplayName();
		}
		return super.getText(element);
	}

	public static synchronized RentalProvider getIntance() {
		if(instance == null) {
			instance = new RentalProvider();
		}
		return instance;
	}
}
