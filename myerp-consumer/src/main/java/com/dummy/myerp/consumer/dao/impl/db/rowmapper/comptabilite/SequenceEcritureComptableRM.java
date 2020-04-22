package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dummy.myerp.consumer.utils.Constants.JOURNAL_CODE;
import static com.dummy.myerp.consumer.utils.Constants.DERNIERE_VALEUR;
import static com.dummy.myerp.consumer.utils.Constants.ANNEE;


public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {
    @Override
    public SequenceEcritureComptable mapRow(ResultSet resultSet, int i) throws SQLException {
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode(resultSet.getString(JOURNAL_CODE));
        sequenceEcritureComptable.setDerniereValeur(resultSet.getInt(DERNIERE_VALEUR));
        sequenceEcritureComptable.setAnnee(resultSet.getInt(ANNEE));
        return sequenceEcritureComptable;
    }
}
