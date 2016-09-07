package com.jiuxian.framework.util.json;

import org.junit.Test;

import java.time.Instant;
import java.util.*;

public class JsonUtilsTest {
	private static Map<String,Object> map = new HashMap<>();
	private static List<Map<String, Object>> list = new ArrayList<>();
	static {
		map.put("key1", "value");
		map.put("key2", new String[]{"v1", "v2"});
		map.put("key3", new Date());
		list.add(map);
		list.add(map);
	}

	@Test
	public void testObjectToJson() {
		String json = JsonUtils.objectToJson(map);
	}

	@Test
	public void testJsonToObject() {
		String json = JsonUtils.objectToJson(map);
		JsonUtils.jsonToObject(json, Map.class);
	}

	@Test
	public void testJsonToList() {
		String json = JsonUtils.objectToJson(list);
		System.out.println(json);
		JsonUtils.jsonToObject(json, List.class);

	}

	@Test
	public void testJsonToMap() {
		String json = JsonUtils.objectToJson(list);
		List<Map> list = JsonUtils.jsonToList(json, Map.class);
		System.out.println(list);
		Instant instant = Instant.now(); //  时间戳
		System.out.println("instant :" + instant);
	}

}
