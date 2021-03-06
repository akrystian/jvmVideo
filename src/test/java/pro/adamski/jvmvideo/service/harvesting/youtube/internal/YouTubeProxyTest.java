package pro.adamski.jvmvideo.service.harvesting.youtube.internal;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static pro.adamski.jvmvideo.TestHelper.getFileContent;
import static pro.adamski.jvmvideo.TestHelper.response200Json;
import static pro.adamski.jvmvideo.service.harvesting.youtube.internal.RequestedPart.ALL;


/**
 * @author akrystian.
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
@PrepareForTest(NetHttpTransport.class)
public class YouTubeProxyTest {

    private final static String FINAL = "Final";
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8181));

    @Test
    public void shouldBuildYouTubeProxy() {
        //when
        final YouTubeProxy youTubeProxy = new YouTubeProxy("appName", "apiKey");

        //then
        assertThat(youTubeProxy, notNullValue());
    }

    @Test
    public void shouldFetchIds() throws Exception {
        //given
        final String file = "/listIdsFromChannel";
        final YouTube youTube = configureMocks(file, this::prepareMocks);

        final DateTime publishedAfter = new DateTime(0L);
        final DateTime publishedBefore = new DateTime(org.joda.time.DateTime.now().getMillis());

        final YouTubeProxy underTest = new YouTubeProxy("apiKey", youTube);

        //when
        final List<SearchResult> results = underTest.listIdsFromChannel("UCBxVkwrVRo8BnQ1g96MHZ0Q",
                publishedAfter, publishedBefore);

        //then
        assertThat(results.size(), CoreMatchers.is(10));
    }


    @Test
    public void shouldGetVideo() throws Exception {
        //given
        final String file = "/getVideo";
        final YouTube youTube = configureMocks(file, this::prepareSimpleMocks);
        final YouTubeProxy underTest = new YouTubeProxy("apiKey", youTube);

        //when
        final List<Video> videos = underTest.getVideo("zQll41ha5_g", ALL);

        //then
        assertThat(videos.size(), CoreMatchers.is(1));
    }

    private YouTube configureMocks(String file, Consumer<String> prepareMocks) throws Exception {
        YouTube youTube;
        URL url = PowerMockito.spy(new URL("http://localhost:8181" + file));
        whenNew(URL.class).withAnyArguments().thenReturn(url);
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null
        ).setApplicationName("appName").build();
        prepareMocks.accept(file);
        return youTube;
    }

    private void prepareMocks(String file) {
        try {
            stubFor(WireMock.get(urlEqualTo(file))
                    .inScenario("simpleScenario")
                    .whenScenarioStateIs(STARTED)
                    .willSetStateTo(FINAL)
                    .willReturn(response200Json(getFileContent(file + "First" + ".json"))));

            stubFor(WireMock.get(urlEqualTo(file))
                    .inScenario("simpleScenario")
                    .whenScenarioStateIs(FINAL)
                    .willSetStateTo(STARTED)
                    .willReturn(response200Json(getFileContent(file + FINAL + ".json"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareSimpleMocks(String file) {
        try {
            stubFor(WireMock.get(urlEqualTo(file))
                    .willReturn(response200Json(getFileContent(file + ".json"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

