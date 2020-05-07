package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceEcritureComptableTest {

	@Test
	public void testConstructeurSansParametresAvecSetters() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable();
		sequence.setJournalCode("AA");
		sequence.setAnnee(2020);
		sequence.setDerniereValeur(10001);

		// Assert
		assertEquals("Test de 'annee' setter et getter", Integer.valueOf(2020), sequence.getAnnee());
		assertEquals("Test de 'derniereValeur' setter et getter", Integer.valueOf(10001), sequence.getDerniereValeur());
		assertEquals("Test de empty constructor", "SequenceEcritureComptable{journalCode=AA, annee=2020, derniereValeur=10001}", sequence.toString());
	}

	@Test
	public void testConstructeurAvec2parametres() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable(2022, 20002);

		// Assert
		assertEquals("Test de 'annee' setter et getter", Integer.valueOf(2022), sequence.getAnnee());
		assertEquals("Test de 'derniereValeur' setter et getter", Integer.valueOf(20002), sequence.getDerniereValeur());
		assertEquals("Test de empty constructor", "SequenceEcritureComptable{journalCode=null, annee=2022, derniereValeur=20002}", sequence.toString());
	}

	@Test
	public void testConstructeurAvec3parametres() {
		// Act
		SequenceEcritureComptable sequence = new SequenceEcritureComptable("AC", 2023, 30003 );

		// Assert
		assertEquals("Test de 'annee' setter et getter", Integer.valueOf(2023), sequence.getAnnee());
		assertEquals("Test de 'derniereValeur' setter et getter", Integer.valueOf(30003), sequence.getDerniereValeur());
		assertEquals("Test de 'journalCode' setter et getter", "AC", sequence.getJournalCode());
		assertEquals("Test de empty constructor", "SequenceEcritureComptable{journalCode=AC, annee=2023, derniereValeur=30003}", sequence.toString());
	}

}
