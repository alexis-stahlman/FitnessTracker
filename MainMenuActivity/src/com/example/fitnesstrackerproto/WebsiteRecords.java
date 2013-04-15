//#######################################################
//
//                  MemoPassword
//
//                Mr.Preecha Homjai
//
//              chahalung@hotmail.com
//
//
//#######################################################
package com.example.fitnesstrackerproto;

public class WebsiteRecords {
	private int id;
	private String sitename;
	private String username;
	private String password;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSitename() {
		return this.sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return this.sitename;
	}

}