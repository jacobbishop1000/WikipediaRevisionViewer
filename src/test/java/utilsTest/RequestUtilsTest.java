package utilsTest;

import exceptions.NetworkConnectionFailedException;
import org.junit.jupiter.api.Test;
import utils.RequestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RequestUtilsTest {

    @Test
    void establishConnectionOneWord() throws IOException, NetworkConnectionFailedException {
        String expectedString = "{\"continue\":{\"rvcontinue\":\"20200224092629|942382068\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"barack obama\",\"to\":\"Barack obama\"}],\"redirects\":[{\"from\":\"Barack obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:50Z\"},{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:28Z\"},{\"user\":\"Smurrayinchester\",\"timestamp\":\"2020-02-26T15:00:49Z\"},{\"user\":\"Kiwi128\",\"timestamp\":\"2020-02-24T09:27:13Z\"}]}}}}";
        assertEquals(expectedString, RequestUtils.establishConnection("obama"));
    }

    @Test
    void establishConnectionMultiWord() throws IOException, NetworkConnectionFailedException {
        String expectedString = "{\"continue\":{\"rvcontinue\":\"20200224092629|942382068\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"barack obama\",\"to\":\"Barack obama\"}],\"redirects\":[{\"from\":\"Barack obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:50Z\"},{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:28Z\"},{\"user\":\"Smurrayinchester\",\"timestamp\":\"2020-02-26T15:00:49Z\"},{\"user\":\"Kiwi128\",\"timestamp\":\"2020-02-24T09:27:13Z\"}]}}}}";
        assertEquals(expectedString, RequestUtils.establishConnection("barack obama"));
    }
}