package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	UserTest.class,
	ConsumerTest.class,
	ProducerTest.class,
	DocumentTest.class,
	DumbSearchTest.class,
	HipsterSearchTest.class,
	PopularitySearchTest.class,
	RandomSearchTest.class,
	SimulationTest.class
})

/**
 * Test Suite for Social Sim.
 * @author Reid Cain-Mondoux
 * @version 0.0.1
 */
public class TestSuite {
}
