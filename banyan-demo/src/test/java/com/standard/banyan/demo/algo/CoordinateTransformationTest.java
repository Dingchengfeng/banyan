package com.standard.banyan.demo.algo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dingchengfeng
 * @date 2023/5/16
 **/
@SpringBootTest
class CoordinateTransformationTest {

    @Test
    void testTransform() {
        CoordinateTransformation.init(
                new Point2D(0, 0), // A1
                new Point2D(1, 1), // A2
                new Point2D(2, 2), // B1
                new Point2D(3, 3)  // B2
        );

        // 测试坐标系A中的点(1, 0)在坐标系B中的转换
        Point2D result = CoordinateTransformation.transform(new Point2D(1, 0));
        assertEquals(3, result.x, 0.0001);
        assertEquals(2, result.y, 0.0001);
    }

    @Test
    void testInverseTransform() {
        CoordinateTransformation.init(
                new Point2D(0, 0), // A1
                new Point2D(1, 1), // A2
                new Point2D(2, 2), // B1
                new Point2D(3, 3)  // B2
        );

        // 测试坐标系B中的点(3, 2)在坐标系A中的逆转换
        Point2D result = CoordinateTransformation.inverseTransform(new Point2D(3, 2));
        assertEquals(1, result.x, 0.0001);
        assertEquals(0, result.y, 0.0001);
    }
}