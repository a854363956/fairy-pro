package com.fairy.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fairy.models.common.Md5Variant;

public class TestMd5Variant {
	@Test
	public void testEncryption() {
		String data = Md5Variant.strongEncryption("123");
		assertEquals(data.length(), 32);
		assertEquals(".|`~-/`||~/!,~=~/'||`/+,?~/|^,'?" , data);
		System.out.println(data);
	}
}
