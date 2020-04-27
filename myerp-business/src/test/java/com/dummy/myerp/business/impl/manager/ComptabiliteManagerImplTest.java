package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;


public class ComptabiliteManagerImplTest {

    @Rule
    public ExpectedException raisedException = ExpectedException.none();

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private EcritureComptable vEcritureComptable;

    @Before
    public void setUp() {
        vEcritureComptable = new EcritureComptable();
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {
        // Arrange
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00000");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(1),null, new BigDecimal(123),null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(2), null, null, new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si pas d'exception
    }

    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_1_01 : L'écriture comptable ne respecte pas les règles de gestion.");
        // Act
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si "FunctionalException RG_Compta_1_01"
    }

    @Test
    public void checkEcritureComptableUnitRG2() throws Exception {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_2_01 : L'écriture comptable n'est pas équilibrée.");
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00000");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(1), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(2), null, null, new BigDecimal(1234)));
        // Act
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si "FunctionalException RG_Compta_2_01"
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_3_01 : L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00000");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(1), null, new BigDecimal(0), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(
                new CompteComptable(1), null, new BigDecimal(0), null));
        // Act
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si "FunctionalException RG_Compta_3_01"
    }

    @Test
    public void checkEcritureComptableUnitRG5_DateVsReference() throws FunctionalException {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_5_01 : La date de l'écriture comptable n'est pas cohérente avec celle de sa référence.");
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00000");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
        // Act
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si "FunctionalException RG_Compta_5_01"
    }

    @Test
    public void checkEcritureComptableUnitRG5_JournalVsReference() throws FunctionalException {
        // Arrange
        raisedException.expect(FunctionalException.class);
        raisedException.expectMessage("RG_Compta_5_02 : Le code du journal de l'écriture comptable n'est pas cohérent avec celui de sa référence.");
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("VE-2020/00000");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
        // Act
        manager.checkEcritureComptableUnit(vEcritureComptable);
        // Assert : Si "FunctionalException RG_Compta_5_02"
    }

}

