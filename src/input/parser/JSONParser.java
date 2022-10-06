package input.parser;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;



/**
 * 
 * A class to separate the description, points, and segments in 
 * a figure passed through as a json file
 * 
 * @author Nick, James, Sally
 * @Date 10-5-22
 */



public class JSONParser
{
	protected ComponentNode  _astRoot;


	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	
	
	/*
	 * 
	 * Method to separate the description, the list of points
	 * and the list of segments
	 * @param string to unparse
	 */
	
	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		
		
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
		
		
		
		//check if the file was empty
		//if it was then throw the exception
		if(JSONroot.isEmpty()) throw new ParseException();
		
		
		
		//this is all the data from the file assgined to a variable
		JSONObject data = JSONroot.getJSONObject("Figure");
		
		
		
		
		//calls the method to get the description and assign to variable
		String desc = descriptionMaker(data);
		
		//gets the point database and assigns to a variable
		PointNodeDatabase points = nodeMaker(data);
		
		//gets the segment database and assigns to variable
		SegmentNodeDatabase segments = segmentMaker(data, points);

		

		

		// TODO: Build the whole AST, check for return class object, and return the root

		_astRoot = new FigureNode(desc, points, segments);

		return _astRoot;

	}
	
	/*
	 * 
	 * Helper method to separate the points in a figure
	 * @param string builder and level
	 */
	

	private PointNodeDatabase nodeMaker(JSONObject data) {
		
		//create a database for the points
		PointNodeDatabase JSONPoints =  new PointNodeDatabase();
		
		//make a json array for everything after the key points
		JSONArray pointArray = data.getJSONArray("Points");
		
		
		//loop through the given points and add each to the array
		for(Object p : pointArray) {
			//assign the variable to a value

			JSONObject jsonPoint = (JSONObject) p; 
			
			//create a new point with the key being x and y for respective values
			PointNode point = new PointNode(jsonPoint.getString("name"), 
					jsonPoint.getInt("x") , jsonPoint.getInt("y"));
			
			//add the point to the database
			JSONPoints.put(point);
			
		}

		
		//return the database
		return JSONPoints;
	}
	
	
	
	/*
	 * 
	 * Helper method to separate the description
	 * @param string builder and level
	 */
	
	private String descriptionMaker(JSONObject data) 
	{
		//get the string of description using the key as the description
		return data.getString("Description"); 
	}

	
	/*
	 * 
	 * Helper method to separate the segments in a figure
	 * @param string builder and level
	 */
	
	private SegmentNodeDatabase segmentMaker(JSONObject data, PointNodeDatabase points) {
		
		SegmentNodeDatabase JSONSegmentDatabase =  new SegmentNodeDatabase();
		
		JSONArray segmentArray = data.getJSONArray("Segments");

		//loop through the items in the segment array
		for(Object s : segmentArray) {
			
			//assign the item to an object
			JSONObject jobject = (JSONObject) s; 
			
			//get the key since this will be used
			String key = jobject.keys().next();
			PointNode keyAsPointNode = getPointNode(key, points);
			
			//get the segments by getting everything after the key
			JSONArray segments = jobject.getJSONArray(key);
			List<PointNode> segmentsList = new ArrayList<>();
			
			//loop through the values after the key
			for(Object s2 : segments) {

			//	JSONObject jsonSegment = (JSONObject) s2;
				
				String key2 = (String) s2;
				PointNode key2AsPointNode = getPointNode(key2, points);
				segmentsList.add(key2AsPointNode);				
			}
				
			JSONSegmentDatabase.addAdjacencyList(keyAsPointNode, segmentsList);
			
		}

		//
		return JSONSegmentDatabase;

	}
	
	/**
	 * Helper method for segmentMaker()
	 * */
	private PointNode getPointNode(String s, PointNodeDatabase points) 
	{
		Object[] pointsList = points.asArray();
		for(int i = 0; i < points.getSize(); i++) 
		{
			PointNode checker = (PointNode) pointsList[i];
			if(checker.getName().equals(s))
				return checker;
		}
		return null;
	}
	// TODO: implement supporting functionality

}