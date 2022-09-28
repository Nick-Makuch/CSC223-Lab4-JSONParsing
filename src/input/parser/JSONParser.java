package input.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
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
		
		System.out.println(JSONroot.keySet());
		System.out.println(JSONroot.get("Figure"));

		JSONObject shit = JSONroot.getJSONObject("Figure");
		System.out.println(shit.keySet());
		System.out.println(shit.get("Points").toString());
		System.out.println(shit.get("Points").toString());
		System.out.println(shit.get("Points").toString());
		
		return _astRoot;

        // TODO: Build the whole AST, check for return class object, and return the root
	}

	private PointNodeDatabase nodeMaker(JSONObject points) {
		PointNodeDatabase someShit = null;
		return someShit;
	}
	
	private SegmentNodeDatabase segMaker(JSONObject segs) {
		
		
	}
    // TODO: implement supporting functionality

}