package edu.ucla.nesl.sensorsafe.model;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User information.")
@XmlRootElement(name = "user")
public class User {

	@XmlElement(name = "username")
	@ApiModelProperty(value = "username", required = true)
	@NotNull
	public String username;

	@XmlElement(name = "password")
	@ApiModelProperty(value = "password", required = true)
	@NotNull
	public String password;
	
	@XmlElement(name = "role")
	public String role;
	
	@XmlElement(name = "owner")
	public String owner;
	
	@XmlElement(name = "email")
	public String email;
	
	@XmlElement(name = "apikey")
	public String apikey;
	
	@XmlElement(name = "oauth_consumer_key")
	public String oauthConsumerKey;
	
	@XmlElement(name = "oauth_consumer_secret")
	public String oauthConsumerSecret;
	
	@XmlElement(name = "oauth_access_key")
	public String oauthAccessKey;
	
	@XmlElement(name = "oauth_access_secret")
	public String oauthAccessSecret;
	
	public User() {}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	
	public void setOAuthConsumerKey(String oauthConsumerKey) {
		this.oauthConsumerKey = oauthConsumerKey;
	}
	
	public void setOAuthConsumerSecret(String oauthConsumerSecret) {
		this.oauthConsumerSecret = oauthConsumerSecret;
	}

	public void setOAuthAccessKey(String oauthAccessKey) {
		this.oauthAccessKey = oauthAccessKey;
	}
	
	public void setOAuthAccessSecret(String oauthAccessSecret) {
		this.oauthAccessSecret = oauthAccessSecret;
	}
}