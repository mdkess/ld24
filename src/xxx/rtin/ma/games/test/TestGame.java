package xxx.rtin.ma.games.test;

import static org.junit.Assert.*;

import org.junit.Test;

import xxx.rtin.ma.games.StaticUtil;

public class TestGame {

    @Test
    public void test() {
        System.out.println(StaticUtil.AngleBetween(45, 90));
        System.out.println(StaticUtil.AngleBetween(180, 270));
        System.out.println(StaticUtil.AngleBetween(270, 180));
        System.out.println(StaticUtil.AngleBetween(10, 350));
    }

}
