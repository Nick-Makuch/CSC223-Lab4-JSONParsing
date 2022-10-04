package input.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

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

	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		
		
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
		
		
		JSONObject data = JSONroot.getJSONObject("Figure");
		
		String desc = descriptionMaker(data);
		
		PointNodeDatabase points = nodeMaker(data);
		
		SegmentNodeDatabase segments = segmentMaker(data, points);

		
		
//		System.out.println(JSONroot.keySet());
		System.out.println(JSONroot.get("Figure"));
//
//		System.out.println(shit.keySet());
//		System.out.println(shit.get("Points").toString());
//		System.out.println(shit.get("Points").toString());
//		System.out.println(shit.get("Points").toString());

		_astRoot = new FigureNode(desc, points, segments);
		
		return _astRoot;

		// TODO: Build the whole AST, check for return class object, and return the root





	}

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
			PointNode point = new PointNode(jsonPoint.getString("name"), jsonPoint.getInt("x") , jsonPoint.getInt("y"));
			
			//add the point to the database
			JSONPoints.put(point);
			
		}

		
		//return the database
		return JSONPoints;
	}
	
	
	
	private String descriptionMaker(JSONObject data) 
	{
		//get the string of description using the key as the description
		return data.getString("Description"); 
	}

	
	
	
	private SegmentNodeDatabase segmentMaker(JSONObject data , PointNodeDatabase points) {
		
		SegmentNodeDatabase JSONSegmentDatabase =  new SegmentNodeDatabase();
		
		JSONArray segmentArray = data.getJSONArray("Segments");
		
		//JSONSegments.addAdjacencyList(the point, list of points);

		//loop through the given points and add each to the array
		for(Object s : segmentArray) {
			
			JSONObject jobject = (JSONObject) s; 
			
			String key = jobject.keys().next();
			
			JSONArray segments = jobject.getJSONArray(key);
			
			//for(Object s2 : segments) {
				
				//JSONObject jsonSegment = (JSONObject) s2;
				
				//JSONSegmentDatabase.addAdjacencyList(key, jsonSegment);
				
			//}
			
			
			
			//input is the two end points
						
		}


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
			if(checker.getName() == s)
				return checker;
		}
		return null;
	}
	// TODO: implement supporting functionality

}