package cucumber.runtime.io;

class ImplementationLoader {

	public static Object newInstance (Class typ)
	{
		String name = typ.getName();
		Object result = null;
		try {
			result = typ.getClassLoader().loadClass(name + "Impl").newInstance();
		}
		catch (Throwable thr)
		{
			thr.printStackTrace();
		}
		return result;
	}
}
