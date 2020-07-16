/*-
 * #%L
 * This file is part of QuPath.
 * %%
 * Copyright (C) 2014 - 2016 The Queen's University of Belfast, Northern Ireland
 * Contact: IP Management (ipmanagement@qub.ac.uk)
 * Copyright (C) 2018 - 2020 QuPath developers, The University of Edinburgh
 * %%
 * QuPath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * QuPath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with QuPath.  If not, see <https://www.gnu.org/licenses/>.
 * #L%
 */

package qupath.lib.gui.viewer.recording;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

/**
 * Data relating to a single recording frame.
 * <p>
 * TODO: This has hung on from Swing days with a lot of AWT.  Should be updated...
 * 
 * @author Pete Bankhead
 * 
 */
class DefaultViewRecordingFrame implements ViewRecordingFrame {
	
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	private long timestamp;
	private Shape region; // Store vertices 
	private Dimension canvasSize;
	private double downFactor;
	private Point2D cursorPosition, eyePosition;
	private Boolean isFixated;
	private double rotation;
	private int z;
	private int t;
	
//	public RecordingFrame(long timestamp, Rectangle imageBounds, Dimension canvasSize) {
//		this(timestamp, imageBounds, canvasSize);
//	}
	
	public DefaultViewRecordingFrame(long timestamp, Shape region, Dimension canvasSize) {
		this(timestamp, region, canvasSize, 1.0, null);
	}
	
	public DefaultViewRecordingFrame(long timestamp, Shape region, Dimension canvasSize, double downFactor, double rotation, int z, int t) {
		this(timestamp, region, canvasSize, downFactor, rotation, null, null, false, z, t);
	}
	
	public DefaultViewRecordingFrame(long timestamp, Shape region, Dimension canvasSize, double downFactor, Point2D cursorPosition) {
		this(timestamp, region, canvasSize, 0, downFactor, cursorPosition, null, false, -1, -1);
	}
	
	public DefaultViewRecordingFrame(long timestamp, Shape region, Dimension canvasSize, double downFactor, double rotation, Point2D cursorPosition, Point2D eyePosition, Boolean isFixated, int z, int t) {
		this.timestamp = timestamp;
		this.region = region;
		this.canvasSize = canvasSize;
		this.downFactor = downFactor;
		this.rotation = rotation;
		this.cursorPosition = cursorPosition;
		this.eyePosition = eyePosition;
		this.isFixated = isFixated;
		this.z = z;
		this.t = t;
	}
	
	
	@Override
	public String toString() {
		String s = String.format("Timestamp: %d, Shape: %s, Canvas size: %d, %d, Rotation: %d", timestamp, region.toString(), canvasSize.width, canvasSize.height, rotation);
		if (cursorPosition != null)
			s += ", Cursor position: " + df.format(cursorPosition.getX()) + ", " + df.format(cursorPosition.getY());
		if (eyePosition != null)
			s += ", Eye position: " + df.format(eyePosition.getX()) + ", " + df.format(eyePosition.getY()) + ", Is fixated: " + isFixated;
		if (z != -1 || t != -1)
			s += ", Z-Slice: " + df.format(z) + ", " + ", Timepoint: " + df.format(t);
		return s;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public Rectangle getImageBounds() {
		return region.getBounds();
	}
	
	@Override
	public Point2D getCursorPosition() {
		return cursorPosition == null ? null : new Point2D.Double(cursorPosition.getX(), cursorPosition.getY());
	}

	@Override
	public boolean hasCursorPosition() {
		return cursorPosition != null;
	}
	
	@Override
	public Point2D getEyePosition() {
		return eyePosition == null ? null : new Point2D.Double(eyePosition.getX(), eyePosition.getY());
	}
	
	/**
	 * Boolean.TRUE if the eye tracking information suggests the eye is fixated, Boolean.FALSE if not, and null if no information is available.
	 * @return
	 */
	@Override
	public Boolean isEyeFixated() {
		return isFixated;
	}

	@Override
	public boolean hasEyePosition() {
		return eyePosition != null;
	}
	
	@Override
	public Shape getImageShape() {
		if (region instanceof Rectangle2D)
			return (Shape)((Rectangle2D)region).clone();
		return new Path2D.Double(region);
	}
	
	@Override
	public Dimension getSize() {
		return canvasSize;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public int getT() {
		return t;
	}
	
	@Override
	public boolean hasZAndT() {
		return z != -1 || t != -1;
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public double getDownFactor() {
		return downFactor;
	}
	
}