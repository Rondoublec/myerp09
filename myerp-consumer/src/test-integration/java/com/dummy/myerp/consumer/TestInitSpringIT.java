package com.dummy.myerp.consumer;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class TestInitSpringIT extends ConsumerTestCase {

    /**
     * Constructeur.
     */
    public TestInitSpringIT() {
        super();
    }

    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getDaoProxy());
    }
}
