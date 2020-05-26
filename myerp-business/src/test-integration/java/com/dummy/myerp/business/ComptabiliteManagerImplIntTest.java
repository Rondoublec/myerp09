package com.dummy.myerp.business;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;
import static org.junit.Assert.assertNotNull;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class ComptabiliteManagerImplIntTest extends BusinessTestCase {

    @Rule
    public ExpectedException raisedException = ExpectedException.none();

    /**
     * Constructeur.
     */
    public ComptabiliteManagerImplIntTest() {
        super();
    }


    @Test
    public void test_GetListEcritureComptable() throws FunctionalException {
        // Arrange
        // Act
        List<EcritureComptable> ecritureComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
        // assert
        Assert.assertTrue(ecritureComptableList.size() > 0);

    }

    /** RG6 : La référence d'une écriture comptable doit être unique,
     *  il n'est pas possible de créer plusieurs écritures ayant la même référence.
     *  */
    @Test
    public void checkRefUniqueEcritureReferenceRG6_ReferenceDoubleAvecIdNull() throws FunctionalException {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_6_01 : Une autre écriture comptable existe déjà avec la même référence.");
        List<CompteComptable> compteComptables = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListCompteComptable();
        List<JournalComptable> journalComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        JournalComptable journalComptable = journalComptableList.get(0);
        EcritureComptable ecritureComptable = new EcritureComptable();
        LigneEcritureComptable l1 = new LigneEcritureComptable();
        LigneEcritureComptable l2 = new LigneEcritureComptable();

        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Libellé test integ");
        ecritureComptable.setJournal(journalComptable);
        l1.setDebit(BigDecimal.valueOf(100));
        l1.setCompteComptable(compteComptables.get(0));
        l1.setLibelle("Facture Fournisseur Test integ");
        l2.setCredit(BigDecimal.valueOf(100));
        l2.setCompteComptable(compteComptables.get(4));
        l2.setLibelle("Paiement Banque Test integ");
        ecritureComptable.getListLigneEcriture().add(l1);
        ecritureComptable.getListLigneEcriture().add(l2);
        // Act
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(ecritureComptable);
        ecritureComptable.setLibelle("Libellé : " + ecritureComptable.getReference());
        SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
        ecritureComptable.setId(null);
        SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
        // Assert : Si "FunctionalException RG_Compta_6_01"

    }

    @Test
    public void checkRefUniqueEcritureReferenceRG6_ReferenceDoubleAvecIdDifferent() throws FunctionalException {
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_6_01 : Une autre écriture comptable existe déjà avec la même référence.");
        List<CompteComptable> compteComptables = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListCompteComptable();
        List<JournalComptable> journalComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        JournalComptable journalComptable = journalComptableList.get(0);
        EcritureComptable ecritureComptable = new EcritureComptable();
        LigneEcritureComptable l1 = new LigneEcritureComptable();
        LigneEcritureComptable l2 = new LigneEcritureComptable();

        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Libellé test integ");
        ecritureComptable.setJournal(journalComptable);
        l1.setDebit(BigDecimal.valueOf(100));
        l1.setCompteComptable(compteComptables.get(0));
        l1.setLibelle("Facture Fournisseur Test integ");
        l2.setCredit(BigDecimal.valueOf(100));
        l2.setCompteComptable(compteComptables.get(4));
        l2.setLibelle("Paiement Banque Test integ");
        ecritureComptable.getListLigneEcriture().add(l1);
        ecritureComptable.getListLigneEcriture().add(l2);
        // Act
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(ecritureComptable);
        ecritureComptable.setLibelle("Libellé : " + ecritureComptable.getReference());
        SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
        ecritureComptable.setId(ecritureComptable.getId()+1);
        SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
        // Assert : Si "FunctionalException RG_Compta_6_01"

    }

    @Test
    public void test_UpdateEcritureComptableAndDelete() throws FunctionalException {
        // Arrange
        final String LIBELLE = "Libellé ";
        final String MODIF = "mis à jour";
        List<CompteComptable> compteComptables = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListCompteComptable();
        List<JournalComptable> journalComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        JournalComptable journalComptable = journalComptableList.get(0);
        EcritureComptable ecritureComptable = new EcritureComptable();
        LigneEcritureComptable l1 = new LigneEcritureComptable();
        LigneEcritureComptable l2 = new LigneEcritureComptable();

        ecritureComptable.setDate(new Date());
        ecritureComptable.setLibelle("Libellé test integ");
        ecritureComptable.setJournal(journalComptable);
        l1.setDebit(BigDecimal.valueOf(100));
        l1.setCompteComptable(compteComptables.get(0));
        l1.setLibelle("Facture Fournisseur Test integ");
        l2.setCredit(BigDecimal.valueOf(100));
        l2.setCompteComptable(compteComptables.get(4));
        l2.setLibelle("Paiement Banque Test integ");
        ecritureComptable.getListLigneEcriture().add(l1);
        ecritureComptable.getListLigneEcriture().add(l2);
        // Act
        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(ecritureComptable);
        ecritureComptable.setLibelle(LIBELLE + ecritureComptable.getReference());
        SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
        ecritureComptable.setLibelle(LIBELLE + ecritureComptable.getReference() + MODIF);
        SpringRegistry.getBusinessProxy().getComptabiliteManager().updateEcritureComptable(ecritureComptable);

        // Nettoyage
        SpringRegistry.getBusinessProxy().getComptabiliteManager().deleteEcritureComptable(ecritureComptable.getId());

    }

}
