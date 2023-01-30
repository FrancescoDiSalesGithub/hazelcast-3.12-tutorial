package com.francescodisalesgithub.hazelcast3.tutorial.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QuorumConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.quorum.QuorumException;
import com.hazelcast.quorum.QuorumType;

@RestController
public class SplitBrainController 
{

	Logger log = LoggerFactory.getLogger(SplitBrainController.class);
	
	@GetMapping("/splitbrain")
	public void splitBrain()
	{
		log.info("[Hazel cast] - starting split brain rest api");
		QuorumConfig quorumConfig = new QuorumConfig();
		quorumConfig.setName("Two Nodes quorum").setEnabled(true).setSize(2).setType(QuorumType.READ_WRITE);
		
		MapConfig mapConfig = new MapConfig();
		mapConfig.setName("myMap").setQuorumName("Two Nodes quorum");
		
		Config config = new Config();
		config.addMapConfig(mapConfig);
		config.addQuorumConfig(quorumConfig);
		
		HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
		HazelcastInstance hz1 = Hazelcast.newHazelcastInstance(config);
		
		IMap<Integer,String> myMap = hz.getMap("myMap");
		myMap.set(1,"prova");
		
		hz1.getLifecycleService().shutdown();
		
		
		try
		{
			Thread.sleep(3000);
			myMap.set(2,"prova1");
			
		}
		catch(QuorumException e)
		{
			log.error("[Hazelcast] - quorum exception "+e);
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			log.error("[Hazelcast] - interrupted exception "+e);
			e.printStackTrace();
		}
		
		
	}
}
