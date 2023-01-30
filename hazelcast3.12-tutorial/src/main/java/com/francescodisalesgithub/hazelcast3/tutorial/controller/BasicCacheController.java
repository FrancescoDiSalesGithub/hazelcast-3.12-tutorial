package com.francescodisalesgithub.hazelcast3.tutorial.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francescodisalesgithub.hazelcast3.tutorial.listener.ListenerMyMap;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;



@RestController
public class BasicCacheController 
{
	
	Logger log = LoggerFactory.getLogger(BasicCacheController.class);
	
	@GetMapping("/insertDemo")
	public void insertDemo()
	{
		HazelcastInstance hz = Hazelcast.newHazelcastInstance();
		IMap<Integer,String> myMap = hz.getMap("myMap");
		
		for(int i=0;i<5;i++)
		{
			myMap.put(i, "myValue "+i);
		}
		
		for(Map.Entry<Integer,String> item : myMap.entrySet())
		{
			log.info("[Hazel cast] - basic insert demo: "+item.getValue());
		}
		
	}
	
	@GetMapping("/insertDemoWithConfig")
	public void insertDemoWithConfig()
	{
		MapConfig mapConfig = new MapConfig("myMap");
		mapConfig.setBackupCount(2);
		mapConfig.setAsyncBackupCount(2);
		mapConfig.addEntryListenerConfig(new EntryListenerConfig("com.francescodisalesgithub.hazelcast3.tutorial.listener.ListenerMyMap", true, false));
		
		Config config = new Config();
		config.addMapConfig(mapConfig);
		
		HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
		IMap<Integer,String> myMap = hz.getMap("myMap");
		
		myMap.put(5,"config insert parody");
		
		for(Map.Entry<Integer, String> item : myMap.entrySet())
		{
			log.info("[Hazel cast] - basic insert with config: "+item.getValue());
		}
	}
	
	
	@GetMapping("/insertDemoWithConfigXML")
	public void insertDemoWithConfigXML()
	{
		
	}

}
