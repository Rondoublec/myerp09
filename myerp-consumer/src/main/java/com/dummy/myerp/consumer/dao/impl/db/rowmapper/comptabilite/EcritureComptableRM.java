package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;

import static com.dummy.myerp.consumer.utils.Constants.DATE;
import static com.dummy.myerp.consumer.utils.Constants.ID;
import static com.dummy.myerp.consumer.utils.Constants.JOURNAL_CODE;
import static com.dummy.myerp.consumer.utils.Constants.LIBELLE;
import static com.dummy.myerp.consumer.utils.Constants.REFERENCE;


/**
 * {@link RowMapper} de {@link EcritureComptable}
 */
public class EcritureComptableRM implements RowMapper<EcritureComptable> {

    /** JournalComptableDaoCache */
    private final JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();


    @Override
    public EcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        EcritureComptable vBean = new EcritureComptable();
        vBean.setId(pRS.getInt(ID));
        vBean.setJournal(journalComptableDaoCache.getByCode(pRS.getString(JOURNAL_CODE)));
        vBean.setReference(pRS.getString(REFERENCE));
        vBean.setDate(pRS.getDate(DATE));
        vBean.setLibelle(pRS.getString(LIBELLE));

        // Chargement des lignes d'écriture
        ConsumerHelper.getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vBean);

        return vBean;
    }
}
