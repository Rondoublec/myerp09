package com.dummy.myerp.consumer;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ComptabiliteDaoImplTest extends ConsumerTestCase{

    @Rule
    public ExpectedException raisedException = ExpectedException.none();

    /**
     * Constructeur.
     */
    public ComptabiliteDaoImplTest() {
        super();
    }

    @Test
    public void check_ListCompteComptable() {
        // arrange
        // act
        List<CompteComptable> compteComptablesList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListCompteComptable();
        // assert
        Assert.assertTrue(compteComptablesList.size() > 0);
    }

    @Test
    public void check_ListJournalComptableList() {
        // arrange
        // act
        List<JournalComptable> journalComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        // assert
        Assert.assertTrue(journalComptableList.size() > 0);
    }
    @Test
    public void check_ListEcritureComptableList() {
        // arrange
        // act
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        // assert
        Assert.assertTrue(ecritureComptableList.size() > 0);
    }

    @Test
    public void check_getDerniereValeurSequenceEcriture() {
        // arrange
        List<JournalComptable> journalComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        String journalCode = journalComptableList.get(1).getCode();
        Calendar calendrier = GregorianCalendar.getInstance();
        int anneeEcriture = calendrier.get(Calendar.YEAR);
        Integer derniereValeur = SpringRegistry.getDaoProxy().getComptabiliteDao().getDerniereValeurSequenceEcriture(journalCode, anneeEcriture);
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, anneeEcriture, 1);
        if (derniereValeur == null) {
            derniereValeur = 1;
            sequenceEcritureComptable.setDerniereValeur(derniereValeur);
            getDaoProxy().getComptabiliteDao().insertSequenceEcriture(sequenceEcritureComptable);
        } else {
            derniereValeur = 1;
            sequenceEcritureComptable.setDerniereValeur(derniereValeur);
            getDaoProxy().getComptabiliteDao().updateSequenceEcriture(sequenceEcritureComptable);
        }
        // act
        Integer nouvelleValeur = SpringRegistry.getDaoProxy().getComptabiliteDao().getDerniereValeurSequenceEcriture(journalCode, anneeEcriture);
        // assert
        Assert.assertEquals(derniereValeur, nouvelleValeur);
        getDaoProxy().getComptabiliteDao().deleteSequenceEcriture(sequenceEcritureComptable);
    }

    @Test
    public void insertSequenceEcriture() {
        // arrange
        List<JournalComptable> journalComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        String journalCode = journalComptableList.get(1).getCode();
        Calendar calendrier = GregorianCalendar.getInstance();
        int anneeEcriture = calendrier.get(Calendar.YEAR)+1;
        Integer derniereValeur = 1;
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, anneeEcriture, derniereValeur);
        getDaoProxy().getComptabiliteDao().insertSequenceEcriture(sequenceEcritureComptable);
        // act
        Integer nouvelleValeur = SpringRegistry.getDaoProxy().getComptabiliteDao().getDerniereValeurSequenceEcriture(journalCode, anneeEcriture);
        // assert
        Assert.assertEquals(derniereValeur, nouvelleValeur);
        getDaoProxy().getComptabiliteDao().deleteSequenceEcriture(sequenceEcritureComptable);
    }

    @Test
    public void check_updateSequenceEcriture() {
        // arrange
        List<JournalComptable> journalComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        String journalCode = journalComptableList.get(1).getCode();
        Calendar calendrier = GregorianCalendar.getInstance();
        int anneeEcriture = calendrier.get(Calendar.YEAR)+1;
        Integer derniereValeur = 1;
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journalCode, anneeEcriture, derniereValeur);
        getDaoProxy().getComptabiliteDao().insertSequenceEcriture(sequenceEcritureComptable);
        derniereValeur = 2;
        sequenceEcritureComptable.setDerniereValeur(derniereValeur);
        getDaoProxy().getComptabiliteDao().updateSequenceEcriture(sequenceEcritureComptable);
        // act
        Integer nouvelleValeur = SpringRegistry.getDaoProxy().getComptabiliteDao().getDerniereValeurSequenceEcriture(journalCode, anneeEcriture);
        // assert
        Assert.assertEquals(derniereValeur, nouvelleValeur);
        getDaoProxy().getComptabiliteDao().deleteSequenceEcriture(sequenceEcritureComptable);
    }

}
