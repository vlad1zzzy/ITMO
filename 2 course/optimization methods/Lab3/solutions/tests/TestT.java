package tests;

import generator.Generator;
import methods.ProfileMatrix;

import java.util.Arrays;

public class TestT {
    public static void main(String[] args) {
        Generator generator = new Generator();
        double[][] gilbert = generator.generateGilbertMatrix(100);
        ProfileMatrix matrix = generator.generateProfileMatrixFromA(gilbert);

    }
}
