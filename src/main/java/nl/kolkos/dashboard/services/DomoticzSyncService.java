package nl.kolkos.dashboard.services;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Device;


@Service
public class DomoticzSyncService {
	@Autowired
	private DomoticzCommunicator domoticz;
	
	@Autowired
	private DeviceService deviceService;
	

	
}
