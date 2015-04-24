package com.example.data;

import java.util.Map;

public class NodeEntry<K, V> implements Map.Entry<K, V> {

	private K mKey;
	private V mValue;

	public NodeEntry(K key, V value) {
		mKey = key;
		mValue = value;
	}

	@Override
	public K getKey() {
		return mKey;
	}

	@Override
	public V getValue() {
		return mValue;
	}

	@Override
	public V setValue(V value) {
		mValue = value;
		return mValue;
	}
}
