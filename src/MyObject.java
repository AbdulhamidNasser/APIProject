import java.util.List;

public class MyObject {
	
	String name="";
	List<String> web_pages;
	String country="";
	 List<String> domains;
	String alpha_two_code="";
	
	public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	  
	  public List<String> getWebPages() {
		    return web_pages;
		  }
		  public void setWebPages(List<String> web_pages) {
		    this.web_pages = web_pages;
		  }
		  
		  public String getCountry() {
			    return country;
			  }
			  public void setCountry(String country) {
			    this.country = country;
			  }
			  
			  public List<String> getDomains() {
				    return domains;
				  }
				  public void setDomains(List<String> domains) {
				    this.domains = domains;
				  }
				  
				  public String getAlphaTwoCode() {
					    return alpha_two_code;
					  }
					  public void setAlphaTwoCode(String alpha_two_code) {
					    this.alpha_two_code = alpha_two_code;
					  }

}
