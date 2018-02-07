package org.sdrc.rocketchat.util;

public interface StateManager {

	Object getValue(String key);

	void setValue(String Key, Object Value);

}