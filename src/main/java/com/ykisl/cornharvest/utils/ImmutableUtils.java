package com.ykisl.cornharvest.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImmutableUtils 
{
	public static <T> List<T> AppendElement(List<T> list, T element) {
	    List<T> newList = new ArrayList<>(list);
	    newList.add(element);
	    return Collections.unmodifiableList(newList);
	}
	
	public static <T> Set<T> AppendElement(Set<T> set, T element) 
	{   
	    Set<T> newSet = new HashSet<>(set);
	    newSet.add(element);
	    return Collections.unmodifiableSet(newSet);
	}
}
