package org.leibnix.admin.core.ui;

import org.eclipse.jface.resource.ImageRegistry;

public class ImageCache {
	
	private static ImageCache mInstance = new ImageCache();
	private ImageRegistry mImageRegistry;
	
	private ImageCache () {
		mImageRegistry = new ImageRegistry();
	};
	
	public static ImageCache getInstance () {
		return (mInstance);
	}
	
	public ImageRegistry getRegistry () {
		return mImageRegistry;
	}

}
