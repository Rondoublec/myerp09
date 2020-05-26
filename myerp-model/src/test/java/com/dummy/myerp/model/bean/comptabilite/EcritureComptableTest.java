package com.dummy.myerp.model.bean.comptabilite;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }
    @Test
    public void test_SettersAndGetters() {
        // Arrange
        Integer VALEUR_ID = 1;
        JournalComptable JOURNAL_COMPTABLE = new JournalComptable("AC", "Achat");
        Date DATE_DU_JOUR = new Date();
        String LIBELLE = "Libelle";
        String REFERENCE = "AC-2020/00000";
        BigDecimal CENT = new BigDecimal(100.00);
        // Act
        EcritureComptable vEcriture = new EcritureComptable();
        vEcriture.setId(VALEUR_ID);
        vEcriture.setJournal(JOURNAL_COMPTABLE);
        vEcriture.setDate(DATE_DU_JOUR);
        vEcriture.setLibelle(LIBELLE);
        vEcriture.setReference(REFERENCE);

        LigneEcritureComptable vLigneEcritureComptable = new LigneEcritureComptable();
        CompteComptable vCompteComptable = new CompteComptable();
        vCompteComptable.setNumero(VALEUR_ID);
        vCompteComptable.setLibelle(LIBELLE);
        vLigneEcritureComptable.setCompteComptable(vCompteComptable);
        vLigneEcritureComptable.setLibelle(LIBELLE);
        vLigneEcritureComptable.setDebit(CENT);
        vLigneEcritureComptable.setCredit(CENT);

        // Assert
        assertEquals("Test de 'id' setter et getter", VALEUR_ID, vEcriture.getId());
        assertEquals("Test de 'JournalComptable' setter et getter", JOURNAL_COMPTABLE, vEcriture.getJournal());
        assertEquals("Test de 'Date' setter et getter", DATE_DU_JOUR, vEcriture.getDate());
        assertEquals("Test de 'Libelle' setter et getter", LIBELLE, vEcriture.getLibelle());
        assertEquals("Test de 'Reference' setter et getter", REFERENCE, vEcriture.getReference());

        assertEquals("Test de 'getCompteComptable' setter et getter", VALEUR_ID, vLigneEcritureComptable.getCompteComptable().getNumero());
        assertEquals("Test de 'getLibelle' setter et getter", LIBELLE, vLigneEcritureComptable.getLibelle());
        assertEquals("Test de 'getDebit' setter et getter", CENT, vLigneEcritureComptable.getDebit());
        assertEquals("Test de 'getCredit' setter et getter", CENT, vLigneEcritureComptable.getCredit());
    }

    @Test
    public void test_WhenIsEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void test_WhenIsNotEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "1"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void test_WhenIsEquilibreeWithNegativeValue() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "261"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "-40", "7"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, null, "-30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(3, "10", null));
        Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());
    }
}
