package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.exception.ParseException;




/**
 * 
 * A test class to test capability of parse and unparse methods
 * 
 * @author Nick, James, Sally
 * @Date 10-5-22
 */

class JSONParserTest


{
	
	

	/*
	 * 
	 * Test method to run the test on the parse method
	 */
	
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		
		return parser.parse(figureStr);
	}
	
	

	/*
	 * 
	 * Test method to ensure functionality with a empty file
	 */
	
	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	

	/*
	 * 
	 * Test method to ensure functionality with a json file
	 */
	
	@Test
	void single_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("single_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		

		node.unparse(sb, 1);
		System.out.println(sb.toString());


	}
	
	

	/*
	 * 
	 * Test method to ensure functionality with a json file
	 */
	
	@Test
	void collinear_line_segments_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();


		node.unparse(sb, 1);

		System.out.println(sb.toString());

	}
	
	

	/*
	 * 
	 * Test method to ensure functionality with a json file
	 */
	
	
	
	@Test
	void crossing_symmetric_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();

		
		node.unparse(sb, 1);

		System.out.println(sb.toString());

	}
	
	
	/*
	 * 
	 * Test method to ensure functionality with a json file
	 */
	
	
	@Test
	void fully_connected_irregular_polygon_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		

		node.unparse(sb, 1);
		
		System.out.println(sb.toString());


	}
	
	
	
	
	
	
	
}
