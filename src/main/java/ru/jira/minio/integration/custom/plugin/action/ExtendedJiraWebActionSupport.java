package ru.jira.minio.integration.custom.plugin.action;

import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.util.Collection;

// see Jira issue: https://jira.atlassian.com/browse/JRASERVER-68091
public abstract class ExtendedJiraWebActionSupport extends JiraWebActionSupport {
    @Override
    public void setErrorMessages(Collection errorMessages) {
        this.errorMessages = errorMessages;
    }
}