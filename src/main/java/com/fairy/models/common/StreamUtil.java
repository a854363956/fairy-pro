package com.fairy.models.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

public class StreamUtil {
    public static String readBytes(InputStream inStream)  
            throws IOException { 
    	return CharStreams.toString(new InputStreamReader(inStream,Charsets.UTF_8));
    }  
    public static String readBytes(BufferedReader bufferedReader) throws IOException {
    	return CharStreams.toString(bufferedReader);
	}
	

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
	    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
	    return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
	}
}
