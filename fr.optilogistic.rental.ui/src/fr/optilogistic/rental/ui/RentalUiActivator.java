package fr.optilogistic.rental.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import fr.optilogistic.rental.ui.pallete.Pallete;

/**
 * The activator class controls the plug-in life cycle
 */
public class RentalUiActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.optilogistic.rental.ui"; //$NON-NLS-1$

	// The shared instance
	private static RentalUiActivator plugin;

	private Map<String, Pallete> palettes = new HashMap<>();

	/**
	 * The constructor
	 */
	public RentalUiActivator() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		printExtensionInfos();
		readPalletes();
	}

	private void printExtensionInfos() {
		for (IConfigurationElement confElem : Platform.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.ui.views")) {
			if (confElem.getName().equals("view")) {
				System.out.println(
						"Plugin: " + confElem.getNamespaceIdentifier() + " Vue: " + confElem.getAttribute("name"));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RentalUiActivator getDefault() {
		return plugin;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		Bundle b = FrameworkUtil.getBundle(this.getClass());

		reg.put(RentalUIConstants.IMG_CUSTOMER,
				ImageDescriptor.createFromURL(b.getEntry(RentalUIConstants.IMG_CUSTOMER)));
		reg.put(RentalUIConstants.IMG_RENTAL, ImageDescriptor.createFromURL(b.getEntry(RentalUIConstants.IMG_RENTAL)));
		reg.put(RentalUIConstants.IMG_RENTAL_OBJECT,
				ImageDescriptor.createFromURL(b.getEntry(RentalUIConstants.IMG_RENTAL_OBJECT)));
		reg.put(RentalUIConstants.IMG_AGENCY, ImageDescriptor.createFromURL(b.getEntry(RentalUIConstants.IMG_AGENCY)));
	}

	private void readPalletes() {
		for (IConfigurationElement confElem : Platform.getExtensionRegistry()
				.getConfigurationElementsFor("fr.optilogistic.rental.ui.pallete")) {
			try {
				Pallete pallete = new Pallete(confElem.getAttribute("id"));
				pallete.setName(confElem.getAttribute("name"));
				pallete.setProvider((IColorProvider) confElem.createExecutableExtension("class"));
				palettes.put(pallete.getId(), pallete);
				System.out.println("Pallete with id " + pallete.getId() + " added to map");
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, Pallete> getPalettes() {
		return palettes;
	}

}
