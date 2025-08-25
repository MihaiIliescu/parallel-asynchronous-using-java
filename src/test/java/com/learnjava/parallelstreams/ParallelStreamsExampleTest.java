package com.learnjava.parallelstreams;
import com.learnjava.parallelstreams.ParallelStreamsExample;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.CommonUtil.timeTaken;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {
        List<String> names = DataSet.namesList();
        startTimer();
        List<String> result = parallelStreamsExample.stringTransform(names);

        Assertions.assertEquals(4, result.size());
        result.forEach(name -> Assertions.assertTrue(name.contains("-")));
        timeTaken();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void stringTransform_1(boolean isParallel) {
        List<String> names = DataSet.namesList();
        stopWatch.reset();
        startTimer();
        List<String> result = parallelStreamsExample.stringTransform(names, isParallel);
        timeTaken();
        Assertions.assertEquals(4, result.size());
        result.forEach(name -> Assertions.assertTrue(name.contains("-")));
    }
}