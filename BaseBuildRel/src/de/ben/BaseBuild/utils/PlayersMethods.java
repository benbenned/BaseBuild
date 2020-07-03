package de.ben.BaseBuild.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.ben.BaseBuild.main.Main;
import net.minecraft.server.v1_8_R3.Packet;

public class PlayersMethods {
	

	public static String tryGetKey(HashMap<String, String> HashMap, String Value) {

		for (String key : HashMap.keySet()) {
			if (HashMap.get(key).equalsIgnoreCase(Value)) {
				return key;
			}
		}
		return null;
	}
	
	public static Set<String> tryGetKeysContainingValue(HashMap<String, String> HashMap, String ContainingPhrase) {
		
		Set<String> keys = new HashSet<>();
		
		for(String key : HashMap.keySet()) {
			if(HashMap.get(key).contains(ContainingPhrase)) {
				keys.add(key);
			}
		}
		return keys;
	}
	
	public static String tryGetRealName(String rndmName) {
		
		return Main.getNames().containsKey(rndmName.toLowerCase()) ? Main.getNames().get(rndmName.toLowerCase()) : null;
	}
	
	public static String tryGetName(String UUID) {
		
		System.out.println("UUID: " + UUID);
		
		return Main.getUuids().containsValue(UUID) ? tryGetKey(Main.getNames(), UUID) : null;
	}
	
	public static String tryGetUUID(String rndmName) {
		
		System.out.println(rndmName);
		
		String name = Main.getNames().get(rndmName.toLowerCase());
		
		System.out.println("[INFO] GETTING NAME: " + name);
		
		return Main.getUuids().containsKey(name) ? Main.getUuids().get(name) : null;
	}
	
	public static boolean isServerAppendeeByUUID(String UUID) {
	
		return Main.getUuids().containsValue(UUID) ? true : false;
	}
	
	public static boolean isServerAppendeeByName(String rndmName) {
		
		return Main.getNames().containsKey(rndmName.toLowerCase()) ? true : false;
	}
	
	public static Field getField(Class<?> clazz, String name) {
		
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch(NoSuchFieldException | SecurityException e) {
			return null;
		}
	}
	
	public static void sendPacket(Packet<?> packet) {
		
		for(Player current : Main.getPlayers()) {
			
			((CraftPlayer) current).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
