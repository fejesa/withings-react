package io.myhealth.withings.transform;

import com.withings.api.heart.*;
import io.myhealth.withings.api.WithingsHeart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class WithingsHeartTransformerTest {

    @Test
    public void transformEmpty() {
        HeartBody body = new HeartBody(Collections.emptyList(), false, 0);
        HeartList heartList = new HeartList(0, body);
        Transformer<HeartList, List<WithingsHeart>> transformer = new WithingsHeartTransformer();
        List<WithingsHeart> result = transformer.transform(heartList);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void transform() {
        HeartMeasurement measurement = new HeartMeasurement("device", 0,
                new Ecg(1, 0),
                new BloodPressure(118, 75), 72, 1595881259);
        HeartList heartList = new HeartList(0, new HeartBody(List.of(measurement), false, 0));
        Transformer<HeartList, List<WithingsHeart>> transformer = new WithingsHeartTransformer();
        List<WithingsHeart> result = transformer.transform(heartList);
        Assertions.assertEquals(1, result.size());
    }
}
