/**
 * A Database to store PointNodes 
 * 
 * <p>Bugs: 
 * 
 * @author Jace Rettig and James ???
 * @Date 9-1-22
 */
package input.components.point;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PointNodeDatabase {
	protected Set<PointNode> _points;
	
	/**
	 * Creates a empty set of pointNodes
	 */
	public PointNodeDatabase() {
		_points = new LinkedHashSet<PointNode>();
	}
	
	/**
	 * Creates a new set of pointNodes with an existing 
	 * list of pointNodes
	 * @param l- a list of PointNodes
	 */
	public PointNodeDatabase(List<PointNode> l) {
		_points = new LinkedHashSet<PointNode>(l);
	}
	
	/**
	 * Adds a pointNode to the database
	 * @param p- the pointNode to add
	 */
	public void put(PointNode p) {
		//if item already in set
		if (!(_points.add(p))) return;
		_points.add(p);
	}
	
	/**
	 * Checks if the set contains a specific pointNode
	 * @param p- the pointNode in question
	 * @return true if in database otherwise false
	 */
	public boolean contains(PointNode p) {
		return _points.contains(p);
	}
	
	/**
	 * Checks if a set contains a specific set of
	 * Coordinates as a node
	 * @param x- x coordinate
	 * @param y- y coordinate
	 * @return true if in database otherwise false
	 */
	public boolean contains(double x, double y) {
		//create a point object with coordinates then compare
		//PointNode p = new PointNode(x, y)
		//compare
		return _points.contains(new PointNode(x, y));
	}
	
	/**
	 * Gets the name of a pointNode
	 * @param p- the desired pointNode
	 * @return the name as a string
	 */
	public String getName(PointNode p) {
		return p.getName();
	}
	
	/**
	 * Gets the name of a pointNode if it exists, otherwise creates
	 * a new pointNode and returns the default name of that point
	 * @param x- x coordinate
	 * @param y- y coordinate
	 * @return the name as a string
	 */
	public String getName(double x, double y) {
		//create a new node with inputs
		PointNode newP = new PointNode(x, y);
		//if set already contains node
		if (_points.contains(newP)) {
			//convert set to array and find point in array, then return point's name
			for(Object point:_points.toArray()) {
				if (point.equals(newP)) {
					return ((PointNode) point).getName();
				}
			}
		} 
		//return default name
		return newP.getName();
	}
	
	/**
	 * Gets a point Node
	 * @param p- the Node to get
	 * @return the retrieved node
	 */
	public PointNode getPoint(PointNode p) {
		return p;
	}
	/**
	 * Gets a point Node with specified coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public PointNode getPoint(double x, double y) {
		//create a new node with inputs
		PointNode newP = new PointNode(x, y);
		//if set already contains node
		if (_points.contains(newP)) {
			//convert set to array and find point in array, then return point
			for(Object point:_points.toArray()) {
				if (point.equals(newP)) {
					return (PointNode) point;
				}
			}
		} 
		return newP;
	}
		
	
}
