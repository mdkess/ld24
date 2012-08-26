package xxx.rtin.ma.games.test;

import static org.junit.Assert.*;

import org.junit.Test;

import xxx.rtin.ma.games.StaticUtil;

public class TestGame {
    private static final float EPSILON = 0.001f;
    @Test
    public void test() {
        assertEquals(45, StaticUtil.AngleBetween(45, 90), EPSILON);
        assertEquals(90, StaticUtil.AngleBetween(180, 270), EPSILON);
        assertEquals(-90, StaticUtil.AngleBetween(270, 180), EPSILON);
        assertEquals(20, StaticUtil.AngleBetween(170, 190), EPSILON);
        assertEquals(-20, StaticUtil.AngleBetween(190, 170), EPSILON);
        assertEquals(20, StaticUtil.AngleBetween(350, 10), EPSILON);
        assertEquals(-20, StaticUtil.AngleBetween(10, 350), EPSILON);
        
        System.out.println(StaticUtil.AngleBetween(180, 270));
        System.out.println(StaticUtil.AngleBetween(270, 180));
        System.out.println(StaticUtil.AngleBetween(10, 350));
        System.out.println(StaticUtil.AngleBetween(10, -10));
        System.out.println(StaticUtil.AngleBetween(-10, 10));
        System.out.println(StaticUtil.AngleBetween(-1, 0));
        System.out.println(StaticUtil.AngleBetween(177, 183));
    }

}
