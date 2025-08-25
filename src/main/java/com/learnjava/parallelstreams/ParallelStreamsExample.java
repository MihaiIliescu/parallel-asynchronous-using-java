package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamsExample {

    public static void main(String[] args) {
        List<String> list = DataSet.namesList();
        startTimer();
        log("initial: " + list);
        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
        List<String> result = parallelStreamsExample.stringTransform(list);
        log("result: " + result);
        timeTaken();
    }

    public List<String> stringTransform(List<String> list) {
        return list
                .parallelStream()
                .map(this::addNameLengthTransform)
                .toList();
    }

    public List<String> stringTransform(List<String> list, boolean parallel) {
        Stream<String> stream = parallel ? list.parallelStream() : list.stream();
        return stream
                .map(this::addNameLengthTransform)
                .toList();
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }
}
