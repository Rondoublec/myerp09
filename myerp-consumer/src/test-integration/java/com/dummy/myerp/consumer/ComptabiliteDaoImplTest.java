package com.dummy.myerp.consumer;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
    public void check_ListEcritureComptableList(){
        // arrange
        // act
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        // assert
        Assert.assertTrue(ecritureComptableList.size() > 0);
    }
    @Test
    public void check_GetEcritureComptableFound() throws NotFoundException {
        // arrange
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        EcritureComptable ecritureComptable = ecritureComptableList.get(0);
        // act
        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptable(ecritureComptable.getId());
        // assert
        Assert.assertTrue(ecritureComptable.getReference().equals(rEcritureComptable.getReference()));
    }
    @Test
    public void check_GetEcritureComptableNotFound() throws NotFoundException {
        // arrange
        raisedException.expect(NotFoundException .class);
        raisedException.expectMessage("EcritureComptable non trouvée : id=99999");
        // act
        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptable(99999);
        // Assert : Si "EcritureComptable non trouvée : id=99999"
    }
    @Test
    public void check_GetEcritureComptableByRefFound() throws NotFoundException {
        // arrange
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        EcritureComptable ecritureComptable = ecritureComptableList.get(0);
        // act
        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(ecritureComptable.getReference());
        // assert
        Assert.assertTrue(ecritureComptable.getId().equals(rEcritureComptable.getId()));
    }
    @Test
    public void check_GetEcritureComptableByRefNotFound() throws NotFoundException {
        // arrange
        raisedException.expect(NotFoundException .class);
        raisedException.expectMessage("EcritureComptable non trouvée : reference=ZZ-1234/99999");
        // act
        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("ZZ-1234/99999");
        // Assert : Si "EcritureComptable non trouvée : reference=ZZ-1234/99999"
    }

    @Test
    public void check_UpdateEcritureComptable() throws NotFoundException {
        // arrange
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        EcritureComptable oldEcritureComptable = ecritureComptableList.get(0);
        EcritureComptable newEcritureComptable = oldEcritureComptable;
        newEcritureComptable.setLibelle(oldEcritureComptable.getLibelle()+"NEW");
        // act
        SpringRegistry.getDaoProxy().getComptabiliteDao().updateEcritureComptable(newEcritureComptable);
        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptable(oldEcritureComptable.getId());
        // assert
        Assert.assertTrue(newEcritureComptable.getLibelle().equals(rEcritureComptable.getLibelle()));
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

    @Test
    public void checkInsertEcritureComptableAndDelete() throws NotFoundException {
        // Arrange
        List<CompteComptable> compteComptables = SpringRegistry.getDaoProxy().getComptabiliteDao().getListCompteComptable();
        List<JournalComptable> journalComptableList = SpringRegistry.getDaoProxy().getComptabiliteDao().getListJournalComptable();
        JournalComptable journalComptable = journalComptableList.get(0);
        EcritureComptable ecritureComptable = new EcritureComptable();
        LigneEcritureComptable l1 = new LigneEcritureComptable();
        LigneEcritureComptable l2 = new LigneEcritureComptable();
        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("LIB_01");
        ecritureComptable.setJournal(journalComptable);
        ecritureComptable.setReference("ZZ-9999/99999");
        l1.setDebit(BigDecimal.valueOf(100));
        l1.setCompteComptable(compteComptables.get(0));
        l1.setLibelle("CPT_01");
        l2.setCredit(BigDecimal.valueOf(100));
        l2.setCompteComptable(compteComptables.get(1));
        l2.setLibelle("CPT_02");
        ecritureComptable.getListLigneEcriture().add(l1);
        ecritureComptable.getListLigneEcriture().add(l2);
        // Act
        SpringRegistry.getDaoProxy().getComptabiliteDao().insertEcritureComptable(ecritureComptable);
        // Assert : Si pas d'exception

        EcritureComptable rEcritureComptable = SpringRegistry.getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(ecritureComptable.getReference());
        SpringRegistry.getDaoProxy().getComptabiliteDao().deleteEcritureComptable(rEcritureComptable.getId());

    }

}
