package nl.kolkos.dashboard.objects;

public class Button {
	private String buttonText;
	private String buttonClass = "btn-primary";
	private String icon = null;
	private String url;
	
	public Button() {}
	
	public Button(String buttonText, String buttonClass, String url, String icon) {
		this.setButtonText(buttonText);
		this.setButtonClass(buttonClass);
		this.setUrl(url);
		this.setIcon(icon);
	}
	
	public Button(String buttonText, String buttonClass, String url) {
		this.setButtonText(buttonText);
		this.setButtonClass(buttonClass);
		this.setUrl(url);
	}
	
	public Button(String buttonText, String url) {
		this.setButtonText(buttonText);
		this.setUrl(url);
	}
	
	
	
	
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String getButtonClass() {
		return buttonClass;
	}
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	
}
