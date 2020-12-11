package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.List;

public class AsIntStream implements IntStream {
    private List<Integer> elements = new ArrayList<>();
    private int length;

    private AsIntStream() {
        this.length = 0;
    }

    private AsIntStream(List<Integer> lst) {
        this.elements = lst;
        this.length = lst.size();
    }
    private AsIntStream(int[] arr) {
        for (int i = 0; i < arr.length; i ++) {
            this.elements.add(arr[i]);
        }
        this.length = arr.length;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    public boolean isEmpty(){
        return this.length == 0;
    }
    public void ifEmpty(){
        if (isEmpty()){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Double average() {
        ifEmpty();
       int elementsSum = sum();
       return (double) elementsSum / this.length;
    }

    public Integer findLimitingValue(int multiplier) {
        Integer limiting = this.elements.get(0);
        for (int i = 0; i < this.length; i++) {
            if (this.elements.get(i) * multiplier > limiting * multiplier) {
                limiting = this.elements.get(i);
            }
        }
        return limiting;
    }

    @Override
    public Integer max() {
        ifEmpty();
        return findLimitingValue(1);
    }

    @Override
    public Integer min() {
        ifEmpty();
        return findLimitingValue(-1);
    }

    @Override
    public long count() {
        return this.length;
    }

    @Override
    public Integer sum() {
        ifEmpty();
        int elementsSum = 0;
        for (int i = 0 ; i < this.length; i++) {
            elementsSum += this.elements.get(i);
        }
        return elementsSum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List<Integer> newElements = new ArrayList<>();
        for (int i = 0; i < this.length; i++){
            int current = this.elements.get(i);
            if (predicate.test(current)){
                newElements.add(current);
            }
        }
        return new AsIntStream(newElements);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < this.length; i++){
            action.accept(this.elements.get(i));
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        List<Integer> newElements = new ArrayList<>();
        for (int i = 0; i < this.length; i++){
            int current = this.elements.get(i);
            newElements.add(mapper.apply(current));
        }
        return new AsIntStream(newElements);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List<Integer> newElements = new ArrayList<>();
        for (int i = 0; i < this.length; i++){
            int current = this.elements.get(i);
            newElements.addAll(func.applyAsIntStream(current).getValues());

        }
        return new AsIntStream(newElements);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int reduced = identity;
        for (int i = 0; i < this.length; i++){
            reduced = op.apply(reduced, this.elements.get(i));
        }
        return reduced;
    }

    @Override
    public int[] toArray() {
        int[] array = new int[this.length];
        for (int i = 0; i < this.length; i++){
            array[i] = this.elements.get(i);
        }
        return array;
    }
    public ArrayList getValues() {
        return (ArrayList)((ArrayList)this.elements).clone();
    }


}
