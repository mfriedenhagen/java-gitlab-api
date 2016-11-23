package org.gitlab.api.http;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;

public class GitLabIT {

    private static String gitlabPort;
    private static String gitlabIp;

    @BeforeClass
    public static void checkForPortProperty() {
        gitlabIp = System.getProperty("docker.host.address");
        gitlabPort = System.getProperty("gitlab.port");
        assumeNotNull(gitlabIp, gitlabPort);
        assumeFalse(gitlabPort.isEmpty());
    }

    @Test
    public void checkLogin() throws IOException {
        final GitlabAPI sut = getGitlabAPI();
        assertNotNull(sut);
    }
    @Test
    public void checkCreateProject() throws IOException {
        final GitlabAPI api = getGitlabAPI();
        final GitlabProject project = api.createProject("first-project");
        assertNotNull(project);
    }

    private GitlabAPI getGitlabAPI() throws IOException {
        final GitlabSession session = GitlabAPI.connect(String.format("http://%s:%s", gitlabIp, gitlabPort), "root", "password");
        final String privateToken = session.getPrivateToken();
        return GitlabAPI.connect(String.format("http://%s:%s", gitlabIp, gitlabPort), privateToken);
    }

}
