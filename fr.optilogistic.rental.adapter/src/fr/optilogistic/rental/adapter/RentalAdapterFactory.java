package fr.optilogistic.rental.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;

final public class RentalAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if(adaptableObject instanceof Rental && adapterType == Customer.class) {
			return (T)((Rental) adaptableObject).getCustomer();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] {Customer.class};
	}
}
