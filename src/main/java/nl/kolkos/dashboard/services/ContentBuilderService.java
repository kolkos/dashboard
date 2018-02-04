package nl.kolkos.dashboard.services;

import org.springframework.stereotype.Service;

@Service
public class ContentBuilderService {
	
	public String buildDismissibleAlert(String message, String alertTheme) {
		
		String html = String.format("<div class='alert %s alert-dismissible fade show'>", alertTheme);
		html += message;
		html += "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">";
		html += "<span aria-hidden=\"true\">&times;</span>";
		html += "</button>";
		html += "</div>";
		
		return html;
	}
}
