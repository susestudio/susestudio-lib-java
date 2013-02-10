package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.suse.studio.client.util.ParserUtils;

@Root(name = "user")
public class DatabaseUser {

    @Element
    private String username;

    @Element
    private String password;

    @Element(name = "database_list", required = false)
    private String databasesString;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getDatabases() {
        return ParserUtils.commaSeparatedStringToList(databasesString);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabases(List<String> databases) {
        this.databasesString = ParserUtils.listToCommaSeparatedString(databases);
    }
}
