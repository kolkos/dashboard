package nl.kolkos.dashboard.controllers.backend;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/config/general")
public class GeneralBackendController {
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String loadMenu() {
		return "backend/menu";
	}
	
	
	
	
	
}
