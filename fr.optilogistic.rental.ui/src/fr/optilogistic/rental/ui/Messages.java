package fr.optilogistic.rental.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "fr.optilogistic.rental.ui.messages"; //$NON-NLS-1$
	public static String RentalPropertyView_FromDate;
	public static String RentalPropertyView_Infos;
	public static String RentalPropertyView_RentDate;
	public static String RentalPropertyView_RentedTo;
	public static String RentalPropertyView_ToDate;
	public static String SimpleHandler_HelloMessage;
	public static String SimpleHandler_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
