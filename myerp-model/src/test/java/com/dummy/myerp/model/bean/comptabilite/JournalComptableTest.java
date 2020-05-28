package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {

    @Test
    public void test_getByCode_toString_WhenJournalComptableExist() {
        // Arrange
        List<JournalComptable> listJournalComptable = new ArrayList<>();
        JournalComptable journalComptable = new JournalComptable();
        journalComptable.setCode("AA");
        journalComptable.setLibelle("Alpha");
        listJournalComptable.add(journalComptable);
        listJournalComptable.add(new JournalComptable("BB","Beta"));
        listJournalComptable.add(new JournalComptable("CC","Gama"));
        // Act
        JournalComptable rJournalComptable = JournalComptable.getByCode(listJournalComptable,"CC");
        // Assert
        Assert.assertEquals("Gama", rJournalComptable.getLibelle());
        Assert.assertEquals("JournalComptable{code='CC', libelle='Gama'}", rJournalComptable.toString());
    }

    @Test
    public void test_getByCode_toString_WhenJournalComptableNotExist() {
        // Arrange
        List<JournalComptable> listJournalComptable = new ArrayList<>();
        listJournalComptable.add(new JournalComptable("AA","Alpha"));
        listJournalComptable.add(new JournalComptable("BB","Beta"));
        listJournalComptable.add(new JournalComptable("CC","Gama"));
        // Act
        JournalComptable rJournalComptable = JournalComptable.getByCode(listJournalComptable,"DD");
        // Assert
        Assert.assertNull(rJournalComptable);
    }

}
