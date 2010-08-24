package com.gwtent.validate.client.reflection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.annotations.Reflect_Full;

/**
 * 
 * To using Validation module, the followings class must have reflection information.
 * 
 * @author James Luo
 *
 */
public class ClassesMustWithReflection {
	
	@Reflect_Full
	public static class MyList extends ArrayList implements List{
		
	}
	
	@Reflect_Full
	public static class MySet extends HashSet implements Set{
		
	}
	
}
