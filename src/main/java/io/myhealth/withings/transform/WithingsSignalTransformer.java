package io.myhealth.withings.transform;

import com.withings.api.heart.Signal;
import io.myhealth.withings.api.WithingsSignal;

public class WithingsSignalTransformer implements Transformer <Signal, WithingsSignal> {

    @Override
    public WithingsSignal transform(Signal from) {
        // TODO: implement signal transformer
        return null;
    }
}
