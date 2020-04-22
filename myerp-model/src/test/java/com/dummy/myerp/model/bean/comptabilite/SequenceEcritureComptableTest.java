package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceEcritureComptableTest {

	@Test
	public void testConstructeurSansParametresAvecSetters() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable();
		sequence.setAnnee(2020);
		sequence.setDerniereValeur(10000);

		// Assert
		assertEquals("Test of 'annee' setter and getter", Integer.valueOf(2020), sequence.getAnnee());
		assertEquals("Test of 'derniereValeur' setter and getter", Integer.valueOf(10000), sequence.getDerniereValeur());
		assertEquals("Test of empty constructor", "SequenceEcritureComptable{journalCode=null, annee=2020, derniereValeur=10000}", sequence.toString());
	}

	@Test
	public void testConstructeurAvec2parametres() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable(2030, 5000);

		// Assert
		assertEquals("Test of 'annee' setter and getter", Integer.valueOf(2030), sequence.getAnnee());
		assertEquals("Test of 'derniereValeur' setter and getter", Integer.valueOf(5000), sequence.getDerniereValeur());
		assertEquals("Test of empty constructor", "SequenceEcritureComptable{journalCode=null, annee=2030, derniereValeur=5000}", sequence.toString());
	}

	@Test
	public void testConstructeurAvec3parametres() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable("CD", 2030, 5000 );

		// Assert
		assertEquals("Test of 'annee' setter and getter", Integer.valueOf(2030), sequence.getAnnee());
		assertEquals("Test of 'derniereValeur' setter and getter", Integer.valueOf(5000), sequence.getDerniereValeur());
		assertEquals("Test of 'journalCode' setter and getter", "CD", sequence.getJournalCode());
		assertEquals("Test of empty constructor", "SequenceEcritureComptable{journalCode=CD, annee=2030, derniereValeur=5000}", sequence.toString());
	}

}
