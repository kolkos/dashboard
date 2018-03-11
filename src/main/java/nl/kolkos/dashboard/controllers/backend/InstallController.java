package nl.kolkos.dashboard.controllers.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.kolkos.dashboard.objects.InstallLogLine;
import nl.kolkos.dashboard.services.InstallerService;

@Controller
@RequestMapping(path="/config/install")
public class InstallController {
	
	@Autowired
	private InstallerService installerService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String installDefaultData(Model model) {
		
		List<InstallLogLine> logLines = installerService.installDefaultData();
		model.addAttribute("logLines", logLines);
		
		model.addAttribute("title", "Install default data");
		model.addAttribute("description", "Install default data.");
		
		return "backend/install";
	}
	
}
