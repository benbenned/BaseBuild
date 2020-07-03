package de.ben.BaseBuild.filebuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileBuilder {
	
	private File f;
	private YamlConfiguration c;
	
	public FileBuilder(String FilePath, String FileName) {
		this.f = new File(FilePath, FileName);
		this.c = YamlConfiguration.loadConfiguration(this.f);
	}
	
	public void setValue(String ValuePath, Object Value) {
		c.set(ValuePath, Value);
	}
	
	public void setHashMap(String ValuePath, HashMap<String, String> HashMap) {
		String map = HashMap.toString();
		c.set(ValuePath, map);
	}
	
	public Object getObject(String ValuePath) {
		return c.get(ValuePath);
	}
	
	public int getInt(String ValuePath) {
		return c.getInt(ValuePath);
	}
	
	public double getDouble(String ValuePath) {
		return c.getDouble(ValuePath);
	} 
	
	public String getString(String ValuePath) {
		return c.getString(ValuePath);
	}
	
	public boolean getBoolean(String ValuePath) {
		return c.getBoolean(ValuePath);
	}
	
	public long getLong(String ValuePath) {
		return c.getLong(ValuePath);
	}
	
	public List<String> getStringList(String ValuePath) {
		return c.getStringList(ValuePath);
	}
	
	public Set<String> getKeys(boolean deep) {
		return c.getKeys(deep);
	}
	
	public ConfigurationSection getConfigurationSection(String Section) {
		return c.getConfigurationSection(Section);
	}
	
	public HashMap<String, String> getHashMap(String ValuePath) {
		String map = c.getString(ValuePath);
		HashMap<String, String> hash = new HashMap<>();
		
		map = map.substring(1, map.length() - 1);
		String[] pairs = map.split(",");
		
		for(String current : pairs) {
			current = current.trim();
			String[] keyValue = current.split("=");
			hash.put(keyValue[0], keyValue[1]);
		}
		return hash;
	}
	
	public boolean exists() {
		return f.exists() ? true : false;
	}
	
	public FileBuilder save() {
		try {
			this.c.save(this.f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

}

