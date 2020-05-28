package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {

    @Test
    public void test_getByNumero_WhenCompteComptableExist() {
        // Arrange
        List<CompteComptable> listCompteComptable = new ArrayList<>();
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setNumero(123);
        compteComptable.setLibelle("un, deux, trois");
        listCompteComptable.add(compteComptable);
        listCompteComptable.add(new CompteComptable(456,"quatre, cinq, six"));
        listCompteComptable.add(new CompteComptable(789,"sept, huit, neuf"));
        // Act
        CompteComptable rCompteCompatble = CompteComptable.getByNumero(listCompteComptable, 456);
        // Assert
        Assert.assertEquals("quatre, cinq, six", rCompteCompatble.getLibelle());
    }

    @Test
    public void test_getByNumero_WhenCompteComptableNotExist() {
        // Arrange
        List<CompteComptable> listCompteComptable = new ArrayList<>();
        listCompteComptable.add(new CompteComptable(123,"un, deux, trois"));
        listCompteComptable.add(new CompteComptable(456,"quatre, cinq, six"));
        listCompteComptable.add(new CompteComptable(789,"sept, huit, neuf"));
        // Act
        CompteComptable rCompteCompatble = CompteComptable.getByNumero(listCompteComptable, 111);
        // Assert
        Assert.assertNull(rCompteCompatble);
    }

}
