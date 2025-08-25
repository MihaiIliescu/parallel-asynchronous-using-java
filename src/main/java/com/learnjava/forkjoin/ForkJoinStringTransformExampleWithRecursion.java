package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinStringTransformExampleWithRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinStringTransformExampleWithRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        log("names : "+ names);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinStringTransformExampleWithRecursion example = new ForkJoinStringTransformExampleWithRecursion(names);
        List<String> result = forkJoinPool.invoke(example);
//
//        names.forEach((name)->{
//            String newValue = addNameLengthTransform(name);
//            resultList.add(newValue);
//        });
        stopWatch.stop();
        log("Final Result : "+ result);
        log("Total Time Taken : "+ stopWatch.getTime());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    @Override
    protected List<String> compute() {
        if (inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(addNameLengthTransform(name)));
            return resultList;
        }
        int middle = inputList.size()/2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinStringTransformExampleWithRecursion(inputList.subList(0, middle)).fork();
        inputList = inputList.subList(middle, inputList.size());
        List<String> rightResult = compute(); //recursion stuff
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
