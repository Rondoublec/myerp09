package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import static com.dummy.myerp.consumer.utils.Constants.NUMERO;
import static com.dummy.myerp.consumer.utils.Constants.LIBELLE;


/**
 * {@link RowMapper} de {@link CompteComptable}
 */
public class CompteComptableRM implements RowMapper<CompteComptable> {

    @Override
    public CompteComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        CompteComptable vBean = new CompteComptable();
        vBean.setNumero(pRS.getInt(NUMERO));
        vBean.setLibelle(pRS.getString(LIBELLE));

        return vBean;
    }
}
