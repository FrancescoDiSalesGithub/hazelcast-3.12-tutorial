package com.francescodisalesgithub.hazelcast3.tutorial.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;

public class ListenerMyMap implements EntryAddedListener<Integer,String>, EntryUpdatedListener<Integer,String>, EntryRemovedListener<Integer,String> {

	Logger log = LoggerFactory.getLogger(ListenerMyMap.class);
	
	@Override
	public void entryRemoved(EntryEvent<Integer, String> event) {
		log.info("entry removed");
		
	}

	@Override
	public void entryUpdated(EntryEvent<Integer, String> event) {
		log.info("entry updated");
		
	}

	@Override
	public void entryAdded(EntryEvent<Integer, String> event) 
	{
		
		log.info("entry added");
		
	}

}
