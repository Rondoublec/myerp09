package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.dao.impl.cache.CompteComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

import static com.dummy.myerp.consumer.utils.Constants.CREDIT;
import static com.dummy.myerp.consumer.utils.Constants.DEBIT;
import static com.dummy.myerp.consumer.utils.Constants.LIBELLE;


/**
 * {@link RowMapper} de {@link LigneEcritureComptable}
 */
public class LigneEcritureComptableRM implements RowMapper<LigneEcritureComptable> {

    /** CompteComptableDaoCache */
    private final CompteComptableDaoCache compteComptableDaoCache = new CompteComptableDaoCache();


    @Override
    public LigneEcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        LigneEcritureComptable vBean = new LigneEcritureComptable();
        vBean.setCompteComptable(compteComptableDaoCache.getByNumero(pRS.getObject("compte_comptable_numero",
                                                                                   Integer.class)));
        vBean.setCredit(pRS.getBigDecimal(CREDIT));
        vBean.setDebit(pRS.getBigDecimal(DEBIT));
        vBean.setLibelle(pRS.getString(LIBELLE));

        return vBean;
    }
}
