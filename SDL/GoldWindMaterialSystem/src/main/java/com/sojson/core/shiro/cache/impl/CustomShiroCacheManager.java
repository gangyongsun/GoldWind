package com.sojson.core.shiro.cache.impl;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

import com.sojson.core.shiro.cache.ShiroCacheManager;

import lombok.Data;

/**
 * shiro Custom Cache
 * 
 * @author alvin
 *
 */
@Data
public class CustomShiroCacheManager implements CacheManager, Destroyable {

	private ShiroCacheManager shiroCacheManager;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return getShiroCacheManager().getCache(name);
	}

	@Override
	public void destroy() throws Exception {
		shiroCacheManager.destroy();
	}

}
