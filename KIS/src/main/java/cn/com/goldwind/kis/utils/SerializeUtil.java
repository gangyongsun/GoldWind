package cn.com.goldwind.kis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.sf.json.JSONObject;

/**
 * Java原生版的 Serialize
 * 
 */
public class SerializeUtil {
	private static final Class<?> CLAZZ = SerializeUtil.class;

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		if (object == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] bytes = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", JSONObject.fromObject(object));
		} finally {
			IoUtil.close(oos, baos);
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @param requiredType
	 * @return
	 */
	public static <T> T deserialize(byte[] bytes, Class<T>... requiredType) {
		Object object = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			if (bytes != null) {
				bais = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bais);
				object = ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", bytes);
		} finally {
			IoUtil.close(ois, bais);
		}
		return (T) object;
	}

	public static Object deserialize(byte[] bytes) {
		return deserialize(bytes, Object.class);
	}

}
