package pro.adamski.jvmvideo;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * @author akrystian.
 */
public class TestHelper {
    private static final String CONTENT_TYPE = "Content-Type";

    public TestHelper() {
        throw new UnsupportedOperationException("Pure static class!");
    }

    public static ResponseDefinitionBuilder response200Json(String fileContent) {
        return aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                .withBody(fileContent);
    }

    public static String getFileContent(String name) throws IOException {
        final InputStream stream = Object.class.getResourceAsStream(name);
        if (stream == null) {
            throw new TestHelperException("Problem with load resource file :" + name + "!");
        }
        return IOUtils.toString(
                stream,
                "UTF-8");
    }
}

class TestHelperException extends RuntimeException {
    TestHelperException(String message) {
        super(message);
    }
}