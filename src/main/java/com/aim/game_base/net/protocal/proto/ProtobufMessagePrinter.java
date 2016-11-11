package com.aim.game_base.net.protocal.proto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.aim.game_base.entity.net.base.Protocal.PT;
import com.google.protobuf.ByteString;

public class ProtobufMessagePrinter {
	public static void print(PT sc, Class<?> clazz) {
		try {
			Method method = clazz.getMethod("parseFrom", ByteString.class);
			Object obj = method.invoke(null, sc.getData());
			System.out.println(obj);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
