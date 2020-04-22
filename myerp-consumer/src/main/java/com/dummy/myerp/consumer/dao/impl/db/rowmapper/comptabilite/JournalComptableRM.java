package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

import static com.dummy.myerp.consumer.utils.Constants.CODE;
import static com.dummy.myerp.consumer.utils.Constants.LIBELLE;


/**
 * {@link RowMapper} de {@link JournalComptable}
 */
public class JournalComptableRM implements RowMapper<JournalComptable> {

    @Override
    public JournalComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        JournalComptable vBean = new JournalComptable();
        vBean.setCode(pRS.getString(CODE));
        vBean.setLibelle(pRS.getString(LIBELLE));

        return vBean;
    }
}
