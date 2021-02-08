package at.ac.htlstp.deimel.springbootdemoserver.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JunitTest {

    @Test
    public void simpleTest(){
        assertEquals(7,3+4);
    }

}
