package server_project.Management;

import java.io.InputStream;
import java.util.Properties;

public class EnvConfig {
	private static final Properties props = new Properties();
	
	
	static {
		try (InputStream in = EnvConfig.class.getResourceAsStream("/config.properties")) {
			if (in == null) {
				throw new RuntimeException("config.properties file not found");
			}
			
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
