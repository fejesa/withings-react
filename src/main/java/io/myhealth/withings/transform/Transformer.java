package io.myhealth.withings.transform;

public interface Transformer<F, T> {

    T transform(F from);

}
