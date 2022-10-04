
/**
 * A database to store segment nodes
 * 
 * <p>Bugs: 
 * 
 * @author Jace Rettig and James Crawford
 * @Date 9-1-22
 */
package input.components.segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.*;

import utilities.io.StringUtilities;

import input.components.ComponentNode;
import input.components.point.*;

public class SegmentNodeDatabase implements ComponentNode {
	private Map<PointNode, Set<PointNode>> _adjLists;
	
	public SegmentNodeDatabase() {
		_adjLists = new HashMap<PointNode, Set<PointNode>>();
	}
	
	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> m) {
		_adjLists = m;
	}
	
	/**
	 * Calculates the number of undirectedEdges in the database
	 * @return the number of undirected edges
	 */
	public int numUndirectedEdges() {
		int numUndirectedEdges = 0;
		//count number of pairs in adjLists
		//returns set of every entry pair
		for (Map.Entry<PointNode, Set<PointNode>> OuterPoint: _adjLists.entrySet()) {
			for (PointNode value: OuterPoint.getValue()) {
				if (_adjLists.get(value).contains(OuterPoint.getKey())) {
					numUndirectedEdges += 1;
				}	
			}
		}
		//divide by 2 to remove duplicates
		numUndirectedEdges = numUndirectedEdges / 2;
		return numUndirectedEdges;
	}
	
	/**
	 * Helper method to add a one way/directed edge from two given points
	 * @param pt1
	 * @param pt2
	 * @throws Exception 
	 */
	private void addDirectedEdge(PointNode pt1, PointNode pt2)  {
		if (_adjLists.get(pt1) == null) {
			Set<PointNode> nodeSet = new HashSet<PointNode>();
			_adjLists.put(pt1, nodeSet);
			_adjLists.get(pt1).add(pt2);
		} 
		else if (_adjLists.get(pt1).contains(pt2) || pt1.equals(pt2)) {
			throw new ArithmeticException("Invalid Edge");
		}
		else {
			_adjLists.get(pt1).add(pt2);
		}
	}
	
	/**
	 * Adds an undirectedEdge from two given points
	 * @param pt1
	 * @param pt2
	 */
	public void addUndirectedEdge(PointNode pt1, PointNode pt2) {
		//Call directed edge twice, makes 1 directed edge
		addDirectedEdge(pt1, pt2);
		addDirectedEdge(pt2, pt1);
			//pt1 -> pt2 and pt1 <- pt2
			//pt1 <-> pt2
	}
	
	/**
	 * Helper method to turn this list into set
	 * @param list of pointNodes
	 * @return set of PointNodes
	 */
	private Set<PointNode> listToSet(List<PointNode> list) {
		Set<PointNode> nodeSet = new HashSet<PointNode>();
		for (PointNode node: list) {
			nodeSet.add(node);
		}
		return nodeSet;
	}
	
	/**
	 * Adds a new adjacencyList to adjLists
	 * @param point- point to add
	 * @param list- list of points that point is next to
	 */
	public void addAdjacencyList(PointNode point, List<PointNode> list) {
		//turn into set
		//Add point as the key as d list as the "value" pair to _adjLists
		_adjLists.put(point, this.listToSet(list));
	}
	
	
	/**
	 * Creates a list of segmentNodes based on the adjacency lists, including duplicates
	 * @return segmentList
	 */
	public List<SegmentNode> asSegmentList() {
		List<SegmentNode> segmentList = new ArrayList<SegmentNode>();
		//loop through each list in adjLists
		for (Map.Entry<PointNode, Set<PointNode>> OuterPoint: _adjLists.entrySet()) {
			//loop through list values
			for (PointNode value: OuterPoint.getValue()) {
				SegmentNode tempSegment = new SegmentNode(OuterPoint.getKey(), value);
				segmentList.add(tempSegment);
			}
		}
		return segmentList;
		
	}
	/**
	 * Helper Method to check if a segment's compliment exists in the list 
	 * @param start of segment
	 * @param end of segment
	 * @param segmentList
	 * @return True if the segmentList contains the reversed segment
	 */
	private boolean hasDirectedSegment(PointNode start, PointNode end, List<SegmentNode> segmentList) {
		SegmentNode segmentReversed = new SegmentNode(end, start);
		//check if compliment is in list
		if (segmentList.contains(segmentReversed)) return true;
		//if not false
		return false;
	}
	
	/**
	 * Creates a list of unique segmentNodes based on the adjacency lists
	 * @return a unique segmentList
	 */
	public List<SegmentNode> asUniqueSegmentList() {
		//unique, CANNOT have duplicate segments
		List<SegmentNode> segmentList = new ArrayList<SegmentNode>();
		//loop through each list in adjLists
		for (Map.Entry<PointNode, Set<PointNode>> OuterPoint: _adjLists.entrySet()) {
			//loop through list values
			for (PointNode value: OuterPoint.getValue()) {
				SegmentNode tempSegment = new SegmentNode(OuterPoint.getKey(), value);
				if (!(this.hasDirectedSegment(OuterPoint.getKey(), value, segmentList))) {
					segmentList.add(tempSegment);
				}
			}
		}
		return segmentList;
	}
	
	/**
	 * Converts the list of segmentNodes into a string of the segmentedNodes
	 * @param list of segmentNodes
	 * @return the list in string form
	 */
	public String segmentListToString(List<SegmentNode> list) {
		String segList = "";
		if (list.size() < 0) return null;
		
		for (SegmentNode sn : list)
		{
			segList += sn.toString() + " ";
		}
		return segList;
	}

	
	@Override
	public void unparse(StringBuilder sb, int level) 
	{
		String str = "";
		
		ArrayList<PointNode> keysArrayList = (ArrayList<PointNode>) _adjLists.keySet();
		
		for(int i = 0; i < keysArrayList.size(); i++) 
		{
			//adds key to string and puts values with said key into an arrayList
			str += StringUtilities.indent(level) + keysArrayList.get(i).getName() + " : ";
			ArrayList<PointNode> values = (ArrayList<PointNode>) _adjLists.get(keysArrayList.get(i));
			
			//adds each value to the string
			for(int j = 0; i < values.size(); i++) 
				str += values.get(j).getName() + " ";
			
			str += "\n";
		}
		
			
		
		
		sb.append(StringUtilities.indent(level) + this.toString());
	}
	
	
	/*public String toString() 
	{
		String str = "";
		
		List<SegmentNode> uniqueList = this.asUniqueSegmentList();
		
		
		str += uniqueList.get(0).getPoint1().getName() + " : ";
		for (int i = 0; i < uniqueList.size(); i++) 
		{
			if(!uniqueList.get(i).getPoint1().getName().equals(uniqueList.get(i-1).getPoint1().getName()));
				str += "\n" + uniqueList.get(i).getPoint1().getName() + " : ";
				
			str += uniqueList.get(i).getPoint2().getName() + " ";
		}
		
		return str;
	}*/
	
	
	
}

